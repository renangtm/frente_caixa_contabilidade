package br.com.inicial;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;

import br.com.base.CFG;
import br.com.base.ET;
import br.com.base.Resources;
import br.com.empresa.Contabilidade;
import br.com.empresa.ContabilidadeService;
import br.com.empresa.Empresa;
import br.com.empresa.EmpresaService;
import br.com.empresa.RepresentadorContabilidade;
import br.com.usuario.Usuario;
import br.com.utilidades.ListModelGenerica;

import javax.swing.JFormattedTextField;
import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.io.IOException;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Color;

public class SelecaoContabilidade extends Modulo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable tblContabilidades;

	private ListModelGenerica<Contabilidade> modelContabilidade;

	private Contabilidade contabilidade;

	private JButton btnConfirmar;

	private JFormattedTextField txtCodigoEx;

	private JFormattedTextField txtDescricao;

	private JFormattedTextField txtNcm;

	public static ImageIcon logo() {

		try {
			return Resources.getConfig();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Contabilidades";

	}

	private void setContabilidade(Contabilidade c) {

		this.contabilidade = c;

		if (c == null) {

			this.txtCodigoEx.setText("Sem contabilidade");
			this.txtDescricao.setText("Sem contabilidade");
			this.txtNcm.setText("Sem contabilidade");

			return;

		}

		this.txtCodigoEx.setText(c.getId() + "");
		this.txtDescricao.setText(c.getPj().getNome());
		this.txtNcm.setText(c.getPj().getCnpj());

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

		Usuario u = et.find(Usuario.class, 1);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					SelecaoContabilidade frame = new SelecaoContabilidade();
					frame.setVisible(true);
					frame.init(u);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings("unused")
	private Usuario operador;
	private Empresa empresa;

	private JButton btRemover;

	@Override
	public void init(Usuario u) {

		this.operador = et.merge(u);
		this.empresa = CFG.empresa;

		ContabilidadeService cs = new ContabilidadeService(et);

		this.modelContabilidade = new ListModelGenerica<Contabilidade>(cs.getEmpresas(""), Contabilidade.class,
				RepresentadorContabilidade.class);
		this.tblContabilidades.setModel(this.modelContabilidade);

		this.setContabilidade(this.empresa.getContabilidade());

		this.tblContabilidades.getSelectionModel().addListSelectionListener(a -> {

			if (!a.getValueIsAdjusting()) {

				if (this.tblContabilidades.getSelectedRow() >= 0) {

					Contabilidade c = this.modelContabilidade.getListaBase()
							.get(this.tblContabilidades.getSelectedRow());

					this.setContabilidade(c);

				}

			}

		});
		
		this.btRemover.addActionListener(a->{
			
			this.setContabilidade(null);
			
		});
		
		this.btnConfirmar.addActionListener(a->{
			
			this.empresa.setContabilidade(this.contabilidade);
			
			this.empresa = new EmpresaService(et).merge(this.empresa);
			
			et.getTransaction().begin();
			et.getTransaction().commit();
			
			info("Operacao efetuada com sucesso");
			
		});

	}

	/**
	 * Create the frame.
	 */

	public SelecaoContabilidade() {
		setTitle("Cadastro de CFOP");
		setBounds(100, 100, 461, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Selecao de Contabilidade");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 11, 346, 38);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 47, 285, 2);
		contentPane.add(separator);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dados da Contabilidade",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 60, 424, 117);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNcm = new JLabel("CNPJ:");
		lblNcm.setBounds(10, 22, 34, 14);
		panel.add(lblNcm);

		JLabel lblDescricao = new JLabel("Razao Social:");
		lblDescricao.setBounds(10, 50, 71, 14);
		panel.add(lblDescricao);

		JLabel lblCodEx = new JLabel("Cod:");
		lblCodEx.setBounds(306, 22, 45, 14);
		panel.add(lblCodEx);

		txtNcm = new JFormattedTextField();
		txtNcm.setEditable(false);
		txtNcm.setEnabled(false);
		txtNcm.setBounds(54, 19, 223, 20);
		panel.add(txtNcm);

		txtDescricao = new JFormattedTextField();
		txtDescricao.setEnabled(false);
		txtDescricao.setBounds(81, 47, 333, 20);
		panel.add(txtDescricao);

		txtCodigoEx = new JFormattedTextField();
		txtCodigoEx.setEnabled(false);
		txtCodigoEx.setBounds(352, 19, 62, 20);
		panel.add(txtCodigoEx);

		btnConfirmar = new JButton("Selecionar");
		btnConfirmar.setBounds(333, 83, 81, 23);
		panel.add(btnConfirmar);
		
		btRemover = new JButton("Remover Contabilidade");
		btRemover.setBounds(180, 83, 143, 23);
		panel.add(btRemover);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 186, 425, 146);
		contentPane.add(scrollPane);

		tblContabilidades = new JTable();
		scrollPane.setViewportView(tblContabilidades);

	}
}
