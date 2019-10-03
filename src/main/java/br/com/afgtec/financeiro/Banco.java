package br.com.afgtec.financeiro;

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

import br.com.afgtec.pessoa.PessoaJuridica;

@Entity
public class Banco {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(fetch=FetchType.EAGER,cascade={ CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinColumn(name="id_pessoa")
	private PessoaJuridica pj;
	
	@Column
	private String conta;
	
	@Column
	private double saldo;
	
	@OneToMany(mappedBy="banco")
	private List<Movimento> movimentos;
	
	@OneToMany(mappedBy="banco")
	private List<Fechamento> fechamentos;
	
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
	

	public List<Movimento> getMovimentos() {
		return movimentos;
	}


	public void setMovimentos(List<Movimento> movimentos) {
		this.movimentos = movimentos;
	}


	public List<Fechamento> getFechamentos() {
		return fechamentos;
	}


	public void setFechamentos(List<Fechamento> fechamentos) {
		this.fechamentos = fechamentos;
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
