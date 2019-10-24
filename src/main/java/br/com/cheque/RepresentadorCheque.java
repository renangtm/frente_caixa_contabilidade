package br.com.cheque;

import java.util.Calendar;

import br.com.utilidades.Campo;
import br.com.utilidades.Representador;

public class RepresentadorCheque extends Representador<Cheque> {

	@Campo(nome = "Cod", editavel = false, ordem = "{{et}}.id")
	private int id;

	@Campo(nome = "Num", editavel = false, ordem = "{{et}}.numero")
	private String numero;

	@Campo(nome = "Responsavel", editavel = false, ordem = "{{et}}.pessoa.nome")
	private String nomePessoa;

	@Campo(nome = "Valor (R$)", editavel = false, ordem = "{{et}}.valor")
	private double valor;

	@Campo(nome = "Data", editavel = false, ordem = "{{et}}.data")
	private Calendar data;

	@Campo(nome = "tipo", editavel = false, ordem = "{{et}}.movimento.operacao.credito")
	private String tipo;

	@Campo(nome = "Recebido", editavel = false, ordem = "{{et}}.recebido")
	private String recebido;

	public RepresentadorCheque(Cheque c) {
		super(c);
		// TODO Auto-generated constructor stub

		this.id = c.getId();
		this.nomePessoa = c.getPessoa().getNome();
		this.valor = c.getValor();
		this.data = c.getData();
		this.recebido = c.isRecebido() ? "SIM" : "NAO";
		this.tipo = c.getMovimento().getOperacao().isCredito() ? "CREDITO" : "DEBITO";
		this.numero = c.getNumero();

	}

}
