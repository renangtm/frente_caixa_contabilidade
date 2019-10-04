package br.com.inicial;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;

import br.com.afgtec.notas.CFOP;
import br.com.afgtec.notas.CFOPService;
import br.com.afgtec.usuario.Usuario;
import br.com.agrofauna.utilidades.GerenciadorLista;
import br.com.entidades.Icones;

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

public class CadastroCFOP_ extends Modulo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable tblNcm;
	private JTextField txtPesquisar;

	private GerenciadorLista<CFOP> model;

	private CFOP ncm;
	private JButton btnConfirmar;
	private JButton btnNovoNcm;
	private JFormattedTextField txtCodigoEx;
	private JFormattedTextField txtDescricao;
	private JFormattedTextField txtNcm;

	
	public static ImageIcon logo() {

		try {
			return Icones.getConfig();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Cadastro de CFOP";

	}
	
	private void setNCM(CFOP ncm) {

		this.ncm = ncm;

		this.btnNovoNcm.setEnabled(this.ncm.getId() > 0);
		this.txtCodigoEx.setValue(ncm.getSubnumero());
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

					CadastroCFOP_ frame = new CadastroCFOP_();
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


		this.tblNcm.getSelectionModel().addListSelectionListener(e -> {

			if (!e.getValueIsAdjusting()) {

				if (tblNcm.getSelectedRow() >= 0) {

					setNCM(model.getModel().getListaBase().get(tblNcm.getSelectedRow()));

				}

			}

		});

		this.btnNovoNcm.addActionListener(e -> {

			CFOP n = new CFOP();
			setNCM(n);
			this.txtNcm.requestFocus();

		});

		this.btnConfirmar.addActionListener(e -> {

			if (!vc(this.txtDescricao) || !vc(this.txtNcm) || !vc(this.txtCodigoEx)) {
				return;
			}

			ncm.setDescricao(this.txtDescricao.getText());
			ncm.setSubnumero(this.txtCodigoEx.getText());
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

	public CadastroCFOP_() {
		setTitle("Cadastro de CFOP");
		setResizable(false);
		setBounds(100, 100, 452, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Cadastro de CFOP");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 11, 346, 38);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 47, 285, 2);
		contentPane.add(separator);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Dados NCM", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 60, 424, 81);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNcm = new JLabel("CFOP");
		lblNcm.setBounds(10, 22, 34, 14);
		panel.add(lblNcm);

		JLabel lblDescricao = new JLabel("Descricao:");
		lblDescricao.setBounds(10, 50, 71, 14);
		panel.add(lblDescricao);

		JLabel lblCodEx = new JLabel("Cod:");
		lblCodEx.setBounds(306, 22, 45, 14);
		panel.add(lblCodEx);

		txtNcm = new JFormattedTextField();
		txtNcm.setBounds(50, 19, 223, 20);
		panel.add(txtNcm);

		txtDescricao = new JFormattedTextField();
		txtDescricao.setBounds(71, 47, 343, 20);
		panel.add(txtDescricao);

		txtCodigoEx = new JFormattedTextField();
		txtCodigoEx.setBounds(352, 19, 62, 20);
		panel.add(txtCodigoEx);

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(353, 152, 81, 23);
		contentPane.add(btnConfirmar);

		btnNovoNcm = new JButton("+ CFOP");
		btnNovoNcm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNovoNcm.setBounds(269, 152, 83, 23);
		contentPane.add(btnNovoNcm);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 186, 424, 125);
		contentPane.add(scrollPane);

		tblNcm = new JTable();
		scrollPane.setViewportView(tblNcm);

		JLabel lblPesquisarNcm = new JLabel("Pesquisar CFOP:");
		lblPesquisarNcm.setBounds(10, 155, 94, 14);
		contentPane.add(lblPesquisarNcm);

		txtPesquisar = new JTextField();
		txtPesquisar.setBounds(93, 152, 166, 20);
		contentPane.add(txtPesquisar);
		txtPesquisar.setColumns(10);
		
		JSlider slPg = new JSlider();
		slPg.setBounds(181, 322, 200, 23);
		contentPane.add(slPg);
		
		JLabel lblPg = new JLabel("lblPg");
		lblPg.setBounds(388, 322, 46, 14);
		contentPane.add(lblPg);

		
		this.model = new GerenciadorLista<CFOP>(CFOP.class,this.tblNcm,new CFOPService(et),null);
		this.model.setFiltro(txtPesquisar);
		this.model.setLblPagina(lblPg);
		this.model.setLblSlider(slPg);
		
		this.model.atualizar();
		
	}
}
