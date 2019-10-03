package br.com.afgtec.financeiro;

import br.com.agrofauna.conversores.ConversorDate;
import br.com.agrofauna.utilidades.Campo;
import br.com.agrofauna.utilidades.Representador;

public class RepresentadorConta extends Representador<Conta>{

	@Campo(nome="Cod",editavel=false)
	private int id;
	
	@Campo(nome="Responsavel",editavel=false)
	private String responsavel;
	
	
	
	@Campo(nome="Devido",editavel=false)
	private double devido;
	
	@Campo(nome="Pago",editavel=false)
	private double pago;
	
	@Campo(nome="Pendencia",editavel=false)
	private double pendencia;
	
	@Campo(nome="Data",editavel=false)
	private String data;
	
	@Campo(nome="Nota",editavel=false)
	private String nota;
	
	public RepresentadorConta(Conta o) {
		super(o);
		
		this.id = o.getVencimento().getNota().getId();
		this.responsavel = (o.getDestino()==null)?"Nota sem Responsavel":o.getDestino().getNome();
		this.devido = o.getVencimento().getValor();
		this.pendencia = o.getPendencia();
		this.pago = this.devido-this.pendencia;
		
		this.data = new ConversorDate().paraString(o.getVencimento().getData());
		
		this.nota = o.getVencimento().getNota().getNumero()+"";
		
	}

	
	
}
