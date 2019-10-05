package br.com.imposto.cofins;

import javax.persistence.Entity;

@Entity
public class COFINSAliq1 extends Cofins {

	private double porcentagemBaseCalculo;
	
	private double valorBaseCalculo;
	
	private double aliquotaCofins;
	
	private double valorCofins;
	
	public COFINSAliq1(){
		
		this.setCst("01");
		
	}

	public double getPorcentagemBaseCalculo() {
		return porcentagemBaseCalculo;
	}

	public void setPorcentagemBaseCalculo(double porcentagemBaseCalculo) {
		this.porcentagemBaseCalculo = porcentagemBaseCalculo;
	}

	public double getAliquotaCofins() {
		return aliquotaCofins;
	}

	public void setAliquotaCofins(double aliquotaCofins) {
		this.aliquotaCofins = aliquotaCofins;
	}

	public double getValorBaseCalculo() {
		return valorBaseCalculo;
	}

	@Override
	public double getValor() {
		
		return valorCofins;
		
	}
	
	@Override
	public void calcularSobre(double valor){
		
		this.valorBaseCalculo = valor*(100/100);
		this.valorCofins = this.valorBaseCalculo*(this.aliquotaCofins/100);
		
	}
	
	@Override
	public Object clone(){
		
		COFINSAliq1 c = new COFINSAliq1();
		c.aliquotaCofins=this.aliquotaCofins;
		c.porcentagemBaseCalculo=this.porcentagemBaseCalculo;
		c.valorBaseCalculo=this.valorBaseCalculo;
		c.valorCofins=this.valorCofins;

		return c;
		
	}
	
}
