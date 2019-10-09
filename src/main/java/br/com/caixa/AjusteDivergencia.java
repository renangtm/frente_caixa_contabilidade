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

import br.com.movimento_financeiro.Movimento;

@Entity
public class AjusteDivergencia {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private ExpedienteCaixa expediente;
	
	@Column
	private double valor;
	
	@Column
	private Calendar momento;
	
	@Column
	private TipoAjuste tipo;
	
	@OneToOne(fetch=FetchType.EAGER,mappedBy="expediente")
	private Movimento movimento;

	public int getId() {
		return id;
	}

	
	
	public TipoAjuste getTipo() {
		return tipo;
	}



	public void setTipo(TipoAjuste tipo) {
		this.tipo = tipo;
	}



	public Movimento getMovimento() {
		
		return movimento;
		
	}



	public void setMovimento(Movimento movimento) {
		this.movimento = movimento;
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
