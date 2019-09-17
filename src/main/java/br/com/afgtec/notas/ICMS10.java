package br.com.afgtec.notas;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class ICMS10 extends Icms{

	@Enumerated(EnumType.ORDINAL)
	@Column
	private ModalidadeBC modalidadeBC;
	
	@Enumerated(EnumType.ORDINAL)
	@Column
	private ModalidadeBCST modalidadeBCST;
	
	@Column
	private double porcentagemBC;
	
	@Column
	private double porcentagemICMS;
	
	@Column
	private double valorICMS;
	
	@Column
	private double porcentagemMargemValorAdicionadoST;
	
	@Column
	private double porcentagemReducaoBCST;
	
	@Column
	private double valorBaseCalculoICMSST;
	
	@Column
	private double alicotaIcmsST;
	
	@Column
	private double valorIcmsST;
	
	
	
	public ModalidadeBCST getModalidadeBCST() {
		return modalidadeBCST;
	}

	public void setModalidadeBCST(ModalidadeBCST modalidadeBCST) {
		this.modalidadeBCST = modalidadeBCST;
	}

	public double getPorcentagemMargemValorAdicionadoST() {
		return porcentagemMargemValorAdicionadoST;
	}

	public void setPorcentagemMargemValorAdicionadoST(double porcentagemMargemValorAdicionadoST) {
		this.porcentagemMargemValorAdicionadoST = porcentagemMargemValorAdicionadoST;
	}

	public double getPorcentagemReducaoBCST() {
		return porcentagemReducaoBCST;
	}

	public void setPorcentagemReducaoBCST(double porcentagemReducaoBCST) {
		this.porcentagemReducaoBCST = porcentagemReducaoBCST;
	}

	public double getValorBaseCalculoICMSST() {
		return valorBaseCalculoICMSST;
	}

	public void setValorBaseCalculoICMSST(double valorBaseCalculoICMSST) {
		this.valorBaseCalculoICMSST = valorBaseCalculoICMSST;
	}

	public double getAlicotaIcmsST() {
		return alicotaIcmsST;
	}

	public void setAlicotaIcmsST(double alicotaIcmsST) {
		this.alicotaIcmsST = alicotaIcmsST;
	}

	public double getValorIcmsST() {
		return valorIcmsST;
	}

	public void setValorIcmsST(double valorIcmsST) {
		this.valorIcmsST = valorIcmsST;
	}

	public ICMS10() {
		super();
		
		this.setCst("10");
		
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
