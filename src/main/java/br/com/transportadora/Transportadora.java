package br.com.transportadora;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.pessoa.PessoaJuridica;

@Entity
public class Transportadora {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.PERSIST)
	@JoinColumn(name = "id_pessoa")
	private PessoaJuridica pj;

	public Transportadora() {

		this.pj = new PessoaJuridica();

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PessoaJuridica getPj() {
		return pj;
	}

	public void setPj(PessoaJuridica pj) {
		this.pj = pj;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((pj == null) ? 0 : pj.hashCode());
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
		Transportadora other = (Transportadora) obj;
		if (id != other.id)
			return false;
		if (pj == null) {
			if (other.pj != null)
				return false;
		} else if (!pj.equals(other.pj))
			return false;
		return true;
	}

}
