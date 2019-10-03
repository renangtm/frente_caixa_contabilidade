package br.com.inicial;

import java.awt.Color;
import java.awt.Component;
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
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.com.afgtec.base.CFG;
import br.com.afgtec.usuario.Usuario;
import br.com.entidades.Icones;
import br.com.entidades.Loading;
import br.com.entidades.Med;

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
        g.drawImage(image, this.getWidth()-image.getWidth(null), 0, this);
    }
}

public class MenuPrincipal extends Tela {

	/**
	 * 
	 */

	private HashMap<Component, Object> objetos = new HashMap<Component, Object>();
	private HashMap<Component, Integer> leveis = new HashMap<Component, Integer>();
	private final List<JPanel> pnlMiniBanner = new ArrayList<JPanel>();

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	
	public MenuPrincipal(Usuario usuario) throws IOException{

		super("RTC - Empresa: " + usuario.getPf().getEmpresa().getPj().getNome() + ", CNPJ: ", 0, 0, 100, 100,
				false);
		
		this.usuario = usuario;
		
		this.setVisible(true);
		
		this.setContentPane(new ImagePanel(Icones.getFundo()));
		
		this.getContentPane().setBackground(Color.LIGHT_GRAY);
		
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
		 * URL imagem = new URL(Rt.getEmpresa().getImagemFundo()); BufferedImage
		 * bi = ImageIO.read(imagem); JLabel lblFundo = new JLabel();
		 * lblFundo.setIcon(new ImageIcon(bi));
		 * 
		 * this.setContentPane(lblFundo); } catch (MalformedURLException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		JLabel logo = new JLabel();
		logo.setIcon(new ImageIcon(Icones.getLogo()));
		getContentPane().add(logo);
		this.lr.setDimensoesComponente(logo, 70, 85, 30, 15);

		JLabel logoCliente = new JLabel();
		getContentPane().add(logoCliente);
		this.lr.setDimensoesComponente(logoCliente, 1, 1, 30, 15);
		
		try {
		
			logoCliente.setIcon(new ImageIcon(usuario.getPf().getEmpresa().getLogo().getArquivo()));
		
		}catch(Exception ex) {
			
			logoCliente.setIcon(new ImageIcon(Icones.getLogo()));
			
		}
		
		final Tela este = this;

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

		formarMenu(modulos, 15, 0, Math.min(6, 70 / modulos.size()));

	
		if(!CFG.moduloSat.isOperavel()){
			
			JOptionPane.showMessageDialog(null, "O Sat nao esta operando","Erro",JOptionPane.ERROR_MESSAGE);
			
		}else{
			
			while(true){
				
				try{
					
					CFG.moduloSat.iniciar();
					
					break;
					
				}catch(Exception ex){
					
					JOptionPane.showMessageDialog(null, "O Sistema nao esta conseguindo inicializar o SAT","Erro",JOptionPane.ERROR_MESSAGE);
					
					try {
						
						Thread.sleep(200000);
						
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
				}
				
			}
			
		}
		
		
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

	private void formarMenu(List<Object> o, int y_base, int level, int h) {

		if (o.size() == 0)
			return;

		
		int largura = 13;
		int largura_img = 4;
		int espacamento = 1;

		Color submenu_color = new Color(100, 160, 100);

		int comprimento = h;

		int initial_y_base = y_base;

		while (y_base + comprimento * o.size() > 100)
			y_base -= comprimento + espacamento;

		final Tela este = this;

		int i = 0;
		for (Object item : o) {

			final int z = y_base + (comprimento + espacamento) * i;

			final JPanel lbl = new JPanel();
			lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
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
				((JLabel) lbl2).setHorizontalAlignment(JLabel.CENTER);
				((JLabel) lbl2).setVerticalAlignment(JLabel.CENTER);
			} catch (Exception ex) {
				((JButton) lbl2).setHorizontalAlignment(JLabel.CENTER);
				((JButton) lbl2).setVerticalAlignment(JLabel.CENTER);
				lbl2.setForeground(new Color(70, 70, 70));
			}

			lbl.add(lbl2);

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

			getContentPane().add(lbl);

			leveis.put(lbl, level);
			objetos.put(lbl, item);

			try {

				@SuppressWarnings("unchecked")
				Class<? extends Modulo> classe = (Class<? extends Modulo>) item;
				objetos.put(lbl, classe);

				Method method = classe.getMethod("nome");
				String ob = method.invoke(null).toString();
				try {
					((JLabel) lbl2).setText(ob);
				} catch (Exception ex) {
					((JButton) lbl2).setText(ob);
				}

				lbl.setBackground(lbl2.getBackground());

				method = classe.getMethod("logo");
				ImageIcon img = (ImageIcon) method.invoke(null);
				img.setImage(img.getImage().getScaledInstance(30, 30, 100));
				lbl3.setIcon(img);

			} catch (Exception e) {

				Object[] sub_menu = (Object[]) item;
				lbl.setBackground(submenu_color);

				((JLabel) lbl2).setText(sub_menu[0].toString());

				try {

					ImageIcon img = (ImageIcon) Icones.getArvore();
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
									
									mod.centralizar();
									

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

	}

}
