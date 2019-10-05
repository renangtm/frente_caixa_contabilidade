package br.com.inicial;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultFormatterFactory;

import br.com.afgtec.base.ET;
import br.com.afgtec.base.Masks;
import br.com.afgtec.base.Resources;
import br.com.afgtec.usuario.Usuario;
import br.com.agrofauna.conversores.ConversorDate;
import br.com.agrofauna.utilidades.GerenciadorLista;
import br.com.agrofauna.utilidades.ListModelGenerica;
import br.com.banco.Banco;
import br.com.banco.Fechamento;
import br.com.banco.RepresentadorFechamento;
import br.com.conta.Conta;
import br.com.conta.ContaService;
import br.com.conta.RepresentadorConta;
import br.com.conta.TipoConta;
import br.com.empresa.Empresa;
import br.com.entidades.nota.RepresentadorVencimento;
import br.com.entidades.nota.Vencimento;
import br.com.entidades.nota.VencimentoService;
import br.com.historico.Historico;
import br.com.movimento_financeiro.Movimento;
import br.com.movimento_financeiro.MovimentoService;
import br.com.movimento_financeiro.RepresentadorMovimento;
import br.com.operacao.Operacao;

import javax.swing.UIManager;
import java.awt.Color;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

public class CadastroMovimento extends Modulo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblFechamento;
	private JButton btnFechar;
	private JButton btnExcluirMovimento;
	private JButton btNovoMovimento;
	private JButton btnConfirmarBanco;
	private JFormattedTextField txtConta;
	private JFormattedTextField txtAgencia;
	private JFormattedTextField txtSaldo;
	private JComboBox<Banco> comboBox;
	private JTextField txtPesquisar;
	private JSlider slPg;
	private JLabel lblPg;
	private Usuario operador;
	private Empresa empresa;

	/**
	 * Launch the application.
	 */
	
	
	public static ImageIcon logo() {

		try {
			return Resources.getDiagrama();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Financeiro";

	}
	
	public static void main(String[] args) {
		
		EntityManager et = ET.nova();
		
		Usuario usuario = et.find(Usuario.class, 1);
		
		System.out.println(usuario.getPf().getNome());
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroMovimento frame = new CadastroMovimento();
					frame.init(usuario);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private GerenciadorLista<Movimento> gr;

	private Banco banco;
	private JComboBox<Operacao> cboOperacao;
	private JComboBox<Historico> cboHistorico;
	private JFormattedTextField txtValorMovimento;
	private JFormattedTextField txtDataMovimento;
	private JLabel lblFicha;
	private JLabel lblValor;
	private JLabel lblData;
	
	private Movimento movimento;
	private JButton btnConfirmar;
	private JLabel lblProgresso;
	private JButton btSlNota;
	
	private Vencimento venc;
	private JTable tblMovimentos;
	private JLabel lblDesc;
	private JLabel lblJuros;
	private JFormattedTextField txtJuros;
	private JFormattedTextField txtDesc;
	private JPanel panel_3;
	private JPanel panel_4;
	private JTable tblContasPagar;
	private JTable tblContasReceber;
	private JTextField txtPesquisaContasPagar;
	private JTextField txtContasReceber;
	private JLabel lblPgContasReceber;
	private JSlider slContasReceber;
	private JSlider slContasPagar;
	private JLabel lblPgContasPagar;
	private GerenciadorLista<Conta> apagar;
	private GerenciadorLista<Conta> areceber;
	
	private void setMovimento(Movimento m) throws ParseException {
		
		this.venc = null;
		
		this.movimento = m;
		
		this.lblData.setText("------");
		this.lblFicha.setText("-----");
		this.lblValor.setText("-----");
		
		
		if(m.getVencimento() != null) {
			
			this.lblData.setText(new ConversorDate().paraString(m.getVencimento().getData()));
			this.lblFicha.setText(m.getVencimento().getNota().getId()+"");
			this.lblValor.setText(Masks.moeda().valueToString(m.getVencimento().getValor()));
			
		}
		
		
		
		this.txtDataMovimento.setValue(new ConversorDate().paraString(m.getData()));
		
		this.txtValorMovimento.setValue(m.getValor());
		this.txtJuros.setValue(m.getJuros());
		this.txtDesc.setValue(m.getDescontos());
		
		if(m.getHistorico() != null) {
			this.cboHistorico.setSelectedItem(m.getHistorico());
		}else {
			this.cboHistorico.setSelectedIndex(0);
		}
		
		if(m.getOperacao() != null) {
			this.cboOperacao.setSelectedItem(m.getOperacao());
		}else {
			this.cboOperacao.setSelectedIndex(0);
		}
		
		this.btNovoMovimento.setEnabled(m.getId()>0);
		
	}
	
	@SuppressWarnings("unchecked")
	public void init(Usuario usu) {

		this.operador = et.merge(usu);
		this.empresa = this.operador.getPf().getEmpresa();
		et.detach(this.operador);
		this.empresa = et.merge(this.empresa);

		// ====================================
		
		Query q = et.createQuery("SELECT b FROM Banco b WHERE b.pj.empresa.id = :empresa");
		q.setParameter("empresa", this.empresa.getId());
		
		List<Banco> bancos = (List<Banco>)(List<?>)q.getResultList();
		
		this.comboBox.setModel(new DefaultComboBoxModel<Banco>(
				bancos.toArray(new Banco[bancos.size()])));

		
		this.comboBox.addActionListener(a->{
			
			this.banco = (Banco)this.comboBox.getSelectedItem();			
			
			MovimentoService serv = new MovimentoService(et);
			serv.setBanco(this.banco);
			
			this.txtSaldo.setValue(this.banco.getSaldo());
			this.txtAgencia.setValue(this.banco.getAgencia());
			this.txtConta.setValue(this.banco.getConta());
			
			
			
			this.gr = new GerenciadorLista<Movimento>(Movimento.class,this.tblMovimentos,serv,null,new Movimento(),RepresentadorMovimento.class);
		
			this.gr.setFiltro(this.txtPesquisar);
			this.gr.setLblPagina(this.lblPg);
			this.gr.setLblSlider(this.slPg);
			
			this.gr.atualizar();
			
			
			ListModelGenerica<Fechamento> lf = new ListModelGenerica<Fechamento>(this.banco.getFechamentos(),Fechamento.class,RepresentadorFechamento.class);
			this.tblFechamento.setModel(lf);
			
		});
	
		this.comboBox.setSelectedIndex(0);
		
		List<Historico> historicos = (List<Historico>)(List<?>)et.createQuery("SELECT h FROM Historico h").getResultList();
		
		this.cboHistorico.setModel(new DefaultComboBoxModel<Historico>(historicos.toArray(new Historico[historicos.size()])));
		
		List<Operacao> operacoes = (List<Operacao>)(List<?>)et.createQuery("SELECT o FROM Operacao o").getResultList();

		this.cboOperacao.setModel(new DefaultComboBoxModel<Operacao>(operacoes.toArray(new Operacao[operacoes.size()])));
		
		this.btNovoMovimento.addActionListener(x->{
			
			Movimento m = new Movimento();
			m.setBanco(this.banco);
			
			try {
				
				this.setMovimento(m);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
		
		
		this.btnConfirmar.addActionListener(a->{
			
			if(
					!vc(this.txtValorMovimento) ||
					!vc(this.txtDataMovimento)) {
				return;
			}
			
			EntityManager esp = ET.nova();
			this.managers.add(esp);
			
			if(this.movimento.getId()>0) {
				
				this.movimento = esp.find(Movimento.class, this.movimento.getId());
				
			}
			
			this.movimento.setBanco((Banco)this.comboBox.getSelectedItem());
			this.movimento.setData(Masks.getData(this.txtDataMovimento.getText()));
			this.movimento.setValor(((Number)this.txtValorMovimento.getValue()).doubleValue());
			this.movimento.setJuros(((Number)this.txtJuros.getValue()).doubleValue());
			this.movimento.setDescontos(((Number)this.txtDesc.getValue()).doubleValue());
			this.movimento.setHistorico((Historico)this.cboHistorico.getSelectedItem());
			this.movimento.setOperacao((Operacao)this.cboOperacao.getSelectedItem());
			
			if(this.venc != null) {
				
				this.movimento.setVencimento(this.venc);
				
			}
			
			MovimentoService ms = new MovimentoService(esp);
			ms.setBanco(this.movimento.getBanco());
			
			this.btnExcluirMovimento.setEnabled(false);
			this.btnConfirmar.setEnabled(false);
			
			ms.mergeMovimento(this.movimento,true, (p,o)->{
				
				if(p>=0) {
				
					this.lblProgresso.setText(p+"%");
					
					if(p == 100) {
						
						esp.getTransaction().begin();
						esp.getTransaction().commit();
						
						this.btnConfirmar.setEnabled(true);
						this.btnExcluirMovimento.setEnabled(true);
						
						info("Operacao efetuada com sucesso");
						
						Movimento m = new Movimento();
						m.setBanco(this.banco);
						
						try {
							
							this.setMovimento(m);
							
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						this.gr.atualizar();
						
						et.refresh(this.banco);
						
						ListModelGenerica<Fechamento> lf = new ListModelGenerica<Fechamento>(this.banco.getFechamentos(),Fechamento.class,RepresentadorFechamento.class);
						this.tblFechamento.setModel(lf);
						
						this.txtSaldo.setValue(this.banco.getSaldo());
						
						this.apagar.atualizar();
						this.areceber.atualizar();
						
					}
					
				}else {
					
					this.lblProgresso.setText("");
					esp.clear();
					erro(o);
					
					this.btnConfirmar.setEnabled(true);
					this.btnExcluirMovimento.setEnabled(true);
					
				}
				
			});
			
		});
		
		
		this.btnConfirmarBanco.addActionListener(a->{
			
			if(
					!vc(this.txtAgencia) ||
					!vc(this.txtConta) ||
					!vc(this.txtSaldo)) {
				return;
			}
			
			this.banco.setAgencia(this.txtAgencia.getText());
			this.banco.setConta(this.txtConta.getText());
			this.banco.setSaldo(((Number)this.txtSaldo.getValue()).doubleValue());
			
			et.merge(this.banco);
			
			et.getTransaction().begin();
			et.getTransaction().commit();
			
			info("Operacao efetuada com sucesso");
			
		});

		this.tblMovimentos.getSelectionModel().addListSelectionListener(x->{
			
			if(!x.getValueIsAdjusting()) {
				
				if(this.gr != null) {
				
					if(this.tblMovimentos.getSelectedRow()>=0) {
						
						Movimento m = this.gr.getModel().getListaBase().get(this.tblMovimentos.getSelectedRow());
						try {
							this.setMovimento(m);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				
				}
				
			}
			
		});
		
		
		
		this.btSlNota.addActionListener(x->{
			
			EntityManager et = ET.nova();
			this.managers.add(et);
			
			VencimentoService vs = new VencimentoService(et);
			vs.setEmpresa(this.empresa);
			
			Seletor<Vencimento> sv = new Seletor<Vencimento>(Vencimento.class,vs,RepresentadorVencimento.class,v->{
				
				this.venc = v;
				
				this.lblData.setText(new ConversorDate().paraString(v.getData()));
				this.lblFicha.setText(v.getNota().getId()+"");
				try {
					this.lblValor.setText(Masks.moeda().valueToString(v.getValor()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			},this);
			sv.setVisible(true);
			
		});
		
		this.btnExcluirMovimento.addActionListener(x->{
			
			if(this.tblMovimentos.getSelectedRow()>=0) {
				
				EntityManager esp = ET.nova();
				this.managers.add(esp);
				
				Movimento m = esp.find(Movimento.class, this.gr.getModel().getListaBase().get(this.tblMovimentos.getSelectedRow()).getId());
				
				m.setBanco(esp.merge(m.getBanco()));
				
				this.btnExcluirMovimento.setEnabled(false);
				this.btnConfirmar.setEnabled(false);
				
				MovimentoService ms = new MovimentoService(esp);
				ms.setBanco(m.getBanco());
				
				ms.deleteMovimento(m,true, (p,o)->{
					
					if(p>=0) {
					
						this.lblProgresso.setText(p+"%");
						
						if(p == 100) {
							
							esp.getTransaction().begin();
							esp.getTransaction().commit();
							
							this.btnConfirmar.setEnabled(true);
							this.btnExcluirMovimento.setEnabled(true);
							
							info("Operacao efetuada com sucesso");
							
							Movimento mm = new Movimento();
							mm.setBanco(this.banco);
							
							try {
								
								this.setMovimento(mm);
								
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							this.gr.atualizar();
							
							et.refresh(this.banco);
							
							ListModelGenerica<Fechamento> lf = new ListModelGenerica<Fechamento>(this.banco.getFechamentos(),Fechamento.class,RepresentadorFechamento.class);
							this.tblFechamento.setModel(lf);
							
							this.txtSaldo.setValue(this.banco.getSaldo());
							
							this.apagar.atualizar();
							this.areceber.atualizar();
							
						}
						
					}else {
						
						esp.clear();
						this.lblProgresso.setText("");
						erro(o);
						
						this.btnConfirmar.setEnabled(true);
						this.btnExcluirMovimento.setEnabled(true);
						
					}
					
				});
				
			}else {
				
				erro("Selecione um movimento");
				
			}
			
		});
		
		this.btnFechar.addActionListener(x->{
			
			Fechamento f = new Fechamento();
			f.setData(Calendar.getInstance());
			f.setValor(this.banco.getSaldo());
			f.setBanco(this.banco);
			
			et.persist(f);
			
			et.getTransaction().begin();
			et.getTransaction().commit();
			
			((ListModelGenerica<Fechamento>)this.tblFechamento.getModel()).add(f);
			
			info("Operacao efetuada com sucesso");
			
		});
		
		
		this.btNovoMovimento.doClick();
		
		ContaService csp = new ContaService(et);
		csp.setEmpresa(this.empresa);
		csp.setTipo(TipoConta.PAGAR);
		
		ContaService csr = new ContaService(et);
		csr.setEmpresa(this.empresa);
		csr.setTipo(TipoConta.RECEBER);
		
		apagar = new GerenciadorLista<Conta>(Conta.class,this.tblContasPagar,csp,null,new Conta(),RepresentadorConta.class);
		apagar.setFiltro(this.txtPesquisaContasPagar);
		apagar.setLblPagina(this.lblPgContasPagar);
		apagar.setLblSlider(this.slContasPagar);
		
		apagar.atualizar();
		
		areceber = new GerenciadorLista<Conta>(Conta.class,this.tblContasReceber,csr,null,new Conta(),RepresentadorConta.class);
		areceber.setFiltro(this.txtPesquisaContasPagar);
		areceber.setLblPagina(this.lblPgContasReceber);
		areceber.setLblSlider(this.slContasReceber);
		
		areceber.atualizar();
		
	}

	/**
	 * Create the frame.
	 */
	public CadastroMovimento() {
		setTitle("Financeiro");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 888, 715);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Financeiro");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(10, 11, 269, 35);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 44, 465, 2);
		contentPane.add(separator);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dados do Banco",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 57, 417, 126);
		contentPane.add(panel);
		panel.setLayout(null);

		comboBox = new JComboBox<Banco>();
		comboBox.setBounds(52, 29, 351, 20);
		panel.add(comboBox);

		txtSaldo = new JFormattedTextField();
		txtSaldo.setBounds(275, 60, 128, 20);
		panel.add(txtSaldo);

		txtSaldo.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));

		JLabel lblSaldo = new JLabel("Saldo:");
		lblSaldo.setBounds(227, 63, 38, 14);
		panel.add(lblSaldo);

		JLabel lblNewLabel_1 = new JLabel("Banco:");
		lblNewLabel_1.setBounds(10, 32, 46, 14);
		panel.add(lblNewLabel_1);

		JLabel lblAgencia = new JLabel("Agencia:");
		lblAgencia.setBounds(10, 63, 53, 14);
		panel.add(lblAgencia);

		JLabel lblConta = new JLabel("Conta:");
		lblConta.setBounds(10, 88, 38, 14);
		panel.add(lblConta);

		txtAgencia = new JFormattedTextField();
		txtAgencia.setBounds(60, 60, 105, 20);
		panel.add(txtAgencia);

		txtConta = new JFormattedTextField();
		txtConta.setBounds(46, 88, 119, 20);
		panel.add(txtConta);

		btnConfirmarBanco = new JButton("Confirmar");
		btnConfirmarBanco.setBounds(298, 91, 105, 23);
		panel.add(btnConfirmarBanco);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 222, 417, 159);
		contentPane.add(scrollPane);
		
		tblMovimentos = new JTable();
		scrollPane.setViewportView(tblMovimentos);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Fechamentos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(656, 57, 216, 343);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 25, 196, 230);
		panel_1.add(scrollPane_1);

		tblFechamento = new JTable();
		scrollPane_1.setViewportView(tblFechamento);

		btnFechar = new JButton("Fechar");
		btnFechar.setBounds(117, 307, 89, 23);
		panel_1.add(btnFechar);

		JLabel lblNewLabel_2 = new JLabel("Pesquisar Movimento:");
		lblNewLabel_2.setBounds(10, 197, 143, 14);
		contentPane.add(lblNewLabel_2);

		txtPesquisar = new JTextField();
		txtPesquisar.setBounds(164, 194, 263, 20);
		contentPane.add(txtPesquisar);
		txtPesquisar.setColumns(10);

		slPg = new JSlider();
		slPg.setBounds(11, 386, 321, 26);
		contentPane.add(slPg);

		lblPg = new JLabel("New label");
		lblPg.setBounds(381, 395, 46, 14);
		contentPane.add(lblPg);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Movimento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(437, 57, 220, 343);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
				btNovoMovimento = new JButton("Novo Movimento");
				btNovoMovimento.setBounds(101, 305, 109, 23);
				panel_2.add(btNovoMovimento);
				
				btnConfirmar = new JButton("Confirmar");
				btnConfirmar.setBounds(101, 271, 109, 23);
				panel_2.add(btnConfirmar);
				
				JLabel lblNewLabel_3 = new JLabel("Ficha:");
				lblNewLabel_3.setBounds(10, 29, 36, 14);
				panel_2.add(lblNewLabel_3);
				
				btSlNota = new JButton("...");
				btSlNota.setBounds(163, 25, 47, 23);
				panel_2.add(btSlNota);
				
				JLabel lblV = new JLabel("Valor:");
				lblV.setBounds(10, 50, 36, 14);
				panel_2.add(lblV);
				
				JLabel lblD = new JLabel("Data:");
				lblD.setBounds(10, 75, 36, 14);
				panel_2.add(lblD);
				
				lblFicha = new JLabel("");
				lblFicha.setFont(new Font("Tahoma", Font.BOLD, 11));
				lblFicha.setBounds(43, 29, 46, 14);
				panel_2.add(lblFicha);
				
				lblValor = new JLabel("");
				lblValor.setFont(new Font("Tahoma", Font.BOLD, 11));
				lblValor.setBounds(43, 50, 119, 14);
				panel_2.add(lblValor);
				
				lblData = new JLabel("");
				lblData.setFont(new Font("Tahoma", Font.BOLD, 11));
				lblData.setBounds(43, 75, 119, 14);
				panel_2.add(lblData);
				
				JSeparator separator_1 = new JSeparator();
				separator_1.setBounds(10, 100, 138, -1);
				panel_2.add(separator_1);
				
				JSeparator separator_2 = new JSeparator();
				separator_2.setBounds(10, 103, 67, -1);
				panel_2.add(separator_2);
				
				JLabel lblNewLabel_4 = new JLabel("V. Mov:");
				lblNewLabel_4.setBounds(10, 100, 46, 14);
				panel_2.add(lblNewLabel_4);
				
				txtValorMovimento = new JFormattedTextField();
				txtValorMovimento.setBounds(72, 100, 138, 20);
				panel_2.add(txtValorMovimento);
				
				txtValorMovimento.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
				
				JLabel lblDtMov = new JLabel("Dt. Mov:");
				lblDtMov.setBounds(10, 134, 46, 14);
				panel_2.add(lblDtMov);
				
				txtDataMovimento = new JFormattedTextField();
				txtDataMovimento.setBounds(72, 131, 138, 20);
				panel_2.add(txtDataMovimento);
				
				Masks.data().install(txtDataMovimento);
				
				JLabel lblNewLabel_5 = new JLabel("Op.:");
				lblNewLabel_5.setBounds(10, 218, 46, 14);
				panel_2.add(lblNewLabel_5);
				
				cboOperacao = new JComboBox<Operacao>();
				cboOperacao.setBounds(61, 215, 149, 20);
				panel_2.add(cboOperacao);
				
				JLabel lblHist = new JLabel("Hist.:");
				lblHist.setBounds(10, 243, 46, 14);
				panel_2.add(lblHist);
				
				cboHistorico = new JComboBox<Historico>();
				cboHistorico.setBounds(61, 240, 149, 20);
				panel_2.add(cboHistorico);
				
				lblProgresso = new JLabel("");
				lblProgresso.setForeground(Color.BLUE);
				lblProgresso.setFont(new Font("Tahoma", Font.BOLD, 11));
				lblProgresso.setHorizontalAlignment(SwingConstants.CENTER);
				lblProgresso.setBounds(10, 271, 85, 29);
				panel_2.add(lblProgresso);
				
				lblDesc = new JLabel("Desc:");
				lblDesc.setBounds(10, 190, 46, 14);
				panel_2.add(lblDesc);
				
				lblJuros = new JLabel("Juros:");
				lblJuros.setBounds(10, 165, 46, 14);
				panel_2.add(lblJuros);
				
				txtJuros = new JFormattedTextField();
				txtJuros.setBounds(72, 162, 138, 20);
				panel_2.add(txtJuros);
				
				txtJuros.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
				
				txtDesc = new JFormattedTextField();
				txtDesc.setBounds(72, 190, 138, 20);
				panel_2.add(txtDesc);
				
				txtDesc.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
				
						btnExcluirMovimento = new JButton("Excluir Movimento");
						btnExcluirMovimento.setBounds(10, 271, 80, 23);
						panel_2.add(btnExcluirMovimento);
				
				this.txtSaldo.setEnabled(false);
				
				panel_3 = new JPanel();
				panel_3.setBorder(new TitledBorder(null, "Contas a Pagar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_3.setBounds(10, 423, 417, 257);
				contentPane.add(panel_3);
				panel_3.setLayout(null);
				
				JScrollPane scrollPane_2 = new JScrollPane();
				scrollPane_2.setBounds(137, 181, -125, -153);
				panel_3.add(scrollPane_2);
				
				JScrollPane scrollPane_3 = new JScrollPane();
				scrollPane_3.setBounds(10, 24, 397, 169);
				panel_3.add(scrollPane_3);
				
				tblContasPagar = new JTable();
				scrollPane_3.setViewportView(tblContasPagar);
				
				JLabel lblNewLabel_6 = new JLabel("Pesquisar");
				lblNewLabel_6.setBounds(10, 204, 46, 14);
				panel_3.add(lblNewLabel_6);
				
				txtPesquisaContasPagar = new JTextField();
				txtPesquisaContasPagar.setColumns(10);
				txtPesquisaContasPagar.setBounds(63, 204, 344, 20);
				panel_3.add(txtPesquisaContasPagar);
				
				slContasPagar = new JSlider();
				slContasPagar.setBounds(10, 226, 321, 26);
				panel_3.add(slContasPagar);
				
				lblPgContasPagar = new JLabel("New label");
				lblPgContasPagar.setBounds(361, 235, 46, 14);
				panel_3.add(lblPgContasPagar);
				
				panel_4 = new JPanel();
				panel_4.setBorder(new TitledBorder(null, "Contas a Receber", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_4.setBounds(437, 423, 430, 257);
				contentPane.add(panel_4);
				panel_4.setLayout(null);
				
				JScrollPane scrollPane_4 = new JScrollPane();
				scrollPane_4.setBounds(10, 24, 410, 167);
				panel_4.add(scrollPane_4);
				
				tblContasReceber = new JTable();
				scrollPane_4.setViewportView(tblContasReceber);
				
				JLabel label = new JLabel("Pesquisar");
				label.setBounds(10, 202, 46, 14);
				panel_4.add(label);
				
				txtContasReceber = new JTextField();
				txtContasReceber.setColumns(10);
				txtContasReceber.setBounds(63, 202, 344, 20);
				panel_4.add(txtContasReceber);
				
				slContasReceber = new JSlider();
				slContasReceber.setBounds(10, 224, 321, 26);
				panel_4.add(slContasReceber);
				
				lblPgContasReceber = new JLabel("New label");
				lblPgContasReceber.setBounds(361, 233, 46, 14);
				panel_4.add(lblPgContasReceber);
				
	}
}
