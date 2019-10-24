package br.com.movimento_financeiro;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;

import br.com.banco.Banco;
import br.com.banco.Fechamento;
import br.com.base.Service;
import br.com.empresa.Empresa;
import br.com.nota.FormaPagamentoNota;
import br.com.nota.SaidaEntrada;

public class MovimentoService implements Service<Movimento> {

	private Banco banco;

	private EntityManager et;

	private Empresa empresa;

	private FormaPagamentoNota formaPagamento;

	public void setFormaPagamentoNota(FormaPagamentoNota fpn) {

		this.formaPagamento = fpn;

	}

	public void setEmpresa(Empresa emp) {

		this.empresa = emp;

	}

	public void setBanco(Banco emp) {

		this.banco = emp;

	}

	public void deleteMovimento(Movimento movimento, boolean deletarFechamento, Listener l) {

		movimento.setValor(0);

		this.mergeMovimento(movimento, deletarFechamento, (p, d, m) -> {

			if (p < 100) {

				l.setConclusao(p, d, m);
				return;

			}

			this.et.remove(movimento);

			l.setConclusao(100, "", m);

		});

	}

	public void mergeMovimento(Movimento movimento, boolean deletarFechamentos, Listener l) {

		Runnable rn = () -> {

			if (movimento.getVencimento() != null) {

				double pago = movimento.getVencimento().getMovimentos().stream()
						.map(x -> x.equals(movimento) ? movimento : x).mapToDouble(x -> x.getValor()).sum();

				if (!movimento.getVencimento().getMovimentos().contains(movimento)) {

					pago += movimento.getValor();
					movimento.getVencimento().getMovimentos().add(movimento);

				}

				if (pago > movimento.getVencimento().getValor()) {

					l.setConclusao(-1, "Esse valor supera o do vencimento", null);
					return;

				}

				if (movimento.getVencimento().getNota().getOperacao().equals(SaidaEntrada.ENTRADA)) {

					if (movimento.getOperacao().isCredito()) {

						l.setConclusao(-1, "Operacao de credito para entrada", null);
						return;

					}

				} else {

					if (!movimento.getOperacao().isCredito()) {

						l.setConclusao(-1, "Operacao de debito para saida", null);
						return;

					}

				}

			}

			Query q = this.et.createQuery(
					"SELECT f FROM Fechamento f WHERE f.banco.id= :banco AND f.data >= :data ORDER BY f.data ASC");
			q.setParameter("banco", movimento.getBanco().getId());
			q.setParameter("data", movimento.getData());

			@SuppressWarnings("unchecked")
			List<Fechamento> fechamentos = (List<Fechamento>) (List<?>) q.getResultList();

			l.setConclusao(5, "", null);

			if (!deletarFechamentos && fechamentos.size() > 0) {

				l.setConclusao(-1, "Existem fechamentos que nao podem ser apagados", null);
				return;

			}

			q = this.et.createQuery(
					"SELECT m FROM Movimento m WHERE m.banco.id = :banco AND (m.data < :data OR (m.data = :data AND m.id < :este)) AND m.id <> :este ORDER BY m.data DESC, m.id DESC");
			q.setParameter("banco", movimento.getBanco().getId());
			q.setParameter("data", movimento.getData());
			q.setParameter("este", movimento.getId() == 0 ? Integer.MAX_VALUE : movimento.getId());
			q.setMaxResults(1);

			@SuppressWarnings("unchecked")
			List<Movimento> anteriores = (List<Movimento>) (List<?>) q.getResultList();

			l.setConclusao(10, "", null);

			double saldo_anterior = 0;

			if (anteriores.size() > 0) {

				Movimento anterior = anteriores.get(0);

				saldo_anterior = anterior.getSaldo();

			}

			int total = 0;
			int buffer = 30;

			q = this.et.createQuery(
					"SELECT COUNT(*) FROM Movimento m WHERE m.banco.id = :banco AND(m.data > :data OR (m.data = :data AND m.id > :este)) AND m.id <> :este ORDER BY m.data ASC, m.id ASC");
			q.setParameter("banco", movimento.getBanco().getId());
			q.setParameter("data", movimento.getData());
			q.setParameter("este", movimento.getId());

			total = Integer.parseInt(q.getResultList().get(0) + "");

			Movimento currente = movimento;

			int index = 0;

			while (index < total) {

				q = this.et.createQuery(
						"SELECT m FROM Movimento m WHERE m.banco.id = :banco AND (m.data > :data OR (m.data = :data AND m.id > :este)) AND m.id <> :este ORDER BY m.data ASC, m.id ASC");
				q.setParameter("banco", movimento.getBanco().getId());
				q.setParameter("data", movimento.getData());
				q.setParameter("este", movimento.getId());
				q.setFirstResult(index);
				q.setMaxResults(Math.min(buffer, total - index));

				@SuppressWarnings("unchecked")
				List<Movimento> movimentos = (List<Movimento>) (List<?>) q.getResultList();

				if (movimentos.size() == 0)
					break;

				for (Movimento m : movimentos) {
					currente.setSaldo(
							saldo_anterior + ((currente.getValor() + currente.getJuros() - currente.getDescontos())
									* (currente.getOperacao().isCredito() ? 1 : -1)));
					if (currente.getSaldo() < 0) {
						l.setConclusao(-1, "Saldo negativo no movimento " + currente.getId(), null);
						return;
					}
					saldo_anterior = currente.getSaldo();
					index++;
					currente = m;
				}

				l.setConclusao(10 + ((index / total) * 80), "", null);

			}

			currente.setSaldo(saldo_anterior + ((currente.getValor() + currente.getJuros() - currente.getDescontos())
					* (currente.getOperacao().isCredito() ? 1 : -1)));
			if (currente.getSaldo() < 0) {
				l.setConclusao(-1, "Saldo negativo no movimento " + currente.getId(), null);
				return;
			}

			fechamentos.stream().forEach(et::remove);

			if (movimento.getVencimento() != null)
				movimento.setVencimento(et.merge(movimento.getVencimento()));

			movimento.setBanco(et.merge(movimento.getBanco()));
			movimento.getBanco().setSaldo(currente.getSaldo());

			if (movimento.getExpediente() != null) {

				movimento.setExpediente(et.merge(movimento.getExpediente()));
				movimento.getExpediente().setCaixa(et.merge(movimento.getExpediente().getCaixa()));

				if (movimento.getFormaPagamento() != null) {
					if (movimento.getFormaPagamento().equals(FormaPagamentoNota.DINHEIRO)) {

						// calcular aqui corretamente na alteracao vai acabar
						// somando em duplicidade um credito ou subtraindo um
						// debito em duplicidade

						movimento.getExpediente()
								.setSaldo_final_atual(movimento.getExpediente().getSaldo_final_atual()
										+ ((movimento.getValor() + movimento.getJuros() - movimento.getDescontos())
												* (movimento.getOperacao().isCredito() ? 1 : -1)));
						movimento.getExpediente().getCaixa()
								.setSaldoAtual(movimento.getExpediente().getCaixa().getSaldoAtual()
										+ ((movimento.getValor() + movimento.getJuros() - movimento.getDescontos())
												* (movimento.getOperacao().isCredito() ? 1 : -1)));

					}
				}

			}

			Movimento mov = movimento;

			if (movimento.getId() == 0) {

				et.persist(movimento);

			} else {

				mov = et.merge(movimento);

			}

			l.setConclusao(100, "", mov);

		};

		Thread th = new Thread(rn);

		th.start();

	}

