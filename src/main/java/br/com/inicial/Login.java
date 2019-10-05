package br.com.inicial;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import afgtec.emissao.SAT;
import br.com.afgtec.base.CFG;
import br.com.afgtec.base.ET;
import br.com.afgtec.base.Resources;
import br.com.afgtec.usuario.Usuario;
import br.com.afgtec.usuario.UsuarioService;

public class Login extends Tela {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	private JLabel lblLogin;

	private JLabel lblSenha;

	private JTextField txtLogin;

	private JPasswordField txtSenha;

	private JButton btLogar;

	private JLabel imgc;

	private JLabel lblEstado;

	@SuppressWarnings("unused")
	private int habilitado;

	public Login() throws IOException {

		super("Login", 37, 30, 26, 40, true);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.habilitado = 0;

		this.imgc = new JLabel();
		this.imgc.setIcon(new ImageIcon(Resources.getLogo()));
		this.add(this.imgc);
		this.lr.setDimensoesComponente(this.imgc, 5, 5, 88, 30);

		this.lblLogin = new JLabel("Login: ");
		this.add(this.lblLogin);
		this.lr.setDimensoesComponente(this.lblLogin, 5, 40, 30, 9);

		this.lblSenha = new JLabel("Senha: ");
		this.add(this.lblSenha);
		this.lr.setDimensoesComponente(this.lblSenha, 5, 52, 30, 9);

		this.txtLogin = new JTextField();
		this.add(this.txtLogin);
		this.lr.setDimensoesComponente(this.txtLogin, 25, 40, 65, 9);

		this.txtSenha = new JPasswordField();
		this.add(this.txtSenha);
		this.lr.setDimensoesComponente(this.txtSenha, 25, 52, 65, 9);

		this.btLogar = new JButton("Entrar");
		this.add(this.btLogar);
		this.lr.setDimensoesComponente(this.btLogar, 5, 70, 88, 12);
		this.btLogar.setEnabled(false);
		this.btLogar.setIcon(Resources.getChave());

		this.lblEstado = new JLabel("Status do sistema: Testando conexão...");
		this.add(this.lblEstado);
		this.lr.setDimensoesComponente(this.lblEstado, 5, 90, 90, 10);

		this.atribuirLogar(this);

		this.lblEstado.setText("Status do sistema: conectado.");
		this.lblEstado.setForeground(new Color(0, 100, 0));
		this.btLogar.setEnabled(true);
		this.txtLogin.requestFocus();

		this.btLogar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				logar();

			}

		});

	}

	private void logar() {

		EntityManager et = ET.nova();
		
		final Login este = this;

		final UsuarioService us = new UsuarioService(et);

		this.btLogar.setEnabled(false);

		Runnable rn = new Runnable() {

			@Override
			public void run() {

				try {

					Usuario usuario = us.getUsuario(txtLogin.getText(), new String(txtSenha.getPassword()));

					btLogar.setEnabled(true);

					if (usuario == null) {

						JOptionPane.showMessageDialog(este, "Esse usuario nao existe");

					}

					try {
						
						CFG.moduloSat = new SAT(usuario.getPf().getEmpresa());
						
						new MenuPrincipal(usuario);
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					este.dispose();

				} catch (Exception ex) {

					JOptionPane.showMessageDialog(este, ex.toString());

				}finally {
					
					et.close();
					
				}

			}

		};

		Thread th = new Thread(rn);
		th.start();

	}

	private void atribuirLogar(Component c) {

		c.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {

				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {

					logar();

				}

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		try {

			Container k = (Container) c;

			for (Component cc : k.getComponents())
				this.atribuirLogar(cc);

		} catch (Exception e) {

		}

	}

}
