package br.com.caixa;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import br.com.empresa.Empresa;


@Entity
public class Caixa {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private int numero;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Empresa empresa;
	
	@Column
	private double saldoAtual;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="caixa")
	private List<ExpedienteCaixa> expedientes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}
	
	

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public double getSaldoAtual() {
		return saldoAtual;
	}

	public void setSaldoAtual(double saldoAtual) {
		this.saldoAtual = saldoAtual;
	}

	public List<ExpedienteCaixa> getExpedientes() {
		return expedientes;
	}

	public void setExpedientes(List<ExpedienteCaixa> expedientes) {
		this.expedientes = expedientes;
	}
	
}
