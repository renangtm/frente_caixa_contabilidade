package br.com.cliente;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.base.Service;

public class ClienteService implements Service<Cliente>{

	private EntityManager et;
	
	public ClienteService(EntityManager et) {
		
		this.et = et;
		
	}
	
	@Override
	public int getCount(String filtro) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Cliente> getElementos(int x1, int x2, String filtro, String ordem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cliente getPeloCodigo(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lixeira(Cliente obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cliente merge(Cliente obj) {
		
		obj.setPessoa(et.merge(obj.getPessoa()));
		
		if(obj.getId() == 0) {
			
			et.persist(obj);
			return obj;
			
		}
		
		Cliente cliente = et.merge(obj);
		
		cliente.getPessoa().setCliente(cliente);
		
		return cliente;
		
	}

	
	
}
