package br.com.conversores;

public class ConversorBooleano implements Conversor<Boolean>{

	@Override
	public boolean podeConverter(@SuppressWarnings("rawtypes") Class classe) {
		// TODO Auto-generated method stub
		return classe.equals(Boolean.class) || classe.equals(boolean.class);
	}

	@Override
	public String paraString(Boolean objeto) {
		
		if((Boolean)objeto) {
			
			return "S";
			
		}
		
		return "N";
		
	}

	@Override
	public Boolean paraObjeto(String string) throws Exception {
	
		if(string.equals("S")) {
			
			return true;
			
		}
		
		return false;
		
	}

}
