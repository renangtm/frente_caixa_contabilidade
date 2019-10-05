package br.com.transportadora;

import br.com.utilidades.Campo;
import br.com.utilidades.Representador;

public class RepresentadorTransportadoraSimples extends Representador<Transportadora> {

	@Campo(nome = "Cod", editavel=false, ordem = "{{et}}.id")
	private int codigo;
	
	@Campo(nome = "Nome", editavel=false, ordem = "{{et}}.pj.nome")
	private String nome;
	
	@Campo(nome = "Cnpj", editavel=false, ordem = "{{et}}.pj.cnpj")
	private String cnpj;


	public RepresentadorTransportadoraSimples(Transportadora p) {
		super(p);

		this.codigo = p.getId();
		this.nome = p.getPj().getNome();
		this.cnpj = p.getPj().getCnpj();

	}

}
