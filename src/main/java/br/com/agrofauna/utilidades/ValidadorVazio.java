package br.com.agrofauna.utilidades;

public class ValidadorVazio implements Validador{

	
	public boolean validar(String t) {
		return !t.equals("");
	}

}
