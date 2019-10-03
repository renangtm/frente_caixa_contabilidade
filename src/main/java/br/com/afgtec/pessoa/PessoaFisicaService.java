package br.com.afgtec.pessoa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;

import br.com.afgtec.base.Service;

public class PessoaFisicaService implements Service<PessoaFisica> {

	private EntityManager et;

	private Empresa empresa;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public PessoaFisicaService(EntityManager et) {

		this.et = et;

	}

	@Override
	public int getCount(String filtro) {

		if (this.empresa == null) {

			throw new RuntimeException("Sem empresa");

		}

		Query q = this.et.createQuery(
				"SELECT COUNT(*) FROM PessoaFisica p WHERE p.empresa.id = :empresa AND (p.cpf LIKE :cpf OR p.nome LIKE :nome OR p.endereco.cidade.nome LIKE :cidade OR p.rg LIKE :rg)");
		q.setParameter("empresa", this.empresa.getId());
		q.setParameter("cpf", "%" + filtro + "%");
		q.setParameter("nome", "%" + filtro + "%");
		q.setParameter("cidade", "%" + filtro + "%");
		q.setParameter("rg", "%" + filtro + "%");

		return Integer.parseInt(q.getResultList().get(0) + "");

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PessoaFisica> getElementos(int x1, int x2, String filtro, String ordem) {

		if (this.empresa == null) {

			throw new RuntimeException("Sem empresa");

		}

		ordem = ordem.replaceAll("\\{\\{et\\}\\}", "p");

		Query q = this.et.createQuery(
				"SELECT p FROM PessoaFisica p WHERE p.empresa.id = :empresa AND (p.cpf LIKE :cpf OR p.nome LIKE :nome OR p.endereco.cidade.nome LIKE :cidade OR p.rg LIKE :rg)"
						+ (!ordem.isEmpty() ? " ORDER BY " + ordem : ""));
		q.setParameter("empresa", this.empresa.getId());
		q.setParameter("cpf", "%" + filtro + "%");
		q.setParameter("nome", "%" + filtro + "%");
		q.setParameter("cidade", "%" + filtro + "%");
		q.setParameter("rg", "%" + filtro + "%");

		q.setFirstResult(x1);
		q.setMaxResults(x2 - x1);

		return (List<PessoaFisica>) (List<?>) q.getResultList();

	}

	@Override
	public PessoaFisica getPeloCodigo(String str) {
		// TODO Auto-generated method stub
		return this.et.find(PessoaFisica.class, Integer.parseInt(str));
	}

	@Override
	public void lixeira(PessoaFisica obj) {
		// TODO Auto-generated method stub
		((Session)this.et.getDelegate()).evict(obj);
	}

}
