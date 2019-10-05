package br.com.entidades.nota;

import br.com.utilidades.Campo;
import br.com.utilidades.Representador;

public class RepresentadorNotaSimples extends Representador<Nota>{

	@Campo(nome="Numero",ordem="{{et}}.numero")
	private int numero;
	
	@Campo(nome="Emitente",ordem="{{et}}.emitente.nome")
	private String emitente;
	
	@Campo(nome="Destinatario",ordem="{{et}}.destinatario.nome")
	private String destinatario;
	
	@Campo(nome="Status",ordem="{{et}}.status")
	private String status;
	
	
	public RepresentadorNotaSimples(Nota n) {
		super(n);
		
		this.numero = n.getNumero();
		this.status = n.getStatus().name();
		this.emitente = n.getEmitente().getNome();
		this.destinatario = n.getDestinatario().getNome();
		
	}
	
	@Override
	public void atualizar(){
	
	}

}
