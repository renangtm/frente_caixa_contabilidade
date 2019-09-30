package br.com.afgtec.notas;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.afgtec.base.Service;
import br.com.afgtec.pessoa.Empresa;

public class NotaService implements Service<Nota> {

	private Empresa empresa;

	private EntityManager et;

	public void setEmpresa(Empresa emp) {

		this.empresa = emp;

	}

	public NotaService(EntityManager et) {

		this.et = et;

	}

	public boolean verificarIntegridadeNota(Nota nota) {

		return Math.abs(
				nota.getVencimentos().stream().mapToDouble(v -> v.getValor()).sum() - nota.getValorTotalNota()) < 1;

	}

	public void mergeNota(Nota nota) {

		if (!this.verificarIntegridadeNota(nota)) {

			throw new RuntimeException("Nota sme integridade");

		}

		if (nota.getId() == 0) {

			this.et.persist(nota);

		} else {

			nota = et.merge(nota);

		}

	}

	@Override
	public int getCount(String filtro) {

		Query qr = et.createQuery("SELECT COUNT(*) FROM Nota n WHERE "
				+ (this.empresa != null ? "n.empresa.id = :empresa AND " : "") + " (n.emitente.nome LIKE :nome_c)");

		if (this.empresa != null)
			qr.setParameter("empresa", this.empresa.getId());

		qr.setParameter("nome_c", "%" + filtro + "%");

		@SuppressWarnings("unchecked")
		List<Long> lista = (List<Long>) (List<?>) qr.getResultList();

		return Integer.parseInt(lista.get(0) + "");

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Nota> getElementos(int x1, int x2, String filtro, String ordem) {

		ordem = ordem.replaceAll("\\{\\{et\\}\\}", "n");

		Query qr = et.createQuery(
				"SELECT n FROM Nota n WHERE " + (this.empresa != null ? "n.empresa.id = :empresa AND " : "")
						+ " (n.emitente.nome LIKE :nome_c)" + (ordem != "" ? " ORDER BY " + ordem : ""));

		if (this.empresa != null)
			qr.setParameter("empresa", this.empresa.getId());

		qr.setParameter("nome_c", "%" + filtro + "%");

		qr.setFirstResult(x1);
		qr.setMaxResults(x2 - x1);

		return (List<Nota>) (List<?>) qr.getResultList();

	}

	@Override
	public Nota getPeloCodigo(String str) {

		@SuppressWarnings("unchecked")
		List<Nota> notas = (List<Nota>) (List<?>) et.createQuery("SELECT n FROM Nota n WHERE n.id = :codigo")
				.setParameter("codigo", Integer.parseInt(str)).getResultList();

		if (notas.size() == 0) {

			return null;

		}

		return (Nota) notas.get(0);

	}

}
