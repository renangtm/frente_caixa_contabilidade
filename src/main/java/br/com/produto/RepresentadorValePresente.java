package br.com.produto;

import br.com.utilidades.Campo;
import br.com.utilidades.Representador;

public class RepresentadorValePresente extends Representador<ValePresente> {

	@Campo(nome = "Cod", editavel = false)
	private int cod;

	@Campo(nome = "Desc", editavel = false)
	private String descricao;

	@Campo(nome = "Qtd", editavel = false)
	private int quantidade;

	@Campo(nome = "Preco", editavel = false)
	private double preco;

	@Campo(nome = "Valor", editavel = false)
	private double valor;

	public RepresentadorValePresente(ValePresente v) {
		super(v);

		this.cod = v.getId();
		this.descricao = v.getNome();
		this.quantidade = (int) v.getQuantidade().getDisponivel();
		this.preco = v.getValor();
		this.valor = v.getValorVale();

	}

}
