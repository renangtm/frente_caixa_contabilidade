package br.com.imposto.pis;

import javax.persistence.Entity;

@Entity
public class PISNt8 extends Pis{
	
	public PISNt8(){
		
		this.setCst("08");
		
	}
	
	
	@Override
	public Object clone(){
		
		PISNt8 p = new PISNt8();
		
		return p;
		
	}
	
}
