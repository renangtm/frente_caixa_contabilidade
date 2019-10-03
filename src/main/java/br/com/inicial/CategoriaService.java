package br.com.inicial;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;

import br.com.afgtec.base.Service;
import br.com.afgtec.produto.Categoria;

public class CategoriaService implements Service<Categoria> {

	private EntityManager et;

	public CategoriaService(EntityManager et) {

		this.et = et;

	}

	@Override
	public int getCount(String filtro) {

		Query q = this.et.createQuery("SELECT COUNT(*) FROM Categoria c WHERE c.nome LIKE :nome");
		q.setParameter("nome", "%" + filtro + "%");

		return Integer.parseInt(q.getResultList().get(0).toString() + "");

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Categoria> getElementos(int x1, int x2, String filtro, String ordem) {

		ordem = ordem.replaceAll("\\{\\{et\\}\\}", "c");
		
		Query q = this.et.createQuery(
				"SELECT c FROM Categoria c WHERE c.nome LIKE :nome" + (!ordem.equals("") ? " ORDER BY " + ordem : ""));
		q.setParameter("nome", "%" + filtro + "%");
		q.setFirstResult(x1);
		q.setMaxResults(x2 - x1);

		return (List<Categoria>) (List<?>) q.getResultList();

	}

	@Override
	public Categoria getPeloCodigo(String str) {

		return this.et.find(Categoria.class, Integer.parseInt(str));

	}

	@Override
	public void lixeira(Categoria obj) {
		// TODO Auto-generated method stub
		((Session)this.et.getDelegate()).evict(obj);
	}

}
