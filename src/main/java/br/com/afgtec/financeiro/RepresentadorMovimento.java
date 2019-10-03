package br.com.afgtec.financeiro;

import br.com.agrofauna.conversores.ConversorDate;
import br.com.agrofauna.utilidades.Campo;
import br.com.agrofauna.utilidades.Representador;

public class RepresentadorMovimento extends Representador<Movimento>{

	@Campo(nome="Cod",editavel=false)
	private int cod;
	
	@Campo(nome="Banco",editavel=false)
	private String banco;
	
	@Campo(nome="Saldo",editavel=false)
	private double saldoAnterior;
	
	@Campo(nome="Valor",editavel=false)
	private double valor;
	
	@Campo(nome="Data",editavel=false)
	private String data;
	
	@Campo(nome="Juros",editavel=false)
	private double juros;
	
	@Campo(nome="desc.",editavel=false)
	private double descontos;
	
	public RepresentadorMovimento(Movimento m) {
		super(m);
		
		this.cod = m.getId();
		this.banco = m.getBanco().getPj().getNome();
		this.saldoAnterior = m.getSaldo();
		this.valor = m.getValor();
		this.data = new ConversorDate().paraString(m.getData());
		this.juros = m.getJuros();
		this.descontos = m.getDescontos();
		
	}

}