package br.com.afgtec.notas;

import java.sql.Date;

import br.com.agrofauna.utilidades.Campo;
import br.com.agrofauna.utilidades.Representador;

public class RepresentadorVencimento extends Representador<Vencimento>{

	@Campo(nome="Codigo",editavel=false)
	private String codigo;
	
	@Campo(nome="Data",editavel=false)
	private Date data;
	
	@Campo(nome="Valor",editavel=false)
	private String valor;
	
	
	
	public RepresentadorVencimento(Vencimento v) {
		super(v);
		
		this.codigo = v.getId()+"";
		this.data = new java.sql.Date(v.getData().getTimeInMillis());
		this.valor = v.getValor()+"";
		
	}
	
	@Override
	public void atualizar(){
	
	}

}
