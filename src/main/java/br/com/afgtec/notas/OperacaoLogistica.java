package br.com.afgtec.notas;

public enum OperacaoLogistica {

	VENDA_DENTRO_ESTADO(0),
	VENDA_FORA_ESTADO(1),
	DEVOLUCAO(2);

	private int id;
	
	private OperacaoLogistica(int id) {
		
		this.id = id;
		
	}

	public int getId() {
		return id;
	}
	
}
