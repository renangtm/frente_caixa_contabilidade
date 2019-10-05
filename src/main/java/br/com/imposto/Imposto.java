package br.com.imposto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.imposto.cofins.Cofins;
import br.com.imposto.pis.Pis;
import br.com.impostos.icms.Icms;

@Entity
public class Imposto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_icms")
	private Icms icms;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_pis")
	private Pis pis;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_cofins")
	private Cofins cofins;

	@Override
	public Object clone() {

		Imposto i = new Imposto();
		i.icms = (Icms) this.icms.clone();
		i.pis = (Pis) this.pis.clone();
		i.cofins = (Cofins) this.cofins;

		return i;

	}

	public Icms getIcms() {
		return icms;
	}

	public void setIcms(Icms icms) {
		this.icms = icms;
	}

	public Pis getPis() {
		return pis;
	}

	public void setPis(Pis pis) {
		this.pis = pis;
	}

	public Cofins getCofins() {
		return cofins;
	}

	public void setCofins(Cofins cofins) {
		this.cofins = cofins;
	}

	public void calcularSobre(double valor) {

		this.icms.calcularSobre(valor);
		this.pis.calcularSobre(valor);
		this.cofins.calcularSobre(valor);

	}

	public double getTotalImpostos() {

		return this.icms.getValorIcms() + this.icms.getValorIcmsST() + this.pis.getValor() + this.cofins.getValor();

	}

}
