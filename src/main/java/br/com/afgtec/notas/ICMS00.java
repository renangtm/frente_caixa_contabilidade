package br.com.afgtec.notas;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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

	public double getValorICMS() {
		return valorICMS;
	}

	public void setValorICMS(double valorICMS) {
		this.valorICMS = valorICMS;
	}
	
	
	
}
