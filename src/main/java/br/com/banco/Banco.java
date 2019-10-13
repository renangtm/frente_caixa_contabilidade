package br.com.banco;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToOne;

import br.com.pessoa.PessoaJuridica;

@Entity
public class Banco {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.PERSIST)
	@JoinColumn(name="id_pessoa")
	private PessoaJuridica pj;
	
	@Column
	private String conta;
	
	@Column
	private double saldo;
	
	@Column
	private String agencia;
	
	
	
	public String getAgencia() {
		return agencia;
	}


	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}


	public Banco(){
		
		this.pj = new PessoaJuridica();
		
	}


	public PessoaJuridica getPj() {
		return pj;
	}



	public void setPj(PessoaJuridica pj) {
		this.pj = pj;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	@Override
	public String toString() {
		
		return this.pj.getNome();
		
	}
	
}
