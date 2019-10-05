package br.com.utilidades;

public class ValidadorCpfCnpj implements Validador{

	
	public boolean validar(String t) {
		
		try{
			Long.parseLong(U.rm(t));
		}catch(Exception e){
			return false;
		}
		
		return true;
	}
	
	

}
