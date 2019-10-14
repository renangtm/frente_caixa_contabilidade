package br.com.empresa;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.base.Service;

public class ParametrosEmissaoService implements Service<ParametrosEmissao>{

	private EntityManager et;
	
	public ParametrosEmissaoService(EntityManager et) {
		
		this.et = et;
		
	}
	
	@Override
	public int getCount(String filtro) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ParametrosEmissao> getElementos(int x1, int x2, String filtro, String ordem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParametrosEmissao getPeloCodigo(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lixeira(ParametrosEmissao obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ParametrosEmissao merge(ParametrosEmissao obj) {
		
		obj.setEmpresa(et.merge(obj.getEmpresa()));
		
		if(obj.getId() == 0) {
			
			et.persist(obj);
			
		}else {
			
			ParametrosEmissao pa = et.merge(obj);
			pa.getEmpresa().setParametrosEmissao(pa);
			return pa;
			
		}
		
		return obj;
		
	}

}
