package br.com.afgtec.impostos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.afgtec.notas.ModalidadeBC;
import br.com.afgtec.notas.MotivoDesoneracao;

@Entity
public class ICMS20 extends Icms {

	@Enumerated(EnumType.ORDINAL)
	@Column
	private ModalidadeBC modalidadeBC;

	@OneToOne
	@JoinColumn(name = "id_icms_original")
	private Icms icmsOriginal;

	@Column
	private double porcentagemReducaoBC;

	@Column
	private double alicotaIcms;

	@Column
	private double valorBC;

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

	public void setIcmsOriginal(Icms icms) {

		this.icmsOriginal = icms;

	}

	public Icms getIcmsOriginal() {

		return this.icmsOriginal;

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

	public void setValorIcms(double valorIcms) {
		this.valorIcms = valorIcms;
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

	// ------------------

	@Override
	public double getValorIcms() {

		return this.valorIcms;

	}

	@Override
	public double getValorBaseCalculo() {

		return this.valorBC;

	}

	@Override
	public double getValorIcmsDesonerado() {

		return this.valorIcmsDesonerado;

	}

	@Override
	public void calcularSobre(double valor) {

		this.valorBC = valor * ((100 - this.porcentagemReducaoBC) / 100);
		this.valorIcms = this.valorBC * (this.alicotaIcms / 100);

		this.valorIcmsDesonerado = 0;
		if (this.icmsOriginal != null) {

			this.icmsOriginal.calcularSobre(valor);

			this.valorIcmsDesonerado = (this.valorIcms - this.icmsOriginal.getValorIcms())
					+ this.icmsOriginal.getValorIcmsDesonerado();

		}

	}
	
	@Override
	public Object clone(){
		
		ICMS20 i = new ICMS20();
		i.alicotaIcms=this.alicotaIcms;
		
		if(this.icmsOriginal != null)
			i.icmsOriginal=(Icms)this.icmsOriginal.clone();
		
		i.modalidadeBC=this.modalidadeBC;
		i.motivo=this.motivo;
		i.porcentagemReducaoBC=this.porcentagemReducaoBC;
		i.valorBC=this.valorBC;
		i.valorIcms=this.valorIcms;
		i.valorIcmsDesonerado=this.valorIcmsDesonerado;
		
		return i;
		
	}

}
