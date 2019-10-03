package br.com.afgtec.impostos;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PISAliq1 extends Pis{
	
	@Column
	private double alicotaPis;
	
	@Column
	private double porcentagemBaseCalculo;
	
	@Column
	private double valorBaseCalculo;
	
	@Column
	private double valorPis;
	
	public PISAliq1(){
		
		this.setCst("01");
		
	}
	
	
	
	public double getPorcentagemBaseCalculo() {
		return porcentagemBaseCalculo;
	}



	public void setPorcentagemBaseCalculo(double porcentagemBaseCalculo) {
		this.porcentagemBaseCalculo = porcentagemBaseCalculo;
	}



	public double getAlicotaPis() {
		return alicotaPis;
	}

	


	public void setAlicotaPis(double alicotaPis) {
		this.alicotaPis = alicotaPis;
	}



	public double getValorBaseCalculo() {
		return valorBaseCalculo;
	}



	@Override
	public void calcularSobre(double valor){
		
		this.valorBaseCalculo =  valor*(100/100);
		this.valorPis = this.valorBaseCalculo*(this.alicotaPis/100);
		
	}
	
	@Override
	public double getValor(){
		
		return this.valorPis;
		
	}
	
	@Override
	public Object clone(){
		
		PISAliq1 p = new PISAliq1();
		p.alicotaPis=this.alicotaPis;
		p.porcentagemBaseCalculo=this.porcentagemBaseCalculo;
		p.valorBaseCalculo=this.valorBaseCalculo;
		p.valorPis=this.valorPis;
		
		return p;
		
	}
	
}
