package br.com.afgtec.notas;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import br.com.afgtec.pessoa.Pessoa;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Icms {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Enumerated(EnumType.ORDINAL)
	@Column
	private OrigemMercadoria origem;

	@Column
	private String cst;

	public void atribuirValor(double valor, Pessoa origem, Pessoa destino) {

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

}
