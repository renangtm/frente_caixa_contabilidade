package br.com.inicial;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;

import br.com.base.CFG;
import br.com.base.Resources;
import br.com.empresa.Contabilidade;
import br.com.empresa.Empresa;
import br.com.usuario.Usuario;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.io.IOException;
import java.util.List;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JComboBox;

public class ControleEmpresa extends Modulo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JComboBox<Empresa> chkEmpresas;

	private JButton btAlterar;

	public static ImageIcon logo() {

		try {
			return Resources.getConfig();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Controlar Empresas";

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

					ControleEmpresa frame = new ControleEmpresa();
					frame.setVisible(true);
					frame.init(null);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private Usuario operador;
	private Empresa empresa;

	@Override
	public void init(Usuario u) {

		this.operador = et.merge(u);
		this.empresa = et.merge(CFG.empresa);

		List<Empresa> empresas = ((Contabilidade) this.operador.getPf().getEmpresa()).getClientes();

		Empresa[] array = new Empresa[empresas.size()+1];
		array[0] = this.operador.getPf().getEmpresa();
		
		for(int i=0;i<empresas.size();i++)
			array[i+1] = empresas.get(i);
		
		
		this.chkEmpresas.setModel(new DefaultComboBoxModel<Empresa>(array));
		this.chkEmpresas.setSelectedItem(this.empresa);
		
		this.btAlterar.addActionListener(a->{
			
			CFG.empresa = (Empresa) this.chkEmpresas.getSelectedItem();
			info("Operacao efetuada com sucesso");
			MenuPrincipal.menu.setControleEmpresa(CFG.empresa);
			dispose();
			
		});
		
	}

	/**
	 * Create the frame.
	 */

	public ControleEmpresa() {
		setTitle("Alterar controle de Empresas");
		setResizable(false);
		setBounds(100, 100, 452, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Controle de Empresas");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 11, 346, 38);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 47, 285, 2);
		contentPane.add(separator);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Alterar Empresa", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 60, 416, 125);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Aqui e possivel alterar a empresa que esta gerenciando.");
		lblNewLabel_1.setBounds(10, 21, 383, 22);
		panel.add(lblNewLabel_1);

		JLabel lblAposEfetuarA = new JLabel(
				"Apos efetuar a alteracao feche as janelas que esta trabalhando e abra novas.");
		lblAposEfetuarA.setBounds(10, 43, 383, 22);
		panel.add(lblAposEfetuarA);

		chkEmpresas = new JComboBox<Empresa>();
		chkEmpresas.setBounds(20, 80, 282, 20);
		panel.add(chkEmpresas);

		btAlterar = new JButton("Alterar");
		btAlterar.setBounds(318, 92, 88, 22);
		panel.add(btAlterar);

	}
}
