package br.com.imposto.pis;

import javax.persistence.Entity;

@Entity
public class PISNt6 extends Pis{
	
	public PISNt6(){
		
		this.setCst("06");
		
	}
	
	
	@Override
	public Object clone(){
		
		PISNt6 p = new PISNt6();
		
		return p;
		
	}
	
}
