package br.com.impostos.icms;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ICMS60 extends Icms{

	@Column
	private double valorBCIcmsSTRetido;
	
	@Column
	private double valorIcmsSTRetido;

	public ICMS60() {
		super();
		
		this.setCst("60");
		
	}

	public double getValorBCIcmsSTRetido() {
		return valorBCIcmsSTRetido;
	}

	public void setValorBCIcmsSTRetido(double valorBCIcmsSTRetido) {
		this.valorBCIcmsSTRetido = valorBCIcmsSTRetido;
	}

	public double getValorIcmsSTRetido() {
		return valorIcmsSTRetido;
	}

	public void setValorIcmsSTRetido(double valorIcmsSTRetido) {
		this.valorIcmsSTRetido = valorIcmsSTRetido;
	}
	
	//--------------------
	
	@Override
	public Object clone(){
		
		ICMS60 icms = new ICMS60();
		
		icms.setOrigem(this.getOrigem());
		icms.valorBCIcmsSTRetido=this.valorBCIcmsSTRetido;
		icms.valorIcmsSTRetido=this.valorIcmsSTRetido;
		
		return icms;
		
	}
	
}
