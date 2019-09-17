package br.com.afgtec.produto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.afgtec.pessoa.Empresa;
import br.com.agrofauna.utilidades.Campo;

@Entity
@Table(name="categoria")
public class Categoria{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Campo(nome = "Codigo",lista=true,editavel=false)
	private int id;
	
	@Column
	@Campo(nome = "Nome",lista=true,editavel=true, ordem="{{et}}.nome")
	private String nome;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_empresa")
	private Empresa empresa;
	
	@Column
	@Campo(nome = "ICMS",lista=true,editavel=true, ordem="{{et}}.icms")
	private double icms;
	
	@Column
	@Campo(nome = "IPI",lista=true,editavel=true, ordem="{{et}}.ipi")
	private double ipi;
	
	@Column
	@Campo(nome = "COFINS",lista=true,editavel=true, ordem="{{et}}.cofins")
	private double cofins;
	
	@Column
	@Campo(nome = "BC",lista=true,editavel=true, ordem="{{et}}.base_calculo")
	private double base_calculo;
	
	@Column
	private boolean loja_virtual;
	
	@Column
	@Campo(nome = "PIS",lista=true,editavel=true, ordem="{{et}}.pis")
	private double pis;
	
	

	public double getIcms() {
		return icms;
	}

	public void setIcms(double icms) {
		this.icms = icms;
	}

	public double getIpi() {
		return ipi;
	}

	public void setIpi(double ipi) {
		this.ipi = ipi;
	}

	public double getCofins() {
		return cofins;
	}

	public void setCofins(double cofins) {
		this.cofins = cofins;
	}

	public double getBase_calculo() {
		return base_calculo;
	}

	public void setBase_calculo(double base_calculo) {
		this.base_calculo = base_calculo;
	}

	public boolean isLoja_virtual() {
		return loja_virtual;
	}

	public void setLoja_virtual(boolean loja_virtual) {
		this.loja_virtual = loja_virtual;
	}

	public double getPis() {
		return pis;
	}

	public void setPis(double pis) {
		this.pis = pis;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	@Override
	public String toString(){
		
		return this.nome;
		
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
}
