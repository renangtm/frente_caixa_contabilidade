package br.com.afgtec.financeiro;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;

import br.com.afgtec.base.Service;
import br.com.afgtec.pessoa.Empresa;

public class BancoService implements Service<Banco> {

	private Empresa empresa;

	private EntityManager et;

	public void setEmpresa(Empresa emp) {

		this.empresa = emp;

	}

	public BancoService(EntityManager et) {

		this.et = et;

	}

	@Override
	public int getCount(String filtro) {

		Query qr = et.createQuery("SELECT COUNT(*) FROM Banco n WHERE "
				+ (this.empresa != null ? "n.pj.empresa.id = :empresa AND " : "") + " (n.pj.nome LIKE :nome_c)");

		if (this.empresa != null)
			qr.setParameter("empresa", this.empresa.getId());

		qr.setParameter("nome_c", "%" + filtro + "%");

		@SuppressWarnings("unchecked")
		List<Long> lista = (List<Long>) (List<?>) qr.getResultList();

		return Integer.parseInt(lista.get(0) + "");

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Banco> getElementos(int x1, int x2, String filtro, String ordem) {

		ordem = ordem.replaceAll("\\{\\{et\\}\\}", "n");

		Query qr = et.createQuery(
				"SELECT n FROM Banco n WHERE " + (this.empresa != null ? "n.pj.empresa.id = :empresa AND " : "")
						+ " (n.pj.nome LIKE :nome_c)" + (ordem != "" ? " ORDER BY " + ordem : ""));

		if (this.empresa != null)
			qr.setParameter("empresa", this.empresa.getId());

		qr.setParameter("nome_c", "%" + filtro + "%");

		qr.setFirstResult(x1);
		qr.setMaxResults(x2 - x1);

		return (List<Banco>) (List<?>) qr.getResultList();

	}

	@Override
	public Banco getPeloCodigo(String str) {

		@SuppressWarnings("unchecked")
		List<Banco> notas = (List<Banco>) (List<?>) et.createQuery("SELECT n FROM Banco n WHERE n.id = :codigo")
				.setParameter("codigo", Integer.parseInt(str)).getResultList();

		if (notas.size() == 0) {

			return null;

		}

		return (Banco) notas.get(0);

	}

	@Override
	public void lixeira(Banco obj) {
		// TODO Auto-generated method stub
		((Session)this.et.getDelegate()).evict(obj);
	}

}
