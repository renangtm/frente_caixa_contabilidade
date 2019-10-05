package br.com.utilidades;

public class ValidadorClasseRisco implements Validador {

	
	public boolean validar(String t) {
		return t.length()==3;
	}

}
