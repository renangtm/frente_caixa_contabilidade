package br.com.codigo_barra;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.base.Service;

public class PadraoCodigoService implements Service<PadraoCodigo>{

	private EntityManager et;
	
	public PadraoCodigoService(EntityManager et) {
		
		this.et = et;
		
	}
	
	@Override
	public int getCount(String filtro) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<PadraoCodigo> getElementos(int x1, int x2, String filtro, String ordem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PadraoCodigo getPeloCodigo(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lixeira(PadraoCodigo obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PadraoCodigo merge(PadraoCodigo obj) {
		
		PadraoCodigo p  = obj;
		
		
		if(p.getId() == 0) {
			
			et.persist(p);
			
		}else {
			
			p = et.merge(p);
			
		}
		
		return p;
		
	}

}
