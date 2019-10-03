package br.com.afgtec.impostos;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PISAliq2 extends Pis{
	
	@Column
	private double alicotaPis;
	
	@Column
	private double porcentagemBaseCalculo;
	
	@Column
	private double valorBaseCalculo;
	
	@Column
	private double valorPis;
	
	public PISAliq2(){
		
		this.setCst("02");
		
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
		
		PISAliq2 p = new PISAliq2();
		p.alicotaPis=this.alicotaPis;
		p.porcentagemBaseCalculo=this.porcentagemBaseCalculo;
		p.valorBaseCalculo=this.valorBaseCalculo;
		p.valorPis=this.valorPis;
		
		return p;
		
	}
	
}
