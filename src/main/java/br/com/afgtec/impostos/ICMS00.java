package br.com.afgtec.impostos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.afgtec.notas.ModalidadeBC;

@Entity
public class ICMS00 extends Icms{

	@Enumerated(EnumType.ORDINAL)
	@Column
	private ModalidadeBC modalidadeBC;
	
	@Column
	private double porcentagemBC;
	
	@Column
	private double porcentagemICMS;
	
	@Column
	private double valorICMS;
	
	@Column
	private double valorBaseCalculo;
	
	public ICMS00() {
		super();
		
		this.setCst("00");
		
	}

	public ModalidadeBC getModalidadeBC() {
		return modalidadeBC;
	}

	public void setModalidadeBC(ModalidadeBC modalidadeBC) {
		this.modalidadeBC = modalidadeBC;
	}

	public double getPorcentagemBC() {
		return porcentagemBC;
	}

	public void setPorcentagemBC(double porcentagemBC) {
		this.porcentagemBC = porcentagemBC;
	}

	public double getPorcentagemICMS() {
		return porcentagemICMS;
	}

	public void setPorcentagemICMS(double porcentagemICMS) {
		this.porcentagemICMS = porcentagemICMS;
	}

	@Override
	public double getValorIcms() {
		
		return valorICMS;
		
	}
	
	@Override
	public double getValorBaseCalculo() {
		
		return valorBaseCalculo;
		
	}
	

	@Override
	public void calcularSobre(double valor){
		
		this.valorBaseCalculo = valor*(this.porcentagemBC/100);
		this.valorICMS = this.valorBaseCalculo*(this.porcentagemICMS/100);
		
	}
	
	@Override
	public Object clone(){
		
		ICMS00 i = new ICMS00(); 
		i.modalidadeBC=this.modalidadeBC;
		i.porcentagemBC=this.porcentagemBC;
		i.porcentagemICMS=this.porcentagemICMS;
		i.valorBaseCalculo=this.valorBaseCalculo;
		i.valorICMS=this.valorICMS;
		
		return i;
		
	}
	
}
