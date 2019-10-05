package br.com.impostos.icms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.entidades.nota.MotivoDesoneracao;

@Entity
public class ICMS90 extends Icms{
	
	@Enumerated(EnumType.ORDINAL)
	@Column
	private ModalidadeBC modalidadeBC;
	
	@Enumerated(EnumType.ORDINAL)
	@Column
	private ModalidadeBCST modalidadeBCST;
	
	@Column
	private double porcentagemReducaoBC;

	@Column
	private double valorBaseCalculo;
	
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
	private double valorIcmsDesonerado;
	
	@Enumerated(EnumType.ORDINAL)
	@Column
	private MotivoDesoneracao motivoDesoneracao;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_icms_original")
	private Icms icmsOriginal;
	
	public ICMS90(){
		
		this.setCst("90");
		
	}
	
	public Icms getIcmsOriginal() {

		return this.icmsOriginal;

	}

	public void setIcmsOriginal(Icms icms) {

		this.icmsOriginal = icms;

	}
	
	public ModalidadeBC getModalidadeBC() {
		return modalidadeBC;
	}

	public void setModalidadeBC(ModalidadeBC modalidadeBC) {
		this.modalidadeBC = modalidadeBC;
	}

	public ModalidadeBCST getModalidadeBCST() {
		return modalidadeBCST;
	}

	public void setModalidadeBCST(ModalidadeBCST modalidadeBCST) {
		this.modalidadeBCST = modalidadeBCST;
	}

	public double getPorcentagemReducaoBC() {
		return porcentagemReducaoBC;
	}

	public void setPorcentagemReducaoBC(double porcentagemReducaoBC) {
		this.porcentagemReducaoBC = porcentagemReducaoBC;
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

	public double getAlicotaIcmsST() {
		return alicotaIcmsST;
	}

	public void setAlicotaIcmsST(double alicotaIcmsST) {
		this.alicotaIcmsST = alicotaIcmsST;
	}

	public MotivoDesoneracao getMotivoDesoneracao() {
		return motivoDesoneracao;
	}

	public void setMotivoDesoneracao(MotivoDesoneracao motivoDesoneracao) {
		this.motivoDesoneracao = motivoDesoneracao;
	}
	
	//----------------------
	
	@Override
	public void calcularSobre(double valor) {

		this.valorBaseCalculo = valor * ((100 - this.porcentagemReducaoBC) / 100);
		this.valorICMS = this.valorBaseCalculo * (this.porcentagemICMS / 100);

		this.valorBaseCalculoICMSST = valor * ((100 - this.porcentagemReducaoBCST) / 100);
		this.valorIcmsST = this.valorBaseCalculoICMSST * (this.alicotaIcmsST / 100)
				* (1 + (this.porcentagemMargemValorAdicionadoST / 100));

		this.valorIcmsDesonerado = 0;
		if (this.icmsOriginal != null) {

			this.icmsOriginal.calcularSobre(valor);

			this.valorIcmsDesonerado = (this.valorIcmsST - this.icmsOriginal.getValorIcms())
					+ this.icmsOriginal.getValorIcmsDesonerado();

		}

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
	public double getValorIcmsDesonerado() {

		return this.valorIcmsDesonerado;

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
	public Object clone() {

		ICMS90 i = new ICMS90();
		i.alicotaIcmsST = this.alicotaIcmsST;

		if (this.icmsOriginal != null)
			i.icmsOriginal = (Icms) this.icmsOriginal.clone();

		i.setOrigem(this.getOrigem());
		i.modalidadeBC = this.modalidadeBC;
		i.modalidadeBCST = this.modalidadeBCST;
		i.motivoDesoneracao = this.motivoDesoneracao;
		i.porcentagemICMS = this.porcentagemICMS;
		i.porcentagemMargemValorAdicionadoST = this.porcentagemMargemValorAdicionadoST;
		i.porcentagemReducaoBC = this.porcentagemReducaoBC;
		i.porcentagemReducaoBCST = this.porcentagemReducaoBCST;
		i.valorBaseCalculo = this.valorBaseCalculo;
		i.valorBaseCalculoICMSST = this.valorBaseCalculoICMSST;
		i.valorICMS = this.valorICMS;
		i.valorIcmsDesonerado = this.valorIcmsDesonerado;
		i.valorIcmsST = this.valorIcmsST;

		return i;

	}
	
	
}
