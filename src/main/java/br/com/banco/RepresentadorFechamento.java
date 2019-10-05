package br.com.banco;

import br.com.conversores.ConversorDate;
import br.com.utilidades.Campo;
import br.com.utilidades.Representador;

public class RepresentadorFechamento extends Representador<Fechamento>{

	@Campo(nome="banco",editavel=false)
	private String banco;

	@Campo(nome="data",editavel=false)
	private String data;
	
	@Campo(nome="valor",editavel=false)
	private double valor;
	
	public RepresentadorFechamento(Fechamento f) {
		super(f);
		
		this.banco = f.getBanco().getPj().getNome();
		this.data = new ConversorDate().paraString(f.getData());
		this.valor = f.getValor();
		
	}
	
}
