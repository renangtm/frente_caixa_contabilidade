package br.com.inicial;

import java.awt.EventQueue;

import javax.persistence.EntityManager;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import br.com.base.ET;
import br.com.base.Masks;
import br.com.base.Resources;
import br.com.conversores.ConversorDate;
import br.com.empresa.Empresa;
import br.com.nota.ModeloNota;
import br.com.nota.Nota;
import br.com.nota.NotaService;
import br.com.nota.ProdutoNota;
import br.com.nota.RepresentadorNotaCompleto;
import br.com.nota.RepresentadorProdutoNota;
import br.com.nota.RepresentadorVencimento;
import br.com.nota.SaidaEntrada;
import br.com.nota.StatusNota;
import br.com.nota.TipoNota;
import br.com.nota.Vencimento;
import br.com.pessoa.Pessoa;
import br.com.pessoa.PessoaFisica;
import br.com.pessoa.PessoaJuridica;
import br.com.pessoa.PessoaJuridicaService;
import br.com.pessoa.PessoaService;
import br.com.pessoa.RepresentadorPessoa;
import br.com.pessoa.RepresentadorPessoaJuridicaSimples;
import br.com.produto.Produto;
import br.com.produto.ProdutoService;
import br.com.transportadora.RepresentadorTransportadoraSimples;
import br.com.transportadora.Transportadora;
import br.com.transportadora.TransportadoraService;
import br.com.usuario.Usuario;
import br.com.utilidades.GerenciadorLista;
import br.com.utilidades.ListModelGenerica;
import br.com.utilidades.ProvedorDeEventos;

import javax.swing.JLabel;
import java.awt.Font;
import java.io.IOException;
import java.util.List;

import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultFormatterFactory;

import com.ibm.icu.text.NumberFormat;

import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JSlider;

public class Notas extends Modulo {

	/**
	 * 
	 */

