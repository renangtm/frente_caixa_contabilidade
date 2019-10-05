package br.com.utilidades;

public class ValidadorVazio implements Validador{

	
	public boolean validar(String t) {
		return !t.equals("");
	}

}
