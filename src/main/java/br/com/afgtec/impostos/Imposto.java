package br.com.afgtec.impostos;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Imposto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private double valorTotal;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_icms")
	private Icms icms;

	@Override
	public Object clone() {

		Imposto i = new Imposto();
		i.valorTotal = this.valorTotal;
		i.icms = (Icms) this.icms.clone();

		return i;

	}

}
