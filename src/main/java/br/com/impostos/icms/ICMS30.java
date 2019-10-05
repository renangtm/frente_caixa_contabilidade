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
public class ICMS30 extends Icms{

	@Enumerated(EnumType.ORDINAL)
	@Column
	private ModalidadeBCST modalidadeBCST;
	
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
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private MotivoDesoneracao motivo;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_icms_original")
	private Icms icmsOriginal;
	
	public ICMS30() {
		super();
		
		this.setCst("30");
		
	}


	
	
	public Icms getIcmsOriginal() {
		return icmsOriginal;
	}




	public void setIcmsOriginal(Icms icmsOriginal) {
		this.icmsOriginal = icmsOriginal;
	}




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

	public double getAlicotaIcmsST() {
		return alicotaIcmsST;
	}


	public void setAlicotaIcmsST(double alicotaIcmsST) {
		this.alicotaIcmsST = alicotaIcmsST;
	}


	public MotivoDesoneracao getMotivo() {
		return motivo;
	}


	public void setMotivo(MotivoDesoneracao motivo) {
		this.motivo = motivo;
	}
	
	//------------------
	
	@Override
	public double getValorIcmsST() {

		return this.valorIcmsST;

	}

	@Override
	public double getBaseCalculoST() {

		return this.valorBaseCalculoICMSST;

	}

	@Override
	public double getValorIcmsDesonerado() {

		return this.valorIcmsDesonerado;

	}

	@Override
	public void calcularSobre(double valor) {

		this.valorBaseCalculoICMSST = valor * ((100 - this.porcentagemReducaoBCST) / 100);
		this.valorIcmsST = this.valorBaseCalculoICMSST * (this.alicotaIcmsST / 100) * (1+(this.porcentagemMargemValorAdicionadoST/100));

		this.valorIcmsDesonerado = 0;
		if (this.icmsOriginal != null) {

			this.icmsOriginal.calcularSobre(valor);

			this.valorIcmsDesonerado = (this.valorIcmsST - this.icmsOriginal.getValorIcms())
					+ this.icmsOriginal.getValorIcmsDesonerado();

		}

	}
	
	@Override
	public Object clone(){
		
		ICMS30 i = new ICMS30();
		
		if(this.icmsOriginal != null)
			i.icmsOriginal=(Icms)this.icmsOriginal.clone();
					
		i.setOrigem(this.getOrigem());
		i.motivo=this.motivo;
		i.valorIcmsDesonerado=this.valorIcmsDesonerado;
		i.alicotaIcmsST=this.alicotaIcmsST;
		i.modalidadeBCST=this.modalidadeBCST;
		i.porcentagemMargemValorAdicionadoST=this.porcentagemMargemValorAdicionadoST;
		i.porcentagemReducaoBCST=this.porcentagemReducaoBCST;
		i.valorBaseCalculoICMSST=this.valorBaseCalculoICMSST;
		i.valorIcmsST=this.valorIcmsST;
		
		return i;
		
	}
	
}
