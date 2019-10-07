package br.com.venda;

import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import br.com.produto.Estoque;

public class VendaService {

	private EntityManager et;

	public VendaService(EntityManager et) {

		this.et = et;
		
	}

	public boolean verificacaoPersistencia(Venda venda) {
		
		venda.setProdutos(venda.getProdutos().stream().map(this::pmv).collect(Collectors.toList()));
		
		for (ProdutoVenda pv : venda.getProdutos()) {
			
			double meta = (venda.getStatus().isDisponivel() ? pv.getQuantidade() : 0);

			double infres = meta - pv.reservaInfluenciada;

			Estoque estoque = pv.getProduto().getEstoque();
			et.refresh(estoque);

			double influenciaRealRes = pv.tipoQuantidade.para(estoque.getTipo(), pv.getProduto(), infres);

			if(estoque.getDisponivel()<influenciaRealRes) {
				
				return false;
				
			}

		}
		
		return true;
		
	}
	
	private ProdutoVenda pmv(ProdutoVenda pn) {
		
		if(pn.getId() == 0) {
			
			et.persist(pn);
			return pn;
			
		}
		
		return et.merge(pn);
		
	}
	
	public synchronized Venda persistirVenda(Venda venda) throws SemEstoqueException {

		venda.setProdutos(venda.getProdutos().stream().map(this::pmv).collect(Collectors.toList()));
		
		for (ProdutoVenda pv : venda.getProdutos()) {
			
			double meta = (venda.getStatus().isDisponivel() ? pv.getQuantidade() : 0);

			double infres = meta - pv.reservaInfluenciada;

			Estoque estoque = pv.getProduto().getEstoque();
			et.refresh(estoque);

			double influenciaRealRes = pv.tipoQuantidade.para(estoque.getTipo(), pv.getProduto(), infres);

			try {

				estoque.rmvDisponivel(influenciaRealRes);
				
				pv.reservaInfluenciada = meta;
				
			} catch (Exception ex) {
				
				throw new SemEstoqueException(pv.getProduto());

			}

		}
		
		
		venda.getNotas().forEach(et::merge);
		
		venda.getProdutos().forEach(x->{
			
			if(x.getQuantidade() == 0) {
				
				et.remove(x);
				
			}
			
		});
		
		venda.getProdutos().removeIf(x->x.getQuantidade()==0);
		
		if (venda.getId() == 0) {

			et.persist(venda);

		} else {

			return et.merge(venda);

		}

		return venda;
		
	}

}
