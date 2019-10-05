package br.com.inicial;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultFormatterFactory;

import br.com.base.ET;
import br.com.base.Masks;
import br.com.base.Resources;
import br.com.entidades.ncm.NCM;
import br.com.entidades.ncm.NCMService;
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

public class CadastroNcm extends Modulo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable tblNcm;
	private JTextField txtPesquisar;

	private GerenciadorLista<NCM> model;

	private NCM ncm;
	private JButton btnConfirmar;
	private JButton btnNovoNcm;
	private JFormattedTextField txtCodigoEx;
	private JFormattedTextField txtDescricao;
	private JFormattedTextField txtAliqIpi;
	private JFormattedTextField txtNcm;

	
	public static ImageIcon logo() {

		try {
			return Resources.getConfig();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Cadastro de NCM";

	}
	
	private void setNCM(NCM ncm) {

		this.ncm = ncm;

		this.btnNovoNcm.setEnabled(this.ncm.getId() > 0);
		this.txtAliqIpi.setValue(ncm.getAlicota_ipi());
		this.txtCodigoEx.setValue(ncm.getEx());
		this.txtDescricao.setValue(ncm.getDescricao());
		this.txtNcm.setValue(ncm.getNumero());

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

					CadastroNcm frame = new CadastroNcm();
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

			NCM n = new NCM();
			setNCM(n);
			this.txtNcm.requestFocus();

		});

		this.btnConfirmar.addActionListener(e -> {

			if (!vc(this.txtAliqIpi) || !vc(this.txtDescricao) || !vc(this.txtNcm) || !vc(this.txtCodigoEx)) {
				return;
			}

			ncm.setAlicota_ipi(((Number) this.txtAliqIpi.getValue()).doubleValue());
			ncm.setDescricao(this.txtDescricao.getText());
			ncm.setEx(((Number) this.txtCodigoEx.getValue()).intValue());
			ncm.setNumero(this.txtNcm.getText());

			try {

				if(ncm.getId() == 0) {
					
					et.persist(ncm);
					
				}else {
					
					et.merge(ncm);
					
				}
				
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

	}

	/**
	 * Create the frame.
	 */

	public CadastroNcm() {
		setTitle("Cadastro de NCM");
		setResizable(false);
		setBounds(100, 100, 452, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Cadastro de NCM");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 11, 346, 38);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 47, 285, 2);
		contentPane.add(separator);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Dados NCM", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 60, 424, 107);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNcm = new JLabel("NCM:");
		lblNcm.setBounds(10, 22, 34, 14);
		panel.add(lblNcm);

		JLabel lblAliqIpi = new JLabel("Aliq IPI(%):");
		lblAliqIpi.setBounds(10, 47, 62, 14);
		panel.add(lblAliqIpi);

		JLabel lblDescricao = new JLabel("Descricao:");
		lblDescricao.setBounds(10, 75, 71, 14);
		panel.add(lblDescricao);

		JLabel lblCodEx = new JLabel("Cod. Ex:");
		lblCodEx.setBounds(283, 22, 62, 14);
		panel.add(lblCodEx);

		txtNcm = new JFormattedTextField();
		txtNcm.setBounds(50, 19, 223, 20);
		panel.add(txtNcm);

		txtAliqIpi = new JFormattedTextField();
		txtAliqIpi.setBounds(71, 44, 62, 20);
		panel.add(txtAliqIpi);
		this.txtAliqIpi.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));

		txtDescricao = new JFormattedTextField();
		txtDescricao.setBounds(71, 72, 343, 20);
		panel.add(txtDescricao);

		txtCodigoEx = new JFormattedTextField();
		txtCodigoEx.setBounds(352, 19, 62, 20);
		panel.add(txtCodigoEx);

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(353, 167, 81, 23);
		contentPane.add(btnConfirmar);

		btnNovoNcm = new JButton("Novo NCM");
		btnNovoNcm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNovoNcm.setBounds(269, 167, 83, 23);
		contentPane.add(btnNovoNcm);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 201, 424, 110);
		contentPane.add(scrollPane);

		tblNcm = new JTable();
		scrollPane.setViewportView(tblNcm);

		JLabel lblPesquisarNcm = new JLabel("Pesquisar NCM:");
		lblPesquisarNcm.setBounds(10, 171, 94, 14);
		contentPane.add(lblPesquisarNcm);

		txtPesquisar = new JTextField();
		txtPesquisar.setBounds(93, 168, 166, 20);
		contentPane.add(txtPesquisar);
		txtPesquisar.setColumns(10);
		
		JSlider slPg = new JSlider();
		slPg.setBounds(181, 322, 200, 23);
		contentPane.add(slPg);
		
		JLabel lblPg = new JLabel("lblPg");
		lblPg.setBounds(388, 322, 46, 14);
		contentPane.add(lblPg);

		
		this.model = new GerenciadorLista<NCM>(NCM.class,this.tblNcm,new NCMService(et),null);
		this.model.setFiltro(txtPesquisar);
		this.model.setLblPagina(lblPg);
		this.model.setLblSlider(slPg);
		
		this.model.atualizar();
		
	}
}
