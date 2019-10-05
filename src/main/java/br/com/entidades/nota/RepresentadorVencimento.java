package br.com.entidades.nota;

import java.sql.Date;

import com.ibm.icu.text.NumberFormat;

import br.com.utilidades.Campo;
import br.com.utilidades.Representador;

public class RepresentadorVencimento extends Representador<Vencimento>{

	@Campo(nome="Codigo",editavel=false)
	private String codigo;
	
	@Campo(nome="Data",editavel=false)
	private Date data;
	
	@Campo(nome="Valor",editavel=false)
	private String valor;
	
	@Campo(nome="Pendente",editavel=false)
	private String baixa;
	
	public RepresentadorVencimento(Vencimento v) {
		super(v);
		
		this.codigo = v.getId()+"";
		this.data = new java.sql.Date(v.getData().getTimeInMillis());
		this.valor = v.getValor()+"";
		
		double p = v.getPendencia();
		
		if(p == 0){
			
			this.baixa = "OK";
			
		}else{
			
			this.baixa = "R$ "+NumberFormat.getCurrencyInstance().format(p);
			
		}
		
	}
	
	@Override
	public void atualizar(){
	
	}

}
