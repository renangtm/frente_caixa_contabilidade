package br.com.inicial;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import br.com.afgtec.usuario.Usuario;
import br.com.entidades.Icones;

public class ImportarXML extends Modulo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImportarXML() {
		
		super("Importando",40,40,20,20,false);
		
	}
	
	public void init(Usuario operador) {
		
		new JFileChooser().showOpenDialog(this);
		
		erro("Precisa ser um XML de nota emitida para a empresa");
		
		Runnable rn = new Runnable() {

			@Override
			public void run() {
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				setVisible(false);
				dispose();
				
			}
			
			
			
		};
		
		new Thread(rn).start();
		
		
	}
	
	public static ImageIcon logo() {

		try {
			return Icones.getPapel();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "ImportarXml";

	}
	
}
