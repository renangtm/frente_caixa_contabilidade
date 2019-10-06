package br.com.venda;

import br.com.pessoa.Produto;

public class SemEstoqueException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SemEstoqueException(Produto produto){
		
		super("Sem estoque "+produto.getNome());
		
	}
	
}
