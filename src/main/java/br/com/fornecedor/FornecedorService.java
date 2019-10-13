package br.com.fornecedor;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.base.Service;

public class FornecedorService implements Service<Fornecedor>{

	private EntityManager et;
	
	public FornecedorService(EntityManager et) {
		
		this.et = et;
		
	}
	
	@Override
	public int getCount(String filtro) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Fornecedor> getElementos(int x1, int x2, String filtro, String ordem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fornecedor getPeloCodigo(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lixeira(Fornecedor obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Fornecedor merge(Fornecedor obj) {
		
		obj.setPj(et.merge(obj.getPJ()));
		
		if(obj.getId() == 0) {
			
			et.persist(obj);
			
			return obj;
			
		}
		
		Fornecedor f = et.merge(obj);
		obj.getPJ().setFornecedor(f);
		
		return f;
		
	}

}
