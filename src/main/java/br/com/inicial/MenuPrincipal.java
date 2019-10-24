package br.com.inicial;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.com.base.CFG;
import br.com.base.ConfiguracaoExpediente;
import br.com.base.ET;
import br.com.base.Resources;
import br.com.caixa.ExpedienteCaixa;
import br.com.usuario.PresetPermissao;
import br.com.usuario.Usuario;
import br.com.utilidades.LayoutRelativo;

class ImagePanel extends JComponent {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private Image image;

	public ImagePanel(Image image) {
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, this.getWidth() - image.getWidth(null), 0, this);
	}
}

public class MenuPrincipal extends TelaFrame {

	/**
	 * 
	 */

	public static MenuPrincipal menu;
	
	private HashMap<Component, Object> objetos = new HashMap<Component, Object>();
	private HashMap<Component, Integer> leveis = new HashMap<Component, Integer>();
	private final List<JPanel> pnlMiniBanner = new ArrayList<JPanel>();

	public JDesktopPane jdp;

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private MenuPrincipal este;

	private Usuario usuario;

	private int index = 100;

	private JPanel lblTopo;
	private JLabel lblStatus;
	private JLabel lblCaixa;

	public MenuPrincipal(Usuario usuario) throws IOException {

		super("RTC - Empresa: " + usuario.getPf().getEmpresa().getPj().getNome() + ", CNPJ: "
				+ usuario.getPf().getEmpresa().getPj().getCnpj(), 0, 0, 100, 100, false);

		CFG.lookAndFeel = usuario.getPf().getEmpresa().getLogo().getLook().getLookAndFell();

		menu = this;
		
		this.usuario = usuario;

		this.este = this;

		this.setVisible(true);

		this.jdp = new JDesktopPane() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics grphcs) {
				super.paintComponent(grphcs);
				try {
					Image fundo = ImageIO
							.read(new ByteArrayInputStream(usuario.getPf().getEmpresa().getLogo().getFundo()));

					grphcs.drawImage(fundo, getWidth() - fundo.getWidth(null), 0, null);
				} catch (Exception exx) {

				}
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(getWidth(), getHeight());
			}

		};

		this.jdp.setBounds(this.getBounds());
		this.jdp.setLayout(null);
		this.setContentPane(this.jdp);

		if (PresetPermissao.CAIXA.encaixe(this.usuario.getPermissoes()) == 1) {

			Loading.getLoading(() -> {

				FrenteCaixa fc = new FrenteCaixa();
				fc.setVisible(false);
				fc.et = ET.nova();
				fc.init(this.usuario);
				fc.setVisible(true);
				jdp.add(fc);
				fc.centralizar();
				fc.setMaximizable(false);
				fc.setClosable(false);
				fc.setIconifiable(false);

			});

			return;

		}
		
		this.lblTopo = new JPanel();

		this.lblTopo.setOpaque(true);
		this.lblTopo.setBackground(new Color(50, 50, 50, 90));

		this.add(this.lblTopo);

		this.jdp.putClientProperty("JDesktopPane.dragMode", "outline");

		this.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {

				for (Modulo modulo : Modulo.modulos) {

					if (modulo.isVisible()) {

						modulo.requestFocus();

					}

				}

			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		this.setBackground(Color.WHITE);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * try {
		 * 
		 * URL imagem = new URL(Rt.getEmpresa().getImagemFundo()); BufferedImage bi =
		 * ImageIO.read(imagem); JLabel lblFundo = new JLabel(); lblFundo.setIcon(new
		 * ImageIcon(bi));
		 * 
		 * this.setContentPane(lblFundo); } catch (MalformedURLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */

		this.lblTopo.setLayout(null);

		LayoutRelativo lrp = new LayoutRelativo(this.lblTopo, 0, 0, 110, 13, this.lr);

		JLabel logoCliente = new JLabel();

		this.lblTopo.add(logoCliente);

		logoCliente.setOpaque(true);
		logoCliente.setBackground(new Color(255, 255, 255, 100));
		logoCliente.setHorizontalAlignment(JLabel.CENTER);
		lrp.setDimensoesComponente(logoCliente, 1, 10, 25, 80);

		logoCliente.setBounds((int) logoCliente.getBounds().getX(), (int) logoCliente.getBounds().getY() + 15,
				logoCliente.getWidth(), logoCliente.getHeight());

		JLabel lblOperador = new JLabel();
		lblOperador.setOpaque(true);
		lblOperador.setForeground(Color.GRAY);
		lblOperador.setFont(new Font("Arial", Font.BOLD, 13));
		lblOperador.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

		lblOperador.setText(
				usuario.getPf().getNome() + " - " + PresetPermissao.getMaisProximo(usuario.getPermissoes()).toString());

		lrp.setDimensoesComponente(lblOperador, 28, 30, 28, 30);

		lblTopo.add(lblOperador);

		lblStatus = new JLabel();
		lblStatus.setOpaque(true);
		lblStatus.setForeground(new Color(255, 255, 255, 100));
		lblStatus.setFont(new Font("Arial", Font.BOLD, 13));
		lblStatus.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		lblStatus.setText("SAT: CONSULTANDO...");

		lblTopo.add(lblStatus);

		lrp.setDimensoesComponente(lblStatus, 28, 61, 28, 30);

		try {

			logoCliente.setIcon(new ImageIcon(usuario.getPf().getEmpresa().getLogo().getArquivo()));

		} catch (Exception ex) {

			logoCliente.setIcon(new ImageIcon(Resources.getLogo()));

		}

		if (PresetPermissao.getMaisProximo(usuario.getPermissoes()).equals(PresetPermissao.GERENTE)) {

			lblCaixa = new JLabel();
			lblCaixa.setOpaque(true);
			lblCaixa.setForeground(new Color(50, 150, 50));
			lblCaixa.setFont(new Font("Arial", Font.BOLD, 13));
			lblCaixa.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
			lblCaixa.setText("CAIXA: ");

			SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {

				@Override
				protected Void doInBackground() throws Exception {

					while (true) {

						try {

							EntityManager et = ET.nova();

							ExpedienteCaixa exc = et.merge(ConfiguracaoExpediente.getExpedienteCaixa(usuario));
							lblCaixa.setText("CAIXA: R$ " + exc.getCaixa().getSaldoAtual());

							et.close();

						} catch (RuntimeException e1) {

							e1.printStackTrace();

							lblCaixa.setText("SEM CAIXA ABERTO, FAVOR CONFIGURAR");

						}

						Thread.sleep(20000);

					}

				}

			};
			sw.execute();

			lblTopo.add(lblCaixa);

			lrp.setDimensoesComponente(lblCaixa, 28, 91, 28, 30);

		}

		final TelaFrame este = this;

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

				List<Component> cc = new ArrayList<Component>();
				for (Component c : leveis.keySet()) {

					if (leveis.get(c) > 0) {

						cc.add(c);
						c.setVisible(false);
						este.remove(c);

					}

				}

				for (Component c : cc) {
					leveis.remove(c);
					objetos.remove(c);
				}

				for (Modulo modulo : Modulo.modulos) {

					if (modulo.isVisible()) {

						modulo.requestFocus();

					}

				}

				esconde();

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

		List<Object> modulos = DisposicaoModulos.getDisposicao(usuario);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		SwingWorker<Void, Void> w = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {

				double perc = 6;

				if ((getHeight() * (perc / 100)) % 1 > 0) {

					perc = (Math.ceil(getHeight() * (perc / 100)) / getHeight()) * 100;

				}

				if (!CFG.moduloSat.isOperavel()) {

					lblStatus.setText("SAT: NAO ESTA OPERANDO");
					lblStatus.setForeground(new Color(150, 50, 50));

				} else {

					while (true) {

						try {

							CFG.moduloSat.iniciar();

							lblStatus.setText("SAT: EM OPERACAO");
							lblStatus.setForeground(Color.CYAN);

							break;

						} catch (Exception ex) {

							lblStatus.setText("SAT: FORA DE OPERACAO");
							lblStatus.setForeground(Color.ORANGE);

						}

					}

				}

				formarMenu(modulos, 15, 0, perc);

				return null;

			}

		};
		w.execute();

	}

	private void esconde() {

		for (JPanel pnl : this.pnlMiniBanner) {

			pnl.setVisible(true);

		}

		for (Component component : leveis.keySet()) {

			Rectangle rect = component.getBounds();

			for (JPanel pnl : this.pnlMiniBanner) {

				if (rect.intersects(pnl.getBounds())) {

					pnl.setVisible(false);

				}

			}

		}

	}

	private void formarMenu(List<Object> o, double y_base, int level, double h) {

		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (InstantiationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		if (o.size() == 0)
			return;

		int largura = 13;
		int largura_img = 4;
		int espacamento = 0;

		Color submenu_color = new Color(50, 50, 50);

		double comprimento = h;

		double initial_y_base = y_base;

		while (y_base + comprimento * o.size() > 100)
			y_base -= comprimento + espacamento;

		final TelaFrame este = this;

		int i = 0;
		for (Object item : o) {

			final double z = y_base + (comprimento + espacamento) * i;

			final JPanel lbl = new JPanel();
			lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY, 0, true));
			getContentPane().add(lbl);

			if (level == 0 || z == initial_y_base) {

				this.lr.setDimensoesComponente(lbl, 1 + (largura + largura_img) * level, z, largura + largura_img,
						comprimento);

			} else {

				this.lr.setDimensoesComponente(lbl, 3 + (largura + largura_img) * level, z, largura + largura_img - 2,
						comprimento);

			}

			lbl.setLayout(null);

			JComponent lblK2 = new JButton();
			lblK2.setBorder(null);
			lblK2.setOpaque(true);
			lblK2.setForeground(new Color(255, 255, 255));
			boolean bt = true;

			try {
				@SuppressWarnings("unused")
				Object[] sub_menu = (Object[]) item;
				lblK2 = new JLabel();
				lblK2.setOpaque(true);
				bt = false;
			} catch (Exception ex) {

			}

			final JComponent lbl2 = lblK2;

			lbl2.setForeground(new Color(255, 255, 255));
			lbl2.setBackground(new Color(20 * (level + 4), 20 * (level + 4), 20 * (level + 4)));

			try {
				((JLabel) lbl2).setHorizontalAlignment(JLabel.LEFT);
				((JLabel) lbl2).setVerticalAlignment(JLabel.CENTER);
			} catch (Exception ex) {
				((JButton) lbl2).setHorizontalAlignment(JLabel.LEFT);
				((JButton) lbl2).setVerticalAlignment(JLabel.CENTER);
			}

			if (level == 0 || z == initial_y_base) {

				if (!bt) {

					this.lr.setDimensoesComponente(lbl2, largura_img, 0, largura - 1, comprimento);

					if (level > 0) {

						this.lr.setDimensoesComponente(lbl2, largura_img + 2, 0, largura - 3, comprimento);

					}

				} else {

					this.lr.setDimensoesComponente(lbl2, largura_img, 0, largura, comprimento);

					if (level > 0) {

						this.lr.setDimensoesComponente(lbl2, largura_img + 2, 0, largura - 2, comprimento);

					}

				}

			} else {

				if (!bt) {

					this.lr.setDimensoesComponente(lbl2, largura_img, 0, largura - 3, comprimento);

				} else {

					this.lr.setDimensoesComponente(lbl2, largura_img, 0, largura - 2, comprimento);

				}

			}

			lbl2.setFont(new Font("Arial", Font.BOLD, 13));
			lbl.add(lbl2);

			final JLabel lbl3 = new JLabel();
			lbl3.setForeground(new Color(255, 255, 255));
			lbl3.setBackground(new Color(20 * (level + 5), 20 * (level + 5), 20 * (level + 5)));
			lbl3.setOpaque(true);
			lbl3.setHorizontalAlignment(JLabel.CENTER);
			lbl3.setVerticalAlignment(JLabel.CENTER);
			// lbl3.setBorder(BorderFactory.createLineBorder(new
			// Color(20*(level+4),20*(level+4),20*(level+4)), 5));

			lbl.add(lbl3);
			this.lr.setDimensoesComponente(lbl3, 0, 0, largura_img, comprimento);

			if (level > 0 && z == initial_y_base) {
				this.lr.setDimensoesComponente(lbl3, 0, 0, largura_img + 2, comprimento);
			}

			// getContentPane().add(lbl);

			leveis.put(lbl, level);
			objetos.put(lbl, item);

			try {

				@SuppressWarnings("unchecked")
				Class<? extends Modulo> classe = (Class<? extends Modulo>) item;
				objetos.put(lbl, classe);

				Method method = classe.getMethod("nome");
				String ob = method.invoke(null).toString();
				try {
					((JLabel) lbl2).setText("  " + ob);
				} catch (Exception ex) {
					((JButton) lbl2).setText("  " + ob);
				}

				lbl.setBackground(lbl2.getBackground());

				method = classe.getMethod("logo");
				ImageIcon img = (ImageIcon) method.invoke(null);
				img.setImage(img.getImage().getScaledInstance(30, 30, 100));
				lbl3.setIcon(img);

			} catch (Exception e) {

				Object[] sub_menu = (Object[]) item;
				lbl.setBackground(submenu_color);

				((JLabel) lbl2).setText("  " + sub_menu[0].toString());

				try {

					ImageIcon img = (ImageIcon) Resources.getArvore();
					img.setImage(img.getImage().getScaledInstance(30, 30, 100));

					lbl3.setIcon(img);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			lbl.requestFocus();
			JComponent actr = lbl;

			if (bt) {
				((JButton) lbl2).addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {

						Loading.getLoading(new Runnable() {

							@Override
							public void run() {
								try {
									@SuppressWarnings("unchecked")

									Class<? extends Modulo> classe = (Class<? extends Modulo>) objetos.get(lbl);
									Med m = new Med("Abrir " + classe.getName());
									Modulo mod = classe.newInstance();
									m.c();
									m.c();
									List<Component> cc = new ArrayList<Component>();
									for (Component c : leveis.keySet()) {

										if (leveis.get(c) > 0) {

											cc.add(c);
											c.setVisible(false);
											este.remove(c);

										}

									}

									for (Component c : cc) {
										leveis.remove(c);
										objetos.remove(c);
									}

									mod.init(usuario);

									mod.setVisible(true);

									JDesktopPane frm = ((JDesktopPane) este.getContentPane());

									frm.add(mod, ++index);

									mod.centralizar();

									mod.requestFocus();

									mod.setSelected(true);

								} catch (Exception ee) {
									ee.printStackTrace();
								}
							}

						});

						esconde();

					}

				});
				actr = lbl2;
			}
			actr.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {

				}

				@SuppressWarnings("unchecked")
				@Override
				public void mouseEntered(MouseEvent arg0) {

					List<Component> cc = new ArrayList<Component>();
					for (Component c : leveis.keySet()) {

						if (leveis.get(c) > leveis.get(lbl)) {

							cc.add(c);
							c.setVisible(false);
							este.remove(c);

						}

					}

					for (Component c : cc) {
						leveis.remove(c);
						objetos.remove(c);
					}

					try {

						Object[] ok = (Object[]) objetos.get(lbl);

						lbl2.setBackground(new Color(20 * (level + 3), 20 * (level + 3), 20 * (level + 3)));
						lbl3.setBackground(new Color(20 * (level + 4), 20 * (level + 4), 20 * (level + 4)));

						formarMenu((List<Object>) ok[1], z, leveis.get(lbl) + 1, h);

					} catch (Exception e) {

						lbl2.setBackground(new Color(20 * (level + 3), 20 * (level + 3), 20 * (level + 3)));
						lbl3.setBackground(new Color(20 * (level + 4), 20 * (level + 4), 20 * (level + 4)));
						lbl.setBackground(lbl2.getBackground());

					}

					esconde();

				}

				@Override
				public void mouseExited(MouseEvent arg0) {

					try {

						@SuppressWarnings("unused")
						Object[] ok = (Object[]) objetos.get(lbl);

						lbl2.setBackground(new Color(20 * (level + 4), 20 * (level + 4), 20 * (level + 4)));
						lbl3.setBackground(new Color(20 * (level + 5), 20 * (level + 5), 20 * (level + 5)));

					} catch (Exception e) {

						lbl2.setBackground(new Color(20 * (level + 4), 20 * (level + 4), 20 * (level + 4)));
						lbl3.setBackground(new Color(20 * (level + 5), 20 * (level + 5), 20 * (level + 5)));
						lbl.setBackground(lbl2.getBackground());

					}

				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

			});

			i++;
		}

		esconde();

		try {
			UIManager.setLookAndFeel(CFG.lookAndFeel);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
