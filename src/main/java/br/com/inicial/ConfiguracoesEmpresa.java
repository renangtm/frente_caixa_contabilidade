package br.com.inicial;

import java.awt.EventQueue;

import br.com.base.ET;
import br.com.base.Resources;
import br.com.empresa.Empresa;
import br.com.empresa.Logo;
import br.com.empresa.ParametrosEmissao;
import br.com.usuario.Usuario;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
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
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class ConfiguracoesEmpresa extends Modulo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNumeroSAT;
	private JTextField txtCodigoAtivacaoSAT;
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

	private Logo logo;
	private JButton btnConfirmar;

	@Override
	public void init(Usuario u) {

		this.operador = et.merge(u);
		this.empresa = this.operador.getPf().getEmpresa();
		et.detach(operador);
		this.empresa = et.merge(empresa);

		this.txtCodigoAtivacaoSAT.setText(this.empresa.getParametrosEmissao().getSenha_sat());
		this.txtNumeroSAT.setText(this.empresa.getParametrosEmissao().getNumeroSat());

		logo = new Logo();

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

		this.btnConfirmar.addActionListener(e -> {

			if (
					!vc(this.txtCodigoAtivacaoSAT) || 
					!vc(this.txtNumeroSAT)) {

				return;

			}

			ParametrosEmissao pa = new ParametrosEmissao();

			if (this.empresa.getParametrosEmissao() != null) {

				pa = this.empresa.getParametrosEmissao();

			}
			
			pa.setSenha_sat(this.txtCodigoAtivacaoSAT.getText());
			pa.setNumeroSat(this.txtNumeroSAT.getText());

			pa.setEmpresa(this.empresa);

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

		this.setBounds(0, 0, 450, 265);

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
		txtNumeroSAT.setBounds(110, 167, 186, 20);
		getContentPane().add(txtNumeroSAT);
		txtNumeroSAT.setColumns(10);

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(330, 191, 104, 23);
		getContentPane().add(btnConfirmar);

		JLabel lbl = new JLabel("Cod. Ativacao SAT:");
		lbl.setBounds(10, 195, 112, 14);
		getContentPane().add(lbl);

		txtCodigoAtivacaoSAT = new JTextField();
		txtCodigoAtivacaoSAT.setColumns(10);
		txtCodigoAtivacaoSAT.setBounds(120, 192, 176, 20);
		getContentPane().add(txtCodigoAtivacaoSAT);

	}
}
