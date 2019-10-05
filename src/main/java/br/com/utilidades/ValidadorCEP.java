package br.com.utilidades;

public class ValidadorCEP implements Validador {

	
	public boolean validar(String t) {
		return t.length()==9;
	}

}
