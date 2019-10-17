package br.com.inicial;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

import br.com.banco.Banco;
import br.com.banco.BancoService;
import br.com.base.AberturaCaixaException;
import br.com.base.CFG;
import br.com.base.ConfiguracaoExpediente;
import br.com.base.ET;
import br.com.base.Resources;
import br.com.caixa.ConfiguracaoLocalCaixa;
import br.com.caixa.ExpedienteCaixa;
import br.com.caixa.ExpedienteCaixaService;
import br.com.caixa.Reposicao;
import br.com.caixa.Sangria;
import br.com.codigo_barra.CodigoBarra;
import br.com.emissao.SAT;
import br.com.emissao.ValidadorDocumento;
import br.com.empresa.Empresa;
import br.com.historico.Historico;
import br.com.impressao.GeradorCupomReposicao;
import br.com.impressao.GeradorCupomSATModelo1;
import br.com.impressao.GeradorCupomSangria;
import br.com.movimento_financeiro.Movimento;
import br.com.movimento_financeiro.MovimentoService;
import br.com.nota.FormaPagamentoNota;
import br.com.nota.Nota;
import br.com.nota.NotaFactory;
import br.com.nota.NotaService;
import br.com.operacao.Operacao;
import br.com.pessoa.Pessoa;
import br.com.pessoa.PessoaService;
import br.com.pessoa.RepresentadorPessoa;
import br.com.produto.Produto;
import br.com.produto.ProdutoService;
import br.com.quantificacao.TipoQuantidade;
import br.com.usuario.TipoPermissao;
import br.com.usuario.Usuario;
import br.com.usuario.UsuarioService;
import br.com.utilidades.GerenciadorLista;
import br.com.utilidades.ListModelGenerica;
import br.com.utilidades.PercentageColumnSetter;
import br.com.venda.FormaPagamento;
import br.com.venda.FormaPagamentoVendaService;
import br.com.venda.ProdutoVenda;
import br.com.venda.RepresentadorProdutoVenda;
import br.com.venda.StatusVenda;
import br.com.venda.Venda;
import br.com.venda.VendaService;
import br.com.webServices.TabelaImpostoAproximado;

import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JSlider;
import java.awt.SystemColor;
import java.awt.Toolkit;

