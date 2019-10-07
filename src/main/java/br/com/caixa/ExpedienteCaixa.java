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


import br.com.movimento_financeiro.Movimento;
import br.com.usuario.Usuario;

@Entity
public class ExpedienteCaixa {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Usuario usuario;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Caixa caixa;
	
	@Column
	private Calendar inicio;
	
	@Column
	private Calendar fim;
	
	@Column
	private double saldo_inicial;
	
	@Column
	private double saldo_final_atual;
	
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="expediente")
	private List<Movimento> movimentos;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="expediente")
	private List<Sangria> sangrias;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="expediente")
	private List<Reposicao> reposicoes;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="expediente")
	private List<AjusteDivergencia> ajustes;

	public ExpedienteCaixa() {
		
		this.inicio = Calendar.getInstance();
		this.fim = null;
		
		this.ajustes = new ArrayList<AjusteDivergencia>();
		this.reposicoes = new ArrayList<Reposicao>();
		this.sangrias  =new ArrayList<Sangria>();
		this.movimentos = new ArrayList<Movimento>();
		
		
	}
	
	
	
	public List<AjusteDivergencia> getAjustes() {
		return ajustes;
	}



	public void setAjustes(List<AjusteDivergencia> ajustes) {
		this.ajustes = ajustes;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public Calendar getInicio() {
		return inicio;
	}

	public void setInicio(Calendar inicio) {
		this.inicio = inicio;
	}

	public Calendar getFim() {
		return fim;
	}

	public void setFim(Calendar fim) {
		this.fim = fim;
	}

	public double getSaldo_inicial() {
		return saldo_inicial;
	}

	public void setSaldo_inicial(double saldo_inicial) {
		this.saldo_inicial = saldo_inicial;
	}

	public double getSaldo_final_atual() {
		return saldo_final_atual;
	}

	public void setSaldo_final_atual(double saldo_final_atual) {
		this.saldo_final_atual = saldo_final_atual;
	}

	public List<Movimento> getMovimentos() {
		return movimentos;
	}

	public void setMovimentos(List<Movimento> movimentos) {
		this.movimentos = movimentos;
	}

	public List<Sangria> getSangrias() {
		return sangrias;
	}

	public void setSangrias(List<Sangria> sangrias) {
		this.sangrias = sangrias;
	}

	public List<Reposicao> getReposicoes() {
		return reposicoes;
	}

	public void setReposicoes(List<Reposicao> reposicoes) {
		this.reposicoes = reposicoes;
	}

	
	
	
}
