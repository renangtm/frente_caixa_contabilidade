package br.com.afgtec.notas;

import br.com.agrofauna.utilidades.Campo;
import br.com.agrofauna.utilidades.Representador;

public class RepresentadorNotaCompleto extends Representador<Nota>{

	@Campo(nome="Codigo",ordem="{{et}}.id")
	private String codigo;
	
	@Campo(nome="Emitente",ordem="{{et}}.pessoa.nome")
	private String pessoa;
	
	@Campo(nome="Transportadora",editavel=false,ordem="{{et}}.transportadora.pj.nome")
	private String transportadora;
	
	@Campo(nome="Destinatario",editavel=false,ordem="{{et}}.destinatario.nome")
	private String destinatario;
	
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
		this.pessoa = n.getEmitente().getNome();
		
		this.transportadora = "";
		this.destinatario = "";
		
		if(n.getTransportadora() != null) {
			this.transportadora= n.getTransportadora().getPj().getNome();
		}
		if(n.getDestinatario() != null) {
			this.destinatario = n.getDestinatario().getNome();
		}
		this.numero = n.getNumero();
		this.total = n.getValorTotalNota();
		this.serie = n.getSerie();
		this.status = n.getStatus().name();
		
		if(this.transportadora == null) {

			this.transportadora = "Sem transportadora";
			
		}else if(this.transportadora.isEmpty()) {
			
			this.transportadora = "Sem transportadora";
			
		}
		
		if(this.destinatario == null) {

			this.destinatario = "Sem destinatario";
			
		}else if(this.destinatario.isEmpty()) {
			
			this.destinatario = "Sem destinatario";
			
		}
		
	}
	
	@Override
	public void atualizar(){
	
	}

}
