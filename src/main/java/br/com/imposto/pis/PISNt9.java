package br.com.imposto.pis;

import javax.persistence.Entity;

@Entity
public class PISNt9 extends Pis{
	
	public PISNt9(){
		
		this.setCst("09");
		
	}
	
	
	@Override
	public Object clone(){
		
		PISNt9 p = new PISNt9();
		
		return p;
		
	}
	
}
