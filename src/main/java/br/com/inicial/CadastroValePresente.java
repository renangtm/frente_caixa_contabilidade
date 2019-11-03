package br.com.inicial;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultFormatterFactory;

import br.com.base.CFG;
import br.com.base.ET;
import br.com.base.Masks;
import br.com.base.Resources;
import br.com.cfop.CFOP;
import br.com.cfop.CFOPService;
import br.com.cfop.ItemTabelaCfop;
import br.com.cfop.TabelaCfop;
import br.com.empresa.Empresa;
import br.com.endereco.Estado;
import br.com.entidades.ncm.NCM;
import br.com.entidades.ncm.NCMService;
import br.com.imposto.cofins.COFINSAliq1;
import br.com.imposto.pis.PISAliq1;
import br.com.impostos.icms.EstadoAliquota;
import br.com.impostos.icms.ICMS40;
import br.com.impostos.icms.TabelaAlicotas;
import br.com.nota.OperacaoLogistica;
import br.com.nota.TipoNota;
import br.com.produto.Categoria;
import br.com.produto.ProdutoService;
import br.com.produto.RepTokenVale;
import br.com.produto.RepresentadorValePresente;
import br.com.produto.ValePresente;
import br.com.produto.ValePresenteService;
import br.com.quantificacao.UnidadePeso;
import br.com.quantificacao.UnidadeVolume;
import br.com.usuario.Usuario;
import br.com.utilidades.ListModelGenerica;

import javax.swing.JFormattedTextField;
import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.io.IOException;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Color;

