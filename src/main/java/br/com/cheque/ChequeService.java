package br.com.cheque;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.base.Service;
import br.com.empresa.Empresa;

public class ChequeService implements Service<Cheque> {

	private EntityManager et;

	private Empresa empresa;

	public EntityManager getEt() {
		return et;
	}

	public void setEt(EntityManager et) {
		this.et = et;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public ChequeService(EntityManager et) {

		this.et = et;

	}

	@Override
	public int getCount(String filtro) {

		if (empresa == null) {

			throw new RuntimeException("Nao existe empresa");

		}

		Query q = et
				.createQuery("SELECT COUNT(*) FROM Cheque c WHERE c.movimento.vencimento.nota.empresa = :empresa AND ("
						+ "c.numero LIKE :filtro OR c.pessoa.nome LIKE :filtro OR c.valor LIKE :filtro)");
		q.setParameter("empresa", this.empresa);
		q.setParameter("filtro", filtro);

		return Integer.parseInt(q.getResultList().get(0).toString());

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cheque> getElementos(int x1, int x2, String filtro, String ordem) {

		if (empresa == null) {

			throw new RuntimeException("Nao existe empresa");

		}

		ordem = ordem.replaceAll("\\{\\{et\\}\\}", "c");

		Query q = et.createQuery("SELECT c FROM Cheque c WHERE c.movimento.vencimento.nota.empresa = :empresa AND ("
				+ "c.numero LIKE :filtro OR c.pessoa.nome LIKE :filtro OR c.valor LIKE :filtro)"
				+ (!ordem.isEmpty() ? " ORDER BY " + ordem : ""));
		q.setParameter("empresa", this.empresa);
		q.setParameter("filtro", filtro);

		q.setFirstResult(x1);
		q.setMaxResults(x2 - x1);

		return (List<Cheque>) (List<Cheque>) q.getResultList();

	}

	@Override
	public Cheque getPeloCodigo(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lixeira(Cheque obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public Cheque merge(Cheque obj) {

		obj.setPessoa(et.merge(obj.getPessoa()));

		if (obj.getId() == 0) {

			et.persist(obj);
			return obj;

		}

		return et.merge(obj);

	}

}
