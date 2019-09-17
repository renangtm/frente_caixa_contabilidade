package br.com.entidades;

import java.util.stream.Stream;

public interface InterfaceImpressora {

	public void addLinha(String linha);

	public void imprimir();
	
	public void resetar();
	
	default void addLinhas(Stream<String> linhas) {
		
		linhas.forEach(this::addLinha);
		
	}
	
}
