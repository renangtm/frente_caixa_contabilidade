package br.com.venda;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import br.com.base.Service;
import br.com.produto.Estoque;

public class VendaService implements Service<Venda>{

	private EntityManager et;

	public VendaService(EntityManager et) {

		this.et = et;
		
	}

	public boolean verificacaoPersistencia(Venda venda) {
		
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
	
	@Override
	public Venda merge(Venda venda) {
		
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
				
				throw new RuntimeException(ex);

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

	@Override
	public int getCount(String filtro) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Venda> getElementos(int x1, int x2, String filtro, String ordem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Venda getPeloCodigo(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lixeira(Venda obj) {
		// TODO Auto-generated method stub
		
	}

	

}
