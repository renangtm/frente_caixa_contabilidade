package br.com.agrofauna.utilidades;

public class ValidadorTelefone implements Validador {

	
	public boolean validar(String t) {
		// TODO Auto-generated method stub
		return t.length()==13;
	}

}
