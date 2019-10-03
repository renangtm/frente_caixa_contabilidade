package br.com.afgtec.impostos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pis {

	public static Pis[] getTodosPis() {

		return new Pis[] { new PISAliq1(), new PISAliq2(), new PISNt4(), new PISNt5(), new PISNt6(), new PISNt7(),
				new PISNt8(), new PISNt9(), new PISOutr(), new PISSt() };

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String cst;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCst() {
		return cst;
	}

	public void setCst(String cst) {
		this.cst = cst;
	}

	public double getValor() {

		return 0;

	}

	public void calcularSobre(double valor) {

	}

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
		Pis other = (Pis) obj;
		if (cst == null) {
			if (other.cst != null)
				return false;
		} else if (!cst.equals(other.cst))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return this.getCst();

	}

}