	public static ImageIcon logo() {

		try {
			return Resources.getPapel();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Notas";

	}

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEmit;
	private JTextField txtDestinatario;
	private JTextField txtNumero;
	private JTextField txtSerie;
	private JTextField txtChave;
	private JTextField txtTransportadora;
	private JTextField txtNomeProduto;
	private JTable tblNotas;
	private JTable tblProdutos;
	private JTable tblVencimentos;
	private JTextField txtPesquisar;
	private JSlider slPg;
	private JLabel lblPg;
	private JButton btNovaNota;
	private JButton btConfirmar;
	private JButton btAddProduto;
	private JFormattedTextField txtQtdProduto;
	private JFormattedTextField txtValorProduto;
	private JButton btSlproduto;
	private JButton btAddVencimento;
	private JFormattedTextField txtValorVencimento;
	private JFormattedTextField txtDataVencimento;
	private JLabel lblLocalTransp;
	private JLabel lblIeTransp;
	private JLabel lblCnpjTransp;
	private JButton btTransportadora;
	private JLabel lblValorTotal;
	private JLabel lblVDesc;
	private JLabel lblValorIpi;
	private JLabel lblIcmsDeson;
	private JLabel lblValorCofins;
	private JLabel lblVPis;
	private JLabel lblValorSeguro;
	private JLabel lblValorIcmsSt;
	private JLabel lblValorIcms;
	private JLabel lblValorBc;
	private JLabel lblValorTotal1;
	private JLabel lblValorIpi1;
	private JLabel lblValorCofinsSt1;
	private JLabel lblValorIpiSt;
	private JLabel lblValorCofins1;
	private JLabel lblValorSeguro1;
	private JLabel lblValorIcmsSt1;
	private JLabel lblValorIcms1;
	private JLabel lblValorBc1;
	private JComboBox<ModeloNota> cboModelo;
	private JComboBox<TipoNota> cboTipo;
	private JComboBox<StatusNota> cboSituacao;
	private JLabel lblCidadeDestinatario;
	private JLabel lblInfo2Destinatario;
	private JLabel lblInfoDestinatario;
	private JButton btDestinatario;
	private JLabel lblCidadeEmitente;
	private JLabel lblIEemitente;
	private JLabel lblCnpjEmitente;
	private JButton btEmit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		EntityManager et = ET.nova();

		Usuario u = et.find(Usuario.class, 1);

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Notas frame = new Notas();
					frame.setVisible(true);
					frame.init(u);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private Usuario operador;

	private Nota nota;

	private GerenciadorLista<Nota> grNt;

	private ListModelGenerica<ProdutoNota> lstPn;

	private ListModelGenerica<Vencimento> lstVn;

	private Empresa empresa;

	private void atualizarValores() {

		this.nota.atualizarRegrasFiscais();

		this.lstPn.atualizaListaBaseConformeFiltros();

		this.lblValorBc.setText(NumberFormat.getCurrencyInstance().format(this.nota.getBaseCalculoIcmsTotal()));
		this.lblValorCofins.setText(NumberFormat.getCurrencyInstance().format(this.nota.getCofinsTotal()));
		this.lblValorIcms.setText(NumberFormat.getCurrencyInstance().format(this.nota.getIcmsTotal()));
		this.lblValorIcmsSt.setText(NumberFormat.getCurrencyInstance().format(this.nota.getIcmsSTTotal()));
		this.lblValorIpi.setText(NumberFormat.getCurrencyInstance().format(this.nota.getTotalIpi()));
		this.lblValorSeguro.setText(NumberFormat.getCurrencyInstance().format(this.nota.getSeguroTotal()));
		this.lblValorTotal.setText(NumberFormat.getCurrencyInstance().format(this.nota.getValorTotalNota()));
		this.lblVPis.setText(NumberFormat.getCurrencyInstance().format(this.nota.getPisTotal()));
		this.lblIcmsDeson.setText(NumberFormat.getCurrencyInstance().format(this.nota.getIcmsDesoneradoTotal()));
		this.lblVDesc.setText(NumberFormat.getCurrencyInstance().format(this.nota.getDescontoTotal()));

	}

	private void setVencimentos(Nota n) {

		this.lstVn = new ListModelGenerica<Vencimento>(n.getVencimentos(), Vencimento.class,
				new ProvedorDeEventos<Vencimento>() {

					@Override
					public void atualizar(List<Vencimento> lista, Vencimento elemento) throws Exception {
						// TODO Auto-generated method stub

					}

					@Override
					public void deletar(List<Vencimento> lista, Vencimento elemento) throws Exception {
						// TODO Auto-generated method stub

					}

					@Override
					public void cadastrar(List<Vencimento> lista, Vencimento elemento) throws Exception {
						// TODO Auto-generated method stub

					}

				}, RepresentadorVencimento.class);
		this.tblVencimentos.setModel(this.lstVn);

	}

	private void setProdutos(Nota n) {

		this.lstPn = new ListModelGenerica<ProdutoNota>(n.getProdutos(), ProdutoNota.class,
				new ProvedorDeEventos<ProdutoNota>() {

					@Override
					public void atualizar(List<ProdutoNota> lista, ProdutoNota elemento) throws Exception {
						// TODO Auto-generated method stub
						atualizarValores();
					}

					@Override
					public void deletar(List<ProdutoNota> lista, ProdutoNota elemento) throws Exception {
						// TODO Auto-generated method stub

					}

					@Override
					public void cadastrar(List<ProdutoNota> lista, ProdutoNota elemento) throws Exception {
						// TODO Auto-generated method stub

					}

				}, RepresentadorProdutoNota.class);
		this.tblProdutos.setModel(this.lstPn);

	}

	private void setNota(Nota n) {

		boolean eq = this.nota == n;

		if (this.nota != null) {

			if (!eq) {
				try {

					this.nota.getVencimentos().removeIf(v -> v.getId() == 0);
					this.nota.getProdutos().removeIf(p -> p.getId() == 0);

				} catch (Exception ex) {

				}
			}

		}

		this.nota = n;

		if (!eq) {

			this.txtNumero.requestFocus();

			this.cboEntradaSaida.setSelectedItem(this.nota.getOperacao());
			
			this.btNovaNota.setEnabled(this.nota.getId() > 0);

			this.cboModelo.setSelectedItem(this.nota.getModelo());
			this.cboSituacao.setSelectedItem(this.nota.getStatus());
			this.cboTipo.setSelectedItem(this.nota.getTipo());
			this.txtData.setValue(new ConversorDate().paraString(this.nota.getData_emissao()));

			this.txtEmit.setText(n.getEmitente().getNome());
			this.lblCnpjEmitente.setText("CNPJ: " + n.getEmitente().getCnpj());
			this.lblIEemitente.setText("IE: " + n.getEmitente().getInscricao_estadual());
			this.lblCidadeEmitente.setText(n.getEmitente().getEndereco().getCidade() + " - "
					+ n.getEmitente().getEndereco().getCidade().getEstado().getSigla());

			if (n.getDestinatario() != null) {

				this.txtDestinatario.setText(n.getDestinatario().getNome());

				if (n.getDestinatario().getClass().equals(PessoaFisica.class)) {

					PessoaFisica d = (PessoaFisica) n.getDestinatario();

					this.lblInfoDestinatario.setText("CPF: " + d.getCpf());
					this.lblInfo2Destinatario.setText("RG:" + d.getRg());

					if (d.getEndereco() != null)
						this.lblCidadeDestinatario.setText(d.getEndereco().getCidade() + " - "
								+ d.getEndereco().getCidade().getEstado().getSigla());

				} else {

					PessoaJuridica d = (PessoaJuridica) n.getDestinatario();

					this.lblInfoDestinatario.setText("CNPJ: " + d.getCnpj());
					this.lblInfo2Destinatario.setText("IE:" + d.getInscricao_estadual());

					if (d.getEndereco() != null)
						this.lblCidadeDestinatario.setText(d.getEndereco().getCidade() + " - "
								+ d.getEndereco().getCidade().getEstado().getSigla());

				}

			} else {

				this.txtDestinatario.setText("Sem destinatario");
				this.lblInfoDestinatario.setText("");
				this.lblInfo2Destinatario.setText("");
				this.lblCidadeDestinatario.setText("");

			}

			if (n.getTransportadora() != null) {

				this.txtTransportadora.setText(n.getTransportadora().getPj().getNome());
				this.lblCnpjTransp.setText(n.getTransportadora().getPj().getCnpj());
				this.lblIeTransp.setText(n.getTransportadora().getPj().getInscricao_estadual());

				if (n.getTransportadora().getPj().getEndereco() != null)
					this.lblCidadeDestinatario.setText(n.getTransportadora().getPj().getEndereco().getCidade() + " - "
							+ n.getTransportadora().getPj().getEndereco().getCidade().getEstado().getSigla());

			} else {

				this.txtTransportadora.setText("Sem transportadora");
				this.lblIeTransp.setText("");
				this.lblCidadeDestinatario.setText("");

			}

			this.txtChave.setText(n.getChave());
			this.txtNumero.setText(n.getNumero() + "");
			this.txtSerie.setText(n.getSerie() + "");

		}

		this.setProdutos(n);
		this.setVencimentos(n);

		this.atualizarValores();

	}

	private Produto aAdicionar;
	private JFormattedTextField txtData;
	private JComboBox<SaidaEntrada> cboEntradaSaida;

	private void salvarNota() {

		if (!vc(this.txtChave) || !vc(this.txtData) || !vc(this.txtNumero) || !vc(this.txtSerie)) {

			return;

		}

		if (this.nota.getEmitente() == null) {

			erro("Selecione um emitente");
			return;

		}

		try {

			this.nota.setChave(this.txtChave.getText());
			this.nota.setData_emissao(Masks.getData(this.txtData.getText()));
			this.nota.setNumero(Integer.parseInt(this.txtNumero.getText()));
			this.nota.setSerie(Integer.parseInt(this.txtSerie.getText()));

			this.nota.setOperacao((SaidaEntrada)this.cboEntradaSaida.getSelectedItem());
			
			this.nota.setModelo((ModeloNota) this.cboModelo.getSelectedItem());
			this.nota.setStatus((StatusNota) this.cboSituacao.getSelectedItem());
			this.nota.setTipo((TipoNota) this.cboTipo.getSelectedItem());

		} catch (Exception ex) {

			ex.printStackTrace();

			erro("Preencha os dados adequadamente");
			return;

		}

		try {

			if (!new NotaService(et).verificacaoPersistencia(nota)) {

				erro("As alteracoes efetuadas nao podem ser salvas");
				return;

			}

			new NotaService(et).merge(nota);

			et.getTransaction().begin();
			et.getTransaction().commit();

			this.setNota(nota);
			
		} catch (Exception ex) {

			ex.printStackTrace();

			info("A nota não tem integridade, verifique se os vencimentos batem com o valor total");

			return;

		}

		this.btNovaNota.doClick();

		this.grNt.atualizar();

		info("Operacao efetuada com sucesso");

	}

	public void init(Usuario usu) {

		this.operador = et.merge(usu);
		this.empresa = this.operador.getPf().getEmpresa();
		et.detach(this.operador);
		this.empresa = et.merge(this.empresa);

		// ===================

		this.cboEntradaSaida.setModel(new DefaultComboBoxModel<SaidaEntrada>(SaidaEntrada.values()));
		
		this.btAddVencimento.addActionListener(x -> {

			if (!vc(this.txtDataVencimento) || !vc(this.txtValorVencimento)) {

				return;

			}

			try {

				Vencimento v = new Vencimento();
				v.setValor(((Number) this.txtValorVencimento.getValue()).doubleValue());
				v.setData(Masks.getData(this.txtDataVencimento.getText()));
				v.setNota(this.nota);

				// et.persist(v);

				this.nota.getVencimentos().add(v);

				this.setVencimentos(this.nota);

			} catch (Exception ex) {

				erro("Preencha adequadamente");

			}

		});

		this.btSlproduto.addActionListener(x -> {

			ProdutoService ps = new ProdutoService(et);
			ps.setEmpresa(empresa);

			Seletor<Produto> s = new Seletor<Produto>(Produto.class, ps, null, p -> {

				this.aAdicionar = p;
				this.txtNomeProduto.setText(p.getNome());

			}, this);
			s.setVisible(true);

		});

		this.btEmit.addActionListener(x -> {

			PessoaJuridicaService ps = new PessoaJuridicaService(et);
			ps.setEmpresa(empresa);

			Seletor<PessoaJuridica> s = new Seletor<PessoaJuridica>(PessoaJuridica.class, ps,
					RepresentadorPessoaJuridicaSimples.class, p -> {

						this.nota.setEmitente(p);
						this.setNota(this.nota);

					}, this);
			s.setVisible(true);

		});

		this.btTransportadora.addActionListener(x -> {

			TransportadoraService ps = new TransportadoraService(et);
			ps.setEmpresa(empresa);

			Seletor<Transportadora> s = new Seletor<Transportadora>(Transportadora.class, ps,
					RepresentadorTransportadoraSimples.class, p -> {

						this.nota.setTransportadora(p);
						this.setNota(this.nota);

					}, this);
			s.setVisible(true);

		});

		this.btDestinatario.addActionListener(x -> {

			PessoaService ps = new PessoaService(et);
			ps.setEmpresa(empresa);

			Seletor<Pessoa> s = new Seletor<Pessoa>(Pessoa.class, ps, RepresentadorPessoa.class, p -> {

				this.nota.setDestinatario(p);
				this.setNota(this.nota);

			}, this);
			s.setVisible(true);

		});

		this.btAddProduto.addActionListener(x -> {

			if (this.aAdicionar == null) {

				erro("Selecione um produto primeiro");
				return;

			}

			if (!vc(this.txtValorProduto) || !vc(this.txtQtdProduto)) {

				return;

			}

			ProdutoNota pn = new ProdutoNota();
			pn.setNota(this.nota);
			pn.setProduto(et.merge(aAdicionar));
			pn.setValor(((Number) this.txtValorProduto.getValue()).doubleValue());
			pn.setQuantidade(((Number) this.txtQtdProduto.getValue()).doubleValue());

			pn.setCfop(et.merge(aAdicionar.getCategoria().getTabelaCfop().getCFOP(this.nota.getOperacaoLogistica())));

			// et.persist(pn);

			this.nota.getProdutos().add(pn);

			aAdicionar = null;
			this.txtNomeProduto.setText("");

			this.atualizarValores();

		});

		this.cboModelo.setModel(new DefaultComboBoxModel<ModeloNota>(ModeloNota.values()));
		this.cboSituacao.setModel(new DefaultComboBoxModel<StatusNota>(StatusNota.values()));
		this.cboTipo.setModel(new DefaultComboBoxModel<TipoNota>(TipoNota.values()));

		NotaService ns = new NotaService(et);
		ns.setEmpresa(empresa);

		this.grNt = new GerenciadorLista<Nota>(Nota.class, this.tblNotas, ns, null, new Nota(),
				RepresentadorNotaCompleto.class);
		this.grNt.setFiltro(this.txtPesquisar);
		this.grNt.setLblPagina(this.lblPg);
		this.grNt.setLblSlider(this.slPg);

		this.grNt.atualizar();

		this.tblNotas.getSelectionModel().addListSelectionListener(x -> {

			if (!x.getValueIsAdjusting()) {
				if (this.tblNotas.getSelectedRow() >= 0) {

					this.setNota(this.grNt.getModel().getListaBase().get(this.tblNotas.getSelectedRow()));

				}
			}

		});

		this.btNovaNota.addActionListener(x -> {

			Nota n = new Nota();
			n.setEmitente(empresa.getPj());
			n.setEmpresa(empresa);
			this.setNota(n);

		});

		this.btConfirmar.addActionListener(x -> {

			this.salvarNota();

		});

		this.btNovaNota.doClick();

	}

	/**
	 * Create the frame.
	 */
	public Notas() {
		setTitle("Notas");
		setResizable(false);
		setBounds(100, 100, 938, 688);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNotas = new JLabel("Entradas e Saidas");
		lblNotas.setFont(new Font("Arial", Font.PLAIN, 22));
		lblNotas.setBounds(10, 11, 624, 42);
		contentPane.add(lblNotas);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 51, 310, 2);
		contentPane.add(separator);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "De", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 64, 310, 139);
		contentPane.add(panel);
		panel.setLayout(null);

