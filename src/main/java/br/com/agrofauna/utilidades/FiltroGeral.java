package br.com.agrofauna.utilidades;

public class FiltroGeral implements Filtro{

	private String termo;
	
	public FiltroGeral(String termo){
		
		this.termo = termo;
		
	}
	
	
	public boolean passa(Object o) {
		
		return o.toString().toUpperCase().indexOf(this.termo.toUpperCase())>=0;
		
	}

	
	
}
