package br.com.inicial;

import java.util.Calendar;

import br.com.cheque.Cheque;
import br.com.nota.FormaPagamentoNota;
import br.com.nota.Vencimento;
import br.com.produto.RetiradaValePresente;

//Classe auxiliar
public class Pagamento {
	
	public double valor;
	public Calendar data;
	public FormaPagamentoNota formaPagamento;
	public Vencimento vencimento;
	public Cheque cheque;
	public RetiradaValePresente retirada;
	
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
	public FormaPagamentoNota getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(FormaPagamentoNota formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public Vencimento getVencimento() {
		return vencimento;
	}
	public void setVencimento(Vencimento vencimento) {
		this.vencimento = vencimento;
	}
	public Cheque getCheque() {
		return cheque;
	}
	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}
	public RetiradaValePresente getRetirada() {
		return retirada;
	}
	public void setRetirada(RetiradaValePresente retirada) {
		this.retirada = retirada;
	}

	
	
}
