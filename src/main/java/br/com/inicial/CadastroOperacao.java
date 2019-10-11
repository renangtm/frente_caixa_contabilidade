package br.com.inicial;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;

import br.com.base.ET;
import br.com.base.Resources;
import br.com.operacao.Operacao;
import br.com.operacao.OperacaoService;
import br.com.usuario.Usuario;
import br.com.utilidades.GerenciadorLista;

import javax.swing.JFormattedTextField;
import javax.persistence.EntityManager;
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
import javax.swing.JCheckBox;

public class CadastroOperacao extends Modulo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable tblNcm;
	private JTextField txtPesquisar;

	private GerenciadorLista<Operacao> model;

	private Operacao ncm;
	private JButton btnConfirmar;
	private JButton btnNovoNcm;
	private JFormattedTextField txtNcm;

	private JCheckBox chkCreditoDebito;

	
	public static ImageIcon logo() {

		try {
			return Resources.getConfig();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Cadastro de Operacao";

	}
	
	private void setNCM(Operacao ncm) {

		this.ncm = ncm;

		this.btnNovoNcm.setEnabled(this.ncm.getId() > 0);
		this.chkCreditoDebito.setSelected(ncm.isCredito());
		
		this.txtNcm.setValue("");
		this.txtNcm.setValue(ncm.getNome());

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

					CadastroOperacao frame = new CadastroOperacao();
					frame.setVisible(true);
					frame.init(null);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	@Override
	public void init(Usuario u) {

		final EntityManager et = ET.nova();
		
		

		this.tblNcm.getSelectionModel().addListSelectionListener(e -> {

			if (!e.getValueIsAdjusting()) {

				if (tblNcm.getSelectedRow() >= 0) {

					setNCM(model.getModel().getListaBase().get(tblNcm.getSelectedRow()));

				}

			}

		});

		this.btnNovoNcm.addActionListener(e -> {

			Operacao n = new Operacao();
			setNCM(n);
			this.txtNcm.requestFocus();

		});

		this.btnConfirmar.addActionListener(e -> {

			if (!vc(this.txtNcm)) {
				return;
			}

			ncm.setNome(this.txtNcm.getText());
			ncm.setCredito(chkCreditoDebito.isSelected());
			
			try {

				new OperacaoService(et).merge(this.ncm);
				
				et.getTransaction().begin();
				et.getTransaction().commit();

				Operacao n = new Operacao();
				setNCM(n);
				
				model.atualizar();
				info("Operacao efetuada com sucesso");

			} catch (Exception ex) {

				ex.printStackTrace();
				erro("Ocorreu um problem ao efetuar essa operacao");

			}

		});
		
		this.btnNovoNcm.doClick();

	}

	/**
	 * Create the frame.
	 */

	public CadastroOperacao() {
		setTitle("Cadastro de NCM");
		setResizable(false);
		setBounds(100, 100, 452, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Cadastro de Operacao");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 11, 346, 38);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 47, 285, 2);
		contentPane.add(separator);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dados da Operacao", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 60, 424, 74);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNcm = new JLabel("Nome:");
		lblNcm.setBounds(10, 22, 34, 14);
		panel.add(lblNcm);

		txtNcm = new JFormattedTextField();
		txtNcm.setBounds(50, 19, 223, 20);
		panel.add(txtNcm);
		
		chkCreditoDebito = new JCheckBox("Credito/Debito");
		chkCreditoDebito.setBounds(6, 43, 97, 23);
		panel.add(chkCreditoDebito);

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(353, 145, 81, 23);
		contentPane.add(btnConfirmar);

		btnNovoNcm = new JButton("Nova Op.");
		btnNovoNcm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNovoNcm.setBounds(266, 145, 83, 23);
		contentPane.add(btnNovoNcm);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 179, 424, 110);
		contentPane.add(scrollPane);

		tblNcm = new JTable();
		scrollPane.setViewportView(tblNcm);

		JLabel lblPesquisarNcm = new JLabel("Pesquisar Operacao:");
		lblPesquisarNcm.setBounds(10, 145, 81, 14);
		contentPane.add(lblPesquisarNcm);

		txtPesquisar = new JTextField();
		txtPesquisar.setBounds(90, 145, 166, 20);
		contentPane.add(txtPesquisar);
		txtPesquisar.setColumns(10);
		
		JSlider slPg = new JSlider();
		slPg.setBounds(175, 300, 200, 23);
		contentPane.add(slPg);
		
		JLabel lblPg = new JLabel("lblPg");
		lblPg.setBounds(388, 300, 46, 14);
		contentPane.add(lblPg);

		
		this.model = new GerenciadorLista<Operacao>(Operacao.class,this.tblNcm,new OperacaoService(et),null);
		this.model.setFiltro(txtPesquisar);
		this.model.setLblPagina(lblPg);
		this.model.setLblSlider(slPg);
		
		this.model.atualizar();
		
	}
}
