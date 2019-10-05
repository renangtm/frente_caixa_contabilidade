package br.com.imposto.pis;

import javax.persistence.Entity;

@Entity
public class PISNt7 extends Pis{
	
	public PISNt7(){
		
		this.setCst("07");
		
	}
	
	
	@Override
	public Object clone(){
		
		PISNt7 p = new PISNt7();
		
		return p;
		
	}
	
}
