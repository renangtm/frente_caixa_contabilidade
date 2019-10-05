package br.com.usuario;

import java.util.List;

import javax.persistence.EntityManager;

public class UsuarioService {

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
	
}
