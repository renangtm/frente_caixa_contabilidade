package br.com.banco;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.base.Service;

public class FechamentoService implements Service<Fechamento>{
	
	private EntityManager et;
	
	public FechamentoService(EntityManager et) {
		
		this.et = et;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Fechamento> getPeloBanco(Banco banco){
		
		Query q = et.createQuery("SELECT f FROM Fechamento f WHERE f.banco = :banco");
		q.setParameter("banco", banco);
		
		return q.getResultList();
		
	}

	@Override
	public int getCount(String filtro) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Fechamento> getElementos(int x1, int x2, String filtro, String ordem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fechamento getPeloCodigo(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lixeira(Fechamento obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Fechamento merge(Fechamento f) {
		
		f.setBanco(et.merge(f.getBanco()));
		
		if(f.getId() == 0) {
			
			et.persist(f);
			return f;
			
		}
		
		return et.merge(f);
		
	}

}
