package br.com.empresa;

import br.com.utilidades.Campo;
import br.com.utilidades.Representador;

public class RepresentadorContabilidade extends Representador<Contabilidade>{

	@Campo(nome="Cod",editavel=false)
	private int id;
	
	@Campo(nome="Nome",editavel=false)
	private String nome;
	
	@Campo(nome="CNPJ",editavel=false)
	private String cnpj;
	
	@Campo(nome="Estado",editavel=false)
	private String estado;
	
	@Campo(nome="Cidade",editavel=false)
	private String cidade;
	
	public RepresentadorContabilidade(Contabilidade c) {
		
		super(c);
		this.id = c.getId();
		this.nome = c.getPj().getNome();
		this.cnpj = c.getPj().getCnpj();
		this.estado = c.getPj().getEndereco().getCidade().getEstado().getSigla();
		this.cidade = c.getPj().getEndereco().getCidade().getNome();
		
	}

	
	
}
