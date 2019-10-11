package br.com.inicial;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultFormatterFactory;

import br.com.base.ET;
import br.com.base.Masks;
import br.com.base.Resources;
import br.com.cfop.ItemTabelaCfop;
import br.com.cfop.RepresentadorItemTabelaCfop;
import br.com.cfop.TabelaCfop;
import br.com.empresa.Empresa;
import br.com.endereco.Estado;
import br.com.imposto.cofins.COFINSAliq1;
import br.com.imposto.cofins.COFINSAliq2;
import br.com.imposto.cofins.Cofins;
import br.com.imposto.pis.PISAliq1;
import br.com.imposto.pis.PISAliq2;
import br.com.imposto.pis.PISOutr;
import br.com.imposto.pis.PISSt;
import br.com.imposto.pis.Pis;
import br.com.impostos.icms.EstadoAliquota;
import br.com.impostos.icms.ICMS00;
import br.com.impostos.icms.ICMS10;
import br.com.impostos.icms.ICMS20;
import br.com.impostos.icms.ICMS30;
import br.com.impostos.icms.ICMS40;
import br.com.impostos.icms.ICMS41;
import br.com.impostos.icms.ICMS50;
import br.com.impostos.icms.ICMS51;
import br.com.impostos.icms.ICMS60;
import br.com.impostos.icms.ICMS70;
import br.com.impostos.icms.ICMS90;
import br.com.impostos.icms.Icms;
import br.com.impostos.icms.ModalidadeBC;
import br.com.impostos.icms.ModalidadeBCST;
import br.com.impostos.icms.OrigemMercadoria;
import br.com.impostos.icms.TabelaAlicotas;
import br.com.impostos.icms.TabelaIcms;
import br.com.nota.OperacaoLogistica;
import br.com.nota.TipoNota;
import br.com.produto.Categoria;
import br.com.produto.CategoriaService;
import br.com.produto.Produto;
import br.com.produto.ProdutoService;
import br.com.usuario.Usuario;
import br.com.utilidades.ListModelGenerica;

public class Categorias extends Modulo {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<Cofins> cboTipoCofins;
	private JFormattedTextField txtAliqCofins;
	private JComboBox<Pis> cboTipoPis;
	private JFormattedTextField txtAliqPis;

	private EntityManager etReq;
	
	
	
	private Categoria cat;
	
