package br.com.impostos.icms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class ICMS10 extends Icms {

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

	@Column
	private double valorBaseCalculo;

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

	@Override
	public double getValorIcms() {

		return this.valorICMS;

	}

	@Override
	public double getValorBaseCalculo() {

		return this.valorBaseCalculo;

	}

	@Override
	public double getValorIcmsST() {

		return this.valorIcmsST;

	}

	@Override
	public double getBaseCalculoST() {

		return this.valorBaseCalculoICMSST;

	}

	@Override
	public void calcularSobre(double valor) {

		this.valorBaseCalculo = valor * (this.porcentagemBC / 100);
		this.valorICMS = this.valorBaseCalculo * (this.porcentagemICMS / 100);

		this.valorBaseCalculoICMSST = valor * (this.alicotaIcmsST / 100);
		this.valorIcmsST = this.valorBaseCalculoICMSST * (this.alicotaIcmsST / 100)
				* (1 + (this.porcentagemMargemValorAdicionadoST / 100));

	}

	@Override
	public Object clone() {

		ICMS10 i = new ICMS10();

		i.setOrigem(this.getOrigem());
		i.alicotaIcmsST = this.alicotaIcmsST;
		i.modalidadeBC = this.modalidadeBC;
		i.modalidadeBCST = this.modalidadeBCST;
		i.porcentagemBC = this.porcentagemBC;
		i.porcentagemICMS = this.porcentagemICMS;
		i.porcentagemMargemValorAdicionadoST = this.porcentagemMargemValorAdicionadoST;
		i.porcentagemReducaoBCST=this.porcentagemReducaoBCST;
		i.valorBaseCalculo=this.valorBaseCalculo;
		i.valorBaseCalculoICMSST=this.valorBaseCalculoICMSST;
		i.valorICMS=this.valorICMS;
		i.valorIcmsST=this.valorIcmsST;
		
		return i;
		
	}

}
