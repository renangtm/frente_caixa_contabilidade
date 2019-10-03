package br.com.afgtec.notas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;

import br.com.afgtec.base.Service;
import br.com.afgtec.pessoa.Empresa;
import br.com.afgtec.produto.Estoque;
import br.com.venda.SemEstoqueException;

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

	public void mergeNota(Nota nota) throws Exception {

		if (!this.verificarIntegridadeNota(nota)) {

			throw new RuntimeException("Nota sme integridade");

		}

		Map<ProdutoNota, Double[]> alteracoes = new HashMap<ProdutoNota, Double[]>();

		for (ProdutoNota pn : nota.getProdutos()) {

			try {

				double meta = 0;

				if (nota.getStatus().equals(StatusNota.ATIVA)) {

					if (nota.getOperacao().equals(SaidaEntrada.SAIDA)) {

						meta = pn.getQuantidade() * -1;

					} else {

						meta = pn.getQuantidade();

					}

				}

				double dif = meta - pn.influenciaEstoques;

				Estoque est = pn.getProduto().getEstoque();
				et.refresh(est);

				double realDif = pn.getTipoInfluenciaEstoque().para(est.getTipo(), pn.getProduto(), dif);

				est.addQuantidades(realDif, realDif);

				alteracoes.put(pn, new Double[] { realDif, pn.influenciaEstoques });

				pn.influenciaEstoques = meta;

			} catch (Exception ex) {

				for (ProdutoNota pa : alteracoes.keySet()) {

					pa.getProduto().getEstoque().rmvQuantidades(alteracoes.get(pa)[0], alteracoes.get(pa)[0]);
					pa.influenciaEstoques = alteracoes.get(pa)[1];

				}

				throw new SemEstoqueException(pn.getProduto());

			}

		}
		// ------------------

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

	@Override
	public void lixeira(Nota obj) {
		// TODO Auto-generated method stub
		((Session) this.et.getDelegate()).evict(obj);
	}

}
