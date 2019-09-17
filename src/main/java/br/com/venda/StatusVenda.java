package br.com.venda;

public enum StatusVenda {
	
	EM_EXECUCAO(0,false,false),
	FINALIZANDO_PAGAMENTO(1,false,true),
	FECHADA(2,true,true),
	CANCELADA(3,false,false);
	
	private int id;
	private boolean quantidade;
	private boolean disponivel;
	
	private StatusVenda(int id,boolean quantidade,boolean disponivel) {
		
		this.id = id;
		this.quantidade = quantidade;
		this.disponivel = disponivel;
		
	}
	
	
	
	
	public boolean isQuantidade() {
		return quantidade;
	}


	public boolean isDisponivel() {
		return disponivel;
	}


	public int getId() {
		
		return this.id;
		
	}

}
