package br.com.usuario;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.base.Service;

public class UsuarioService implements Service<Usuario>{

	private EntityManager et;
	
	public UsuarioService(EntityManager et) {
		
		this.et = et;
		
	}
	
	@SuppressWarnings("unchecked")
	public Usuario getUsuario(String login,String senha) {
		
		List<Usuario> usuarios = (List<Usuario>)(List<?>)et.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :usuario AND u.senha = :senha")
		.setParameter("usuario", login)
		.setParameter("senha", senha)
		.getResultList();
		
		
		if(usuarios.size() == 0) {
			
			return null;
			
		}
		
		return (Usuario)usuarios.get(0);
		
	}

	@Override
	public int getCount(String filtro) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Usuario> getElementos(int x1, int x2, String filtro, String ordem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario getPeloCodigo(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lixeira(Usuario obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuario merge(Usuario obj) {
		
		Usuario t = obj;
		
		if(t.getId() == 0) {
			
			et.persist(t);
			
		}else {
			
			t = et.merge(t);
			
		}
		
		t.setPf(et.merge(t.getPf()));
		t.getPf().setUsuario(t);
		
		return t;
		
	}
	
}
