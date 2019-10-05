package br.com.transportadora;

import br.com.utilidades.Campo;
import br.com.utilidades.Representador;

public class RepresentadorTransportadora extends Representador<Transportadora> {

	@Campo(nome = "Cod", editavel=false, ordem = "{{et}}.pj.id")
	private int codigo;

	@Campo(nome = "Nome", editavel=false, ordem = "{{et}}.pj.nome")
	private String nome;

	@Campo(nome = "Cnpj", editavel=false, ordem = "{{et}}.pj.cnpj")
	private String cnpj;

	@Campo(nome = "Ie", editavel=false, ordem = "{{et}}.pj.inscricao_estadual")
	private String ie;

	@Campo(nome = "Estado", editavel=false, ordem = "{{et}}.pj.endereco.cidade.estado.sigla")
	private String estado;

	@Campo(nome = "Cidade", editavel=false, ordem = "{{et}}.pj.endereco.cidade.nome")
	private String cidade;


	public RepresentadorTransportadora(Transportadora p) {
		super(p);

		this.codigo = p.getPj().getId();
		this.nome = p.getPj().getNome();
		this.cnpj = p.getPj().getCnpj();
		this.ie = p.getPj().getInscricao_estadual();
		this.estado = p.getPj().getEndereco().getCidade().getEstado().getSigla();
		this.cidade = p.getPj().getEndereco().getCidade().getNome();

	}

}
