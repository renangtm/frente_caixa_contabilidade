package br.com.processosComNFe;

public class FormataNumero {
	
	private int casas;
	
	public FormataNumero(int casasDecimais){
		
		this.casas = casasDecimais;
		
	}
	
	public String f(double valor){
		
		String s = valor+"";
		
		String[] ss = s.split("\\.");
		
		String p0 = ss[0];
		
		String p1 = "";
		
		if(ss.length>1)p1=ss[1];
		
		if(this.casas==0)return p0;
		
		
		p0+=".";
		
		for(int i=0;i<this.casas;i++)p0+=(i<p1.length())?(p1.charAt(i)+""):"0";
		
		return p0;
		
	}
	

}
