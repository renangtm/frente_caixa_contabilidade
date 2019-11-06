package br.com.produto;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.base.Service;
import br.com.empresa.Empresa;

public class BandeiraCartaoService implements Service<BandeiraCartao> {

	private EntityManager et;


	public BandeiraCartaoService(EntityManager et) {

		this.et = et;

	}
	
	private Empresa empresa;

	public void setEmpresa(Empresa emp) {

		this.empresa = emp;

	}

	@Override
	public int getCount(String filtro) {

		if (this.empresa == null) {

			throw new RuntimeException("Service sem empresa");

		}

		Query qr = et
				.createQuery("SELECT COUNT(*) FROM BandeiraCartao p WHERE p.empresa.id = :empresa AND (p.nome LIKE :nome)");
		qr.setParameter("empresa", this.empresa.getId());
		qr.setParameter("nome", "%" + filtro + "%");

		@SuppressWarnings("unchecked")
		List<Long> lista = (List<Long>) (List<?>) qr.getResultList();

		return Integer.parseInt(lista.get(0) + "");

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BandeiraCartao> getElementos(int x1, int x2, String filtro, String ordem) {

		if (this.empresa == null) {

			throw new RuntimeException("Service sem empresa");

		}

		ordem = ordem.replaceAll("\\{\\{et\\}\\}", "p");

		Query qr = et.createQuery("SELECT p FROM BandeiraCartao p WHERE p.empresa.id = :empresa AND (p.nome LIKE :nome)"
				+ (ordem != "" ? " ORDER BY " + ordem : ""));
		qr.setParameter("empresa", this.empresa.getId());
		qr.setParameter("nome", "%" + filtro + "%");
		qr.setFirstResult(x1);
		qr.setMaxResults(x2 - x1);

		return (List<BandeiraCartao>) (List<?>) qr.getResultList();

	}

	@Override
	public BandeiraCartao getPeloCodigo(String str) {
		// TODO Auto-generated method stub
		
		Query q = et.createQuery("SELECT p FROM Tipo p WHERE p.id = :id");
		q.setParameter("id", Integer.parseInt(str));
		
		return (BandeiraCartao) q.getResultList().get(0);
		
		
	}

	@Override
	public void lixeira(BandeiraCartao obj) {

	}

	@Override
	public BandeiraCartao merge(BandeiraCartao obj) {

		BandeiraCartao t = obj;

		t.setEmpresa(et.merge(t.getEmpresa()));
		
		if (t.getId() == 0) {

			et.persist(t);

		} else {

			t = et.merge(t);

		}

		return t;

	}

}
