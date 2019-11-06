package br.com.inicial;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;

import br.com.base.Resources;
import br.com.empresa.Empresa;
import br.com.produto.BandeiraCartao;
import br.com.produto.BandeiraCartaoService;
import br.com.usuario.Usuario;
import br.com.utilidades.GerenciadorLista;

import javax.swing.JFormattedTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JSlider;
import java.awt.Color;

public class CadastroBandeiraCartao extends Modulo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable tblNcm;
	private JTextField txtPesquisar;

	private GerenciadorLista<BandeiraCartao> model;

	private BandeiraCartao tipo;
	private JButton btnConfirmar;
	private JButton btnNovoNcm;
	private JFormattedTextField txtDescricao;

	public static ImageIcon logo() {

		try {
			return Resources.getConfig();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Cadastro de Bandeira de Cartao";

	}

	private void setNCM(BandeiraCartao tipo) {

		this.tipo = tipo;

		this.btnNovoNcm.setEnabled(this.tipo.getId() > 0);
		this.txtDescricao.setValue(this.tipo.getNome());
		this.txtCnpj.setValue(this.tipo.getCnpjCredenciadora());

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

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					CadastroBandeiraCartao frame = new CadastroBandeiraCartao();
					frame.setVisible(true);
					frame.init(null);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private Empresa empresa;

	private JSlider slPg;

	private JLabel lblPg;
	private JLabel lblCnpj;
	private JFormattedTextField txtCnpj;

	@Override
	public void init(Usuario u) {

		this.empresa = et.merge(u.getPf().getEmpresa());

		this.tblNcm.getSelectionModel().addListSelectionListener(e -> {

			if (!e.getValueIsAdjusting()) {

				if (tblNcm.getSelectedRow() >= 0) {

					setNCM(model.getModel().getListaBase().get(tblNcm.getSelectedRow()));

				}

			}

		});

		this.btnNovoNcm.addActionListener(e -> {

			BandeiraCartao n = new BandeiraCartao();
			n.setEmpresa(this.empresa);
			setNCM(n);
			this.txtDescricao.requestFocus();

		});

		this.btnConfirmar.addActionListener(e -> {

			if (!vc(this.txtDescricao) || !vc(this.txtCnpj)) {
				return;
			}

			tipo.setNome(this.txtDescricao.getText());
			tipo.setCnpjCredenciadora(this.txtCnpj.getText());

			try {

				new BandeiraCartaoService(et).merge(tipo);

				et.getTransaction().begin();
				et.getTransaction().commit();

				this.btnNovoNcm.doClick();
				model.atualizar();
				info("Operacao efetuada com sucesso");

			} catch (Exception ex) {

				ex.printStackTrace();
				erro("Ocorreu um problem ao efetuar essa operacao");

			}

		});

		this.btnNovoNcm.doClick();

		BandeiraCartaoService ts = new BandeiraCartaoService(et);
		ts.setEmpresa(this.empresa);

		this.model = new GerenciadorLista<BandeiraCartao>(BandeiraCartao.class, this.tblNcm, ts, null);
		this.model.setFiltro(txtPesquisar);
		this.model.setLblPagina(lblPg);
		this.model.setLblSlider(slPg);

		this.model.atualizar();

	}

	/**
	 * Create the frame.
	 */

	public CadastroBandeiraCartao() {
		setTitle("Cadastro de Bandeira de Cartao");
		setBounds(100, 100, 461, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Cadastro de Bandeira de Cartao");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 11, 346, 38);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 47, 285, 2);
		contentPane.add(separator);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dados da Categoria",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 60, 424, 83);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNcm = new JLabel("Nome");
		lblNcm.setBounds(10, 22, 34, 14);
		panel.add(lblNcm);

		txtDescricao = new JFormattedTextField();
		txtDescricao.setBounds(54, 19, 360, 20);
		panel.add(txtDescricao);

		lblCnpj = new JLabel("Cnpj");
		lblCnpj.setBounds(10, 47, 34, 14);
		panel.add(lblCnpj);

		txtCnpj = new JFormattedTextField();
		txtCnpj.setBounds(54, 44, 198, 20);
		panel.add(txtCnpj);

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(350, 154, 81, 23);
		contentPane.add(btnConfirmar);

		btnNovoNcm = new JButton("+ Band.");
		btnNovoNcm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNovoNcm.setBounds(269, 154, 71, 23);
		contentPane.add(btnNovoNcm);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 188, 425, 123);
		contentPane.add(scrollPane);

		tblNcm = new JTable();
		scrollPane.setViewportView(tblNcm);

		JLabel lblPesquisarNcm = new JLabel("Pesquisar Tipo");
		lblPesquisarNcm.setBounds(10, 157, 94, 14);
		contentPane.add(lblPesquisarNcm);

		txtPesquisar = new JTextField();
		txtPesquisar.setBounds(114, 154, 145, 20);
		contentPane.add(txtPesquisar);
		txtPesquisar.setColumns(10);

		slPg = new JSlider();
		slPg.setBounds(181, 322, 200, 23);
		contentPane.add(slPg);

		lblPg = new JLabel("lblPg");
		lblPg.setBounds(388, 322, 46, 14);
		contentPane.add(lblPg);

	}
}
