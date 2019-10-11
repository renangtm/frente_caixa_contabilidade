package br.com.produto;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;


import br.com.base.Service;

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
		
	}

	@Override
	public Categoria merge(Categoria obj) {
		
		Categoria cat = obj;
		
		if(cat.getId() == 0) {
			
			et.persist(cat);
			
		}else {
			
			cat = et.merge(cat);
			
		}
		
		cat.setTabelaAlicota(et.merge(cat.getTabelaAlicota()));
		cat.setTabelaCfop(et.merge(cat.getTabelaCfop()));
		
		cat.setCofins(et.merge(cat.getCofins()));
		
		cat.setIcms(et.merge(cat.getIcms()));
		
		cat.setPis(et.merge(cat.getPis()));
		
		
		return cat;
		
	}

}
