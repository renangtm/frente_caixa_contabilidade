package br.com.empresa;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ParametrosEmissao {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String senha_sat;

	@OneToOne(fetch=FetchType.EAGER,mappedBy="parametrosEmissao",cascade= {CascadeType.MERGE})
	private Empresa empresa;
	
	@Column
	private String numeroSat;
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getNumeroSat() {
		return numeroSat;
	}

	public void setNumeroSat(String numeroSat) {
		this.numeroSat = numeroSat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSenha_sat() {
		return senha_sat;
	}

	public void setSenha_sat(String senha_sat) {
		this.senha_sat = senha_sat;
	}
	
	
	
}
