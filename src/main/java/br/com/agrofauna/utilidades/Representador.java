package br.com.agrofauna.utilidades;

public abstract class Representador<T> {

	protected T objetoOriginal;
	
	protected Object parametro;
	
	public Representador(T objetoOriginal){
		
		this.objetoOriginal = objetoOriginal;
		
	}
	
	public void setParametro(Object obj){
		
		this.parametro=obj;
		
	}
	
	
	
	public void atualizar(){
		
	}
	
}
