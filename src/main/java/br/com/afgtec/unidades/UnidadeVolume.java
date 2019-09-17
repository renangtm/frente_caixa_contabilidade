package br.com.afgtec.unidades;

public enum UnidadeVolume {
	
	ML(1,1),
	LT(2,1000),
	CM3(3,1);
	
	private int id;
	private double valor;

	private UnidadeVolume(int id,double valor){
		
		this.id = id;
		this.valor = valor;
		
	}
	
	public int getId(){
		
		return this.id;
		
	}
	
	public double para(UnidadeVolume up,double valor){
		
		return valor*(this.valor/up.valor);
		
	}
	
}
