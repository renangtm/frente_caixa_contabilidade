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
import br.com.historico.Historico;
import br.com.historico.HistoricoService;
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

public class CadastroHistorico extends Modulo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable tblNcm;
	private JTextField txtPesquisar;

	private GerenciadorLista<Historico> model;

	private Historico ncm;
	private JButton btnConfirmar;
	private JButton btnNovoNcm;
	private JFormattedTextField txtNcm;

	
	public static ImageIcon logo() {

		try {
			return Resources.getConfig();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Cadastro de Historico";

	}
	
	private void setNCM(Historico ncm) {

		this.ncm = ncm;

		this.btnNovoNcm.setEnabled(this.ncm.getId() > 0);
		
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

					CadastroHistorico frame = new CadastroHistorico();
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

			Historico n = new Historico();
			setNCM(n);
			this.txtNcm.requestFocus();

		});

		this.btnConfirmar.addActionListener(e -> {

			if (!vc(this.txtNcm)) {
				return;
			}

			ncm.setNome(this.txtNcm.getText());
			
			try {

				new HistoricoService(et).merge(ncm);
				
				et.getTransaction().begin();
				et.getTransaction().commit();

				Historico n = new Historico();
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

	public CadastroHistorico() {
		setTitle("Cadastro de Historico");
		setResizable(false);
		setBounds(100, 100, 452, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Cadastro de Historico");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 11, 346, 38);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 47, 285, 2);
		contentPane.add(separator);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dados do Historico", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 60, 424, 74);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNcm = new JLabel("Nome:");
		lblNcm.setBounds(10, 36, 34, 14);
		panel.add(lblNcm);

		txtNcm = new JFormattedTextField();
		txtNcm.setBounds(47, 33, 223, 20);
		panel.add(txtNcm);

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(353, 145, 81, 23);
		contentPane.add(btnConfirmar);

		btnNovoNcm = new JButton("Novo Hist");
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

		JLabel lblPesquisarNcm = new JLabel("Pesquisar Hist:");
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

		
		this.model = new GerenciadorLista<Historico>(Historico.class,this.tblNcm,new HistoricoService(et),null);
		this.model.setFiltro(txtPesquisar);
		this.model.setLblPagina(lblPg);
		this.model.setLblSlider(slPg);
		
		this.model.atualizar();
		
	}
}
