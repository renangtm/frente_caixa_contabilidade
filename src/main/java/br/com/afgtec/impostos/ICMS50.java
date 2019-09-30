package br.com.afgtec.impostos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.afgtec.notas.MotivoDesoneracao;

@Entity
public class ICMS50 extends Icms {

	@Column
	private double valorIcmsDesonerado;

	@Column
	@Enumerated(EnumType.ORDINAL)
	private MotivoDesoneracao motivo;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_icms_original")
	private Icms icmsOriginal;

	public ICMS50() {
		super();

		this.setCst("50");

	}

	public Icms getIcmsOriginal() {
		return icmsOriginal;
	}

	public void setIcmsOriginal(Icms icmsOriginal) {
		this.icmsOriginal = icmsOriginal;
	}

	public MotivoDesoneracao getMotivo() {
		return motivo;
	}

	public void setMotivo(MotivoDesoneracao motivo) {
		this.motivo = motivo;
	}

	// -----------

	@Override
	public double getValorIcmsDesonerado() {

		return valorIcmsDesonerado;

	}

	@Override
	public void calcularSobre(double valor) {

		this.valorIcmsDesonerado = 0;
		if (this.icmsOriginal != null) {

			this.icmsOriginal.calcularSobre(valor);

			this.valorIcmsDesonerado = this.icmsOriginal.getValorIcms() + this.icmsOriginal.getValorIcmsDesonerado();

		}

	}

	@Override
	public Object clone() {

		ICMS50 i = new ICMS50();

		i.setOrigem(this.getOrigem());
		
		if(this.icmsOriginal != null)
			i.icmsOriginal = (Icms) this.icmsOriginal.clone();
		
		i.motivo = this.motivo;
		i.valorIcmsDesonerado = this.valorIcmsDesonerado;

		return i;
		
	}

}
