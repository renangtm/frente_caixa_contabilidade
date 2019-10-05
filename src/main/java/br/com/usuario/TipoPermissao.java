package br.com.usuario;

public enum TipoPermissao {
	
	CAIXA(1),
	NOTAS(2);
	
	private int id;
	
	private TipoPermissao(int id) {
		
		this.id = id;
		
	}
	
	public int getId() {
		
		return this.id;
		
	}
	
}
