package br.com.impostos.icms;

public enum ModalidadeBCST {
	
	PRECO_TABELADO(0),
	LISTA_NEGATIVA(1),
	LISTA_POSITIVA(2),
	LISTA_NEUTRA(3),
	MARGEM_VALOR_AGREGADO(4),
	PAUTA(5);
	
	private int id;
	
	private ModalidadeBCST(int id) {
		
		this.id = id;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

}
