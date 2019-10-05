package br.com.utilidades;

public class ValidadorEmail implements Validador {

	
	public boolean validar(String t) {
		try{
			return t.split("@").length==2;
		}catch(Exception ex){
			return false;
		}
	}

}
