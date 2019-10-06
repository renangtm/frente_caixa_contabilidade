package br.com.pessoa;

import br.com.utilidades.Campo;
import br.com.utilidades.Representador;

public class RepresentadorPessoaJuridica extends Representador<PessoaJuridica> {

	@Campo(nome = "Cod", editavel=false, ordem = "{{et}}.id")
	private int codigo;

	@Campo(nome = "Nome", editavel=false, ordem = "{{et}}.nome")
	private String nome;

	@Campo(nome = "Cnpj", editavel=false, ordem = "{{et}}.cnpj")
	private String cnpj;

	@Campo(nome = "Ie", editavel=false, ordem = "{{et}}.inscricao_estadual")
	private String ie;

	@Campo(nome = "Estado", editavel=false, ordem = "{{et}}.endereco.cidade.estado.sigla")
	private String estado;

	@Campo(nome = "Cidade", editavel=false, ordem = "{{et}}.endereco.cidade.nome")
	private String cidade;


	public RepresentadorPessoaJuridica(PessoaJuridica p) {
		super(p);

		this.codigo = p.getId();
		this.nome = p.getNome();
		this.cnpj = p.getCnpj();
		this.ie = p.getInscricao_estadual();
		this.estado = p.getEndereco().getCidade().getEstado().getSigla();
		this.cidade = p.getEndereco().getCidade().getNome();

	}

}
