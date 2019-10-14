package br.com.usuario;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.base.ET;
import br.com.base.Service;
import br.com.empresa.Empresa;

public class UsuarioService implements Service<Usuario> {

	private EntityManager et;

	public UsuarioService(EntityManager et) {

		this.et = et;

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		EntityManager et = ET.nova();

		Usuario u = et.find(Usuario.class, 1);

		UsuarioService us = new UsuarioService(et);

		System.out.println(us.getUsuario("caixa", "caixa").getPf().getNome());
	}

	public Usuario getPorSenhaPermissao(String senha, TipoPermissao tp, Empresa empresa) {

		Query q = et.createQuery(
				"SELECT u FROM Usuario u INNER JOIN Permissao p ON p.usuario=u WHERE u.senha = :senha AND p.tipo= :tipo AND u.pf.empresa = :empresa AND p.incluir=true AND p.alterar=true AND p.excluir=true AND p.consultar=true");
		q.setParameter("senha", senha);
		q.setParameter("tipo", tp);
		q.setParameter("empresa", empresa);

		if (q.getResultList().size() > 0) {

			return (Usuario) q.getResultList().get(0);

		}

		return null;

	}

	@SuppressWarnings("unchecked")
	public Usuario getUsuario(String login, String senha) {

		List<Usuario> usuarios = (List<Usuario>) (List<?>) et
				.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :usuario AND u.senha = :senha")
				.setParameter("usuario", login).setParameter("senha", senha).getResultList();

		if (usuarios.size() == 0) {

			return null;

		}

		return (Usuario) usuarios.get(0);

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

		t.setPf(et.merge(t.getPf()));

		if (t.getId() == 0) {

			et.persist(t);

		} else {

			t = et.merge(t);
			t.getPf().setUsuario(t);

		}

		return t;

	}

}
