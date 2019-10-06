package br.com.nota;

public enum FormaPagamentoNota {
	
	DINHEIRO(1),
	CHEQUE(2),
	CARTAO_CREDITO(3),
	CARTAO_DEBITO(4),
	CREDITO_LOJA(5),
	VALE_ALIMENTACAO(6),
	VALE_REFEICAO(7),
	VALE_PRESENTE(8),
	VALE_COMBUSTIVEL(9),
	OUTROS(99);
	
	private int id;

	private FormaPagamentoNota(int id){
		
		this.id = id;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
