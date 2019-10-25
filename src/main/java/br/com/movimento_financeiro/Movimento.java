package br.com.movimento_financeiro;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.banco.Banco;
import br.com.caixa.ExpedienteCaixa;
import br.com.caixa.Sangria;
import br.com.historico.Historico;
import br.com.nota.FormaPagamentoNota;
import br.com.nota.Vencimento;
import br.com.operacao.Operacao;

@Entity
public class Movimento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_banco")
	private Banco banco;
	
	@Column
	private double valor;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Calendar data;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	private Historico historico;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	private Operacao operacao;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_vencimento")
	private Vencimento vencimento;
	
	@OneToOne(fetch=FetchType.EAGER,optional=true)
	private ExpedienteCaixa expediente;
	
	@Enumerated(EnumType.ORDINAL)
	@Column
	private FormaPagamentoNota formaPagamento;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Sangria sangria;
	
	
	@Column
	private double saldo;

	@Column
	private double juros;
	
	@Column
	private double descontos;
	
	public Movimento() {
		
		this.data = Calendar.getInstance();
		
	}
	
	
	
	
	public Sangria getSangria() {
		return sangria;
	}




	public void setSangria(Sangria sangria) {
		this.sangria = sangria;
	}




	public FormaPagamentoNota getFormaPagamento() {
		return formaPagamento;
	}




	public void setFormaPagamento(FormaPagamentoNota formaPagamento) {
		this.formaPagamento = formaPagamento;
	}




	public ExpedienteCaixa getExpediente() {
		return expediente;
	}



	public void setExpediente(ExpedienteCaixa expediente) {
		this.expediente = expediente;
	}



	public double getJuros() {
		return juros;
	}



	public void setJuros(double juros) {
		this.juros = juros;
	}



	public double getDescontos() {
		return descontos;
	}



	public void setDescontos(double descontos) {
		this.descontos = descontos;
	}



	public Historico getHistorico() {
		return historico;
	}

	public void setHistorico(Historico historico) {
		this.historico = historico;
	}
	
	

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Vencimento getVencimento() {
		return vencimento;
	}

	public void setVencimento(Vencimento vencimento) {
		this.vencimento = vencimento;
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
		Movimento other = (Movimento) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
}
