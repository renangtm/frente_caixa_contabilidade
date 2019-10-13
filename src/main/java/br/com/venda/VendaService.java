package br.com.venda;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.base.Service;
import br.com.produto.Estoque;

public class VendaService implements Service<Venda>{

	private EntityManager et;

	public VendaService(EntityManager et) {

		this.et = et;
		
	}
	
	public Venda getVendaPorComanda(String comanda) {
		
		Query q = et.createQuery("SELECT v FROM Venda v WHERE v.comandaRepresentada = :comanda AND v.status = :status");
		q.setParameter("comanda", comanda);
		q.setParameter("status", StatusVenda.EM_EXECUCAO);
		
		if(q.getResultList().size()>0) {
			
			return (Venda)q.getResultList().get(0);
			
		}
		
		return null;
		
	}

	public boolean verificacaoPersistencia(Venda venda) {
		
		List<Integer> refreshed = new ArrayList<Integer>();
		
		for (ProdutoVenda pv : venda.getProdutos()) {
			
			double meta = (venda.getStatus().isDisponivel() ? pv.getQuantidade() : 0);

			double infres = meta - pv.reservaInfluenciada;

			Estoque estoque = pv.getProduto().getEstoque();
			
			if(!refreshed.contains(estoque.getId())) {
				
				et.refresh(estoque);
				refreshed.add(estoque.getId());
				
			}

			double influenciaRealRes = pv.tipoQuantidade.para(estoque.getTipo(), pv.getProduto(), infres);

			if(estoque.getDisponivel()<influenciaRealRes) {
				
				return false;
				
			}

		}
		
		return true;
		
	}
	
	
	@Override
	public Venda merge(Venda venda) {
	
		List<Integer> refreshed = new ArrayList<Integer>();
	
		for (ProdutoVenda pv : venda.getProdutos()) {
			
			double meta = (venda.getStatus().isDisponivel() ? pv.getQuantidade() : 0);

			double infres = meta - pv.reservaInfluenciada;

			Estoque estoque = pv.getProduto().getEstoque();
			
			if(!refreshed.contains(estoque.getId())) {
				
				et.refresh(estoque);
				refreshed.add(estoque.getId());
				
			}

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
