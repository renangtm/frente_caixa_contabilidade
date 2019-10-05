package br.com.utilidades;

public class Arredondamento {

	private Arredondamento(){
		
	}
	
	private static final int casas  = 2;
	
	public static double arredondar(double numero){
		
		numero*=Math.pow(10, casas);
		
		numero = Math.round(numero);
		
		return numero/Math.pow(10, casas);
		
	}

	
}
