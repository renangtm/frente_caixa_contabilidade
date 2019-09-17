package br.com.afgtec.notas;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class ICMS20 extends Icms{

	@Enumerated(EnumType.ORDINAL)
	@Column
	private ModalidadeBC modalidadeBC;
	
	@Column
	private double porcentagemReducaoBC;
	
	@Column
	private double valorBC;
	
	@Column
	private double alicotaIcms;
	
	@Column
	private double valorIcms;
	
	
	@Column
	private double valorIcmsDesonerado;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private MotivoDesoneracao motivo;
	

	public ICMS20() {
		super();
		
		this.setCst("20");
		
	}


	public ModalidadeBC getModalidadeBC() {
		return modalidadeBC;
	}


	public void setModalidadeBC(ModalidadeBC modalidadeBC) {
		this.modalidadeBC = modalidadeBC;
	}


	public double getPorcentagemReducaoBC() {
		return porcentagemReducaoBC;
	}


	public void setPorcentagemReducaoBC(double porcentagemReducaoBC) {
		this.porcentagemReducaoBC = porcentagemReducaoBC;
	}


	public double getValorBC() {
		return valorBC;
	}


	public void setValorBC(double valorBC) {
		this.valorBC = valorBC;
	}


	public double getAlicotaIcms() {
		return alicotaIcms;
	}


	public void setAlicotaIcms(double alicotaIcms) {
		this.alicotaIcms = alicotaIcms;
	}


	public double getValorIcms() {
		return valorIcms;
	}


	public void setValorIcms(double valorIcms) {
		this.valorIcms = valorIcms;
	}


	public double getValorIcmsDesonerado() {
		return valorIcmsDesonerado;
	}


	public void setValorIcmsDesonerado(double valorIcmsDesonerado) {
		this.valorIcmsDesonerado = valorIcmsDesonerado;
	}


	public MotivoDesoneracao getMotivo() {
		return motivo;
	}


	public void setMotivo(MotivoDesoneracao motivo) {
		this.motivo = motivo;
	}

	
}
