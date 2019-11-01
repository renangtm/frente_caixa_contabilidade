package br.com.produto;

import br.com.utilidades.Campo;

public class RepTokenVale {
	
	@Campo(nome="Codigo")
	private String token;
	
	@Campo(nome="Valor")
	private double valor;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}

	
	
}
