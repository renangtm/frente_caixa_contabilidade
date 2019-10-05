package br.com.afgtec.pessoa;

import br.com.utilidades.Campo;
import br.com.utilidades.Representador;

public class RepresentadorPessoa extends Representador<Pessoa> {

	@Campo(nome = "Cod", editavel=false, ordem = "{{et}}.id")
	private int codigo;

	@Campo(nome = "Nome", editavel=false, ordem = "{{et}}.nome")
	private String nome;
	
	@Campo(nome = "CNPJ/CPF", editavel=false)
	private String cnpjCpf;

	@Campo(nome = "IE/RG", editavel=false)
	private String ieRg;
	
	@Campo(nome="Estado",editavel=false,ordem="{{et}}.endereco.cidade.estado.sigla")
	private String estado;
	
	@Campo(nome="Cidade",editavel=false,ordem="{{et}}.endereco.cidade.nome")
	private String cidade;

	public RepresentadorPessoa(Pessoa p) {
		super(p);

		this.codigo = p.getId();
		this.nome = p.getNome();

		try {
		
			PessoaFisica pf = (PessoaFisica)p;
			
			this.cnpjCpf = pf.getCpf();
			this.ieRg = pf.getRg();
			
		}catch(Exception ex) {
			
			PessoaJuridica pj = (PessoaJuridica)p;
			
			this.cnpjCpf = pj.getCnpj();
			this.ieRg = pj.getInscricao_estadual();
			
		}
		
		this.estado = p.getEndereco().getCidade().getEstado().getSigla();
		this.cidade = p.getEndereco().getCidade().getNome();
		
	}

}
