package br.com.empresa;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import br.com.base.Service;

public class EmpresaService implements Service<Empresa>{
	
	
	private EntityManager et;
	
	public EmpresaService(EntityManager et) {
		
		this.et = et;
		
	}

	@Override
	public int getCount(String filtro) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Empresa> getElementos(int x1, int x2, String filtro, String ordem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Empresa getPeloCodigo(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lixeira(Empresa obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Empresa merge(Empresa obj) {
		
		if(obj.getLogo() != null)
			obj.setLogo(et.merge(obj.getLogo()));
		
		
		obj.setPadroesCodigo(obj.getPadroesCodigo().stream().map(p->et.merge(p)).collect(Collectors.toList()));
		
		if(obj.getParametrosEmissao() != null)
			obj.setParametrosEmissao(et.merge(obj.getParametrosEmissao()));
		
		
		if(obj.getId() == 0) {
			
			et.persist(obj);
			
			return obj;
			
		}else {
			
			Empresa emp = et.merge(obj);
			
			obj.getPadroesCodigo().forEach(p->p.setEmpresa(emp));
			
			if(emp.getParametrosEmissao() != null)
				emp.getParametrosEmissao().setEmpresa(emp);
			
			obj.getPj().setEmpresa(emp);
			
			return emp;
			
		}
		
	}

}
