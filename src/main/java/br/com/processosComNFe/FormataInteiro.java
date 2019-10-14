package br.com.processosComNFe;

public class FormataInteiro {

	private int quantidade;
	
	public FormataInteiro(int quantidade){
		
		this.quantidade = quantidade;
		
	}
	
	public String f(int inteiro){
		
		String it = inteiro+"";
		
		String res = "";
		
		for(int i=quantidade,j=it.length()-1;i>0;i--,j--){
			if(j>=0){
				res=it.charAt(j)+res;
			}else{ 
				res="0"+res;
			}
		}
		
		return res;
		
	}
	
	
}
