package br.com.pessoa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.base.Service;
import br.com.empresa.Empresa;

public class PessoaService implements Service<Pessoa> {

	private EntityManager et;

	private Empresa empresa;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public PessoaService(EntityManager et) {

		this.et = et;

	}

	@Override
	public int getCount(String filtro) {

		if (this.empresa == null) {

			throw new RuntimeException("Sem empresa");

		}

		Query q = this.et.createQuery(
				"SELECT COUNT(*) FROM Pessoa p WHERE p.empresa.id = :empresa AND (p.nome LIKE :nome OR p.endereco.cidade.nome LIKE :cidade)");
		q.setParameter("empresa", this.empresa.getId());
		q.setParameter("nome", "%" + filtro + "%");
		q.setParameter("cidade", "%" + filtro + "%");

		return Integer.parseInt(q.getResultList().get(0) + "");

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> getElementos(int x1, int x2, String filtro, String ordem) {

		if (this.empresa == null) {

			throw new RuntimeException("Sem empresa");

		}

		ordem = ordem.replaceAll("\\{\\{et\\}\\}", "p");

		Query q = this.et.createQuery(
				"SELECT p FROM Pessoa p WHERE p.empresa.id = :empresa AND (p.nome LIKE :nome OR p.endereco.cidade.nome LIKE :cidade)"
						+ (!ordem.isEmpty() ? " ORDER BY " + ordem : ""));
		q.setParameter("empresa", this.empresa.getId());
		q.setParameter("nome", "%" + filtro + "%");
		q.setParameter("cidade", "%" + filtro + "%");

		q.setFirstResult(x1);
		q.setMaxResults(x2 - x1);

		return (List<Pessoa>) (List<?>) q.getResultList();

	}

	@Override
	public Pessoa getPeloCodigo(String str) {
		// TODO Auto-generated method stub
		return this.et.find(Pessoa.class, Integer.parseInt(str));
	}

	@Override
	public void lixeira(Pessoa obj) {
		
	}

	@Override
	public Pessoa merge(Pessoa obj) {
		
		Pessoa pessoa = obj;
		
		if(obj.getId() == 0) {
			
			et.persist(pessoa);
			
		}else {
			
			pessoa = et.merge(pessoa);
			
		}
		
		return pessoa;
		
	}

}
