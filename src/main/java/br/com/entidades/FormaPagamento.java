package br.com.entidades;

public interface FormaPagamento {
	
	public String getNome();
	
	public void efetuarPagamento(double valor,Visor visor,AoFinalizar aoFinalizar);
	
}
