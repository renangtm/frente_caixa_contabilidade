package br.com.inicial;

import java.awt.EventQueue;

import javax.persistence.EntityManager;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import br.com.afgtec.base.ET;
import br.com.afgtec.base.Masks;
import br.com.afgtec.pessoa.Empresa;
import br.com.afgtec.pessoa.PessoaJuridica;
import br.com.afgtec.pessoa.PessoaJuridicaService;
import br.com.afgtec.pessoa.RepresentadorPessoaJuridica;
import br.com.afgtec.transportadora.RepresentadorTransportadora;
import br.com.afgtec.transportadora.Transportadora;
import br.com.afgtec.transportadora.TransportadoraService;
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
import java.awt.Color;

public class CadastroTransportadoras extends Modulo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
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
	private JFormattedTextField txtDataAbertura;
	private JFormattedTextField txtCnpj;
	private JFormattedTextField txtIe;
	private JLabel lblPg;
	private JSlider slPg;

	/**
	 * Launch the application.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * 
	 */
	
	
	public static ImageIcon logo() {

		try {
			return Icones.getCaminhao();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Transportadoras";

	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		EntityManager et = ET.nova();

		Usuario u = et.find(Usuario.class, 3);

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					CadastroTransportadoras frame = new CadastroTransportadoras();
					frame.setVisible(true);
					frame.init(u);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private Usuario operador;

	private Transportadora t;

	private GerenciadorLista<Transportadora> gr;
	private JButton btCnpj;

	@SuppressWarnings("unused")
	private PessoaJuridica pj;
	
	private boolean norec = false;
	
	private void setPessoaJuridica(PessoaJuridica pj) {

		this.pj =pj;
		
		this.t.setPj(pj);

		this.setTransportadora(t);

	}

	private void setTransportadora(Transportadora t) {

		this.btNovaPessoa.setEnabled(t.getId()>0);
		
		this.t = t;

		this.txtNome.setText(t.getPj().getNome());

		this.txtRua.setText((t.getPj().getEndereco() == null) ? "" : t.getPj().getEndereco().getRua());

		this.txtCep.setValue((t.getPj().getEndereco() == null) ? "" : t.getPj().getEndereco().getCep());

		this.txtBairro.setText((t.getPj().getEndereco() == null) ? "" : t.getPj().getEndereco().getBairro());

		this.txtNumero.setText((t.getPj().getEndereco() == null) ? "" : t.getPj().getEndereco().getNumero());

		if (t.getPj().getEndereco() != null) {

			this.cboEstado.setSelectedItem(t.getPj().getEndereco().getCidade().getEstado());

			this.cboCidade.setSelectedItem(t.getPj().getEndereco().getCidade());

		}

		this.norec=true;
		this.txtCnpj.setValue(t.getPj().getCnpj());
		this.norec = false;
		
		this.txtEmail.setText(t.getPj().getEmail());

		this.txtIe.setText(t.getPj().getInscricao_estadual());

		this.txtSkype.setText(t.getPj().getSkype());

		this.txtWhatsapp.setValue(t.getPj().getWhatsapp());

		this.txtTelefone.setValue(t.getPj().getTelefone());

		this.txtDataAbertura.setValue(new ConversorDate().paraString(t.getPj().getAbertura()));

		this.txtNome.requestFocus();

	}

	private void salvarTransportadora() {

		if (!vc(txtNome) || !vc(txtDataAbertura) || !vc(txtEmail) || !vc(txtIe) || !vc(txtCnpj) || !vc(txtSkype)
				|| !vc(txtWhatsapp) || !vc(txtBairro) || !vc(txtCep) || !vc(txtRua)) {

			return;

		}

		try {
			
			this.t.getPj().setNome(this.txtNome.getText());
			this.t.getPj().setEmail(this.txtEmail.getText());
			this.t.getPj().setInscricao_estadual(this.txtIe.getText());
			this.t.getPj().setCnpj(this.txtCnpj.getText());
			this.t.getPj().setSkype(this.txtSkype.getText());
			this.t.getPj().setTelefone(this.txtTelefone.getText());
			this.t.getPj().setWhatsapp(this.txtWhatsapp.getText());
			this.t.getPj().setAbertura(Masks.getData(this.txtDataAbertura.getText()));

			Endereco e = new Endereco();
			e.setBairro(this.txtBairro.getText());
			e.setCep(this.txtCep.getText());
			e.setCidade((Cidade) this.cboCidade.getSelectedItem());
			e.setNumero(this.txtNumero.getText());
			e.setRua(this.txtRua.getText());

			this.t.getPj().setEndereco(e);

		} catch (Exception ex) {

			ex.printStackTrace();

			erro("Preencha os dados adequadamente");

			return;
		}
		
		if(t.getId() == 0) {
			
			et.persist(t);
			
		}else {
			
			et.merge(t);
			
		}
		
		et.getTransaction().begin();
		et.getTransaction().commit();

		info("Operacao efetuada com sucesso");

		this.gr.atualizar();

		this.btNovaPessoa.doClick();

	}

	private Empresa empresa;
	
	public void init(Usuario usu) {

		this.operador = et.merge(usu);
		this.empresa = this.operador.getPf().getEmpresa();
		et.detach(usu);
		this.empresa = et.merge(empresa);

		TransportadoraService pfs = new TransportadoraService(et);
		pfs.setEmpresa(empresa);

		this.gr = new GerenciadorLista<Transportadora>(Transportadora.class, this.tblPessoasFisicas, pfs, null,
				new Transportadora(), RepresentadorTransportadora.class);
		this.gr.setFiltro(this.txtPesquisar);
		this.gr.setLblPagina(this.lblPg);
		this.gr.setLblSlider(this.slPg);

		this.gr.atualizar();

		this.tblPessoasFisicas.getSelectionModel().addListSelectionListener(e -> {

			if (!e.getValueIsAdjusting()) {
				if (this.tblPessoasFisicas.getSelectedRow() >= 0) {

					this.setTransportadora(
							this.gr.getModel().getListaBase().get(this.tblPessoasFisicas.getSelectedRow()));

				}
			}

		});

		this.btNovaPessoa.addActionListener(e -> {

			Transportadora pj = new Transportadora();
			pj.getPj().setEmpresa(empresa);
			this.setTransportadora(pj);

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

		this.btConfirmar.addActionListener(a -> {

			this.salvarTransportadora();

		});

		this.txtCnpj.addActionListener(x -> {

			if(this.norec)
				return;
			
			PessoaJuridicaService pjs = new PessoaJuridicaService(et);
			pjs.setEmpresa(empresa);

			PessoaJuridica pj = pjs.getPeloCNPJ(this.txtCnpj.getText());

			if (pj != null) {

				this.setPessoaJuridica(pj);

			}else{
				
				this.setPessoaJuridica(new PessoaJuridica());
				
			}

		});

		this.btCnpj.addActionListener(x -> {

			PessoaJuridicaService pjs = new PessoaJuridicaService(et);
			pjs.setEmpresa(empresa);

			Seletor<PessoaJuridica> s =
					new Seletor<PessoaJuridica>(PessoaJuridica.class, pjs, RepresentadorPessoaJuridica.class, p -> {
				this.setPessoaJuridica(p);
			},this);
			
			s.setVisible(true);

		});
		
		this.btNovaPessoa.doClick();

	}

	/**
	 * Create the frame.
	 */
	public CadastroTransportadoras() {
		setResizable(false);
		setBounds(100, 100, 651, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Cadastro de Transportadoras");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(18, 11, 315, 32);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(18, 54, 460, 2);
		contentPane.add(separator);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dados da empresa",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(18, 70, 295, 142);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 60, 39, 14);
		panel.add(lblNome);

		JLabel lblRg = new JLabel("IE:");
		lblRg.setBounds(10, 85, 28, 14);
		panel.add(lblRg);

		JLabel lblDataAbertura = new JLabel("Data Abertura:");
		lblDataAbertura.setBounds(10, 110, 94, 14);
		panel.add(lblDataAbertura);

		txtNome = new JTextField();
		txtNome.setBounds(48, 57, 237, 20);
		panel.add(txtNome);
		txtNome.setColumns(10);

		txtIe = new JFormattedTextField();
		txtIe.setBounds(48, 82, 192, 20);
		panel.add(txtIe);

		txtDataAbertura = new JFormattedTextField();
		txtDataAbertura.setBounds(106, 107, 179, 20);
		panel.add(txtDataAbertura);

		Masks.data().install(txtDataAbertura);

		JLabel lblCpf = new JLabel("CNPJ:");
		lblCpf.setBounds(10, 32, 46, 14);
		panel.add(lblCpf);

		txtCnpj = new JFormattedTextField();
		txtCnpj.setBackground(Color.LIGHT_GRAY);
		txtCnpj.setBounds(48, 26, 181, 20);
		panel.add(txtCnpj);

		Masks.cnpj().install(txtCnpj);

		btCnpj = new JButton("...");
		btCnpj.setBounds(239, 25, 46, 23);
		panel.add(btCnpj);

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
		panel_2.setBounds(18, 223, 460, 110);
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

		btNovaPessoa = new JButton("Nova Transportadora");
		btNovaPessoa.setBounds(489, 241, 146, 32);
		contentPane.add(btNovaPessoa);

		btConfirmar = new JButton("Confirmar");
		btConfirmar.setBounds(488, 284, 146, 32);
		contentPane.add(btConfirmar);

		txtPesquisar = new JTextField();
		txtPesquisar.setColumns(10);
		txtPesquisar.setBounds(107, 344, 306, 20);
		contentPane.add(txtPesquisar);

		JLabel lblPesquisa = new JLabel("Pesquisa:");
		lblPesquisa.setBounds(18, 347, 79, 14);
		contentPane.add(lblPesquisa);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(18, 375, 617, 142);
		contentPane.add(scrollPane);

		tblPessoasFisicas = new JTable();
		scrollPane.setViewportView(tblPessoasFisicas);

		lblPg = new JLabel("Pg");
		lblPg.setBounds(589, 534, 46, 14);
		contentPane.add(lblPg);

		slPg = new JSlider();
		slPg.setBounds(18, 528, 561, 23);
		contentPane.add(slPg);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(270, 95, 32, 23);
		contentPane.add(btnNewButton);
	}

}
