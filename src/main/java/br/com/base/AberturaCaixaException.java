package br.com.base;

public class AberturaCaixaException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AberturaCaixaException() {
		
		
		super("Nao foi possivel abrir o caixa, verifique as configuracoes do caixa");
		
	}
	
}
