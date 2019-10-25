package br.com.inicial;

import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import br.com.banco.Banco;
import br.com.banco.BancoService;
import br.com.base.Masks;
import br.com.caixa.ExpedienteCaixa;
import br.com.cheque.ChequeService;
import br.com.emissao.SAT;
import br.com.emissao.ValidadorDocumento;
import br.com.historico.Historico;
import br.com.impressao.GeradorCupomSATModelo1;
import br.com.movimento_financeiro.Movimento;
import br.com.movimento_financeiro.MovimentoService;
import br.com.nota.FormaPagamentoNota;
import br.com.nota.Nota;
import br.com.nota.NotaFactory;
import br.com.nota.NotaService;
import br.com.operacao.Operacao;
import br.com.venda.FormaPagamentoVendaService;
import br.com.venda.StatusVenda;
import br.com.venda.Venda;
import br.com.venda.VendaService;
import br.com.webServices.TabelaImpostoAproximado;

import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class SelecaoFormaPagamento extends JDialog {

	/**
	 * 
	 */

	private List<Pagamento> pagamentos;

	private static List<KeyEventDispatcher> atalhos = new ArrayList<KeyEventDispatcher>();

	private static final long serialVersionUID = 1L;

	private Venda venda;

	private List<br.com.venda.FormaPagamento> pg;
	private List<JLabel> lbls;

	private EntityManager et;

	private br.com.venda.FormaPagamento selecionada;
	private JTextField txtDinheiro;
	private JTextField txtTroco;

	private JLabel lblTroco;

	private JLabel lblDinheiro;

	private FrenteCaixa fc;

	private ExpedienteCaixa expediente;

	private void setD(boolean v) {

		this.lblDinheiro.setVisible(v);
		this.lblTroco.setVisible(v);
		this.txtDinheiro.setVisible(v);
		this.txtTroco.setVisible(v);

	}

	private void setC(boolean v) {

		this.lblDinheiro.setVisible(v);
		this.txtDinheiro.setVisible(v);

	}
	
	public void atualizarPagamento() {

		if ((venda.getTotal() - pagamentos.stream().mapToDouble(p -> p.valor).sum()) > 0) {

			System.out.println((venda.getTotal() - pagamentos.stream().mapToDouble(p -> p.valor).sum())+"@@@@###");
			
			try {
			
				lblDinheiro.setText("Pagamento Restante: R$ "
						+ Masks.moeda().valueToString(venda.getTotal() - pagamentos.stream().mapToDouble(p -> p.valor).sum()));

			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else {

			finalizarVenda(pagamentos);

		}
		
	}

	public void atualizar() {

		this.atualizarPagamento();

		this.lbls.forEach(l -> {

			getContentPane().remove(l);

		});

		this.lbls.clear();

		int i = 0;
		for (br.com.venda.FormaPagamento fp : this.pg) {

			JLabel lbl = new JLabel();

			lbl.setOpaque(true);

			lbl.setHorizontalAlignment(JLabel.CENTER);

			lbl.setBounds(5, 5 + (30 * i), 305, 25);

			lbl.setText(fp.toString());

			lbl.setFont(new Font("Arial", Font.BOLD, 18));

			lbl.setBackground(Color.WHITE);

			if (fp == selecionada) {

				lbl.setBackground(Color.BLACK);
				lbl.setForeground(Color.WHITE);

			}

			getContentPane().add(lbl);

			lbls.add(lbl);

			i++;
		}

		this.validate();

		this.setVisible(false);
		this.setVisible(true);

	}

	public void centralizar() {

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension size = tk.getScreenSize();

		this.setLocation((int) (size.getWidth() / 2 - this.getWidth() / 2),
				(int) (size.getHeight() / 2 - this.getHeight() / 2));

	}

	private boolean executando = false;

	private void finalizarVenda(List<Pagamento> pgtos) {

		if (this.executando)
			return;

		this.executando = true;

		Runnable rn = () -> {

			System.out.println("ponto1====================");
			this.venda.setStatus(StatusVenda.FECHADA);
			this.venda.setData(Calendar.getInstance());

			if (this.venda.getCliente() != null) {

				this.venda.setCliente(et.merge(this.venda.getCliente()));

			}

			this.venda.getProdutos().stream().forEach(p -> p.setProduto(et.merge(p.getProduto())));

			try {

				NotaFactory nf = new NotaFactory();

				nf.setFp(this.selecionada);
				nf.setParcelas(1);
				nf.setPrazoPagamento(0);
				nf.setTransportadora(null);

				if (this.pagamentos.stream().mapToDouble(p -> p.valor).sum() < this.venda.getTotal()) {

					return;

				}

				nf.setValorMeioPagamento(this.pagamentos.stream().mapToDouble(p -> p.valor).sum());
				
				nf.setVenda(this.venda);
				System.out.println("ponto2====================");
				List<Nota> notas = null;

				try {

					notas = nf.getNotas();

				} catch (Exception exx) {

					exx.printStackTrace();

					return;

				}

				NotaService ns = new NotaService(et);
				ns.setEmpresa(this.venda.getEmpresa());

				VendaService vs = new VendaService(et);

				if (!vs.verificacaoPersistencia(venda)
						|| notas.stream().map(ns::verificacaoPersistencia).anyMatch(b -> !b)) {

					throw new RuntimeException("Nao é possivel criar a venda nem a nota");

				}

				SAT sat = new SAT(venda.getEmpresa());// teste

				ValidadorDocumento vd = new ValidadorDocumento(ns, sat, new GeradorCupomSATModelo1(),
						new TabelaImpostoAproximado());

				if (this.cpfNota != "" && this.cpfNota != null) {

					notas.forEach(n -> n.setCpfNotaSemDestinatario(this.cpfNota));

				}

				notas.forEach(n -> {

					try {

						vd.validarFiscalmente(n);

					} catch (Exception ex) {

						ex.printStackTrace();

						return;

					}

				});
				System.out.println("ponto3====================");
				this.venda = new VendaService(et).merge(venda);

				et.getTransaction().begin();
				et.getTransaction().commit();

				notas = notas.stream().map(n -> new NotaService(et).merge(n)).collect(Collectors.toList());

				this.venda.setNotas(notas);

				et.getTransaction().begin();
				et.getTransaction().commit();

				LinkedList<Thread> ths = new LinkedList<Thread>();

				Banco banco = this.venda.getEmpresa().getPj().getBanco();

				if (banco == null) {

					banco = new Banco();
					banco.setPj(this.venda.getEmpresa().getPj());
					banco.setSaldo(0);

					banco = new BancoService(et).merge(banco);

				}

				final Banco bc = banco;

				final MovimentoService mvs = new MovimentoService(et);
				mvs.setBanco(banco);

				Historico hi = (Historico) et.createQuery("SELECT h FROM Historico h").getResultList().get(0);

				Operacao op = (Operacao) et.createQuery("SELECT o FROM Operacao o WHERE o.credito=true").getResultList()
						.get(0);
				System.out.println("ponto4====================");
				final List<Nota> fnotas = notas;
				
				ths.addAll(pgtos.stream().map(p -> {

					p.vencimento = fnotas.get(0).getVencimentos().get(0);

					Thread t = new Thread(() -> {

						Movimento m = new Movimento();
						m.setBanco(bc);
						m.setData(Calendar.getInstance());
						m.setDescontos(0);
						m.setHistorico(hi);
						m.setOperacao(op);
						m.setValor(p.valor);
						m.setVencimento(p.vencimento);
						m.setFormaPagamento(p.formaPagamento);
						m.setExpediente(expediente);

						mvs.mergeMovimento(m, true, new MovimentoService.Listener() {

							@Override
							public void setConclusao(double porcentagem, String observacao, Movimento mov) {

								if (porcentagem == 100) {
									
									ths.removeFirst();

									expediente.getMovimentos().add(mov);

									if (p.cheque != null) {

										p.cheque.setMovimento(mov);
										p.cheque = new ChequeService(et).merge(p.cheque);

									}

									et.getTransaction().begin();
									et.getTransaction().commit();

									if (ths.size() > 0) {
										ths.getFirst().start();
									} else {

										executando = false;
										dispose();
										
										KeyboardFocusManager keyManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
										atalhos.forEach(a->keyManager.removeKeyEventDispatcher(a));
										
										fc.novaVenda();

									}

								}

							}

						});
					});
					return t;

				}).collect(Collectors.toList()));

				ths.getFirst().start();

			} catch (Exception e) {

				e.printStackTrace();
				return;

			}

		};

		Loading.getLoading(rn);

	}

	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */

	private String cpfNota;

	private boolean din = false;

	public SelecaoFormaPagamento(Venda venda, EntityManager et, ExpedienteCaixa exp, String cpfNota, FrenteCaixa fc) {

		setTitle("Selecao de Forma de Pagamento");
		setResizable(false);
		setBounds(100, 100, 315, 300);

		this.fc = fc;

		this.expediente = exp;

		this.pagamentos = new ArrayList<Pagamento>();

		this.cpfNota = cpfNota;

		KeyboardFocusManager keyManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();

		atalhos.forEach(d -> keyManager.removeKeyEventDispatcher(d));

		final SelecaoFormaPagamento este = this;
		KeyEventDispatcher disp = new KeyEventDispatcher() {

			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {

				if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_UP) {

					System.out.println("******");
					int idx = pg.indexOf(selecionada);

					if (idx == 0)
						return true;

					System.out.println("OKKKKK");

					selecionada = pg.get(idx - 1);

					atualizar();

				} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_DOWN) {

					System.out.println("#####");

					int idx = pg.indexOf(selecionada);

					if (idx == pg.size() - 1)
						return true;

					System.out.println("OKKKKK2");

					selecionada = pg.get(idx + 1);

					atualizar();

				} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (selecionada.getFormaPagamento() == FormaPagamentoNota.CHEQUE) {

						setVisible(false);
						fc.setVisible(false);

						CadastroCheque cc = new CadastroCheque(pagamentos, este, fc, et);
						cc.init(fc.operador);
						cc.setVisible(true);
						
						
						MenuPrincipal.menu.jdp.add(cc);
						
						try {
							cc.setSelected(true);
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						cc.centralizar();
						
						return true;

					}

					if ((selecionada.getFormaPagamento() == FormaPagamentoNota.DINHEIRO
							|| selecionada.getFormaPagamento().toString().startsWith("CARTAO")) && !din) {

						lbls.forEach(l -> {

							getContentPane().remove(l);

						});

						lbls.clear();

						validate();

						if (selecionada.getFormaPagamento() == FormaPagamentoNota.DINHEIRO) {
							setD(true);
						} else {
							setC(true);
						}

						setVisible(false);
						setVisible(true);

						txtDinheiro.requestFocus();

						din = true;

						return true;

					} else if ((selecionada.getFormaPagamento() == FormaPagamentoNota.DINHEIRO
							|| selecionada.getFormaPagamento().toString().startsWith("CARTAO"))) {

						return false;

					}

					finalizarVenda(pagamentos);

				}

				return false;

			}

		};

		keyManager.addKeyEventDispatcher(disp);
		atalhos.add(disp);

		this.venda = venda;

		this.et = et;

		this.lbls = new ArrayList<JLabel>();

		FormaPagamentoVendaService serv = new FormaPagamentoVendaService();

		this.pg = serv.getFormasPagamento();

		this.selecionada = this.pg.get(0);
		getContentPane().setLayout(null);

		lblDinheiro = new JLabel("Pagamento");
		lblDinheiro.setFont(new Font("Arial", Font.BOLD, 17));
		lblDinheiro.setBounds(10, 48, 289, 26);
		getContentPane().add(lblDinheiro);

		lblTroco = new JLabel("Troco");
		lblTroco.setFont(new Font("Arial", Font.BOLD, 17));
		lblTroco.setBounds(38, 157, 101, 14);
		getContentPane().add(lblTroco);

		txtDinheiro = new JTextField();
		txtDinheiro.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtDinheiro.setHorizontalAlignment(SwingConstants.CENTER);
		txtDinheiro.setColumns(10);
		txtDinheiro.setBounds(68, 84, 200, 54);
		getContentPane().add(txtDinheiro);

		txtTroco = new JTextField();
		txtTroco.setEnabled(false);
		txtTroco.setEditable(false);
		txtTroco.setHorizontalAlignment(SwingConstants.CENTER);
		txtTroco.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtTroco.setColumns(10);
		txtTroco.setBounds(68, 182, 200, 54);
		getContentPane().add(txtTroco);

		this.setD(false);

		this.txtDinheiro.addActionListener(a -> {

			double valor = Double.parseDouble(this.txtDinheiro.getText().replaceAll(",", "."));

			Pagamento pg = new Pagamento();

			pg.data = Calendar.getInstance();
			pg.formaPagamento = selecionada.getFormaPagamento();
			pg.valor = Math.min(valor, venda.getTotal() - pagamentos.stream().mapToDouble(p -> p.valor).sum());

			if (pg.valor > 0) {

				pagamentos.add(pg);

			}


			setD(false);
			
			din = false;
			
			this.atualizar();
			
			txtDinheiro.setText("");
			

		});

		this.txtDinheiro.addCaretListener(x -> {

			double v = Double.parseDouble(this.txtDinheiro.getText().replaceAll(",", "."))
					+ this.pagamentos.stream().mapToDouble(p -> p.valor).sum();

			this.txtTroco.setText(((v - this.venda.getTotal()) + "").replaceAll("\\.", ","));

		});

		this.atualizar();

	}

}
