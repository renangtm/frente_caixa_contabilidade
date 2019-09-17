package br.com.afgtec.notas;

public enum TipoNota {

	NORMAL(0),
	COMPLEMENTAR(1),
	DEVOLUCAO(2),
	AJUSTE(3);
	
	private int id;
	
	private TipoNota(int id){
		
		this.id = id;
		
	}
	
	public int getId(){
		
		return this.id;
		
	}
	
}
