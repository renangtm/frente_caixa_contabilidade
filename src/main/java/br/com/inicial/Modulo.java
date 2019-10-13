package br.com.inicial;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.UIManager;

import br.com.base.ET;
import br.com.usuario.Usuario;

interface ComponentIteractor{
	
	void iteract(Component c);
	
}

public class Modulo extends Tela {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static List<Modulo> modulos = new ArrayList<Modulo>();

	protected String fixedTitle = "";
	
	protected EntityManager et;

	protected List<EntityManager> managers = new ArrayList<EntityManager>();
	
	public boolean validarFormulario() {

		return this.validarContainer(this.getContentPane());

	}
	

	public boolean vc(Component comp) {

		try {

			JTextField txt = (JTextField) comp;

			if (!txt.isEnabled()) {
				txt.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
				return true;
			}

			if (!txt.getText().equals("")) {
				if (txt.getText().charAt(0) != ' ') {

					txt.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
					return true;

				}
			}

			txt.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			txt.requestFocus();
			erro("Preencha os campos adequadamente");
			return false;

		} catch (Exception ex) {

		}

		return true;

	}
	
	public void percorrerComponentes(ComponentIteractor ci) {

		for (Component c : this.getContentPane().getComponents()) {
			
			ci.iteract(c);
			
			try {

				Container f = (Container) c;

				this.percorrerComponentes(f,ci);

			} catch (Exception ex) {

			}

		}

	}
	
	public void percorrerComponentes(Container comp,ComponentIteractor ci) {

		for (Component c : comp.getComponents()) {
			
			ci.iteract(c);
			
			try {

				Container f = (Container) c;

				this.percorrerComponentes(f,ci);

			} catch (Exception ex) {

			}

		}

	}

	public boolean validarContainer(Container comp) {

		for (Component c : comp.getComponents()) {

			try {

				if (!this.vc(c)) {

					return false;

				}

			} catch (Exception ex2) {

			}

			try {

				Container f = (Container) c;

				if (!this.validarContainer(f)) {

					return false;

				}

			} catch (Exception ex) {

			}

		}

		return true;

	}

	@Override
	public void setTitle(String str) {

		if (!this.fixedTitle.equals("")) {

			super.setTitle(this.fixedTitle + " - " + str);

		} else {

			super.setTitle(str);

		}

	}

	public Modulo(String titulo, int x, int y, int width, int height, boolean res) {
		super(titulo, x, y, width, height, res);

		this.fixedTitle = titulo;

		modulos.add(this);
		/*
		 * for(Modulo modulo:Modulo.modulos){
		 * 
		 * if(modulo.isVisible()){
		 * 
		 * modulo.requestFocus();
		 * 
		 * }
		 * 
		 * }
		 */

		this.setVisible(true);
		this.et = ET.nova();
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				
				et.close();
				managers.forEach(e->e.close());
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
	}

	public void centralizar() {

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension size = tk.getScreenSize();

		this.setLocation((int) (size.getWidth() / 2 - this.getWidth() / 2),
				(int) (size.getHeight() / 2 - this.getHeight() / 2));

	}

	public void init(Usuario usuario) {

	};

	public Modulo() {
		super();
		this.setVisible(true);
		this.et = ET.nova();
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				
				et.close();
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
	}

	public Modulo(String titulo, int x, int y, int width, int height, boolean res, boolean contrato) {
		super(titulo, x, y, width, height, res);

		this.setVisible(true);
		//this.et = ET.nova();
		
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				
				et.close();
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
	}

	public static ImageIcon logo() {

		return null;

	}

	public static String nome() {

		return "";

	}

}
