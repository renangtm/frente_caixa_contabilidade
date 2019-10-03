package br.com.afgtec.pessoa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;

import br.com.afgtec.base.Service;

public class PessoaJuridicaService implements Service<PessoaJuridica> {

	private EntityManager et;

	private Empresa empresa;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public PessoaJuridicaService(EntityManager et) {

		this.et = et;

	}

	@Override
	public int getCount(String filtro) {

		if (this.empresa == null) {

			throw new RuntimeException("Sem empresa");

		}

		Query q = this.et.createQuery(
				"SELECT COUNT(*) FROM PessoaJuridica p WHERE p.empresa.id = :empresa AND (p.cnpj LIKE :cnpj OR p.nome LIKE :nome OR p.endereco.cidade.nome LIKE :cidade OR p.inscricao_estadual LIKE :ie)");
		q.setParameter("empresa", this.empresa.getId());
		q.setParameter("cnpj", "%" + filtro + "%");
		q.setParameter("nome", "%" + filtro + "%");
		q.setParameter("cidade", "%" + filtro + "%");
		q.setParameter("ie", "%" + filtro + "%");

		return Integer.parseInt(q.getResultList().get(0) + "");

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PessoaJuridica> getElementos(int x1, int x2, String filtro, String ordem) {

		if (this.empresa == null) {

			throw new RuntimeException("Sem empresa");

		}

		ordem = ordem.replaceAll("\\{\\{et\\}\\}", "p");

		Query q = this.et.createQuery(
				"SELECT p FROM PessoaJuridica p WHERE p.empresa.id = :empresa AND (p.cnpj LIKE :cnpj OR p.nome LIKE :nome OR p.endereco.cidade.nome LIKE :cidade OR p.inscricao_estadual LIKE :ie)"
						+ (!ordem.isEmpty() ? " ORDER BY " + ordem : ""));
		q.setParameter("empresa", this.empresa.getId());
		q.setParameter("cnpj", "%" + filtro + "%");
		q.setParameter("nome", "%" + filtro + "%");
		q.setParameter("cidade", "%" + filtro + "%");
		q.setParameter("ie", "%" + filtro + "%");

		q.setFirstResult(x1);
		q.setMaxResults(x2 - x1);

		return (List<PessoaJuridica>) (List<?>) q.getResultList();

	}
	
	public PessoaJuridica getPeloCNPJ(String cnpj) {

		if (this.empresa == null) {

			throw new RuntimeException("Sem empresa");

		}

		Query q = this.et
				.createQuery("SELECT p FROM PessoaJuridica p WHERE p.empresa.id = :empresa AND p.cnpj LIKE :cnpj");
		q.setParameter("empresa", this.empresa.getId());
		q.setParameter("cnpj", "%" + cnpj + "%");

		@SuppressWarnings("unchecked")
		List<PessoaJuridica> t = (List<PessoaJuridica>) (List<?>) q.getResultList();

		if (t.size() > 0) {

			return t.get(0);

		}

		return null;

	}

	@Override
	public PessoaJuridica getPeloCodigo(String str) {
		// TODO Auto-generated method stub
		return this.et.find(PessoaJuridica.class, Integer.parseInt(str));
	}

	@Override
	public void lixeira(PessoaJuridica obj) {
		// TODO Auto-generated method stub
		((Session)this.et.getDelegate()).evict(obj);
	}

}