public class CadastroValePresente extends Modulo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable tblCupons;

	private ListModelGenerica<ValePresente> lstValePresente;

	private ListModelGenerica<RepTokenVale> rtv;

	private JFormattedTextField txtPreco;
	private JFormattedTextField txtDescricao;
	private JFormattedTextField txtValor;
	private JTable tblRepCodigo;

	private JButton btNovoCupom;

	private JFormattedTextField txtQuantidade;

	public static ImageIcon logo() {

		try {
			return Resources.getConfig();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Cadastro de Cupom de Desconto";

	}

	private ValePresente vp;

	private void setValePresente(ValePresente vp) {

		this.vp = vp;

		this.txtDescricao.setText(vp.getNome());
		this.txtPreco.setValue(vp.getValor());
		this.txtValor.setValue(vp.getValorVale());
		this.txtQuantidade.setText(((int) vp.getEstoque().getDisponivel()) + "");

		this.txtQuantidade.setEnabled(vp.getId() == 0);

		this.rtv = new ListModelGenerica<RepTokenVale>(this.vp.getTokens(), RepTokenVale.class);
		this.tblRepCodigo.setModel(this.rtv);

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		EntityManager et = ET.nova();

		Usuario usuario = et.find(Usuario.class, 1);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					CadastroValePresente frame = new CadastroValePresente();
					frame.init(usuario);
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void confirmar() {

		if (!vc(this.txtDescricao) || !vc(this.txtPreco) || !vc(this.txtQuantidade) || !vc(this.txtValor)) {

			return;

		}

		try {

			Integer.parseInt(this.txtQuantidade.getText());

		} catch (Exception ex) {

			erro("Digite a quantidade corretamente");
			this.txtQuantidade.requestFocus();
			return;

		}

		this.vp.setNome(this.txtDescricao.getText());
		this.vp.setValor(((Number) this.txtPreco.getValue()).doubleValue());
		this.vp.setValorVale(((Number) this.txtValor.getValue()).doubleValue());
		this.vp.setCusto(this.vp.getValorVale());

		try {

			this.vp.getEstoque().setQuantidades(Integer.parseInt(this.txtQuantidade.getText()),
					Integer.parseInt(this.txtQuantidade.getText()));
			this.vp.setEstoqueInicial((int) this.vp.getEstoque().getQuantidade());

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.vp = (ValePresente) new ProdutoService(et).merge(this.vp);

		if (this.vp.getId() == 0) {

			this.lstValePresente.add(this.vp);

		}

		et.getTransaction().begin();
		et.getTransaction().commit();

		info("Operacao executada com sucesso");

		this.setValePresente(this.vp);

	}

	@SuppressWarnings("unused")
	private Usuario operador;

	private Empresa empresa;

	private JButton btnConfirmar;

	@Override
	public void init(Usuario u) {

		this.operador = et.merge(u);
		this.empresa = et.merge(CFG.empresa);

		ValePresenteService vs = new ValePresenteService(this.et);
		vs.setEmpresa(this.empresa);

		this.lstValePresente = new ListModelGenerica<ValePresente>(vs.getTodosComEstoque(), ValePresente.class,
				RepresentadorValePresente.class);

		this.tblCupons.setModel(this.lstValePresente);

		this.tblCupons.getSelectionModel().addListSelectionListener(a -> {

			if (!a.getValueIsAdjusting()) {

				if (this.tblCupons.getSelectedRow() >= 0) {

					this.setValePresente(this.lstValePresente.getListaBase().get(this.tblCupons.getSelectedRow()));

				}

			}

		});

		@SuppressWarnings("unchecked")
		List<Estado> todosEstados = this.et.createQuery("SELECT e FROM Estado e").getResultList();

		this.btNovoCupom.addActionListener(a -> {

			Categoria cat = new Categoria();

			cat.setCofins(new COFINSAliq1());
			cat.setPis(new PISAliq1());
			cat.setIcms(new ICMS40());

			TabelaAlicotas ta = new TabelaAlicotas();

			for (Estado e : todosEstados) {

				for (Estado e2 : todosEstados) {

					for (TipoNota tn : TipoNota.values()) {
						EstadoAliquota ea = new EstadoAliquota();
						ea.setAliquota(0);
						ea.setDestino(e);
						ea.setOrigem(e2);
						ea.setOpl(tn);

						ta.getAlicotas().add(ea);

					}

				}

			}

			cat.setTabelaAlicota(ta);

			CFOPService cs = new CFOPService(et);

			CFOP venda = cs.getPeloCodigo("5152");

			if (venda == null) {
				venda = new CFOP();
				venda.setDescricao("Venda de mercadoria");
				venda.setNumero("5152");
				et.persist(venda);
			}

			CFOP vendafe = cs.getPeloNumero("6152");

			if (vendafe == null) {
				vendafe = new CFOP();
				vendafe.setDescricao("Venda de mercadoria");
				vendafe.setNumero("6152");
				et.persist(vendafe);
			}

			TabelaCfop tbc = new TabelaCfop();

			ItemTabelaCfop itemvenda = new ItemTabelaCfop();
			itemvenda.setCfop(venda);
			itemvenda.setOperacao(OperacaoLogistica.VENDA_DENTRO_ESTADO);

			ItemTabelaCfop itemvendafe = new ItemTabelaCfop();
			itemvenda.setCfop(vendafe);
			itemvenda.setOperacao(OperacaoLogistica.VENDA_FORA_ESTADO);

			tbc.getItens().add(itemvenda);
			tbc.getItens().add(itemvendafe);

			cat.setTabelaCfop(tbc);

			NCMService ns = new NCMService(et);
			NCM ncm = ns.getPeloNumero("32432121345");

			if (ncm == null) {

				ncm = new NCM();
				ncm.setDescricao("32432121345");
				ncm.setNumero("32432121345");
				et.persist(ncm);

			}

			ValePresente vp = new ValePresente();
			vp.setCategoria(cat);
			vp.setCodigo_barra((Math.random() * 999999) + "");
			vp.setEmpresa(this.empresa);
			vp.setNcm(ncm);
			vp.setPeso(1);
			vp.setUnidade_volume(UnidadeVolume.ML);
			vp.setUnidadePeso(UnidadePeso.KG);
			vp.setUnidadePeso(UnidadePeso.KG);
			vp.setVolume(1);

			this.setValePresente(vp);

		});

		this.btNovoCupom.doClick();

		this.btnConfirmar.addActionListener(a -> {

			this.confirmar();

		});

	}

	/**
	 * Create the frame.
	 */

	public CadastroValePresente() {
		setTitle("Cadastro de Cupom de Desconto");
		setBounds(100, 100, 559, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Cadastro de Cupom de Desconto");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 11, 346, 38);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 47, 285, 2);
		contentPane.add(separator);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dados do Cupom",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 60, 523, 106);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNcm = new JLabel("Valor:");
		lblNcm.setBounds(10, 22, 71, 14);
		panel.add(lblNcm);

		JLabel lblDescricao = new JLabel("Descricao:");
		lblDescricao.setBounds(10, 50, 71, 14);
		panel.add(lblDescricao);

		JLabel lblCodEx = new JLabel("Preco:");
		lblCodEx.setBounds(297, 22, 45, 14);
		panel.add(lblCodEx);

		txtValor = new JFormattedTextField();
		txtValor.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
		txtValor.setBounds(100, 19, 125, 20);
		panel.add(txtValor);

		txtDescricao = new JFormattedTextField();
		txtDescricao.setBounds(81, 47, 333, 20);
		panel.add(txtDescricao);

		txtPreco = new JFormattedTextField();
		txtPreco.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
		txtPreco.setBounds(352, 19, 62, 20);
		panel.add(txtPreco);

		JLabel lblQuantidade = new JLabel("Quantidade:");
		lblQuantidade.setBounds(10, 75, 71, 14);
		panel.add(lblQuantidade);

		txtQuantidade = new JFormattedTextField();
		txtQuantidade.setBounds(81, 72, 90, 20);
		panel.add(txtQuantidade);

		btNovoCupom = new JButton("Novo Cupom");
		btNovoCupom.setBounds(194, 71, 109, 23);
		panel.add(btNovoCupom);

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(313, 71, 101, 23);
		panel.add(btnConfirmar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 177, 276, 220);
		contentPane.add(scrollPane);

		tblCupons = new JTable();
		scrollPane.setViewportView(tblCupons);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(296, 177, 237, 220);
		contentPane.add(scrollPane_1);

		tblRepCodigo = new JTable();
		scrollPane_1.setViewportView(tblRepCodigo);

	}
}