		txtEmit = new JTextField();
		txtEmit.setEnabled(false);
		txtEmit.setBounds(10, 25, 224, 20);
		panel.add(txtEmit);
		txtEmit.setColumns(10);

		btEmit = new JButton("...");
		btEmit.setBounds(244, 24, 56, 23);
		panel.add(btEmit);

		lblCnpjEmitente = new JLabel("CNPJ: 123423421");
		lblCnpjEmitente.setBounds(10, 56, 224, 14);
		panel.add(lblCnpjEmitente);

		lblIEemitente = new JLabel("IE: 123423421");
		lblIEemitente.setBounds(10, 81, 224, 14);
		panel.add(lblIEemitente);

		lblCidadeEmitente = new JLabel("Guarulhos - SP");
		lblCidadeEmitente.setBounds(10, 106, 224, 14);
		panel.add(lblCidadeEmitente);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Para", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(330, 64, 310, 139);
		contentPane.add(panel_1);

		txtDestinatario = new JTextField();
		txtDestinatario.setEnabled(false);
		txtDestinatario.setColumns(10);
		txtDestinatario.setBounds(10, 25, 224, 20);
		panel_1.add(txtDestinatario);

		btDestinatario = new JButton("...");
		btDestinatario.setBounds(244, 24, 56, 23);
		panel_1.add(btDestinatario);

