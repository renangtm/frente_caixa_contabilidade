package br.com.caixa;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import br.com.usuario.Usuario;
import br.com.utilidades.Campo;

@Entity
public class Sangria {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private ExpedienteCaixa expediente;
	
	@Column
	@Campo(nome="valor")
	private double valor;
	
	@Column
	private double saldo_caixa;
	
	@Column
	@Campo(nome="data")
	private Calendar momento;

	@OneToOne
	private Usuario gerente;
	
	@ManyToOne
	private Reducao reducao;

	public int getId() {
		return id;
	}
	
	

	public double getSaldo_caixa() {
		return saldo_caixa;
	}



	public void setSaldo_caixa(double saldo_caixa) {
		this.saldo_caixa = saldo_caixa;
	}



	public Reducao getReducao() {
		return reducao;
	}



	public void setReducao(Reducao sangria) {
		this.reducao = sangria;
	}



	public Usuario getGerente() {
		return gerente;
	}



	public void setGerente(Usuario gerente) {
		this.gerente = gerente;
	}



	public void setId(int id) {
		this.id = id;
	}

	public ExpedienteCaixa getExpediente() {
		return expediente;
	}

	public void setExpediente(ExpedienteCaixa expediente) {
		this.expediente = expediente;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Calendar getMomento() {
		return momento;
	}

	public void setMomento(Calendar momento) {
		this.momento = momento;
	}
	
}
