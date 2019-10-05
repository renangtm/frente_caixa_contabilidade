package br.com.cfop;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.entidades.nota.OperacaoLogistica;

@Entity
public class ItemTabelaCfop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
	@JoinColumn(name="id_cfop")
	private CFOP cfop;

	@Enumerated(EnumType.ORDINAL)
	@Column
	private OperacaoLogistica operacao;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CFOP getCfop() {
		return cfop;
	}

	public void setCfop(CFOP cfop) {
		this.cfop = cfop;
	}

	public OperacaoLogistica getOperacao() {
		return operacao;
	}

	public void setOperacao(OperacaoLogistica operacao) {
		this.operacao = operacao;
	}

	@Override
	public Object clone() {

		ItemTabelaCfop i = new ItemTabelaCfop();
		i.id = 0;
		i.cfop = this.cfop;
		i.operacao = this.operacao;

		return i;

	}

}