	public static ImageIcon logo() {

		try {
			return Resources.getCotacoes();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Categorias de Produto";

	}

	private void salvarCategoria() {

		if (!this.validarContainer((Container) this.pnlIcms)) {

			return;

		}

		Cofins cof = (Cofins) ((Cofins) this.cboTipoCofins.getSelectedItem()).clone();

		if (cof.getClass().equals(COFINSAliq1.class)) {

			COFINSAliq1 a1 = (COFINSAliq1) cof;
			a1.setAliquotaCofins(((Number) this.txtAliqCofins.getValue()).doubleValue());

			System.out.println(((Number) this.txtAliqCofins.getValue()).doubleValue()+"-----------");
			
		} else if (cof.getClass().equals(COFINSAliq2.class)) {

			COFINSAliq2 a2 = (COFINSAliq2) cof;
			a2.setAliquotaCofins(((Number) this.txtAliqCofins.getValue()).doubleValue());

		}

		this.cat.setCofins(cof);

		Pis pis = (Pis) ((Pis) this.cboTipoPis.getSelectedItem()).clone();

		if (pis.getClass().equals(PISAliq1.class)) {

			PISAliq1 a1 = (PISAliq1) pis;
			a1.setAlicotaPis(((Number) this.txtAliqPis.getValue()).doubleValue());

		} else if (pis.getClass().equals(PISAliq2.class)) {

			PISAliq2 a2 = (PISAliq2) pis;
			a2.setAlicotaPis(((Number) this.txtAliqPis.getValue()).doubleValue());

		} else if (pis.getClass().equals(PISOutr.class)) {

			PISOutr a2 = (PISOutr) pis;
			a2.setAlicotaPis(((Number) this.txtAliqPis.getValue()).doubleValue());

		} else if (pis.getClass().equals(PISSt.class)) {

			PISSt a2 = (PISSt) pis;
			a2.setAlicotaPis(((Number) this.txtAliqPis.getValue()).doubleValue());

		}

		this.cat.setPis(pis);

		if (this.cboCst.getSelectedItem().getClass().equals(ICMS00.class)) {

			ICMS00 i = new ICMS00();

			i.setModalidadeBC((ModalidadeBC) this.cboModalidadeBC.getSelectedItem());
			i.setOrigem((OrigemMercadoria) this.cboOrigem.getSelectedItem());


			i.setPorcentagemBC(((Number) this.txtReducaoBase.getValue()).doubleValue());

			this.cat.setIcms(i);

		} else if (this.cboCst.getSelectedItem().getClass().equals(ICMS10.class)) {

			ICMS10 i = new ICMS10();

			i.setModalidadeBC((ModalidadeBC) this.cboModalidadeBC.getSelectedItem());
			i.setOrigem((OrigemMercadoria) this.cboOrigem.getSelectedItem());

			i.setPorcentagemBC(((Number) this.txtReducaoBase.getValue()).doubleValue());

			i.setModalidadeBCST((ModalidadeBCST) this.cboModalidadeBCST.getSelectedItem());

			i.setPorcentagemMargemValorAdicionadoST(
					((Number) this.txtMargemAdcionada.getValue()).doubleValue());

			i.setPorcentagemReducaoBCST(((Number) this.txtReducaoBaseSt.getValue()).doubleValue());

			this.cat.setIcms(i);

		} else if (this.cboCst.getSelectedItem().getClass().equals(ICMS20.class)) {

			ICMS20 i = new ICMS20();

			i.setModalidadeBC((ModalidadeBC) this.cboModalidadeBC.getSelectedItem());
			i.setOrigem((OrigemMercadoria) this.cboOrigem.getSelectedItem());

			i.setPorcentagemReducaoBC(((Number) this.txtReducaoBase.getValue()).doubleValue());

			this.cat.setIcms(i);

		} else if (this.cboCst.getSelectedItem().getClass().equals(ICMS30.class)) {

			ICMS30 i = new ICMS30();

			this.cboModalidadeBC.setSelectedItem(i.getModalidadeBCST());
			this.cboOrigem.setSelectedItem(i.getOrigem());
			this.txtReducaoBase.setValue(i.getPorcentagemReducaoBCST());

			i.setModalidadeBCST((ModalidadeBCST) this.cboModalidadeBCST.getSelectedItem());
			i.setOrigem((OrigemMercadoria) this.cboOrigem.getSelectedItem());

			i.setPorcentagemReducaoBCST(((Number) this.txtReducaoBaseSt.getValue()).doubleValue());

			i.setPorcentagemMargemValorAdicionadoST(
					((Number) this.txtMargemAdcionada.getValue()).doubleValue());

			this.cat.setIcms(i);

		} else if (this.cboCst.getSelectedItem().getClass().equals(ICMS40.class)) {

			ICMS40 i = new ICMS40();

			i.setOrigem((OrigemMercadoria) this.cboOrigem.getSelectedItem());

			this.cat.setIcms(i);

		} else if (this.cboCst.getSelectedItem().getClass().equals(ICMS41.class)) {

			ICMS41 i = new ICMS41();

			i.setOrigem((OrigemMercadoria) this.cboOrigem.getSelectedItem());

			this.cat.setIcms(i);

		} else if (this.cboCst.getSelectedItem().getClass().equals(ICMS50.class)) {

			ICMS50 i = new ICMS50();

			i.setOrigem((OrigemMercadoria) this.cboOrigem.getSelectedItem());

			this.cat.setIcms(i);

		} else if (this.cboCst.getSelectedItem().getClass().equals(ICMS51.class)) {

			ICMS51 i = new ICMS51();

			i.setModalidadeBC((ModalidadeBC) this.cboModalidadeBC.getSelectedItem());
			i.setOrigem((OrigemMercadoria) this.cboOrigem.getSelectedItem());

			i.setPorcentagemReducaoBC(((Number) this.txtReducaoBase.getValue()).doubleValue());

			this.cat.setIcms(i);

		} else if (this.cboCst.getSelectedItem().getClass().equals(ICMS60.class)) {

			ICMS60 i = new ICMS60();

			i.setOrigem((OrigemMercadoria) this.cboOrigem.getSelectedItem());

			this.cat.setIcms(i);

		} else if (this.cboCst.getSelectedItem().getClass().equals(ICMS70.class)) {

			ICMS70 i = new ICMS70();

			i.setModalidadeBC((ModalidadeBC) this.cboModalidadeBC.getSelectedItem());
			i.setOrigem((OrigemMercadoria) this.cboOrigem.getSelectedItem());

			i.setPorcentagemReducaoBC(((Number) this.txtReducaoBase.getValue()).doubleValue());

			i.setModalidadeBCST((ModalidadeBCST) this.cboModalidadeBCST.getSelectedItem());

			i.setPorcentagemMargemValorAdicionadoST(
					((Number) this.txtMargemAdcionada.getValue()).doubleValue());

			i.setPorcentagemReducaoBCST(((Number) this.txtReducaoBaseSt.getValue()).doubleValue());

			this.cat.setIcms(i);

		} else if (this.cboCst.getSelectedItem().getClass().equals(ICMS90.class)) {

			ICMS90 i = new ICMS90();

			i.setModalidadeBC((ModalidadeBC) this.cboModalidadeBC.getSelectedItem());
			i.setOrigem((OrigemMercadoria) this.cboOrigem.getSelectedItem());

			
			i.setPorcentagemReducaoBC(((Number) this.txtReducaoBase.getValue()).doubleValue());

			i.setModalidadeBCST((ModalidadeBCST) this.cboModalidadeBCST.getSelectedItem());

			i.setPorcentagemMargemValorAdicionadoST(
					((Number) this.txtMargemAdcionada.getValue()).doubleValue());

			i.setPorcentagemReducaoBCST(((Number) this.txtReducaoBaseSt.getValue()).doubleValue());

			this.cat.setIcms(i);

		}
		
		new CategoriaService(et).merge(this.cat);
		
		this.dispose();
		
	}

	private void setCategoria(Categoria cat) {

		
		if(this.cat.getTabelaAlicota() == null) {
			
			this.cat.setTabelaAlicota(new TabelaAlicotas());
			
		}
		
		if(this.cat.getTabelaCfop() == null) {
			
			this.cat.setTabelaCfop(new TabelaCfop());
			
		}
		
		if(this.cat.getTabelaAlicota().getAlicotas().isEmpty()) {
			
			@SuppressWarnings("unchecked")
			List<Estado> estados = (List<Estado>)(List<?>)this.etReq.createQuery("SELECT e FROM Estado e").getResultList();
			
			for(Estado origem:estados) {
				for(Estado destino:estados) {
					for(TipoNota opl:TipoNota.values()) {
						EstadoAliquota ea = new EstadoAliquota();
						ea.setOrigem(origem);
						ea.setOpl(opl);
						ea.setDestino(destino);
						ea.setAliquota(TabelaIcms.getIcms(origem, destino));
						ea.setAliquota_st(TabelaIcms.getIcms(origem, destino));
						cat.getTabelaAlicota().getAlicotas().add(ea);
					}
				}
			}
			
			
		}
		
		if(this.cat.getTabelaCfop().getItens().isEmpty()) {
			
			for(OperacaoLogistica op:OperacaoLogistica.values()) {
				
				ItemTabelaCfop i = new ItemTabelaCfop();
				i.setOperacao(op);
				i.setCfop(null);

				this.cat.getTabelaCfop().getItens().add(i);
				
			}
			
		}
		
		this.tblAliquotas.setModel(new ListModelGenerica<EstadoAliquota>(this.cat.getTabelaAlicota().getAlicotas(),EstadoAliquota.class));
		
		this.tblCfop.setModel(new ListModelGenerica<ItemTabelaCfop>(this.cat.getTabelaCfop().getItens(),ItemTabelaCfop.class,RepresentadorItemTabelaCfop.class,etReq));
		
		this.cat = cat;

		this.cboTipoCofins.setSelectedItem(cat.getCofins());

		if (cat.getCofins().getClass().equals(COFINSAliq1.class)) {

			COFINSAliq1 a1 = (COFINSAliq1) cat.getCofins();
			this.cboTipoCofins.setSelectedItem(a1);
			this.txtAliqCofins.setValue(a1.getAliquotaCofins());
			
		} else if (cat.getCofins().getClass().equals(COFINSAliq2.class)) {

			COFINSAliq2 a2 = (COFINSAliq2) cat.getCofins();
			this.cboTipoCofins.setSelectedItem(a2);
			this.txtAliqCofins.setValue(a2.getAliquotaCofins());
			
		}

		if(cat.getPis().getClass().equals(PISAliq1.class)){
		
			PISAliq1 p1 = (PISAliq1)cat.getPis();
			
			this.txtAliqPis.setValue(p1.getAlicotaPis());
		
			
		}else if(cat.getPis().getClass().equals(PISAliq2.class)){
			
			PISAliq1 p2 = (PISAliq1)cat.getPis();
			
			this.txtAliqPis.setValue(p2.getAlicotaPis());
			
		}
		
		this.cboTipoPis.setSelectedItem(cat.getPis());

		if (cat.getIcms().getClass().equals(ICMS00.class)) {

			ICMS00 i = (ICMS00) cat.getIcms();

			this.cboCst.setSelectedItem(i);
			
			this.cboModalidadeBC.setSelectedItem(i.getModalidadeBC());
			this.cboOrigem.setSelectedItem(i.getOrigem());
			this.txtReducaoBase.setValue(i.getPorcentagemBC());
			this.txtReducaoBaseSt.setValue(0);
			this.txtMargemAdcionada.setValue(0);

		} else if (cat.getIcms().getClass().equals(ICMS10.class)) {

			ICMS10 i = (ICMS10) cat.getIcms();

			this.cboCst.setSelectedItem(i);
			
			this.cboModalidadeBC.setSelectedItem(i.getModalidadeBC());
			this.cboOrigem.setSelectedItem(i.getOrigem());
			this.txtReducaoBase.setValue(i.getPorcentagemBC());

			this.cboModalidadeBCST.setSelectedItem(i.getModalidadeBCST());

			this.txtMargemAdcionada.setValue(i.getPorcentagemMargemValorAdicionadoST());

			this.txtReducaoBaseSt.setValue(i.getPorcentagemReducaoBCST());

		} else if (cat.getIcms().getClass().equals(ICMS20.class)) {

			ICMS20 i = (ICMS20) cat.getIcms();
			
			this.cboCst.setSelectedItem(i);

			this.cboModalidadeBC.setSelectedItem(i.getModalidadeBC());
			this.cboOrigem.setSelectedItem(i.getOrigem());
			this.txtReducaoBase.setValue(i.getPorcentagemReducaoBC());

		} else if (cat.getIcms().getClass().equals(ICMS30.class)) {

			ICMS30 i = (ICMS30) cat.getIcms();

			this.cboCst.setSelectedItem(i);
			
			this.cboModalidadeBCST.setSelectedItem(i.getModalidadeBCST());
			this.cboOrigem.setSelectedItem(i.getOrigem());
			this.txtReducaoBaseSt.setValue(i.getPorcentagemReducaoBCST());

		} else if (cat.getIcms().getClass().equals(ICMS40.class)) {

			ICMS40 i = (ICMS40) cat.getIcms();

			this.cboCst.setSelectedItem(i);
			
			this.cboOrigem.setSelectedItem(i.getOrigem());

		} else if (cat.getIcms().getClass().equals(ICMS41.class)) {

			ICMS41 i = (ICMS41) cat.getIcms();
			
			this.cboCst.setSelectedItem(i);
			
			this.cboOrigem.setSelectedItem(i.getOrigem());

		} else if (cat.getIcms().getClass().equals(ICMS50.class)) {

			ICMS50 i = (ICMS50) cat.getIcms();

			this.cboCst.setSelectedItem(i);
			
			this.cboOrigem.setSelectedItem(i.getOrigem());

		} else if (cat.getIcms().getClass().equals(ICMS51.class)) {

			ICMS51 i = (ICMS51) cat.getIcms();

			this.cboCst.setSelectedItem(i);
			
			this.cboOrigem.setSelectedItem(i.getOrigem());
			this.cboModalidadeBC.setSelectedItem(i.getModalidadeBC());
			this.txtReducaoBase.setValue(i.getPorcentagemReducaoBC());

		} else if (cat.getIcms().getClass().equals(ICMS60.class)) {

			ICMS60 i = (ICMS60) cat.getIcms();

			this.cboCst.setSelectedItem(i);
			
			this.cboOrigem.setSelectedItem(i.getOrigem());

		} else if (cat.getIcms().getClass().equals(ICMS70.class)) {

			ICMS70 i = (ICMS70) cat.getIcms();

			this.cboCst.setSelectedItem(i);
			
			this.cboModalidadeBC.setSelectedItem(i.getModalidadeBC());
			this.cboOrigem.setSelectedItem(i.getOrigem());
			this.txtReducaoBase.setValue(i.getPorcentagemReducaoBC());

			this.cboModalidadeBCST.setSelectedItem(i.getModalidadeBCST());

			this.txtMargemAdcionada.setValue(i.getPorcentagemMargemValorAdicionadoST());

			this.txtReducaoBaseSt.setValue(i.getPorcentagemReducaoBCST());

		} else if (cat.getIcms().getClass().equals(ICMS90.class)) {

			ICMS90 i = (ICMS90) cat.getIcms();

			this.cboCst.setSelectedItem(i);
			
			this.cboModalidadeBC.setSelectedItem(i.getModalidadeBC());
			this.cboOrigem.setSelectedItem(i.getOrigem());
			this.txtReducaoBase.setValue(i.getPorcentagemReducaoBC());

			this.cboModalidadeBCST.setSelectedItem(i.getModalidadeBCST());

			this.txtMargemAdcionada.setValue(i.getPorcentagemMargemValorAdicionadoST());

			this.txtReducaoBaseSt.setValue(i.getPorcentagemReducaoBCST());

		}

	}

	private Empresa empresa;
	private JTable tblAliquotas;
	private JComboBox<OrigemMercadoria> cboOrigem;
	private JComboBox<ModalidadeBC> cboModalidadeBC;
	private JComboBox<ModalidadeBCST> cboModalidadeBCST;
	private JButton btConfirmarImpostos;
	private JFormattedTextField txtMargemAdcionada;
	private JFormattedTextField txtReducaoBase;
	private JFormattedTextField txtReducaoBaseSt;
	private JComboBox<Icms> cboCst;
	private JTable tblCfop;
	private JPanel pnlIcms;
	private JButton btCopiar;

	@Override
	public void init(Usuario operador) {

		operador = etReq.merge(operador);
		empresa = operador.getPf().getEmpresa();
		etReq.detach(operador);
		empresa = etReq.merge(empresa);

		// =================
		
		
		this.btCopiar.addActionListener(x->{
			
			ProdutoService ps = new ProdutoService(et);
			ps.setEmpresa(this.empresa);
			
			Seletor<Produto> sp = new Seletor<Produto>(Produto.class,ps,null,p->{
				
				if(p.getCategoria() != null) {
					
					Categoria clone = (Categoria) p.getCategoria().clone();
					
					this.cat.setCofins(clone.getCofins());
					this.cat.setIcms(clone.getIcms());
					this.cat.setLoja_virtual(clone.isLoja_virtual());
					this.cat.setPis(clone.getPis());
					this.cat.setCofins(clone.getCofins());
					this.cat.setTabelaAlicota(clone.getTabelaAlicota());
					this.cat.setTabelaCfop(clone.getTabelaCfop());
					
					this.setCategoria(this.cat);
					
				}
				
			},this);
			
			sp.setVisible(true);
			
		});
		
		this.setCategoria(this.cat);

		this.btConfirmarImpostos.addActionListener(a -> {

			this.salvarCategoria();// Metodo criado por ser extenso.

		});

		this.cboModalidadeBC.setModel(new DefaultComboBoxModel<ModalidadeBC>(ModalidadeBC.values()));

		this.cboModalidadeBCST.setModel(new DefaultComboBoxModel<ModalidadeBCST>(ModalidadeBCST.values()));

		this.cboOrigem.setModel(new DefaultComboBoxModel<OrigemMercadoria>(OrigemMercadoria.values()));

		this.cboTipoPis.setModel(new DefaultComboBoxModel<Pis>(Pis.getTodosPis()));
		this.cboTipoCofins.setModel(new DefaultComboBoxModel<Cofins>(Cofins.getTodosCofins()));

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		EntityManager et = ET.nova();

		Usuario u = et.find(Usuario.class, 3);

		Categoria c = et.find(Categoria.class, 1);

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Categorias frame = new Categorias(c,et);
					frame.setVisible(true);
					frame.init(u);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Categorias(Categoria c,EntityManager etq) {

		this.etReq = etq;
		this.cat = c;

		setTitle("Configuracao do produto");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 806, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JSeparator separator = new JSeparator();
		separator.setBounds(76, 60, 311, -11);
		contentPane.add(separator);

		JPanel panel_12 = new JPanel();
		panel_12.setBorder(
				new TitledBorder(null, "Configuracao Cofins", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_12.setBounds(30, 283, 338, 82);
		contentPane.add(panel_12);
		panel_12.setLayout(null);

		JLabel lblTipo = new JLabel("CST:");
		lblTipo.setBounds(10, 24, 107, 14);
		panel_12.add(lblTipo);

		JLabel lblAliquota = new JLabel("Aliquota (%):");
		lblAliquota.setBounds(10, 49, 80, 14);
		panel_12.add(lblAliquota);

		cboTipoCofins = new JComboBox<Cofins>();
		cboTipoCofins.setBounds(61, 21, 171, 20);
		panel_12.add(cboTipoCofins);

		txtAliqCofins = new JFormattedTextField();
		txtAliqCofins.setBounds(83, 49, 80, 20);
		panel_12.add(txtAliqCofins);

		JPanel panel_13 = new JPanel();
		panel_13.setLayout(null);
		panel_13.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Configuracao PIS",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_13.setBounds(378, 283, 311, 82);
		contentPane.add(panel_13);

		JLabel lblCst = new JLabel("CST:");
		lblCst.setBounds(10, 24, 27, 14);
		panel_13.add(lblCst);

		JLabel label_37 = new JLabel("Aliquota (%):");
		label_37.setBounds(10, 49, 107, 14);
		panel_13.add(label_37);

		cboTipoPis = new JComboBox<Pis>();
		cboTipoPis.setBounds(47, 21, 171, 20);
		panel_13.add(cboTipoPis);

		txtAliqPis = new JFormattedTextField();
		txtAliqPis.setBounds(83, 49, 79, 20);
		panel_13.add(txtAliqPis);

		btCopiar = new JButton("Copiar de outro produto");
		btCopiar.setBounds(30, 11, 182, 38);
		contentPane.add(btCopiar);

		pnlIcms = new JPanel();
		pnlIcms.setBorder(new TitledBorder(null, "Icms", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlIcms.setBounds(30, 60, 242, 212);
		contentPane.add(pnlIcms);
		pnlIcms.setLayout(null);

		JLabel lblNewLabel = new JLabel("CST:");
		lblNewLabel.setBounds(10, 22, 29, 14);
		pnlIcms.add(lblNewLabel);

		cboCst = new JComboBox<Icms>(new DefaultComboBoxModel<Icms>(new Icms[] { new ICMS00(), new ICMS10(), new ICMS20(),
				new ICMS30(), new ICMS40(), new ICMS50(), new ICMS51(), new ICMS60(), new ICMS70(), new ICMS90(), }));
		cboCst.setBounds(55, 19, 113, 20);
		pnlIcms.add(cboCst);

		JLabel lblRed = new JLabel("Origem da Mercadoria:");
		lblRed.setBounds(10, 56, 160, 14);
		pnlIcms.add(lblRed);

		JLabel lblModalidade = new JLabel(" Modalidade BC:");
		lblModalidade.setBounds(10, 81, 86, 14);
		pnlIcms.add(lblModalidade);

		JLabel lblModalidadeBcSt = new JLabel(" Modalidade BC ST:");
		lblModalidadeBcSt.setBounds(10, 106, 98, 14);
		pnlIcms.add(lblModalidadeBcSt);

		JLabel lblAliquota_1 = new JLabel("Margem Adicionada BC ST");
		lblAliquota_1.setBounds(10, 130, 130, 14);
		pnlIcms.add(lblAliquota_1);

		JLabel lblReducaoBaseCalculo = new JLabel("Reducao Base Calculo ICMS:");
		lblReducaoBaseCalculo.setBounds(10, 155, 144, 14);
		pnlIcms.add(lblReducaoBaseCalculo);

		JLabel lblReducaoBaseCalculo_1 = new JLabel("Reducao Base Calculo ICMS ST:");
		lblReducaoBaseCalculo_1.setBounds(10, 180, 160, 14);
		pnlIcms.add(lblReducaoBaseCalculo_1);

		cboOrigem = new JComboBox<OrigemMercadoria>();
		cboOrigem.setBounds(127, 53, 103, 20);
		pnlIcms.add(cboOrigem);

		cboModalidadeBC = new JComboBox<ModalidadeBC>();
		cboModalidadeBC.setBounds(89, 78, 141, 20);
		pnlIcms.add(cboModalidadeBC);

		cboModalidadeBCST = new JComboBox<ModalidadeBCST>();
		cboModalidadeBCST.setBounds(112, 103, 118, 20);
		pnlIcms.add(cboModalidadeBCST);

		txtMargemAdcionada = new JFormattedTextField();
		txtMargemAdcionada.setBounds(150, 127, 80, 20);
		pnlIcms.add(txtMargemAdcionada);

		txtReducaoBase = new JFormattedTextField();
		txtReducaoBase.setBounds(151, 152, 80, 20);
		pnlIcms.add(txtReducaoBase);

		txtReducaoBaseSt = new JFormattedTextField();
		txtReducaoBaseSt.setBounds(171, 177, 60, 20);
		pnlIcms.add(txtReducaoBaseSt);

		txtReducaoBaseSt.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
		txtReducaoBaseSt.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
		txtMargemAdcionada.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Cfop", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(538, 60, 252, 212);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrCfop = new JScrollPane();
		scrCfop.setBounds(10, 26, 232, 175);
		panel_1.add(scrCfop);
		
		tblCfop = new JTable();
		scrCfop.setViewportView(tblCfop);

		btConfirmarImpostos = new JButton("Confirmar");
		btConfirmarImpostos.setBounds(698, 283, 92, 82);
		contentPane.add(btConfirmarImpostos);
						
						JPanel panel = new JPanel();
						panel.setBorder(new TitledBorder(null, "Alicotas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
						panel.setBounds(277, 60, 252, 212);
						contentPane.add(panel);
						panel.setLayout(null);
						
								JScrollPane scrollPane = new JScrollPane();
								scrollPane.setBounds(10, 29, 232, 172);
								panel.add(scrollPane);
								
										tblAliquotas = new JTable();
										scrollPane.setViewportView(tblAliquotas);

	}

	public EntityManager getEtReq() {
		return etReq;
	}

	public void setEtReq(EntityManager etReq) {
		this.etReq = etReq;
	}
}
