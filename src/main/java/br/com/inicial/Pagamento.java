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

}
