package br.com.afgtec.pessoa;

import br.com.agrofauna.utilidades.Campo;
import br.com.agrofauna.utilidades.Representador;

public class RepresentadorPessoaJuridicaSimples extends Representador<PessoaJuridica> {

	@Campo(nome = "Cod", editavel=false, ordem = "{{et}}.id")
	private int codigo;
	
	@Campo(nome = "Nome", editavel=false, ordem = "{{et}}.nome")
	private String nome;
	
	@Campo(nome = "Cnpj", editavel=false, ordem = "{{et}}.cnpj")
	private String cnpj;


	public RepresentadorPessoaJuridicaSimples(PessoaJuridica p) {
		super(p);

		this.codigo = p.getId();
		this.nome = p.getNome();
		this.cnpj = p.getCnpj();

	}

}
