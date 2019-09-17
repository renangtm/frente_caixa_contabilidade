package br.com.afgtec.arquivos;

import java.io.File;

public class ArquivoUtils {

	private ArquivoUtils(){
		
		
	}
	
	public static String getExtensao(File arquivo){
		
		String nome = arquivo.getName();
		
		String[] parts = nome.split("\\.");
		
		if(parts.length==0){
			return "";
		}
		
		return parts[parts.length-1];
		
	}
	
}
