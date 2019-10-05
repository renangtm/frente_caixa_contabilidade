package br.com.entidades.ncm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.utilidades.Campo;

@Entity
public class NCM {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Campo(nome="Cod",editavel=false,ordem="{{et}}.id")
	private int id;
	
	@Column
	@Campo(nome="Numero",editavel=false,ordem="{{et}}.numero")
	private String numero;
	
	@Column
	private String descricao;
	
	@Column
	@Campo(nome="Ex",editavel=false,ordem="{{et}}.editavel")
	private int ex;
	
	@Column
	@Campo(nome="Ipi(%)",editavel=false,ordem="{{et}}.alicota_ipi")
	private double alicota_ipi;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getEx() {
		return ex;
	}

	public void setEx(int ex) {
		this.ex = ex;
	}

	public double getAlicota_ipi() {
		return alicota_ipi;
	}

	public void setAlicota_ipi(double alicota_ipi) {
		this.alicota_ipi = alicota_ipi;
	}
	
	
	
	
}
