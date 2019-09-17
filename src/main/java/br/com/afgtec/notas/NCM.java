package br.com.afgtec.notas;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NCM {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String numero;
	
	@Column
	private String descricao;
	
	@Column
	private int ex;
	
	@Column
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
