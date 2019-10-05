package br.com.impostos.icms;

public enum ModalidadeBC {
	
	MARGEM_VALOR_AGREGADO(0),
	PAUTA(1),
	PRECO_TABELADO_MAX(2),
	VALOR_OPERACAO(3);
	
	private int id;
	
	private ModalidadeBC(int id) {
		
		this.id = id;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

}
