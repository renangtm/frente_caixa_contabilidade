package br.com.afgtec.impostos;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PISQtde extends Pis{
	
	@Column
	private double quantidadeVendida;
	
	@Column
	private double valorAliqProd;
	
	@Column
	private double valorPis;
	
	public PISQtde(){
		
		this.setCst("03");
		
	}
	
	
	
	public double getQuantidadeVendida() {
		return quantidadeVendida;
	}



	public void setQuantidadeVendida(double quantidadeVendida) {
		this.quantidadeVendida = quantidadeVendida;
	}



	public double getValorAliqProd() {
		return valorAliqProd;
	}



	public void setValorAliqProd(double valorAliqProd) {
		this.valorAliqProd = valorAliqProd;
	}



	@Override
	public void calcularSobre(double valor){
		
		this.valorPis = this.valorAliqProd*this.quantidadeVendida;
		
	}
	
	@Override
	public double getValor(){
		
		return this.valorPis;
		
	}
	
	@Override
	public Object clone(){
		
		PISQtde p = new PISQtde();
		p.quantidadeVendida=this.quantidadeVendida;
		p.valorAliqProd=this.valorAliqProd;
		p.valorPis=this.valorPis;
		
		return p;
		
	}
	
}
