package br.com.pessoa;

import br.com.conversores.ConversorDate;
import br.com.utilidades.Campo;
import br.com.utilidades.Representador;

public class RepresentadorPessoaFisica extends Representador<PessoaFisica> {

	@Campo(nome = "Cod", editavel=false, ordem = "{{et}}.id")
	private int codigo;

	@Campo(nome = "Nome", editavel=false, ordem = "{{et}}.nome")
	private String nome;

	@Campo(nome = "Cpf", editavel=false, ordem = "{{et}}.cpf")
	private String cpf;

	@Campo(nome = "Rg", editavel=false, ordem = "{{et}}.rg")
	private String rg;

	@Campo(nome = "Estado", editavel=false, ordem = "{{et}}.endereco.cidade.estado.sigla")
	private String estado;

	@Campo(nome = "Cidade", editavel=false, ordem = "{{et}}.endereco.cidade.nome")
	private String cidade;

	@Campo(nome = "Data Nasc", editavel=false, ordem = "{{et}}.data_nascimento")
	private String dataNascimento;

	public RepresentadorPessoaFisica(PessoaFisica p) {
		super(p);

		this.codigo = p.getId();
		this.nome = p.getNome();
		this.cpf = p.getCpf();
		this.rg = p.getRg();
		this.estado = p.getEndereco().getCidade().getEstado().getSigla();
		this.cidade = p.getEndereco().getCidade().getNome();
		this.dataNascimento = new ConversorDate().paraString(p.getData_nascimento());

	}

}
