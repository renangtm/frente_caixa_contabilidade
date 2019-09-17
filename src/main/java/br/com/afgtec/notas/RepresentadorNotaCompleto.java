package br.com.afgtec.notas;

import br.com.agrofauna.utilidades.Campo;
import br.com.agrofauna.utilidades.Representador;

public class RepresentadorNotaCompleto extends Representador<Nota>{

	@Campo(nome="Codigo",ordem="{{et}}.id")
	private String codigo;
	
	@Campo(nome="Emitente",ordem="{{et}}.pessoa.nome")
	private String pessoa;
	
	@Campo(nome="Transportadora",editavel=false,ordem="{{et}}.transportadora.nome")
	private String transportadora;
	
	@Campo(nome="Numero",ordem="{{et}}.numero")
	private int numero;
	
	@Campo(nome="Total")
	private double total;
	
	@Campo(nome="Serie",ordem="{{et}}.serie")
	private int serie;
	
	@Campo(nome="Status",ordem="{{et}}.status")
	private String status;
	
	
	public RepresentadorNotaCompleto(Nota n) {
		super(n);
		
		this.codigo = n.getId()+"";
		this.pessoa = n.getEmitente().getRazao_social();
		this.transportadora= n.getTransportadora().getRazao_social();
		this.numero = n.getNumero();
		this.total = n.getTotal();
		this.serie = n.getSerie();
		this.status = n.getStatus().name();
		
	}
	
	@Override
	public void atualizar(){
	
	}

}
