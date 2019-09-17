package br.com.afgtec.notas;

public enum StatusNota {

	ATIVA(0,true),
	CANCELADA(1,true);
	
	private int id;
	private boolean interfereEstoque;
	
	private StatusNota(int id,boolean ie){
		
		this.id = id;
		this.interfereEstoque = ie;
		
	}
	
	public boolean isInterfereEstoque(){
		
		return this.interfereEstoque;
		
	}
	
	public int getId(){
		
		return this.id;
		
	}
	
}