	public MovimentoService(EntityManager et) {

		this.et = et;

	}

	@Override
	public int getCount(String filtro) {

		Query qr = et.createQuery(
				"SELECT COUNT(*) FROM Movimento n WHERE " + (this.banco != null ? "n.banco.id = :banco AND " : "")
						+ (this.empresa != null ? "n.banco.pj.empresa = :empresa AND " : "")
						+ (this.formaPagamento != null ? "n.formaPagamento = :formaPagamento AND " : "")
						+ " (n.banco.pj.nome LIKE :nome_c)");

		if (this.banco != null)
			qr.setParameter("banco", this.banco.getId());

		if (this.empresa != null)
			qr.setParameter("empresa", this.empresa);

		if (this.formaPagamento != null)
			qr.setParameter("formaPagamento", this.formaPagamento);

		qr.setParameter("nome_c", "%" + filtro + "%");

		@SuppressWarnings("unchecked")
		List<Long> lista = (List<Long>) (List<?>) qr.getResultList();

		return Integer.parseInt(lista.get(0) + "");

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Movimento> getElementos(int x1, int x2, String filtro, String ordem) {

		ordem = ordem.replaceAll("\\{\\{et\\}\\}", "n");

		Query qr = et
				.createQuery("SELECT n FROM Movimento n WHERE " + (this.banco != null ? "n.banco.id = :banco AND " : "")
						+ (this.empresa != null ? "n.banco.pj.empresa = :empresa AND " : "")
						+ (this.formaPagamento != null ? "n.formaPagamento = :formaPagamento AND " : "")
						+ " (n.banco.pj.nome LIKE :nome_c) "
						+ (ordem != "" ? " ORDER BY " + ordem : "ORDER BY n.data DESC, n.id DESC"));

		if (this.banco != null)
			qr.setParameter("banco", this.banco.getId());

		if (this.empresa != null)
			qr.setParameter("empresa", this.empresa);

		if (this.formaPagamento != null)
			qr.setParameter("formaPagamento", this.formaPagamento);

		qr.setParameter("nome_c", "%" + filtro + "%");

		qr.setFirstResult(x1);
		qr.setMaxResults(x2 - x1);

		List<Movimento> ret = (List<Movimento>) (List<?>) qr.getResultList();

		return ret;

	}

	@Override
	public Movimento getPeloCodigo(String str) {

		@SuppressWarnings("unchecked")
		List<Movimento> notas = (List<Movimento>) (List<?>) et
				.createQuery("SELECT n FROM Movimento n WHERE n.id = :codigo")
				.setParameter("codigo", Integer.parseInt(str)).getResultList();

		if (notas.size() == 0) {

			return null;

		}

		return (Movimento) notas.get(0);

	}

	public interface Listener {

		public void setConclusao(double porcentagem, String observacao, Movimento merged);

	}

	@Override
	public void lixeira(Movimento obj) {
		// TODO Auto-generated method stub
		((Session) this.et.getDelegate()).evict(obj);
	}

	@Override
	public Movimento merge(Movimento obj) {

		throw new UnsupportedOperationException(
				"O movimento tem um processo de merge repleto de regras de negocio, pode demorar por isso conta com o metodo mergeMovimento para inserir");

	}

}
