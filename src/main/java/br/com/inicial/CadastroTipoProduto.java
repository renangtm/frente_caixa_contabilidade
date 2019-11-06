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
import br.com.produto.Tipo;
import br.com.produto.TipoService;
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

public class CadastroTipoProduto extends Modulo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable tblNcm;
	private JTextField txtPesquisar;

	private GerenciadorLista<Tipo> model;

	private Tipo tipo;
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

		return "Cadastro de Tipo de Produtos";

	}
	
	private void setNCM(Tipo tipo) {

		this.tipo = tipo;

		this.btnNovoNcm.setEnabled(this.tipo.getId() > 0);
		this.txtDescricao.setValue(this.tipo.getNome());

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

					CadastroTipoProduto frame = new CadastroTipoProduto();
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

			Tipo n = new Tipo();
			n.setEmpresa(this.empresa);
			setNCM(n);
			this.txtDescricao.requestFocus();

		});

		this.btnConfirmar.addActionListener(e -> {

			if (!vc(this.txtDescricao)) {
				return;
			}

			tipo.setNome(this.txtDescricao.getText());

			try {

							
				new TipoService(et).merge(tipo);
				
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
		
		TipoService ts = new TipoService(et);
		ts.setEmpresa(this.empresa);
		
		this.model = new GerenciadorLista<Tipo>(Tipo.class,this.tblNcm,ts,null);
		this.model.setFiltro(txtPesquisar);
		this.model.setLblPagina(lblPg);
		this.model.setLblSlider(slPg);
		
		this.model.atualizar();

	}

	/**
	 * Create the frame.
	 */

	public CadastroTipoProduto() {
		setTitle("Cadastro de CFOP");
		setBounds(100, 100, 461, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Cadastro de Tipos de Produto");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 11, 346, 38);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 47, 285, 2);
		contentPane.add(separator);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dados da Categoria", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 60, 424, 51);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNcm = new JLabel("Nome");
		lblNcm.setBounds(10, 22, 34, 14);
		panel.add(lblNcm);

		txtDescricao = new JFormattedTextField();
		txtDescricao.setBounds(54, 19, 360, 20);
		panel.add(txtDescricao);

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(353, 122, 81, 23);
		contentPane.add(btnConfirmar);

		btnNovoNcm = new JButton("+ Tipo");
		btnNovoNcm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNovoNcm.setBounds(272, 122, 71, 23);
		contentPane.add(btnNovoNcm);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 156, 425, 155);
		contentPane.add(scrollPane);

		tblNcm = new JTable();
		scrollPane.setViewportView(tblNcm);

		JLabel lblPesquisarNcm = new JLabel("Pesquisar Tipo");
		lblPesquisarNcm.setBounds(13, 125, 94, 14);
		contentPane.add(lblPesquisarNcm);

		txtPesquisar = new JTextField();
		txtPesquisar.setBounds(117, 122, 145, 20);
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
