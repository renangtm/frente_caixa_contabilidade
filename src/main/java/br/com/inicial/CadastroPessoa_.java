package br.com.inicial;

import java.awt.EventQueue;

import javax.persistence.EntityManager;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import br.com.afgtec.base.ET;
import br.com.afgtec.base.Masks;
import br.com.afgtec.cliente.Cliente;
import br.com.afgtec.fornecedor.Fornecedor;
import br.com.afgtec.pessoa.Empresa;
import br.com.afgtec.pessoa.Pessoa;
import br.com.afgtec.pessoa.PessoaFisica;
import br.com.afgtec.pessoa.PessoaJuridica;
import br.com.afgtec.pessoa.PessoaService;
import br.com.afgtec.pessoa.RepresentadorPessoa;
import br.com.afgtec.transportadora.Transportadora;
import br.com.afgtec.usuario.Usuario;
import br.com.agrofauna.conversores.ConversorDate;
import br.com.agrofauna.utilidades.GerenciadorLista;
import br.com.entidades.Icones;

import javax.swing.JLabel;
import java.awt.Font;
import java.io.IOException;
import java.util.List;

import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;

import afgtec.endereco.Cidade;
import afgtec.endereco.Endereco;
import afgtec.endereco.Estado;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;

public class CadastroPessoa_ extends Modulo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtRua;
	private JTextField txtBairro;
	private JTextField txtNumero;
	private JTextField txtPesquisar;
	private JTable tblPessoasFisicas;
	private JButton btConfirmar;
	private JButton btNovaPessoa;
	private JComboBox<Cidade> cboCidade;
	private JComboBox<Estado> cboEstado;
	private JFormattedTextField txtCep;
	private JFormattedTextField txtSkype;
	private JFormattedTextField txtEmail;
	private JFormattedTextField txtWhatsapp;
	private JFormattedTextField txtTelefone;
	private JLabel lblPg;
	private JSlider slPg;

	/**
	 * Launch the application.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		EntityManager et = ET.nova();

		Usuario u = et.find(Usuario.class, 3);

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					CadastroPessoa frame = new CadastroPessoa();
					frame.setVisible(true);
					frame.init(u);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private Usuario operador;

	private Pessoa p;

	private GerenciadorLista<Pessoa> gr;

	public static ImageIcon logo() {

		try {
			return Icones.getCfg();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Cadastro de Pessoas";

	}
	
	private void setPessoaFisica(Pessoa p) {

		this.p = p;

		try {
		
			PessoaFisica pf = (PessoaFisica)this.p;
			
			this.tabbedPane.setSelectedIndex(0);
			
			this.txtNome.setText(pf.getNome());
			
			this.txtRua.setText((pf.getEndereco()==null)?"":pf.getEndereco().getRua());
			
			this.txtCep.setValue((pf.getEndereco()==null)?"":pf.getEndereco().getCep());
			
			this.txtBairro.setText((pf.getEndereco()==null)?"":pf.getEndereco().getBairro());
			
			this.txtNumero.setText((pf.getEndereco()==null)?"":pf.getEndereco().getNumero());
			
			if(pf.getEndereco() != null){
				
				this.cboEstado.setSelectedItem(pf.getEndereco().getCidade().getEstado());
				 
				this.cboCidade.setSelectedItem(pf.getEndereco().getCidade());
				
			}
			
			this.txtCpf.setValue(pf.getCpf());
			
			this.txtDtNasc.setValue(new ConversorDate().paraString(pf.getData_nascimento()));
			
			this.txtEmail.setText(pf.getEmail());
			
			this.txtRg.setText(pf.getRg());
			
			this.txtSkype.setText(pf.getSkype());
			
			this.txtWhatsapp.setValue(pf.getWhatsapp());
			
			this.txtTelefone.setValue(pf.getTelefone());
			
			this.txtNome.requestFocus();
			
			if(p.getId()>0) {
				
				this.tabbedPane.setEnabledAt(0,true);
				this.tabbedPane.setEnabledAt(1, false);
				
				this.chkColaborador.setSelected(pf.getUsuario() != null);
				
				this.chkCliente.setSelected(pf.getCliente() != null);
				
				
			}else {
				
				this.tabbedPane.setEnabledAt(0,true);
				this.tabbedPane.setEnabledAt(1, true);
				
				this.chkColaborador.setSelected(false);
				this.chkCliente.setSelected(false);
				
			}
			
			
			
			
		}catch(Exception ex) {
			
			PessoaJuridica pf = (PessoaJuridica)this.p;
			
			this.tabbedPane.setSelectedIndex(1);
			
			this.txtRazSoc.setText(pf.getNome());
			
			this.txtRua.setText((pf.getEndereco()==null)?"":pf.getEndereco().getRua());
			
			this.txtCep.setValue((pf.getEndereco()==null)?"":pf.getEndereco().getCep());
			
			this.txtBairro.setText((pf.getEndereco()==null)?"":pf.getEndereco().getBairro());
			
			this.txtNumero.setText((pf.getEndereco()==null)?"":pf.getEndereco().getNumero());
			
			if(pf.getEndereco() != null){
				
				this.cboEstado.setSelectedItem(pf.getEndereco().getCidade().getEstado());
				 
				this.cboCidade.setSelectedItem(pf.getEndereco().getCidade());
				
			}
			
			this.txtCnpj.setValue(pf.getCnpj());
			
			this.txtDataAbertura.setValue(new ConversorDate().paraString(pf.getAbertura()));
			
			this.txtEmail.setText(pf.getEmail());
			
			this.txtIe.setText(pf.getInscricao_estadual());
			
			this.txtSkype.setText(pf.getSkype());
			
			this.txtWhatsapp.setValue(pf.getWhatsapp());
			
			this.txtTelefone.setValue(pf.getTelefone());
			
			this.txtNome.requestFocus();
			
			if(p.getId()>0) {
				
				this.tabbedPane.setEnabledAt(0,false);
				this.tabbedPane.setEnabledAt(1, true);
				
				this.chkFornecedor.setSelected(pf.getFornecedor() != null);
				
				this.chkClienteJ.setSelected(pf.getCliente() != null);
				
				this.chkTransp.setSelected(pf.getTransportadora() != null);
				
				
			}else {
				
				this.chkFornecedor.setSelected(false);
				this.chkClienteJ.setSelected(false);
				this.chkTransp.setSelected(false);
				
				this.tabbedPane.setEnabledAt(0,true);
				this.tabbedPane.setEnabledAt(1, true);
				
			}
			
			
			
		}
		
		
		
		
	}
	
	private void salvarPessoaFisica(){
		
		if(this.p.getId() == 0) {
			
			if(this.tabbedPane.getSelectedIndex() == 0) {
				
				this.p = new PessoaFisica();
				
			}else {
				
				this.p = new PessoaJuridica();
				
			}
			
		}
		
		
		try {
			
			PessoaFisica pf = (PessoaFisica)this.p;
		
			if(
					!vc(txtNome) ||
					!vc(txtDtNasc) ||
					!vc(txtEmail) ||
					!vc(txtRg) ||
					!vc(txtCpf) ||
					!vc(txtSkype) ||
					!vc(txtWhatsapp) ||
					!vc(txtBairro) ||
					!vc(txtCep) ||
					!vc(txtRua)){
				
				return;
				
			}
			
			try{
			
				pf.setNome(this.txtNome.getText());
				pf.setData_nascimento(Masks.getData(this.txtDtNasc.getText()));
				pf.setEmail(this.txtEmail.getText());
				pf.setRg(this.txtRg.getText());
				pf.setSkype(this.txtSkype.getText());
				pf.setTelefone(this.txtTelefone.getText());
				pf.setWhatsapp(this.txtWhatsapp.getText());
				pf.setCpf(this.txtCpf.getText());
				
				Endereco e = new Endereco();
				e.setBairro(this.txtBairro.getText());
				e.setCep(this.txtCep.getText());
				e.setCidade((Cidade)this.cboCidade.getSelectedItem());
				e.setNumero(this.txtNumero.getText());
				e.setRua(this.txtRua.getText());
				
				pf.setEndereco(e);
			
				if(this.chkColaborador.isSelected()) {
					
					if(pf.getUsuario() == null) {
						
						Usuario u = new Usuario();
						u.setPf(pf);
						
						et.persist(u);
						
						pf.setUsuario(u);
						
					}
					
				}else {
					
					if(pf.getUsuario() != null) {
						
						et.remove(pf.getUsuario());
						
						pf.setUsuario(null);
						
					}
					
				}
				
				if(this.chkCliente.isSelected()) {
					
					if(pf.getCliente() == null) {
						
						Cliente u = new Cliente();
						u.setPessoa(pf);
						
						et.persist(u);
						
						pf.setCliente(u);
						
					}
					
				}else {
					
					if(pf.getCliente() != null) {
						
						et.remove(pf.getCliente());
						
						pf.setCliente(null);
						
					}
					
				}
				
			}catch(Exception ex){
				
				ex.printStackTrace();
				
				erro("Preencha os dados adequadamente");
				
			}
			
			if(pf.getId() == 0) {
				
				et.persist(pf);
				
			}else {
				
				et.merge(pf);
				
			}
			
			et.getTransaction().begin();
			et.getTransaction().commit();
			
			info("Operacao efetuada com sucesso");
			
			this.gr.atualizar();
			
			this.btNovaPessoa.doClick();
			
		}catch(ClassCastException ex) {
			
			
			PessoaJuridica pf = (PessoaJuridica)this.p;
			
			if(
					!vc(txtRazSoc) ||
					!vc(txtDataAbertura) ||
					!vc(txtEmail) ||
					!vc(txtIe) ||
					!vc(txtCnpj) ||
					!vc(txtSkype) ||
					!vc(txtWhatsapp) ||
					!vc(txtBairro) ||
					!vc(txtCep) ||
					!vc(txtRua)){
				
				return;
				
			}
			
			try{
			
				pf.setNome(this.txtRazSoc.getText());
				pf.setAbertura(Masks.getData(this.txtDataAbertura.getText()));
				pf.setEmail(this.txtEmail.getText());
				pf.setInscricao_estadual(this.txtIe.getText());
				pf.setSkype(this.txtSkype.getText());
				pf.setTelefone(this.txtTelefone.getText());
				pf.setWhatsapp(this.txtWhatsapp.getText());
				pf.setCnpj(this.txtCnpj.getText());
				
				Endereco e = new Endereco();
				e.setBairro(this.txtBairro.getText());
				e.setCep(this.txtCep.getText());
				e.setCidade((Cidade)this.cboCidade.getSelectedItem());
				e.setNumero(this.txtNumero.getText());
				e.setRua(this.txtRua.getText());
				
				pf.setEndereco(e);
			
				if(this.chkFornecedor.isSelected()) {
					
					if(pf.getFornecedor() == null) {
						
						Fornecedor u = new Fornecedor();
						u.setPj(pf);
						
						et.persist(u);
						
						pf.setFornecedor(u);
						
					}
					
				}else {
					
					if(pf.getFornecedor() != null) {
						
						et.remove(pf.getFornecedor());
						
						pf.setFornecedor(null);
						
					}
					
				}
				
				if(this.chkClienteJ.isSelected()) {
					
					if(pf.getCliente() == null) {
						
						Cliente u = new Cliente();
						u.setPessoa(pf);
						
						et.persist(u);
						
						pf.setCliente(u);
						
					}
					
				}else {
					
					if(pf.getCliente() != null) {
						
						et.remove(pf.getCliente());
						
						pf.setCliente(null);
						
					}
					
				}
				
				if(this.chkTransp.isSelected()) {
					
					if(pf.getTransportadora() == null) {
						
						Transportadora u = new Transportadora();
						u.setPj(pf);
						
						et.persist(u);
						
						pf.setTransportadora(u);
						
					}
					
				}else {
					
					if(pf.getTransportadora() != null) {
						
						et.remove(pf.getTransportadora());
						
						pf.setTransportadora(null);
						
					}
					
				}
				
			}catch(Exception ex2){
				
				ex2.printStackTrace();
				
				erro("Preencha os dados adequadamente");
				
			}
			
			if(pf.getId() == 0) {
				
				et.persist(pf);
				
			}else {
				
				et.merge(pf);
				
			}
			
			et.getTransaction().begin();
			et.getTransaction().commit();
			
			info("Operacao efetuada com sucesso");
			
			this.gr.atualizar();
			
			this.btNovaPessoa.doClick();
			
			
		}
		
		
	}

	private Empresa empresa;
	private JTextField txtNome;
	private JTextField txtRazSoc;
	private JCheckBox chkTransp;
	private JFormattedTextField txtDataAbertura;
	private JFormattedTextField txtCnpj;
	private JFormattedTextField txtIe;
	private JFormattedTextField txtDtNasc;
	private JFormattedTextField txtRg;
	private JFormattedTextField txtCpf;
	private JCheckBox chkColaborador;
	private JCheckBox chkCliente;
	private JTabbedPane tabbedPane;
	private JCheckBox chkClienteJ;
	private JCheckBox chkFornecedor;
	
	public void init(Usuario usu) {

		
		this.operador = et.merge(usu);
		this.empresa = this.operador.getPf().getEmpresa();
		et.detach(this.operador);
		this.empresa = et.merge(this.empresa);
		
		//====================================
		
		PessoaService pfs = new PessoaService(et);
		pfs.setEmpresa(this.empresa);

		this.gr = new GerenciadorLista<Pessoa>(Pessoa.class, this.tblPessoasFisicas, pfs, null,
				new Pessoa(), RepresentadorPessoa.class);
		this.gr.setFiltro(this.txtPesquisar);
		this.gr.setLblPagina(this.lblPg);
		this.gr.setLblSlider(this.slPg);

		this.gr.atualizar();

		this.tblPessoasFisicas.getSelectionModel().addListSelectionListener(e -> {

			if (!e.getValueIsAdjusting()) {
				if (this.tblPessoasFisicas.getSelectedRow() >= 0) {

					this.setPessoaFisica(
							this.gr.getModel().getListaBase().get(this.tblPessoasFisicas.getSelectedRow()));

				}
			}

		});

		this.btNovaPessoa.addActionListener(e -> {

			PessoaFisica pf = new PessoaFisica();
			pf.setEmpresa(this.empresa);
			this.setPessoaFisica(pf);

		});

		@SuppressWarnings("unchecked")
		List<Estado> estados = (List<Estado>) (List<?>) et.createQuery("SELECT e FROM Estado e").getResultList();

		this.cboEstado.setModel(new DefaultComboBoxModel<Estado>(estados.toArray(new Estado[estados.size()])));

		if (estados.size() > 0) {
			if (estados.get(0).getCidades().size() > 0) {

				this.cboCidade.setModel(new DefaultComboBoxModel<Cidade>(
						estados.get(0).getCidades().toArray(new Cidade[estados.get(0).getCidades().size()])));

			}
		}

		this.cboEstado.addActionListener(e -> {

			Estado est = (Estado) this.cboEstado.getSelectedItem();

			this.cboCidade.setModel(
					new DefaultComboBoxModel<Cidade>(est.getCidades().toArray(new Cidade[est.getCidades().size()])));

		});

		this.btConfirmar.addActionListener(a->{
			
			this.salvarPessoaFisica();
			
		});
		
		this.btNovaPessoa.doClick();
		
	}

	/**
	 * Create the frame.
	 */
	public CadastroPessoa_() {
		setResizable(false);
		setBounds(100, 100, 651, 635);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Cadastro de Pessoa");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(18, 11, 315, 32);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(18, 54, 460, 2);
		contentPane.add(separator);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Contatos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(323, 70, 312, 142);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(10, 23, 53, 14);
		panel_1.add(lblTelefone);

		JLabel lblCelular = new JLabel("Whatsapp:");
		lblCelular.setBounds(10, 48, 66, 14);
		panel_1.add(lblCelular);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 76, 53, 14);
		panel_1.add(lblEmail);

		JLabel lblSkype = new JLabel("Skype:");
		lblSkype.setBounds(10, 101, 53, 14);
		panel_1.add(lblSkype);

		txtTelefone = new JFormattedTextField();
		txtTelefone.setBounds(70, 20, 232, 20);
		panel_1.add(txtTelefone);

		Masks.telefone().install(txtTelefone);

		txtWhatsapp = new JFormattedTextField();
		txtWhatsapp.setBounds(70, 45, 232, 20);
		panel_1.add(txtWhatsapp);

		Masks.telefone().install(txtWhatsapp);

		txtEmail = new JFormattedTextField();
		txtEmail.setBounds(48, 73, 254, 20);
		panel_1.add(txtEmail);

		txtSkype = new JFormattedTextField();
		txtSkype.setBounds(48, 101, 254, 20);
		panel_1.add(txtSkype);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Endereco", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(18, 267, 460, 110);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblRua = new JLabel("Rua:");
		lblRua.setBounds(10, 22, 94, 14);
		panel_2.add(lblRua);

		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setBounds(10, 47, 94, 14);
		panel_2.add(lblBairro);

		JLabel lblNumero = new JLabel("Numero:");
		lblNumero.setBounds(10, 72, 94, 14);
		panel_2.add(lblNumero);

		JLabel lblCep = new JLabel("CEP:");
		lblCep.setBounds(237, 22, 35, 14);
		panel_2.add(lblCep);

		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setBounds(237, 72, 44, 14);
		panel_2.add(lblCidade);

		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(237, 47, 44, 14);
		panel_2.add(lblEstado);

		txtRua = new JTextField();
		txtRua.setBounds(40, 19, 175, 20);
		panel_2.add(txtRua);
		txtRua.setColumns(10);

		txtBairro = new JTextField();
		txtBairro.setColumns(10);
		txtBairro.setBounds(50, 44, 175, 20);
		panel_2.add(txtBairro);

		txtNumero = new JTextField();
		txtNumero.setColumns(10);
		txtNumero.setBounds(60, 72, 114, 20);
		panel_2.add(txtNumero);

		txtCep = new JFormattedTextField();
		txtCep.setBounds(271, 19, 179, 20);
		panel_2.add(txtCep);

		Masks.cep().install(txtCep);

		cboEstado = new JComboBox<Estado>();
		cboEstado.setBounds(281, 44, 114, 20);
		panel_2.add(cboEstado);

		cboCidade = new JComboBox<Cidade>();
		cboCidade.setBounds(281, 69, 169, 20);
		panel_2.add(cboCidade);

		btNovaPessoa = new JButton("Nova Pessoa");
		btNovaPessoa.setBounds(488, 277, 146, 32);
		contentPane.add(btNovaPessoa);

		btConfirmar = new JButton("Confirmar");
		btConfirmar.setBounds(489, 334, 146, 32);
		contentPane.add(btConfirmar);

		txtPesquisar = new JTextField();
		txtPesquisar.setColumns(10);
		txtPesquisar.setBounds(107, 388, 306, 20);
		contentPane.add(txtPesquisar);

		JLabel lblPesquisa = new JLabel("Pesquisa:");
		lblPesquisa.setBounds(18, 391, 79, 14);
		contentPane.add(lblPesquisa);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(18, 419, 617, 142);
		contentPane.add(scrollPane);

		tblPessoasFisicas = new JTable();
		scrollPane.setViewportView(tblPessoasFisicas);

		lblPg = new JLabel("Pg");
		lblPg.setBounds(589, 578, 46, 14);
		contentPane.add(lblPg);

		slPg = new JSlider();
		slPg.setBounds(18, 572, 561, 23);
		contentPane.add(slPg);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new TitledBorder(null, "Dados da Pessoa", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabbedPane.setBounds(18, 67, 306, 196);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("F\u00EDsica", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 11, 46, 14);
		panel.add(lblNome);
		
		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(10, 36, 31, 14);
		panel.add(lblCpf);
		
		JLabel lblRg = new JLabel("RG:");
		lblRg.setBounds(10, 61, 46, 14);
		panel.add(lblRg);
		
		chkCliente = new JCheckBox("Cliente");
		chkCliente.setBounds(109, 108, 97, 23);
		panel.add(chkCliente);
		
		chkColaborador = new JCheckBox("Colaborador");
		chkColaborador.setBounds(10, 108, 97, 23);
		panel.add(chkColaborador);
		
		txtNome = new JTextField();
		txtNome.setBounds(46, 8, 233, 20);
		panel.add(txtNome);
		txtNome.setColumns(10);
		
		txtCpf = new JFormattedTextField();
		txtCpf.setBounds(46, 33, 195, 20);
		panel.add(txtCpf);
		
		txtRg = new JFormattedTextField();
		txtRg.setBounds(46, 58, 195, 20);
		panel.add(txtRg);
		
		JLabel lblDataNascimento = new JLabel("Data Nascimento:");
		lblDataNascimento.setBounds(10, 87, 97, 14);
		panel.add(lblDataNascimento);
		
		txtDtNasc = new JFormattedTextField();
		txtDtNasc.setBounds(96, 84, 166, 20);
		panel.add(txtDtNasc);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Jur\u00EDdica", null, panel_3, null);
		panel_3.setLayout(null);
		
		chkFornecedor = new JCheckBox("Fornecedor");
		chkFornecedor.setBounds(10, 111, 80, 23);
		panel_3.add(chkFornecedor);
		
		chkClienteJ = new JCheckBox("Cliente");
		chkClienteJ.setBounds(92, 111, 59, 23);
		panel_3.add(chkClienteJ);
		
		txtIe = new JFormattedTextField();
		txtIe.setBounds(46, 61, 195, 20);
		panel_3.add(txtIe);
		
		JLabel lblIr = new JLabel("IE:");
		lblIr.setBounds(10, 64, 31, 14);
		panel_3.add(lblIr);
		
		JLabel lblCnpj = new JLabel("CNPJ:");
		lblCnpj.setBounds(10, 39, 31, 14);
		panel_3.add(lblCnpj);
		
		txtCnpj = new JFormattedTextField();
		txtCnpj.setBounds(46, 36, 195, 20);
		panel_3.add(txtCnpj);
		
		txtRazSoc = new JTextField();
		txtRazSoc.setColumns(10);
		txtRazSoc.setBounds(66, 11, 213, 20);
		panel_3.add(txtRazSoc);
		
		JLabel lblRazSoc = new JLabel("Raz. Soc:");
		lblRazSoc.setBounds(10, 14, 46, 14);
		panel_3.add(lblRazSoc);
		
		JLabel lblDataAbertura = new JLabel("Data Abertura:");
		lblDataAbertura.setBounds(10, 89, 80, 14);
		panel_3.add(lblDataAbertura);
		
		txtDataAbertura = new JFormattedTextField();
		txtDataAbertura.setBounds(95, 86, 184, 20);
		panel_3.add(txtDataAbertura);
		
		chkTransp = new JCheckBox("Transp");
		chkTransp.setBounds(153, 111, 59, 23);
		panel_3.add(chkTransp);
		
		JCheckBox chkBanco = new JCheckBox("Banco");
		chkBanco.setBounds(220, 111, 59, 23);
		panel_3.add(chkBanco);
	}
}
