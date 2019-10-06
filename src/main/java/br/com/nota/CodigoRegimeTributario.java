package br.com.nota;

public enum CodigoRegimeTributario {

	SIMPLES_NACIONAL(1),
	SIMPLES_NACIONAL_EXCESSO_RECEITA_BRUTA(2),
	REGIME_NORMAL(3);
	
	private int id;
	
	private CodigoRegimeTributario(int id){
		
		this.id = id;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