public class FrenteCaixa extends Modulo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Usuario operador;

	private Empresa empresa;
	private JTextField txtCliente;
	private JTextField txtValorUnitario;
	private JTextField txtQuantidade;
	private JTextField txtSubTotal;
	private JTextField txtTotal;
	private JTextField txtDinheiro;
	private JTextField txtTroco;
	private JTextField txtBipe;
	private JTextField txtPesquisa;
	private JTable tblProdutos;
	private JTable tblVenda;

	private static List<KeyEventDispatcher> atalhos = new ArrayList<KeyEventDispatcher>();

	private JTabbedPane tbpBP;

	private JComboBox<FormaPagamento> cboFormaPagto;

	private JLabel lblPg;

	private JSlider slider;

	private GerenciadorLista<Produto> grProdutos;

	private ListModelGenerica<ProdutoVenda> lstProdutoVenda;

	private ProdutoVenda produtoSelecionado;

	private Venda venda;

	private JButton btCliente;

	private FormaPagamento formaPagamento;

	private JLabel txtDescricaoProduto;

	private PercentageColumnSetter percProd;

	private JScrollPane srcProdutos;

	protected void setDestinatario(Pessoa pessoa) {

		this.venda.setCliente(pessoa);
		this.mostrarVenda();

	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException, InvalidKeyException, NoSuchAlgorithmException,
			CertificateException, KeyStoreException, SignatureException, IOException {

		EntityManager et = ET.nova();

		Usuario u = et.find(Usuario.class, 1);

		CFG.moduloSat = new SAT(u.getPf().getEmpresa());

		ConfiguracaoLocalCaixa clc = ConfiguracaoLocalCaixa.getConfiguracaoLocalCaixa(u.getPf().getEmpresa());

		if (clc != null) {

			if (!clc.verificarAssinatura()) {

				clc = null;

			}

		}

		CFG.clc = clc;

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					FrenteCaixa frame = new FrenteCaixa();
					frame.setVisible(true);
					frame.init(u);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected void finalizarVenda() {

		this.venda.setStatus(StatusVenda.FECHADA);
		this.venda.setData(Calendar.getInstance());

		if (this.venda.getCliente() != null) {

			this.venda.setCliente(et.merge(this.venda.getCliente()));

		}

		this.venda.getProdutos().stream().forEach(p -> p.setProduto(et.merge(p.getProduto())));

		try {

			NotaFactory nf = new NotaFactory();

			nf.setFp(this.formaPagamento);
			nf.setParcelas(1);
			nf.setPrazoPagamento(0);
			nf.setTransportadora(null);

			if (this.formaPagamento.getFormaPagamento().equals(FormaPagamentoNota.DINHEIRO)) {

				if (this.venda.getTotal() > Double.parseDouble(this.txtDinheiro.getText().replaceAll(",", "."))) {

					alerta("Pagamento menor que o devido.");
					return;

				}

				nf.setValorMeioPagamento(Double.parseDouble(this.txtDinheiro.getText().replaceAll(",", ".")));

			}

			nf.setVenda(this.venda);

			List<Nota> notas = null;

			try {

				notas = nf.getNotas();

			} catch (Exception exx) {

				erro(exx.getMessage());
				return;

			}

			NotaService ns = new NotaService(et);
			ns.setEmpresa(this.empresa);

			VendaService vs = new VendaService(et);

			if (!vs.verificacaoPersistencia(venda)
					|| notas.stream().map(ns::verificacaoPersistencia).anyMatch(b -> !b)) {

				throw new RuntimeException("Nao é possivel criar a venda nem a nota");

			}

			SAT sat = new SAT(venda.getEmpresa());// teste

			ValidadorDocumento vd = new ValidadorDocumento(ns, sat, new GeradorCupomSATModelo1(),
					new TabelaImpostoAproximado());

			if (this.cpf != "") {

				notas.forEach(n -> n.setCpfNotaSemDestinatario(this.cpf));

			}

			notas.forEach(n -> {

				try {

					vd.validarFiscalmente(n);

				} catch (Exception ex) {

					erro("Nao foi possivel emitir o cupom devido a inconsistencias fiscais, contate o seu contador.");

					ex.printStackTrace();

					return;

				}

			});

			this.venda = new VendaService(et).merge(venda);

			et.getTransaction().begin();
			et.getTransaction().commit();

			notas = notas.stream().map(n -> new NotaService(et).merge(n)).collect(Collectors.toList());

			this.venda.setNotas(notas);

			et.getTransaction().begin();
			et.getTransaction().commit();

			LinkedList<Thread> ths = new LinkedList<Thread>();

			Banco banco = this.empresa.getPj().getBanco();

			if (banco == null) {

				banco = new Banco();
				banco.setPj(this.empresa.getPj());
				banco.setSaldo(0);

				banco = new BancoService(et).merge(banco);

			}

			final Banco bc = banco;

			final MovimentoService mvs = new MovimentoService(et);
			mvs.setBanco(banco);

			Historico hi = (Historico) et.createQuery("SELECT h FROM Historico h").getResultList().get(0);

			Operacao op = (Operacao) et.createQuery("SELECT o FROM Operacao o WHERE o.credito=true").getResultList()
					.get(0);

			ths.addAll(notas.stream().flatMap(n -> n.getVencimentos().stream()).map(v -> {

				return new Thread(() -> {

					Movimento m = new Movimento();
					m.setBanco(bc);
					m.setData(Calendar.getInstance());
					m.setDescontos(0);
					m.setHistorico(hi);
					m.setOperacao(op);
					m.setValor(v.getValor());
					m.setVencimento(v);
					m.setFormaPagamento(v.getNota().getForma_pagamento());
					m.setExpediente(expediente);

					mvs.mergeMovimento(m, true, new MovimentoService.Listener() {

						@Override
						public void setConclusao(double porcentagem, String observacao, Movimento mov) {

							if (porcentagem == 100) {

								ths.removeFirst();

								expediente.getMovimentos().add(mov);

								et.getTransaction().begin();
								et.getTransaction().commit();

								if (ths.size() > 0) {
									ths.getFirst().start();
								} else {
									novaVenda();
								}

							}

						}

					});

				});
			}).collect(Collectors.toList()));

			ths.getFirst().start();

		} catch (Exception e) {

			e.printStackTrace();

			erro("Ocorreu um problema");

			return;

		}

	}

	protected void setFormaPagamento(FormaPagamento fp) {

		this.formaPagamento = fp;

	}

	private String cpf;

	protected void setVenda(Venda venda) {
		
		this.venda = venda;
		
		this.formaPagamento = this.cboFormaPagto.getItemAt(0);

		this.lstProdutoVenda = new ListModelGenerica<ProdutoVenda>(this.venda.getProdutos(), ProdutoVenda.class,
				RepresentadorProdutoVenda.class);
		this.lstProdutoVenda.getFiltrosObjeto().add(f -> {

			ProdutoVenda p = (ProdutoVenda) f;

			return p.getQuantidade() > 0;

		});

		this.produtoSelecionado = null;

		this.tblVenda.setModel(this.lstProdutoVenda);

		this.mostrarVenda();

		this.txtSubTotal.setText("");
		this.txtDinheiro.setText("");
		this.txtTroco.setText("");
		
		
	}
	
	protected void novaVenda() {

		if (this.venda != null) {

			et.detach(this.venda);

		}

		new Thread(() -> {

			this.cpf = console("CPF da nota ?");

		}).start();

		this.venda = new Venda();

		this.venda.setOperador(this.operador);

		this.venda.setEmpresa(this.empresa);

		this.setVenda(this.venda);

	}

	protected void addProduto(Produto produto, TipoQuantidade tipo, double quantidade) {

		ProdutoVenda pv = new ProdutoVenda();

		pv.setProduto(produto);

		pv.setVenda(venda);

		pv.setQuantidade(tipo.para(pv.getTipoQuantidade(), produto, quantidade));

		pv.setValor(pv.getTipoQuantidade().para(produto.getEstoque().getTipo(), produto, produto.getValor()));

		this.venda.getProdutos().add(pv);

		this.produtoSelecionado = pv;

		this.txtQuantidade.requestFocus();

		this.mostrarVenda();

	}

	protected void addProduto(Produto produto) {

		this.addProduto(produto, produto.getEstoque().getTipo(),
				TipoQuantidade.UN.para(produto.getEstoque().getTipo(), produto, 1));

	}

	protected void removerProduto(ProdutoVenda pv) {

		pv.setQuantidade(0);

		this.mostrarVenda();

	}

	protected void mostrarVenda() {

		this.txtCliente.setText("");
		if (this.venda.getCliente() != null) {
			this.txtCliente.setText(this.venda.getCliente().getNome());
		}

		this.lblQuantidadeItens.setText(
				this.venda.getProdutos().size() + " " + (this.venda.getProdutos().size() > 1 ? "Itens" : "Item"));

		this.txtDescricaoProduto.setText("");
		this.txtQuantidade.setText("0 UN");
		this.txtValorUnitario.setText("0,00");

		if (this.produtoSelecionado != null) {

			this.txtDescricaoProduto.setText(this.produtoSelecionado.getProduto().getNome());
			this.txtQuantidade.setText((this.produtoSelecionado.getQuantidade() + "").replaceAll("\\.", ",") + " "
					+ this.produtoSelecionado.getTipoQuantidade().toString());
			this.txtValorUnitario.setText((this.produtoSelecionado.getValor() + "").replaceAll("\\.", ","));

			this.txtSubTotal
					.setText(((this.produtoSelecionado.getValor() * this.produtoSelecionado.getQuantidade()) + "")
							.replaceAll("\\.", ","));

		}

		this.txtTotal.setText((this.venda.getTotal() + "").replaceAll("\\.", ","));

		if (this.formaPagamento.getFormaPagamento().equals(FormaPagamentoNota.DINHEIRO)) {
			try {
				this.txtTroco.setText(((Double.parseDouble(this.txtDinheiro.getText().replaceAll(",", ".")) - this.venda.getTotal())
						+ "").replaceAll("\\.", ","));
			} catch (Exception ex) {

			}
		}

		this.lstProdutoVenda.setLista(this.venda.getProdutos());
		this.lstProdutoVenda.atualizaListaBaseConformeFiltros();

		this.tblProdutos.setDefaultRenderer(String.class, new ProdCellRenderer());

		percProd = new PercentageColumnSetter(Produto.class);
		percProd.resize(this.tblProdutos, this.srcProdutos);

	}

	private ExpedienteCaixa expediente;

	private JButton btFinalizarPedido;

	private JButton btNovaVenda;

	private JButton btSangria;

	private JButton btReposicao;

	private JLabel lblQuantidadeItens;

	public void init(Usuario operador) {

		this.operador = et.merge(operador);
		this.empresa = this.operador.getPf().getEmpresa();
		this.empresa = et.merge(this.empresa);

		try {

			expediente = et.merge(ConfiguracaoExpediente.getExpedienteCaixa(operador));

		} catch (AberturaCaixaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			erro("Nao foi possivel abrir o caixa configure o numero do caixa na tela de configuracao de caixa primerio");
			this.dispose();
			return;

		}

		this.setTitle(operador.getPf().getEmpresa().getPj().getNome() + " - Operador: " + operador.getPf().getNome());

		ProdutoService ps = new ProdutoService(et);
		ps.setEmpresa(this.empresa);
		ps.setComEstoque(true);

		this.grProdutos = new GerenciadorLista<Produto>(Produto.class, this.tblProdutos, ps, 30, null, null, null);
		this.grProdutos.setLblPagina(this.lblPg);
		this.grProdutos.setLblSlider(this.slider);
		this.grProdutos.setFiltro(this.txtPesquisa);
		this.grProdutos.atualizar();

		this.txtBipe.addActionListener(e -> {

			try {

				CodigoBarra cb = new CodigoBarra(this.txtBipe.getText(), this.empresa.getPadroesCodigo(), ps);

				addProduto(cb.getProduto(), cb.getProduto().getEstoque().getTipo(), cb.getQuantidade());

			} catch (Exception ex) {

			}

			this.txtBipe.setText("");

		});

		this.txtQuantidade.addActionListener(x -> {

			if (this.produtoSelecionado != null) {

				double quantidade = Double.parseDouble(this.txtQuantidade.getText().replaceAll(",", ".").split(" ")[0]);
				this.produtoSelecionado.setQuantidade(quantidade);

				this.mostrarVenda();

				this.txtPesquisa.requestFocus();

			}

		});

		this.txtValorUnitario.addActionListener(x -> {

			if (this.produtoSelecionado != null) {

				double valor = Double.parseDouble(this.txtValorUnitario.getText().replaceAll(",", ".").split(" ")[0]);
				this.produtoSelecionado.setValor(valor);

				this.mostrarVenda();

			}

		});

		this.tblVenda.getSelectionModel().addListSelectionListener(x -> {

			if (!x.getValueIsAdjusting()) {

				if (this.tblVenda.getSelectedRow() >= 0) {

					this.produtoSelecionado = this.lstProdutoVenda.getListaBase().get(this.tblVenda.getSelectedRow());
					this.mostrarVenda();
					this.txtQuantidade.requestFocus();

				}

			}

		});

		FormaPagamentoVendaService fps = new FormaPagamentoVendaService();

		List<FormaPagamento> fp = fps.getFormasPagamento();

		this.cboFormaPagto
				.setModel(new DefaultComboBoxModel<FormaPagamento>(fp.toArray(new FormaPagamento[fp.size()])));

		this.cboFormaPagto.addActionListener(x -> {

			FormaPagamento f = (FormaPagamento) this.cboFormaPagto.getSelectedItem();

			if (f.getFormaPagamento().equals(FormaPagamentoNota.DINHEIRO)) {

				this.txtDinheiro.setEnabled(false);
				this.txtTroco.setEnabled(false);

				this.txtDinheiro.setText("0,00");
				this.txtTroco.setText("0,00");
				this.txtDinheiro.setEnabled(false);
				this.txtTroco.setEnabled(false);

				this.txtDinheiro.setText("0,00");
				this.txtTroco.setText("0,00");

			} else {

				this.txtDinheiro.setEnabled(true);
				this.txtTroco.setEnabled(true);

				this.txtDinheiro.setText("0,00");
				this.txtTroco.setText("0,00");

			}

			this.setFormaPagamento(f);

		});

		this.txtDinheiro.addCaretListener(x -> {

			this.mostrarVenda();

		});

		this.tblProdutos.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {

					if (tblProdutos.getSelectedRow() >= 0) {

						Produto produto = grProdutos.getModel().getListaBase().get(tblProdutos.getSelectedRow());

						addProduto(produto);

					}

				}

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

		this.txtPesquisa.addActionListener(e -> {

			List<Produto> produtos = this.grProdutos.getModel().getListaBase();

			if (produtos.size() > 0) {

				addProduto(produtos.get(0));

			}

		});

		new Thread(() -> {

			while (true) {

				if (this.tbpBP.getSelectedIndex() == 0) {

					this.txtBipe.requestFocus();

				}else if(this.tbpBP.getSelectedIndex() == 2) {
					
					this.txtComanda.requestFocus();
					
				}

				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}).start();

		this.btNovaVenda.addActionListener(a -> {
			novaVenda();
		});

		this.btFinalizarPedido.addActionListener(a -> {

			if (venda.getProdutos().size() == 0) {

				alerta("Coloque produtos na venda");
				return;
			}

			boolean pf = true;

			if (tbpBP.getSelectedIndex() == 0) {

				txtBipe.setText("");
				tbpBP.setSelectedIndex(1);

			}

			if (formaPagamento.getFormaPagamento().equals(FormaPagamentoNota.DINHEIRO)) {
				try {

					Double.parseDouble(txtDinheiro.getText().replaceAll(",", "."));

				} catch (Exception exx) {
					txtDinheiro.requestFocus();
					pf = false;
				}
			}

			if (pf)
				finalizarVenda();

		});

		this.btSangria.addActionListener(a -> {
			String senha = console("Senha de Administrador");

			Usuario gerente = new UsuarioService(et).getPorSenhaPermissao(senha, TipoPermissao.GERENCIA_CAIXAS,
					operador.getPf().getEmpresa());

			if (gerente == null) {

				erro("Senha invalida");
				return;

			}

			double valor = Double.parseDouble(console("Digite o valor").replaceAll(",", "."));

			try {

				ExpedienteCaixa exp = et.merge(ConfiguracaoExpediente.getExpedienteCaixa(operador));

				if (valor > exp.getSaldo_final_atual()) {

					erro("O Valor e maior que o saldo desse caixa");
					return;

				}

				Sangria sangria = new Sangria();
				sangria.setExpediente(exp);
				sangria.setMomento(Calendar.getInstance());
				sangria.setValor(valor);
				sangria.setGerente(gerente);

				exp.getSangrias().add(sangria);

				new ExpedienteCaixaService(et).merge(exp);

				et.getTransaction().begin();
				et.getTransaction().commit();

				new GeradorCupomSangria().gerarCupom(sangria);

			} catch (Exception e1) {

				e1.printStackTrace();

				alerta("Nao existe expediente aberto");

			}
		});

		this.btReposicao.addActionListener(a -> {
			String senha = console("Senha de Administrador");

			Usuario gerente = new UsuarioService(et).getPorSenhaPermissao(senha, TipoPermissao.GERENCIA_CAIXAS,
					operador.getPf().getEmpresa());

			if (gerente == null) {

				erro("Senha invalida");
				return;

			}

			double valor = Double.parseDouble(console("Digite o valor").replaceAll(",", "."));

			try {

				ExpedienteCaixa exp = et.merge(ConfiguracaoExpediente.getExpedienteCaixa(operador));

				Reposicao reposicao = new Reposicao();
				reposicao.setExpediente(exp);
				reposicao.setMomento(Calendar.getInstance());
				reposicao.setValor(valor);
				reposicao.setGerente(gerente);

				exp.getReposicoes().add(reposicao);

				new ExpedienteCaixaService(et).merge(exp);

				et.getTransaction().begin();
				et.getTransaction().commit();

				new GeradorCupomReposicao().gerarCupom(reposicao);

			} catch (Exception e1) {

				e1.printStackTrace();

				alerta("Nao existe expediente aberto");
				return;

			}

		});
		
		this.txtComanda.addActionListener(a->{
			
			String codigo = this.txtComanda.getText();
			
			VendaService vs = new VendaService(et);
			
			
			Venda venda = vs.getVendaPorComanda(codigo);
			
			if(venda == null) {
				
				alerta("Nao existe nada nessa comanda");
				this.txtComanda.setText("");
				return;
				
			}
			
			this.setVenda(venda);
			
			this.txtComanda.setText("");
			
		});

		KeyboardFocusManager keyManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		final FrenteCaixa este = this;

		FrenteCaixa.atalhos.forEach(d -> keyManager.removeKeyEventDispatcher(d));

		KeyEventDispatcher disp = new KeyEventDispatcher() {

			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (este.isDisplayable()) {
					if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_F1) {

						tbpBP.setSelectedIndex(0);
						txtPesquisa.setText("");

						return true;

					} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_F2) {

						txtBipe.setText("");
						tbpBP.setSelectedIndex(1);

						return true;

					} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_F5) {

						txtComanda.setText("");
						tbpBP.setSelectedIndex(2);
						
						return true;

					} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_F3) {

						novaVenda();

						return true;

					} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_F4) {

						if (venda.getProdutos().size() == 0) {

							alerta("Coloque produtos na venda");
							return true;

						}

						boolean pf = true;

						if (tbpBP.getSelectedIndex() == 0 || tbpBP.getSelectedIndex() == 2) {

							txtBipe.setText("");
							tbpBP.setSelectedIndex(1);

						}

						if (formaPagamento.getFormaPagamento().equals(FormaPagamentoNota.DINHEIRO)) {
							try {

								Double.parseDouble(txtDinheiro.getText().replaceAll(",", "."));

							} catch (Exception exx) {
								txtDinheiro.requestFocus();
								pf = false;
							}
						}

						if (pf)
							finalizarVenda();

						return true;

					} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_F7) {

						String senha = console("Senha de Administrador");

						Usuario gerente = new UsuarioService(et).getPorSenhaPermissao(senha,
								TipoPermissao.GERENCIA_CAIXAS, operador.getPf().getEmpresa());

						if (gerente == null) {

							erro("Senha invalida");
							return false;

						}

						double valor = Double.parseDouble(console("Digite o valor").replaceAll(",", "."));

						try {

							ExpedienteCaixa exp = et.merge(ConfiguracaoExpediente.getExpedienteCaixa(operador));

							if (valor > exp.getSaldo_final_atual()) {

								erro("O Valor e maior que o saldo desse caixa");
								return false;

							}

							Sangria sangria = new Sangria();
							sangria.setExpediente(exp);
							sangria.setMomento(Calendar.getInstance());
							sangria.setValor(valor);
							sangria.setGerente(gerente);

							exp.getSangrias().add(sangria);

							new ExpedienteCaixaService(et).merge(exp);

							et.getTransaction().begin();
							et.getTransaction().commit();

							new GeradorCupomSangria().gerarCupom(sangria);

						} catch (Exception e1) {

							e1.printStackTrace();

							alerta("Nao existe expediente aberto");
							return false;

						}

					} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_F8) {

						String senha = console("Senha de Administrador");

						Usuario gerente = new UsuarioService(et).getPorSenhaPermissao(senha,
								TipoPermissao.GERENCIA_CAIXAS, operador.getPf().getEmpresa());

						if (gerente == null) {

							erro("Senha invalida");
							return false;

						}

						double valor = Double.parseDouble(console("Digite o valor").replaceAll(",", "."));

						try {

							ExpedienteCaixa exp = et.merge(ConfiguracaoExpediente.getExpedienteCaixa(operador));

							Reposicao reposicao = new Reposicao();
							reposicao.setExpediente(exp);
							reposicao.setMomento(Calendar.getInstance());
							reposicao.setValor(valor);
							reposicao.setGerente(gerente);

							exp.getReposicoes().add(reposicao);

							new ExpedienteCaixaService(et).merge(exp);

							et.getTransaction().begin();
							et.getTransaction().commit();

							new GeradorCupomReposicao().gerarCupom(reposicao);

						} catch (Exception e1) {

							e1.printStackTrace();

							alerta("Nao existe expediente aberto");
							return false;

						}

					}

				}
				return false;

			}

		};

		keyManager.addKeyEventDispatcher(disp);

		FrenteCaixa.atalhos.add(disp);

		this.btCliente.addActionListener(x -> {

			PessoaService pes = new PessoaService(et);
			pes.setEmpresa(this.empresa);

			Seletor<Pessoa> s = new Seletor<Pessoa>(Pessoa.class, pes, RepresentadorPessoa.class, this::setDestinatario,
					this);

			s.setVisible(true);

		});

		this.novaVenda();

	}
	
	
	
	private double wrebase = 900;
	private double hrebase = 650;
	private JPanel panel_2;
	private JTextField txtComanda;
	
	private void rebase() {
		
		double nw = this.getWidth();
		double nh = this.getHeight();
		
		this.percorrerComponentes(c->{
			
			Rectangle rect = c.getBounds();
			
			rect.height *= nh/hrebase;
			rect.y *= nh/hrebase;
			
			rect.width *= nw/wrebase;
			rect.x *= nw/wrebase;
			
			c.setBounds(rect);
			
		});
		
		this.wrebase = nw;
		this.hrebase = nh;
		
	}

	public FrenteCaixa() {
		setResizable(false);

		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);

		Toolkit tk = Toolkit.getDefaultToolkit();
		
		
		this.setBounds(0, 0, (int)tk.getScreenSize().getWidth(), (int)tk.getScreenSize().getHeight());

		srcProdutos = new JScrollPane();
		srcProdutos.setBounds(10, 67, 323, 433);
		getContentPane().add(srcProdutos);

		tblProdutos = new JTable();
		srcProdutos.setViewportView(tblProdutos);

		JLabel lblDescricao = new JLabel("Descricao:");
		lblDescricao.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDescricao.setBounds(359, 67, 88, 14);
		getContentPane().add(lblDescricao);

		txtDescricaoProduto = new JLabel("");
		txtDescricaoProduto.setFont(new Font("Tahoma", Font.BOLD, 24));
		txtDescricaoProduto.setBackground(new Color(0, 153, 255));
		txtDescricaoProduto.setForeground(Color.WHITE);
		txtDescricaoProduto.setOpaque(true);
		txtDescricaoProduto.setHorizontalAlignment(SwingConstants.CENTER);
		txtDescricaoProduto.setBounds(359, 92, 525, 45);
		getContentPane().add(txtDescricaoProduto);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCliente.setBounds(586, 11, 88, 14);
		getContentPane().add(lblCliente);

		txtCliente = new JTextField();
		txtCliente.setEnabled(false);
		txtCliente.setColumns(10);
		txtCliente.setBounds(585, 36, 246, 20);
		getContentPane().add(txtCliente);

		btCliente = new JButton("...");
		btCliente.setBounds(841, 35, 43, 23);
		getContentPane().add(btCliente);

		JLabel lblValorUnitario = new JLabel("Valor Unitario:");
		lblValorUnitario.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblValorUnitario.setBounds(359, 148, 88, 14);
		getContentPane().add(lblValorUnitario);

		JLabel lblQuantidade = new JLabel("Quantidade:");
		lblQuantidade.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblQuantidade.setBounds(546, 148, 88, 14);
		getContentPane().add(lblQuantidade);

		JLabel lblTotal = new JLabel("Sub Total");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotal.setBounds(718, 148, 88, 14);
		getContentPane().add(lblTotal);

		txtValorUnitario = new JTextField();
		txtValorUnitario.setFont(new Font("Tahoma", Font.BOLD, 23));
		txtValorUnitario.setHorizontalAlignment(SwingConstants.CENTER);
		txtValorUnitario.setBounds(359, 173, 146, 45);
		getContentPane().add(txtValorUnitario);
		txtValorUnitario.setColumns(10);

		txtQuantidade = new JTextField();
		txtQuantidade.setHorizontalAlignment(SwingConstants.CENTER);
		txtQuantidade.setFont(new Font("Tahoma", Font.BOLD, 23));
		txtQuantidade.setColumns(10);
		txtQuantidade.setBounds(546, 173, 146, 45);
		getContentPane().add(txtQuantidade);

		txtSubTotal = new JTextField();
		txtSubTotal.setEditable(false);
		txtSubTotal.setForeground(new Color(30, 144, 255));
		txtSubTotal.setFont(new Font("Tahoma", Font.BOLD, 25));
		txtSubTotal.setHorizontalAlignment(SwingConstants.CENTER);
		txtSubTotal.setColumns(10);
		txtSubTotal.setBounds(718, 173, 166, 45);
		getContentPane().add(txtSubTotal);

		JLabel lblNewLabel_2 = new JLabel("X");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(511, 173, 25, 55);
		getContentPane().add(lblNewLabel_2);

		JLabel label = new JLabel("=");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.BOLD, 24));
		label.setBounds(693, 173, 25, 55);
		getContentPane().add(label);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(359, 229, 525, 145);
		getContentPane().add(scrollPane_1);

		tblVenda = new JTable();
		scrollPane_1.setViewportView(tblVenda);

		txtTotal = new JTextField();
		txtTotal.setFont(new Font("Tahoma", Font.BOLD, 33));
		txtTotal.setEditable(false);
		txtTotal.setForeground(new Color(124, 252, 0));
		txtTotal.setHorizontalAlignment(SwingConstants.CENTER);
		txtTotal.setColumns(10);
		txtTotal.setBounds(652, 420, 232, 68);
		getContentPane().add(txtTotal);

		JLabel lblTotal_1 = new JLabel("Total");
		lblTotal_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotal_1.setBounds(652, 395, 88, 14);
		getContentPane().add(lblTotal_1);

		JLabel lblFormaDePagamento = new JLabel("Forma de Pagamento");
		lblFormaDePagamento.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFormaDePagamento.setBounds(359, 395, 196, 14);
		getContentPane().add(lblFormaDePagamento);

		cboFormaPagto = new JComboBox<FormaPagamento>();
		cboFormaPagto.setBounds(359, 420, 275, 20);
		getContentPane().add(cboFormaPagto);

		txtDinheiro = new JTextField();
		txtDinheiro.setForeground(new Color(0, 0, 255));
		txtDinheiro.setFont(new Font("Tahoma", Font.BOLD, 19));
		txtDinheiro.setHorizontalAlignment(SwingConstants.CENTER);
		txtDinheiro.setColumns(10);
		txtDinheiro.setBounds(359, 451, 118, 37);
		getContentPane().add(txtDinheiro);

		txtTroco = new JTextField();
		txtTroco.setForeground(new Color(255, 0, 0));
		txtTroco.setFont(new Font("Tahoma", Font.BOLD, 19));
		txtTroco.setHorizontalAlignment(SwingConstants.CENTER);
		txtTroco.setColumns(10);
		txtTroco.setBounds(487, 451, 147, 37);
		getContentPane().add(txtTroco);

		JLabel lblDinheiro = new JLabel("Dinheiro");
		lblDinheiro.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDinheiro.setBounds(359, 490, 118, 14);
		getContentPane().add(lblDinheiro);

		JLabel lblTroco = new JLabel("Troco");
		lblTroco.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTroco.setBounds(487, 490, 118, 14);
		getContentPane().add(lblTroco);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 545, 874, 2);
		getContentPane().add(separator);

		tbpBP = new JTabbedPane(JTabbedPane.LEFT);
		tbpBP.setBounds(10, 11, 566, 47);
		getContentPane().add(tbpBP);

		JPanel panel = new JPanel();
		tbpBP.addTab("Bipe (F1)", null, panel, null);
		panel.setLayout(null);

		txtBipe = new JTextField();
		txtBipe.setBackground(new Color(173, 216, 230));
		txtBipe.setBounds(10, 11, 344, 20);
		panel.add(txtBipe);
		txtBipe.setColumns(10);

		JPanel panel_1 = new JPanel();
		tbpBP.addTab("Pesquisa (F2)", null, panel_1, null);
		panel_1.setLayout(null);

		txtPesquisa = new JTextField();
		txtPesquisa.setColumns(10);
		txtPesquisa.setBounds(10, 11, 345, 20);
		panel_1.add(txtPesquisa);
		
		panel_2 = new JPanel();
		tbpBP.addTab("Comanda (F5)", null, panel_2, null);
		panel_2.setLayout(null);
		
		txtComanda = new JTextField();
		txtComanda.setBounds(10, 11, 336, 20);
		panel_2.add(txtComanda);
		txtComanda.setColumns(10);

		lblPg = new JLabel("New label");
		lblPg.setBounds(287, 511, 46, 14);
		getContentPane().add(lblPg);

		slider = new JSlider();
		slider.setBounds(10, 511, 267, 23);
		getContentPane().add(slider);

		btFinalizarPedido = new JButton("F4 - Finalizar pedido");
		btFinalizarPedido.setForeground(new Color(30, 144, 255));
		btFinalizarPedido.setFont(new Font("Tahoma", Font.BOLD, 11));
		btFinalizarPedido.setBounds(10, 558, 173, 54);
		getContentPane().add(btFinalizarPedido);

		btNovaVenda = new JButton("F3 Nova Venda");
		btNovaVenda.setForeground(Color.BLACK);
		btNovaVenda.setFont(new Font("Tahoma", Font.BOLD, 11));
		btNovaVenda.setBounds(193, 558, 173, 54);
		getContentPane().add(btNovaVenda);

		btSangria = new JButton("F7 - Sangria");
		btSangria.setForeground(Color.RED);
		btSangria.setFont(new Font("Tahoma", Font.BOLD, 11));
		btSangria.setBounds(376, 558, 173, 54);
		getContentPane().add(btSangria);

		btReposicao = new JButton("F8 - Reposicao");
		btReposicao.setForeground(Color.BLUE);
		btReposicao.setFont(new Font("Tahoma", Font.BOLD, 11));
		btReposicao.setBounds(559, 558, 173, 54);
		getContentPane().add(btReposicao);

		lblQuantidadeItens = new JLabel("1 Item");
		lblQuantidadeItens.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuantidadeItens.setForeground(SystemColor.text);
		lblQuantidadeItens.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblQuantidadeItens.setOpaque(true);
		lblQuantidadeItens.setBackground(SystemColor.textHighlight);
		lblQuantidadeItens.setBounds(750, 385, 134, 24);
		getContentPane().add(lblQuantidadeItens);
		
		this.rebase();

	}

	public static ImageIcon logo() {

		try {
			return Resources.getCotacoes();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Frente de caixa";

	}
}

class ProdCellRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int col) {

		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

		if (col == 0) {
			((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
		} else {
			((JLabel) c).setHorizontalAlignment(JLabel.LEFT);
		}

		if (!isSelected) {
			if (row % 2 == 0) {
				c.setBackground(Color.LIGHT_GRAY);
			} else {
				c.setBackground(Color.WHITE);
			}
		}

		return c;
	}

}