		lblInfoDestinatario = new JLabel("CNPJ: 123423421");
		lblInfoDestinatario.setBounds(10, 56, 224, 14);
		panel_1.add(lblInfoDestinatario);

		lblInfo2Destinatario = new JLabel("IE: 123423421");
		lblInfo2Destinatario.setBounds(10, 81, 224, 14);
		panel_1.add(lblInfo2Destinatario);

		lblCidadeDestinatario = new JLabel("Guarulhos - SP");
		lblCidadeDestinatario.setBounds(10, 106, 224, 14);
		panel_1.add(lblCidadeDestinatario);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(
				new TitledBorder(null, "Informacoes Nota", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 211, 310, 152);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblNewLabel = new JLabel("Numero:");
		lblNewLabel.setBounds(10, 23, 53, 14);
		panel_2.add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("Serie:");
		lblNewLabel_2.setBounds(10, 46, 53, 14);
		panel_2.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Chave:");
		lblNewLabel_3.setBounds(10, 68, 53, 14);
		panel_2.add(lblNewLabel_3);

		JLabel lblSituacao_1 = new JLabel("Situacao:");
		lblSituacao_1.setBounds(10, 93, 53, 14);
		panel_2.add(lblSituacao_1);

		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(147, 23, 53, 14);
		panel_2.add(lblTipo);

		txtNumero = new JTextField();
		txtNumero.setBounds(57, 20, 80, 20);
		panel_2.add(txtNumero);
		txtNumero.setColumns(10);

		txtSerie = new JTextField();
		txtSerie.setColumns(10);
		txtSerie.setBounds(44, 43, 93, 20);
		panel_2.add(txtSerie);

		txtChave = new JTextField();
		txtChave.setColumns(10);
		txtChave.setBounds(57, 65, 231, 20);
		panel_2.add(txtChave);

		cboSituacao = new JComboBox<StatusNota>();
		cboSituacao.setBounds(56, 90, 232, 20);
		panel_2.add(cboSituacao);

		cboTipo = new JComboBox<TipoNota>();
		cboTipo.setBounds(175, 20, 112, 20);
		panel_2.add(cboTipo);

		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setBounds(147, 46, 43, 14);
		panel_2.add(lblModelo);

		cboModelo = new JComboBox<ModeloNota>();
		cboModelo.setBounds(185, 43, 102, 20);
		panel_2.add(cboModelo);

		JLabel lblData = new JLabel("Data:");
		lblData.setBounds(10, 118, 53, 14);
		panel_2.add(lblData);

		txtData = new JFormattedTextField();
		txtData.setBounds(57, 118, 102, 20);
		panel_2.add(txtData);

		Masks.data().install(txtData);
		
		cboEntradaSaida = new JComboBox<SaidaEntrada>();
		cboEntradaSaida.setBounds(169, 118, 119, 20);
		panel_2.add(cboEntradaSaida);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Totais", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(620, 211, 301, 152);
		contentPane.add(panel_3);
		panel_3.setLayout(null);

		lblValorBc1 = new JLabel("Valor BC:");
		lblValorBc1.setBounds(10, 22, 53, 14);
		panel_3.add(lblValorBc1);

		lblValorIcms1 = new JLabel("Valor ICMS:");
		lblValorIcms1.setBounds(10, 41, 71, 14);
		panel_3.add(lblValorIcms1);

		lblValorIcmsSt1 = new JLabel("Valor ICMS ST:");
		lblValorIcmsSt1.setBounds(10, 62, 71, 14);
		panel_3.add(lblValorIcmsSt1);

		lblValorSeguro1 = new JLabel("Valor Seg:");
		lblValorSeguro1.setBounds(10, 82, 71, 14);
		panel_3.add(lblValorSeguro1);

		JLabel lblValorPis = new JLabel("Valor PIS:");
		lblValorPis.setBounds(10, 101, 71, 14);
		panel_3.add(lblValorPis);

		lblValorCofins1 = new JLabel("Valor COFINS:");
		lblValorCofins1.setBounds(155, 22, 71, 14);
		panel_3.add(lblValorCofins1);

		lblValorCofinsSt1 = new JLabel("Valor ICMS Des:");
		lblValorCofinsSt1.setBounds(155, 41, 83, 14);
		panel_3.add(lblValorCofinsSt1);

		lblValorIpiSt = new JLabel("Valor Desconto:");
		lblValorIpiSt.setBounds(155, 82, 93, 14);
		panel_3.add(lblValorIpiSt);

		lblValorIpi1 = new JLabel("Valor IPI:");
		lblValorIpi1.setBounds(155, 62, 93, 14);
		panel_3.add(lblValorIpi1);

		lblValorTotal1 = new JLabel("Total Produtos:");
		lblValorTotal1.setBounds(155, 101, 93, 14);
		panel_3.add(lblValorTotal1);

		lblValorBc = new JLabel("Valor BC:");
		lblValorBc.setFont(new Font("Arial", Font.BOLD, 12));
		lblValorBc.setBounds(64, 22, 71, 14);
		panel_3.add(lblValorBc);

		lblValorIcms = new JLabel("Valor BC:");
		lblValorIcms.setFont(new Font("Arial", Font.BOLD, 12));
		lblValorIcms.setBounds(74, 40, 71, 14);
		panel_3.add(lblValorIcms);

		lblValorIcmsSt = new JLabel("Valor BC:");
		lblValorIcmsSt.setFont(new Font("Arial", Font.BOLD, 12));
		lblValorIcmsSt.setBounds(85, 62, 60, 14);
		panel_3.add(lblValorIcmsSt);

		lblValorSeguro = new JLabel("Valor BC:");
		lblValorSeguro.setFont(new Font("Arial", Font.BOLD, 12));
		lblValorSeguro.setBounds(64, 82, 60, 14);
		panel_3.add(lblValorSeguro);

		lblVPis = new JLabel("Valor BC:");
		lblVPis.setFont(new Font("Arial", Font.BOLD, 12));
		lblVPis.setBounds(64, 101, 60, 14);
		panel_3.add(lblVPis);

		lblValorCofins = new JLabel("Valor BC:");
		lblValorCofins.setFont(new Font("Arial", Font.BOLD, 12));
		lblValorCofins.setBounds(236, 22, 60, 14);
		panel_3.add(lblValorCofins);

		lblIcmsDeson = new JLabel("Valor BC:");
		lblIcmsDeson.setFont(new Font("Arial", Font.BOLD, 12));
		lblIcmsDeson.setBounds(236, 41, 60, 14);
		panel_3.add(lblIcmsDeson);

		lblValorIpi = new JLabel("Valor BC:");
		lblValorIpi.setFont(new Font("Arial", Font.BOLD, 12));
		lblValorIpi.setBounds(204, 62, 60, 14);
		panel_3.add(lblValorIpi);

		lblVDesc = new JLabel("Valor BC:");
		lblVDesc.setFont(new Font("Arial", Font.BOLD, 12));
		lblVDesc.setBounds(236, 82, 60, 14);
		panel_3.add(lblVDesc);

		lblValorTotal = new JLabel("Valor BC:");
		lblValorTotal.setForeground(Color.RED);
		lblValorTotal.setFont(new Font("Arial", Font.BOLD, 12));
		lblValorTotal.setBounds(236, 101, 60, 14);
		panel_3.add(lblValorTotal);

		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Transporte",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(650, 64, 271, 139);
		contentPane.add(panel_4);

		txtTransportadora = new JTextField();
		txtTransportadora.setEnabled(false);
		txtTransportadora.setColumns(10);
		txtTransportadora.setBounds(10, 25, 170, 20);
		panel_4.add(txtTransportadora);

		btTransportadora = new JButton("...");
		btTransportadora.setBounds(190, 24, 56, 23);
		panel_4.add(btTransportadora);

		lblCnpjTransp = new JLabel("CNPJ:");
		lblCnpjTransp.setBounds(10, 56, 224, 14);
		panel_4.add(lblCnpjTransp);

		lblIeTransp = new JLabel("IE:");
		lblIeTransp.setBounds(10, 81, 224, 14);
		panel_4.add(lblIeTransp);

		lblLocalTransp = new JLabel("Guarulhos - SP");
		lblLocalTransp.setBounds(10, 106, 224, 14);
		panel_4.add(lblLocalTransp);

		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Vencimentos",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_5.setBounds(330, 211, 280, 152);
		contentPane.add(panel_5);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 61, 260, 77);
		panel_5.add(scrollPane);

