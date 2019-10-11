package br.com.inicial;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.base.AberturaCaixaException;
import br.com.base.CFG;
import br.com.base.ConfiguracaoExpediente;
import br.com.base.ET;
import br.com.base.Masks;
import br.com.base.Resources;
import br.com.caixa.Caixa;
import br.com.caixa.CaixaService;
import br.com.caixa.ConfiguracaoLocalCaixa;
import br.com.caixa.ExpedienteCaixa;
import br.com.caixa.ExpedienteCaixaService;
import br.com.caixa.Reposicao;
import br.com.caixa.RepresentadorExpedienteCaixa;
import br.com.caixa.Sangria;
import br.com.conversores.ConversorDate;
import br.com.movimento_financeiro.Movimento;
import br.com.movimento_financeiro.RepresentadorMovimentoCaixa;
import br.com.usuario.Usuario;
import br.com.utilidades.ListModelGenerica;

import javax.swing.JLabel;
import java.awt.Font;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.util.Calendar;
import java.util.List;

import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultFormatterFactory;

import com.ibm.icu.text.NumberFormat;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JFormattedTextField;
import javax.swing.UIManager;

public class ConfiguracaoCaixa extends Modulo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EntityManager et = ET.nova();
		
		Usuario u = et.find(Usuario.class, 1);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfiguracaoCaixa frame = new ConfiguracaoCaixa();
					frame.init(u);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static ImageIcon logo() {

		try {
			return Resources.getConfig();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Configuracoes do Caixa";

	}

	private Usuario usu;
	
	private ConfiguracaoLocalCaixa clc;
	private JButton btConfirmar;
	private JLabel lblStatus;
	private JTable tblMovimentos;
	private JTable tblSangrias;
	private JButton btSangria;
	private JFormattedTextField txtValorReposicao;
	private JFormattedTextField txtValorSangria;
	private JButton btReposicao;
	private JLabel lblOperador;
	private JButton btnFechar;
	private JLabel lblSaldoAtual;
	private JLabel lblAgora;
	private JLabel lblInicio;
	private JLabel lblSaldo;
	
	private ExpedienteCaixa exp;
	private JTable tblReposicao;
	private JPanel panel_3;
	private JScrollPane scrollPane_3;
	private JTable tblExpedientes;
	
	private void inicializar() throws InvalidKeyException, NoSuchAlgorithmException, CertificateException, KeyStoreException, SignatureException, IOException, AberturaCaixaException {
		
		this.textField.setText(clc.getNumeroCaixa()+"");
		this.lblStatus.setBackground(Color.GREEN);
		
		this.setExpediente(et.merge(ConfiguracaoExpediente.getExpedienteCaixa(this.usu)));
		
		Query q = et.createQuery("SELECT e FROM ExpedienteCaixa e WHERE e.caixa=:caixa ORDER BY e.id DESC");
		q.setParameter("caixa", exp.getCaixa());
		
		@SuppressWarnings("unchecked")
		List<ExpedienteCaixa> expedientes = (List<ExpedienteCaixa>)(List<?>)q.getResultList();
		
		ListModelGenerica<ExpedienteCaixa> exps = new ListModelGenerica<ExpedienteCaixa>(expedientes,ExpedienteCaixa.class,RepresentadorExpedienteCaixa.class);
		this.tblExpedientes.setModel(exps);
		
		this.tblExpedientes.getSelectionModel().addListSelectionListener(e->{
			
			if(!e.getValueIsAdjusting()) {
				
				if(this.tblExpedientes.getSelectedRow() >= 0) {
					
					ExpedienteCaixa exx = exps.getListaBase().get(this.tblExpedientes.getSelectedRow());
					
					this.setExpediente(exx);
					
				}
				
			}
			
		});
		
	}
	
	private void setExpediente(ExpedienteCaixa ex) {
		
		exp = ex;
		
		this.textField.setText(exp.getCaixa().getNumero()+"");
		
		this.lblOperador.setText(exp.getUsuario().getPf().getNome());
		
		this.lblInicio.setText(new ConversorDate().paraStringComHora(exp.getInicio()));
		
		if(exp.getFim() == null) {
			
			this.lblAgora.setText("-----------");
			this.btnFechar.setEnabled(true);
			
			this.btSangria.setEnabled(true);
			this.btReposicao.setEnabled(true);
			
		}else {
			
			this.lblAgora.setText(new ConversorDate().paraStringComHora(exp.getFim()));
			this.btnFechar.setEnabled(false);
			
			this.btSangria.setEnabled(false);
			this.btReposicao.setEnabled(false);
			
		}
		
		this.lblSaldo.setText(NumberFormat.getCurrencyInstance().format(exp.getSaldo_inicial()));
		
		this.lblSaldoAtual.setText(NumberFormat.getCurrencyInstance().format(exp.getSaldo_final_atual()));
		
		ListModelGenerica<Movimento> mdlMov = new ListModelGenerica<Movimento>(exp.getMovimentos(),Movimento.class,RepresentadorMovimentoCaixa.class);
		this.tblMovimentos.setModel(mdlMov);
		
		
		ListModelGenerica<Sangria> mdlSang = new ListModelGenerica<Sangria>(exp.getSangrias(),Sangria.class);
		this.tblSangrias.setModel(mdlSang);
		
		ListModelGenerica<Reposicao> mdlRepo = new ListModelGenerica<Reposicao>(exp.getReposicoes(),Reposicao.class);
		this.tblReposicao.setModel(mdlRepo);
		
		
		
	}
	
	@Override
	public void init(Usuario op) {
		
		this.usu = op;
		
		clc = ConfiguracaoLocalCaixa.getConfiguracaoLocalCaixa(this.usu.getPf().getEmpresa());
	
		if(clc == null) {
			
			this.lblStatus.setBackground(Color.RED);
			
		}else {
			
			try {
				
				if(!clc.verificarAssinatura()) {
					
					this.lblStatus.setBackground(Color.ORANGE);
					
				}else {
					
					this.inicializar();
					
				}
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
			}
			
		}
		
		this.btReposicao.addActionListener(e->{
			
			if(
					!vc(this.txtValorReposicao)) {
				
				return;
				
			}
			
			double valor = ((Number)this.txtValorReposicao.getValue()).doubleValue();
			
			Reposicao repo = new Reposicao();
			repo.setExpediente(exp);
			repo.setMomento(Calendar.getInstance());
			repo.setValor(valor);
			
			exp.getReposicoes().add(repo);
		
			new ExpedienteCaixaService(et).merge(exp);
			
			et.getTransaction().begin();
			et.getTransaction().commit();
			
			this.setExpediente(exp);
			
			this.txtValorReposicao.setValue(0);
			
		});
		
		this.btnFechar.addActionListener(e->{
			
			this.exp.setFim(Calendar.getInstance());
			
			et.getTransaction().begin();
			et.getTransaction().commit();
			
			info("Procedimento efetuado com sucesso");
			
			try {
				CFG.clc = null;
				CFG.moduloSat = null;
				ConfiguracaoExpediente.exp = null;
				new Login();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.dispose();
			
		});
		
		this.btSangria.addActionListener(e->{
			
			if(
					!vc(this.txtValorSangria)) {
				
				return;
				
			}
			
			double valor = ((Number)this.txtValorSangria.getValue()).doubleValue();
			
			if(valor > exp.getCaixa().getSaldoAtual()) {
				
				erro("O valor da sangria tem que ser menor ou igual ao saldo");
				return;
				
			}
			
			Sangria repo = new Sangria();
			repo.setExpediente(exp);
			repo.setMomento(Calendar.getInstance());
			repo.setValor(valor);
			
			exp.getSangrias().add(repo);
			
			new ExpedienteCaixaService(et).merge(exp);
			
			et.getTransaction().begin();
			et.getTransaction().commit();
			
			this.setExpediente(exp);
			
			this.txtValorReposicao.setValue(0);
			
		});
		
		this.btConfirmar.addActionListener(x->{
			
			if(this.clc == null) {
				
				this.clc = new ConfiguracaoLocalCaixa(this.usu.getPf().getEmpresa());
				
			}
			
			int numero = Integer.parseInt(this.textField.getText());
			
			CaixaService cs = new CaixaService(et);
			cs.setEmpresa(this.usu.getPf().getEmpresa());
			
			Caixa caixa = cs.getPeloNumero(numero);
			
			if(caixa == null) {
				
				caixa = new Caixa();
				caixa.setNumero(numero);
				caixa.setSaldoAtual(0);
				caixa.setEmpresa(et.merge(this.usu.getPf().getEmpresa()));
				
				new CaixaService(et).merge(caixa);
				
				et.getTransaction().begin();
				et.getTransaction().commit();
				
			}
			
			try {
				
				this.clc.setNumeroCaixa(numero);
				this.clc.salvar();
				
				CFG.clc = clc;
				
				info("Operacao efetuada com sucesso");
				this.inicializar();
				
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CertificateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SignatureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AberturaCaixaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
	}
	
	
	/**
	 * Create the frame.
	 */
	public ConfiguracaoCaixa() {
		
		setTitle("Configuracao de Caixa");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 931, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Caixa");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 11, 235, 40);
		contentPane.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 51, 846, 1);
		contentPane.add(separator);
		
		lblStatus = new JLabel("");
		lblStatus.setOpaque(true);
		lblStatus.setBackground(Color.RED);
		lblStatus.setBounds(809, 11, 47, 29);
		contentPane.add(lblStatus);
		
		JPanel pnlI = new JPanel();
		pnlI.setBorder(new TitledBorder(null, "Informacoes Caixa", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlI.setBounds(10, 62, 320, 193);
		contentPane.add(pnlI);
		pnlI.setLayout(null);
		
		JLabel lblSa = new JLabel("Saldo Inicial:");
		lblSa.setBounds(10, 120, 86, 14);
		pnlI.add(lblSa);
		
		lblSaldo = new JLabel("0,00");
		lblSaldo.setBounds(77, 119, 103, 14);
		pnlI.add(lblSaldo);
		lblSaldo.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		btConfirmar = new JButton("Alterar Numero");
		btConfirmar.setBounds(174, 21, 134, 23);
		pnlI.add(btConfirmar);
		
		textField = new JTextField();
		textField.setBounds(106, 22, 58, 20);
		pnlI.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("N\u00FAmero do Caixa:");
		lblNewLabel_1.setBounds(10, 25, 86, 14);
		pnlI.add(lblNewLabel_1);
		
		JLabel lblInicioExpediente = new JLabel("Inicio Expediente:");
		lblInicioExpediente.setBounds(10, 95, 86, 14);
		pnlI.add(lblInicioExpediente);
		
		lblInicio = new JLabel("0,00");
		lblInicio.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblInicio.setBounds(106, 95, 202, 14);
		pnlI.add(lblInicio);
		
		JLabel lblAt = new JLabel("Fim:");
		lblAt.setBounds(10, 145, 47, 14);
		pnlI.add(lblAt);
		
		lblAgora = new JLabel("0,00");
		lblAgora.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAgora.setBounds(50, 145, 142, 14);
		pnlI.add(lblAgora);
		
		JLabel lblSA = new JLabel("Saldo Atual:");
		lblSA.setBounds(10, 168, 58, 14);
		pnlI.add(lblSA);
		
		lblSaldoAtual = new JLabel("0,00");
		lblSaldoAtual.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSaldoAtual.setBounds(77, 168, 109, 14);
		pnlI.add(lblSaldoAtual);
		
		btnFechar = new JButton("Fechar Caixa");
		btnFechar.setBounds(205, 159, 103, 23);
		pnlI.add(btnFechar);
		
		JLabel lblOp = new JLabel("Operador:");
		lblOp.setBounds(10, 70, 58, 14);
		pnlI.add(lblOp);
		
		lblOperador = new JLabel("0,00");
		lblOperador.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblOperador.setBounds(77, 70, 103, 14);
		pnlI.add(lblOperador);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Movimentos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 260, 320, 191);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 300, 159);
		panel.add(scrollPane);
		
		tblMovimentos = new JTable();
		scrollPane.setViewportView(tblMovimentos);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Sangrias", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(337, 63, 235, 192);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 22, 215, 127);
		panel_1.add(scrollPane_1);
		
		tblSangrias = new JTable();
		scrollPane_1.setViewportView(tblSangrias);
		
		JLabel lblNewLabel_2 = new JLabel("Valor R$:");
		lblNewLabel_2.setBounds(10, 167, 46, 14);
		panel_1.add(lblNewLabel_2);
		
		txtValorSangria = new JFormattedTextField();
		txtValorSangria.setBounds(75, 161, 93, 20);
		panel_1.add(txtValorSangria);
		
		txtValorSangria.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
		
		btSangria = new JButton("+");
		btSangria.setBounds(178, 160, 47, 23);
		panel_1.add(btSangria);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Reposicoes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(340, 266, 235, 192);
		contentPane.add(panel_2);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 22, 215, 127);
		panel_2.add(scrollPane_2);
		
		tblReposicao = new JTable();
		scrollPane_2.setViewportView(tblReposicao);
		
		JLabel label = new JLabel("Valor R$:");
		label.setBounds(10, 167, 46, 14);
		panel_2.add(label);
		
		txtValorReposicao = new JFormattedTextField();
		txtValorReposicao.setBounds(75, 161, 93, 20);
		panel_2.add(txtValorReposicao);
		
		txtValorReposicao.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
		
		btReposicao = new JButton("+");
		btReposicao.setBounds(178, 160, 47, 23);
		panel_2.add(btReposicao);
		
		panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Expedientes deste caixa", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(582, 63, 333, 388);
		contentPane.add(panel_3);
		
		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 22, 313, 355);
		panel_3.add(scrollPane_3);
		
		tblExpedientes = new JTable();
		scrollPane_3.setViewportView(tblExpedientes);
	}
}
