package br.com.arquivos;

import java.awt.Desktop;
import java.net.URI;

import javax.swing.JOptionPane;

public class AbrirArquivo {

	private AbrirArquivo(){
		
	}
	
	
	public static void abrirArquivo(String link){
		
		Desktop dks = Desktop.getDesktop();
		try {
			dks.browse(new URI(link));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Arquivo no link: "+link,"Não foi possível abrir o arquivo no seu navegador",JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
}