		tblVencimentos = new JTable();
		scrollPane.setViewportView(tblVencimentos);

		txtDataVencimento = new JFormattedTextField();
		txtDataVencimento.setBounds(10, 38, 138, 20);
		panel_5.add(txtDataVencimento);

		Masks.data().install(txtDataVencimento);

		txtValorVencimento = new JFormattedTextField();
		txtValorVencimento.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
		txtValorVencimento.setBounds(150, 38, 63, 20);
		panel_5.add(txtValorVencimento);

		btAddVencimento = new JButton("+");
		btAddVencimento.setBounds(216, 37, 56, 23);
		panel_5.add(btAddVencimento);

		JLabel lblData_1 = new JLabel("Data");
		lblData_1.setBounds(10, 25, 46, 14);
		panel_5.add(lblData_1);

		JLabel lblValor = new JLabel("Valor");
		lblValor.setBounds(150, 25, 46, 14);
		panel_5.add(lblValor);

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Produtos", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_6.setBounds(10, 368, 818, 115);
		contentPane.add(panel_6);
		panel_6.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 22, 621, 82);
		panel_6.add(scrollPane_1);

		tblProdutos = new JTable();
		scrollPane_1.setViewportView(tblProdutos);

		btSlproduto = new JButton("...");
		btSlproduto.setBounds(752, 22, 56, 23);
		panel_6.add(btSlproduto);

		txtNomeProduto = new JTextField();
		txtNomeProduto.setEnabled(false);
		txtNomeProduto.setColumns(10);
		txtNomeProduto.setBounds(641, 23, 107, 20);
		panel_6.add(txtNomeProduto);

		JLabel Valor = new JLabel("Valor");
		Valor.setBounds(645, 50, 46, 14);
		panel_6.add(Valor);

		JLabel lblQtd = new JLabel("Qtd");
		lblQtd.setBounds(701, 50, 46, 14);
		panel_6.add(lblQtd);

		txtValorProduto = new JFormattedTextField();
		txtValorProduto.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
		txtValorProduto.setBounds(645, 73, 46, 20);
		panel_6.add(txtValorProduto);

		txtQtdProduto = new JFormattedTextField();
		txtQtdProduto.setBounds(701, 73, 46, 20);
		panel_6.add(txtQtdProduto);

		txtQtdProduto.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));

		btAddProduto = new JButton("+");
		btAddProduto.setBounds(752, 56, 56, 37);
		panel_6.add(btAddProduto);

		btConfirmar = new JButton("Confirmar");
		btConfirmar.setBounds(838, 374, 83, 42);
		contentPane.add(btConfirmar);

		btNovaNota = new JButton("Nova Nota");
		btNovaNota.setBounds(838, 427, 83, 42);
		contentPane.add(btNovaNota);

		JLabel lblNewLabel_1 = new JLabel("Pesquisar Notas:");
		lblNewLabel_1.setBounds(10, 494, 142, 14);
		contentPane.add(lblNewLabel_1);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 519, 914, 130);
		contentPane.add(scrollPane_2);

		tblNotas = new JTable();
		scrollPane_2.setViewportView(tblNotas);

		txtPesquisar = new JTextField();
		txtPesquisar.setBounds(94, 491, 316, 20);
		contentPane.add(txtPesquisar);
		txtPesquisar.setColumns(10);

		lblPg = new JLabel("New label");
		lblPg.setBounds(879, 494, 46, 14);
		contentPane.add(lblPg);

		slPg = new JSlider();
		slPg.setBounds(420, 482, 449, 26);
		contentPane.add(slPg);
	}
}
