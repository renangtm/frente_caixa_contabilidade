package br.com.afgtec.usuario;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.afgtec.base.ET;
import br.com.afgtec.pessoa.Usuario;

public class UsuarioService {

	
	@SuppressWarnings("unchecked")
	public Usuario getUsuario(String login,String senha) {
		
		EntityManager et = ET.nova();
		
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
