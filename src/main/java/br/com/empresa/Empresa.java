package br.com.empresa;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.codigo_barra.PadraoCodigo;
import br.com.pessoa.PessoaJuridica;
@Entity
public class Empresa{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@OneToMany(fetch=FetchType.LAZY,mappedBy="empresa")
	private List<PadraoCodigo> padroesCodigo;
	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.PERSIST)
	@JoinColumn(name="id_parametros")
	private ParametrosEmissao parametrosEmissao;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_logo")
	private Logo logo;
	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.PERSIST)
	@JoinColumn(name="id_pessoa")
	private PessoaJuridica pj;

	@Column
	private String tokenAPIImpostosAproximados;
	
	public String getTokenAPIImpostosAproximados() {
		return tokenAPIImpostosAproximados;
	}



	public void setTokenAPIImpostosAproximados(String tokenAPIImpostosAproximados) {
		this.tokenAPIImpostosAproximados = tokenAPIImpostosAproximados;
	}



	public Empresa(){
		
		this.pj = new PessoaJuridica();
		this.logo = new Logo();
		
	}
	


	public ParametrosEmissao getParametrosEmissao() {
		return parametrosEmissao;
	}



	public void setParametrosEmissao(ParametrosEmissao parametrosEmissao) {
		this.parametrosEmissao = parametrosEmissao;
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
	
	public Logo getLogo() {
		return logo;
	}

	public void setLogo(Logo logo) {
		this.logo = logo;
	}

	public List<PadraoCodigo> getPadroesCodigo() {
		return padroesCodigo;
	}

	public void setPadroesCodigo(List<PadraoCodigo> padroesCodigo) {
		this.padroesCodigo = padroesCodigo;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Empresa other = (Empresa) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
}
