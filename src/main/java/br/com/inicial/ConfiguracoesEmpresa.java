package br.com.inicial;

import java.awt.EventQueue;

import br.com.base.CFG;
import br.com.base.ET;
import br.com.base.Resources;
import br.com.config.CertificadoPFX;
import br.com.conversores.ConversorDate;
import br.com.empresa.Empresa;
import br.com.empresa.Look;
import br.com.empresa.Visual;
import br.com.empresa.ParametrosEmissao;
import br.com.usuario.Usuario;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JComboBox;

public class ConfiguracoesEmpresa extends Modulo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNumeroSAT;
	private JTextField txtCodigoAtivacaoSAT;
	
	@SuppressWarnings("unused")
	private Usuario operador;
	
	private Empresa empresa;
	
	private JLabel lblLbllogo;

	public static ImageIcon logo() {

		try {
			return Resources.getConfig();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Configuracoes da Empresa";

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

					ConfiguracoesEmpresa frame = new ConfiguracoesEmpresa();
					frame.et = et;
					frame.init(u);
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private Visual logo;
	private JButton btnConfirmar;
	private JTextField txtTokenApi;
	private JTextField txtUltimaNFe;
	private JTextField txtIdLote;
	private JTextField txtSenhaCertificado;
	private JButton btCertificado;

	private byte[] certificado;
	private JComboBox<Look> cboEstilo;
	private JButton btImagemFundo;
	private byte[] fundo;

	private void printCertificado() {

		try {

			CertificadoPFX c = new CertificadoPFX(new ByteArrayInputStream(this.certificado),
					this.txtSenhaCertificado.getText());

			String validade = "De: "
					+ new ConversorDate().paraString(new java.sql.Date(c.getPublicKey().getNotBefore().getTime()));
			validade += " Até: "
					+ new ConversorDate().paraString(new java.sql.Date(c.getPublicKey().getNotAfter().getTime()));

			this.btCertificado.setText(validade);

		} catch (Exception ex) {

			if (this.certificado == null) {
				this.btCertificado.setText("Sem certificado");
			} else {
				this.btCertificado.setText("Senha invalida");
			}

		}

	}

	@Override
	public void init(Usuario u) {

		this.operador = et.merge(u);
		this.empresa = CFG.empresa;

		this.txtCodigoAtivacaoSAT.setText(this.empresa.getParametrosEmissao().getSenha_sat());
		this.txtNumeroSAT.setText(this.empresa.getParametrosEmissao().getNumeroSat());
		this.txtTokenApi.setText(this.empresa.getTokenAPIImpostosAproximados());

		this.txtUltimaNFe.setText(this.empresa.getParametrosEmissao().getUltimaNFe() + "");

		if (this.empresa.getParametrosEmissao().getSenhaCertificado() != null)
			this.txtSenhaCertificado.setText(this.empresa.getParametrosEmissao().getSenhaCertificado() + "");
		else
			this.txtSenhaCertificado.setText("");

		this.txtIdLote.setText(this.empresa.getParametrosEmissao().getIdLote() + "");

		this.certificado = this.empresa.getParametrosEmissao().getCertificadoDigital();

		this.printCertificado();

		logo = new Visual();

		if (this.empresa.getLogo() != null) {

			logo = this.empresa.getLogo();

		}

		try {

			BufferedImage img = ImageIO.read(new ByteArrayInputStream(this.empresa.getLogo().getArquivo()));
			this.lblLbllogo.setIcon(new ImageIcon(img));

		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		this.btCertificado.addActionListener(a -> {

			JFileChooser jfc = new JFileChooser();

			jfc.showOpenDialog(this);

			File file = jfc.getSelectedFile();

			if (file == null)
				return;

			try {

				@SuppressWarnings("resource")
				FileInputStream fis = new FileInputStream(file);

				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				int l = 0;
				byte[] buffer = new byte[1024];

				while ((l = fis.read(buffer, 0, buffer.length)) > 0) {

					baos.write(buffer, 0, l);

				}

				this.certificado = baos.toByteArray();

				this.printCertificado();

			} catch (Exception ex) {

				erro("Ocorreu um problema ao ler o arquivo");
				return;

			}

		});

		this.btImagemFundo.addActionListener(a -> {

			JFileChooser jfc = new JFileChooser();

			FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG FILES", "png");
			jfc.setFileFilter(filter);
			
			
			jfc.showOpenDialog(this);

			File file = jfc.getSelectedFile();

			if (file == null)
				return;

			try {

				@SuppressWarnings("resource")
				FileInputStream fis = new FileInputStream(file);

				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				int l = 0;
				byte[] buffer = new byte[1024];

				while ((l = fis.read(buffer, 0, buffer.length)) > 0) {

					baos.write(buffer, 0, l);

				}

				this.fundo = baos.toByteArray();

				this.printCertificado();

			} catch (Exception ex) {

				erro("Ocorreu um problema ao ler o arquivo");
				return;

			}

		});
		
		this.cboEstilo.setModel(new DefaultComboBoxModel<Look>(Look.values()));
		
		this.cboEstilo.setSelectedItem(empresa.getLogo().getLook());
		
		this.txtSenhaCertificado.addCaretListener(c -> {

			this.printCertificado();

		});

		this.btnConfirmar.addActionListener(e -> {

			if (!vc(this.txtCodigoAtivacaoSAT) || !vc(this.txtNumeroSAT) || !vc(this.txtIdLote)
					|| !vc(this.txtSenhaCertificado) || !vc(this.txtUltimaNFe) || !vc(this.txtTokenApi)) {

				return;

			}

			ParametrosEmissao pa = new ParametrosEmissao();

			if (this.empresa.getParametrosEmissao() != null) {

				pa = this.empresa.getParametrosEmissao();

			}

			try {

				int idLote = Integer.parseInt(this.txtIdLote.getText());

				int numeroNFe = Integer.parseInt(this.txtUltimaNFe.getText());

				pa.setIdLote(idLote);

				pa.setUltimaNFe(numeroNFe);

			} catch (Exception ex) {

				erro("Preencha os campos adequadamente");
				return;

			}

			pa.setSenha_sat(this.txtCodigoAtivacaoSAT.getText());
			pa.setNumeroSat(this.txtNumeroSAT.getText());
			pa.setCertificadoDigital(this.certificado);
			pa.setSenhaCertificado(this.txtSenhaCertificado.getText());

			this.empresa.setTokenAPIImpostosAproximados(this.txtTokenApi.getText());

			pa.setEmpresa(this.empresa);

			logo.setFundo(fundo);
			
			logo.setLook((Look)cboEstilo.getSelectedItem());
			
			this.empresa.setParametrosEmissao(et.merge(pa));
			this.empresa.setLogo(et.merge(logo));

			et.getTransaction().begin();
			et.getTransaction().commit();

			info("Operacao efetuada com sucesso");

		});

		final ConfiguracoesEmpresa este = this;
		this.lblLbllogo.addMouseListener(new MouseListener() {

			@SuppressWarnings("resource")
			@Override
			public void mouseClicked(MouseEvent e) {

				// TODO Auto-generated method stub
				JFileChooser jfc = new JFileChooser();
				jfc.showOpenDialog(este);

				try {

					File fl = jfc.getSelectedFile();

					if (fl == null)
						return;

					InputStream is = new FileInputStream(fl);

					ByteArrayOutputStream baos = new ByteArrayOutputStream();

					int l = 0;
					byte[] buffer = new byte[1024];

					while ((l = is.read(buffer, 0, buffer.length)) > 0) {
						baos.write(buffer, 0, l);
					}

					byte[] antes = logo.getArquivo();

					logo.setArquivo(baos.toByteArray());

					try {

						BufferedImage img = ImageIO.read(new ByteArrayInputStream(logo.getArquivo()));
						lblLbllogo.setIcon(new ImageIcon(img));

					} catch (IOException exx) {

						logo.setArquivo(antes);
						exx.printStackTrace();

					}

				} catch (Exception ex) {

					ex.printStackTrace();
					erro("Ocorreu um problema ao selecionar o arquivo");
					return;

				}

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

	}

	/**
	 * Create the frame.
	 */

	public ConfiguracoesEmpresa() {
		setResizable(false);
		setTitle("Configuracoes da Empresa");
		setResizable(false);
		getContentPane().setLayout(null);

		this.setBounds(0, 0, 450, 440);

		JLabel lblNewLabel = new JLabel("Configuracoes da Empresa");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(10, 11, 424, 38);
		getContentPane().add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 44, 378, 2);
		getContentPane().add(separator);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Logo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 60, 424, 99);
		getContentPane().add(panel);
		panel.setLayout(null);

		lblLbllogo = new JLabel("");
		lblLbllogo.setBounds(10, 21, 404, 67);
		panel.add(lblLbllogo);

		JLabel lblNewLabel_1 = new JLabel("Numero do SAT:");
		lblNewLabel_1.setBounds(10, 170, 92, 14);
		getContentPane().add(lblNewLabel_1);

		txtNumeroSAT = new JTextField();
		txtNumeroSAT.setBounds(110, 167, 324, 20);
		getContentPane().add(txtNumeroSAT);
		txtNumeroSAT.setColumns(10);

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(330, 377, 104, 23);
		getContentPane().add(btnConfirmar);

		JLabel lbl = new JLabel("Cod. Ativacao SAT:");
		lbl.setBounds(10, 195, 112, 14);
		getContentPane().add(lbl);

		txtCodigoAtivacaoSAT = new JTextField();
		txtCodigoAtivacaoSAT.setColumns(10);
		txtCodigoAtivacaoSAT.setBounds(120, 192, 314, 20);
		getContentPane().add(txtCodigoAtivacaoSAT);

		JLabel lblTokenApiI = new JLabel("Token API Impostos:");
		lblTokenApiI.setBounds(10, 220, 112, 14);
		getContentPane().add(lblTokenApiI);

		txtTokenApi = new JTextField();
		txtTokenApi.setColumns(10);
		txtTokenApi.setBounds(120, 217, 314, 20);
		getContentPane().add(txtTokenApi);

		JLabel lblUtimaNfe = new JLabel("Utima NFe:");
		lblUtimaNfe.setBounds(10, 244, 92, 14);
		getContentPane().add(lblUtimaNfe);

		JLabel lblIdLote = new JLabel("Id Lote:");
		lblIdLote.setBounds(255, 244, 67, 14);
		getContentPane().add(lblIdLote);

		JLabel lblCertificado = new JLabel("Certificado:");
		lblCertificado.setBounds(10, 269, 77, 14);
		getContentPane().add(lblCertificado);

		JLabel lblSenhaCertificado = new JLabel("Senha Certificado:");
		lblSenhaCertificado.setBounds(10, 296, 92, 14);
		getContentPane().add(lblSenhaCertificado);

		txtUltimaNFe = new JTextField();
		txtUltimaNFe.setColumns(10);
		txtUltimaNFe.setBounds(110, 241, 117, 20);
		getContentPane().add(txtUltimaNFe);

		txtIdLote = new JTextField();
		txtIdLote.setColumns(10);
		txtIdLote.setBounds(322, 241, 112, 20);
		getContentPane().add(txtIdLote);

		btCertificado = new JButton("Sem certificado");
		btCertificado.setBounds(77, 265, 295, 23);
		getContentPane().add(btCertificado);

		txtSenhaCertificado = new JTextField();
		txtSenhaCertificado.setColumns(10);
		txtSenhaCertificado.setBounds(102, 293, 220, 20);
		getContentPane().add(txtSenhaCertificado);
		
		JLabel lblImgf = new JLabel("Imagem de Fundo");
		lblImgf.setBounds(10, 325, 92, 14);
		getContentPane().add(lblImgf);
		
		btImagemFundo = new JButton("Colocar nova imagem");
		btImagemFundo.setBounds(127, 321, 245, 23);
		getContentPane().add(btImagemFundo);
		
		JLabel lblEstilo = new JLabel("Estilo:");
		lblEstilo.setBounds(10, 350, 92, 14);
		getContentPane().add(lblEstilo);
		
		cboEstilo = new JComboBox<Look>();
		cboEstilo.setBounds(120, 347, 188, 20);
		getContentPane().add(cboEstilo);

	}
}
