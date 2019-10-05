package br.com.impostos.icms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class ICMS51 extends Icms {

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
	private double valorIcmsOperacao;

	@Column
	private double percentualDiferimento;

	@Column
	private double valorIcmsDiferido;

	public ICMS51() {
		super();

		this.setCst("51");

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

	public double getAlicotaIcms() {
		return alicotaIcms;
	}

	public void setAlicotaIcms(double alicotaIcms) {
		this.alicotaIcms = alicotaIcms;
	}

	public double getValorIcmsOperacao() {
		return valorIcmsOperacao;
	}

	public double getPercentualDiferimento() {
		return percentualDiferimento;
	}

	public void setPercentualDiferimento(double percentualDiferimento) {
		this.percentualDiferimento = percentualDiferimento;
	}

	public double getValorIcmsDiferido() {
		return valorIcmsDiferido;
	}

	// ----------------

	@Override
	public double getValorIcms() {

		return this.valorIcmsOperacao;

	}

	@Override
	public double getValorBaseCalculo() {

		return this.valorBC;

	}

	@Override
	public void calcularSobre(double valor) {

		this.valorBC = valor * ((100 - this.porcentagemReducaoBC) / 100);
		this.valorIcms = this.valorBC * (this.alicotaIcms / 100);

		this.valorIcmsDiferido = this.valorBC * (this.percentualDiferimento / 100);

		this.valorIcmsOperacao = this.valorIcms - this.valorIcmsDiferido;

	}
	
	@Override
	public Object clone(){
		
		ICMS51 i = new ICMS51();
		
		i.setOrigem(this.getOrigem());
		i.alicotaIcms=this.alicotaIcms;
		i.modalidadeBC=this.modalidadeBC;
		i.percentualDiferimento=this.percentualDiferimento;
		i.porcentagemReducaoBC=this.porcentagemReducaoBC;
		i.valorBC=this.valorBC;
		i.valorIcms=this.valorIcms;
		i.valorIcmsDiferido=this.valorIcmsDiferido;
		i.valorIcmsOperacao=this.valorIcmsOperacao;
		
		return i;
		
	}

}
