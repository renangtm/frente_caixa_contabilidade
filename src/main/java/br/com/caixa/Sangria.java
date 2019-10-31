package br.com.caixa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.movimento_financeiro.Movimento;
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
	@Campo(nome="data")
	private Calendar momento;

	@OneToOne(fetch=FetchType.EAGER)
	private Usuario gerente;
	
	@OneToMany(mappedBy="sangria")
	private List<Movimento> movimentos;

	@OneToMany(mappedBy="sangria")
	private List<Reposicao> reposicoes;
	
	@Column
	private double saldo_inicial;
	
	public Sangria(){
		
		this.reposicoes = new ArrayList<Reposicao>();
		this.movimentos = new ArrayList<Movimento>();
		
	}
	
	
	
	
	public double getSaldo_inicial() {
		return saldo_inicial;
	}




	public void setSaldo_inicial(double saldo_inicial) {
		this.saldo_inicial = saldo_inicial;
	}




	public List<Reposicao> getReposicoes() {
		return reposicoes;
	}



	public void setReposicoes(List<Reposicao> reposicoes) {
		this.reposicoes = reposicoes;
	}



	public List<Movimento> getMovimentos() {
		return movimentos;
	}



	public void setMovimentos(List<Movimento> movimentos) {
		this.movimentos = movimentos;
	}



	public int getId() {
		return id;
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
