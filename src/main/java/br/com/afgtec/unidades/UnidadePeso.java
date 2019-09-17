package br.com.afgtec.unidades;

public enum UnidadePeso {
	
	GR(1,1),
	KG(2,1000);
	
	private int id;
	private double valor;

	private UnidadePeso(int id,double valor){
		
		this.id = id;
		this.valor = valor;
		
	}
	
	public int getId(){
		
		return this.id;
		
	}
	
	public double para(UnidadePeso up,double valor){
		
		return valor*(this.valor/up.valor);
		
	}
	
}
