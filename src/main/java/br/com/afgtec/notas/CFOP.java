package br.com.afgtec.notas;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.agrofauna.utilidades.Campo;

@Entity
public class CFOP {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Campo(nome="Cod",editavel=false,ordem="{{et}}.id")
	private int id;
	
	@Column
	@Campo(nome="Numero",editavel=false,ordem="{{et}}.numero")
	private String numero;
	
	@Column
	@Campo(nome="SbN",editavel=false,ordem="{{et}}.subnumero")
	private String subnumero;
	
	@Column
	@Campo(nome="Des",editavel=false,ordem="{{et}}.descricao")
	private String descricao;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getSubnumero() {
		return subnumero;
	}

	public void setSubnumero(String subnumero) {
		this.subnumero = subnumero;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}
