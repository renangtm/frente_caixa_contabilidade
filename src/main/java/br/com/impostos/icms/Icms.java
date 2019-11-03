package br.com.impostos.icms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Icms {

	public static Icms[] getTodosIcms() {

		return new Icms[] { new ICMS00(), new ICMS10(), new ICMS20(), new ICMS30(), new ICMS40(), new ICMS41(),
				new ICMS50(), new ICMS51(), new ICMS60(), new ICMS70(), new ICMS90() };

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Enumerated(EnumType.ORDINAL)
	@Column
	private OrigemMercadoria origem;

	@Column
	private String cst;
	
	public void calcularSobre(double valor) {

	}

	public double getValorIcms() {

		return 0;

	}

	public double getValorBaseCalculo() {

		return 0;

	}

	public double getValorIcmsDesonerado() {

		return 0;

	}

	public double getValorIcmsST() {

		return 0;

	}

	public double getBaseCalculoST() {

		return 0;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public OrigemMercadoria getOrigem() {
		return origem;
	}

	public void setOrigem(OrigemMercadoria origem) {
		this.origem = origem;
	}

	public String getCst() {
		return cst;
	}

	public void setCst(String cst) {
		this.cst = cst;
	}

	@Override
	public Object clone() {

		return null;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cst == null) ? 0 : cst.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Icms other = (Icms) obj;
		if (cst == null) {
			if (other.cst != null)
				return false;
		} else if (!cst.equals(other.cst))
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		return this.cst;
		
	}
	
}
