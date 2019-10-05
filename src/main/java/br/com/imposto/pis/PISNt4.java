package br.com.imposto.pis;

import javax.persistence.Entity;

@Entity
public class PISNt4 extends Pis{
	
	public PISNt4(){
		
		this.setCst("04");
		
	}
	
	
	@Override
	public Object clone(){
		
		PISNt4 p = new PISNt4();
		
		return p;
		
	}
	
}
