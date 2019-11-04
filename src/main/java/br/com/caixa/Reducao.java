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
public class Reducao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private ExpedienteCaixa expediente;
	
	@Column
	@Campo(nome="data")
	private Calendar momento;

	@OneToOne(fetch=FetchType.EAGER)
	private Usuario gerente;
	
	@OneToMany(mappedBy="reducao")
	private List<Movimento> movimentos;

	@OneToMany(mappedBy="reducao")
	private List<Reposicao> reposicoes;
	
	@OneToMany(mappedBy="reducao")
	private List<Sangria> sangrias;
	
	@Column
	private double saldo_inicial;
	
	public Reducao(){
		
		this.reposicoes = new ArrayList<Reposicao>();
		this.sangrias = new ArrayList<Sangria>();
		this.movimentos = new ArrayList<Movimento>();
		
	}
	
	
	
	
	public List<Sangria> getSangrias() {
		return sangrias;
	}




	public void setSangrias(List<Sangria> sangrias) {
		this.sangrias = sangrias;
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
	
	public Calendar getMomento() {
		return momento;
	}

	public void setMomento(Calendar momento) {
		this.momento = momento;
	}

	
	
}
