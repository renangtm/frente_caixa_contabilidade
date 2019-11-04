package br.com.inicial;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;

import br.com.base.CFG;
import br.com.base.ET;
import br.com.base.Resources;
import br.com.capturaXML.RepresentadorXML;
import br.com.capturaXML.XML;
import br.com.capturaXML.XMLService;
import br.com.empresa.Empresa;
import br.com.usuario.Usuario;
import br.com.utilidades.ListModelGenerica;

import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.io.IOException;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AprovacaoXml extends Modulo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable tblNcm;

	public static ImageIcon logo() {

		try {
			return Resources.getConfig();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Aprovacao de XML";

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

					AprovacaoXml frame = new AprovacaoXml();
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

	private JButton btManifestar;

	private ListModelGenerica<XML> model;
	private JButton btnOperacaoNaoRealizada;
	private JButton btnDesconhecimentoDaOperacao;

	@Override
	public void init(Usuario u) {

		this.operador = et.merge(u);
		this.empresa = et.merge(CFG.empresa);

		this.btManifestar.setEnabled(false);
		this.btnDesconhecimentoDaOperacao.setEnabled(false);
		this.btnOperacaoNaoRealizada.setEnabled(false);
		
		this.tblNcm.getSelectionModel().addListSelectionListener(a->{
			
			if(!a.getValueIsAdjusting()) {
				
				if(this.tblNcm.getSelectedRow() >= 0) {
					
					this.btManifestar.setEnabled(true);
					this.btnDesconhecimentoDaOperacao.setEnabled(true);
					this.btnOperacaoNaoRealizada.setEnabled(true);
					
				}
				
			}
			
		});
		
		this.btManifestar.addActionListener(a->{
			
			alerta("Erro esse documento ja foi manifestado");
			
			XML xml = this.model.getListaBase().get(this.tblNcm.getSelectedRow());
			
			xml.setVisto(true);
			
			new XMLService(et).merge(xml);
			
			et.getTransaction().begin();
			et.getTransaction().commit();
			
			this.model.remover(this.model.getListaBase().indexOf(xml));
			
		});
		
		this.btnOperacaoNaoRealizada.addActionListener(a->{
			
			alerta("Erro esse documento ja foi manifestado");
			
			XML xml = this.model.getListaBase().get(this.tblNcm.getSelectedRow());
			
			xml.setVisto(true);
			
			new XMLService(et).merge(xml);
			
			et.getTransaction().begin();
			et.getTransaction().commit();
			
			this.model.remover(this.model.getListaBase().indexOf(xml));
			
		});
		
		this.btnDesconhecimentoDaOperacao.addActionListener(a->{
			
			alerta("Erro esse documento ja foi manifestado");
			
			XML xml = this.model.getListaBase().get(this.tblNcm.getSelectedRow());
			
			xml.setVisto(true);
			
			new XMLService(et).merge(xml);
			
			et.getTransaction().begin();
			et.getTransaction().commit();
			
			this.model.remover(this.model.getListaBase().indexOf(xml));
			
		});
		
		List<XML> xmls = new XMLService(et).getXMLsNaoVistadosUsuario(this.empresa);

		for (XML xml : xmls) {

			if (xml.getChaveDocumentoReferenciado() == null)
				continue;

			for (XML xml2 : xmls) {

				if (xml2.getChave() == null)
					continue;

				if (xml2.getChave().replaceAll("&asp", "")
						.equals(xml.getChaveDocumentoReferenciado().replaceAll("&asp", ""))) {

					xml2.setAssociado(xml);

				}

			}

		}

		this.model = new ListModelGenerica<XML>(xmls, XML.class, RepresentadorXML.class);

		this.tblNcm.setModel(this.model);
		
	}

	/**
	 * Create the frame.
	 */

	public AprovacaoXml() {
		setTitle("Aprovacao de XML");
		setResizable(false);
		setBounds(100, 100, 661, 422);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Aprovacao de XML");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 11, 346, 33);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 47, 635, 2);
		contentPane.add(separator);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 635, 288);
		contentPane.add(scrollPane);

		tblNcm = new JTable();
		scrollPane.setViewportView(tblNcm);

		btManifestar = new JButton("Confirmacao da Operacao");
		btManifestar.setBounds(468, 359, 177, 23);
		contentPane.add(btManifestar);
		
		btnOperacaoNaoRealizada = new JButton("Operacao nao Realizada");
		btnOperacaoNaoRealizada.setBounds(281, 359, 177, 23);
		contentPane.add(btnOperacaoNaoRealizada);
		
		btnDesconhecimentoDaOperacao = new JButton("Desconhecimento da Operacao");
		btnDesconhecimentoDaOperacao.setBounds(47, 359, 224, 23);
		contentPane.add(btnDesconhecimentoDaOperacao);

	}
}
