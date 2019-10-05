package br.com.afgtec.transportadora;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;

import br.com.afgtec.base.Service;
import br.com.empresa.Empresa;
import br.com.transportadora.Transportadora;

public class TransportadoraService implements Service<Transportadora> {

	private EntityManager et;

	private Empresa empresa;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public TransportadoraService(EntityManager et) {

		this.et = et;

	}

	@Override
	public int getCount(String filtro) {

		if (this.empresa == null) {

			throw new RuntimeException("Sem empresa");

		}

		Query q = this.et.createQuery(
				"SELECT COUNT(*) FROM Transportadora p WHERE p.pj.empresa.id = :empresa AND (p.pj.cnpj LIKE :cnpj OR p.pj.nome LIKE :nome OR p.pj.endereco.cidade.nome LIKE :cidade OR p.pj.inscricao_estadual LIKE :ie)");
		q.setParameter("empresa", this.empresa.getId());
		q.setParameter("cnpj", "%" + filtro + "%");
		q.setParameter("nome", "%" + filtro + "%");
		q.setParameter("cidade", "%" + filtro + "%");
		q.setParameter("ie", "%" + filtro + "%");

		return Integer.parseInt(q.getResultList().get(0) + "");

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transportadora> getElementos(int x1, int x2, String filtro, String ordem) {

		if (this.empresa == null) {

			throw new RuntimeException("Sem empresa");

		}

		ordem = ordem.replaceAll("\\{\\{et\\}\\}", "p");

		Query q = this.et.createQuery(
				"SELECT p FROM Transportadora p WHERE p.pj.empresa.id = :empresa AND (p.pj.cnpj LIKE :cnpj OR p.pj.nome LIKE :nome OR p.pj.endereco.cidade.nome LIKE :cidade OR p.pj.inscricao_estadual LIKE :ie)"
						+ (!ordem.isEmpty() ? " ORDER BY " + ordem : ""));
		q.setParameter("empresa", this.empresa.getId());
		q.setParameter("cnpj", "%" + filtro + "%");
		q.setParameter("nome", "%" + filtro + "%");
		q.setParameter("cidade", "%" + filtro + "%");
		q.setParameter("ie", "%" + filtro + "%");

		q.setFirstResult(x1);
		q.setMaxResults(x2 - x1);

		return (List<Transportadora>) (List<?>) q.getResultList();

	}

	public Transportadora getPeloCNPJ(String cnpj) {

		if (this.empresa == null) {

			throw new RuntimeException("Sem empresa");

		}

		Query q = this.et.createQuery(
				"SELECT p FROM Transportadora p WHERE p.pj.empresa.id = :empresa AND p.pj.cnpj LIKE :cnpj");
		q.setParameter("empresa", this.empresa.getId());
		q.setParameter("cnpj", "%" + cnpj + "%");

		@SuppressWarnings("unchecked")
		List<Transportadora> t = (List<Transportadora>) (List<?>) q.getResultList();

		if (t.size() > 0) {

			return t.get(0);

		}

		return null;

	}

	@Override
	public Transportadora getPeloCodigo(String str) {

		if (this.empresa == null) {

			throw new RuntimeException("Sem empresa");

		}

		Query q = this.et.createQuery(
				"SELECT p FROM Transportadora p WHERE p.pj.empresa.id = :empresa AND p.pj.codigo = :codigo");
		q.setParameter("empresa", this.empresa.getId());
		q.setParameter("codigo", Integer.parseInt(str));

		@SuppressWarnings("unchecked")
		List<Transportadora> t = (List<Transportadora>) (List<?>) q.getResultList();

		if (t.size() > 0) {

			return t.get(0);

		}

		return null;

	}

	@Override
	public void lixeira(Transportadora obj) {
		// TODO Auto-generated method stub
		((Session)this.et.getDelegate()).evict(obj);
	}

}
