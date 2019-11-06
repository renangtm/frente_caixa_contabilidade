package br.com.produto;

import br.com.utilidades.Campo;
import br.com.utilidades.Representador;

public class RepresentadorProdutoCompleto extends Representador<Produto> {

	@Campo(nome = "Codigo", editavel = true, ordem = "{{et}}.codigo_barra")
	private String codigo;

	@Campo(nome = "Nome", editavel = true, ordem = "{{et}}.nome")
	private String nome;

	@Campo(nome = "Estoque", editavel = true, ordem = "{{et}}.estoque.quantidade")
	private double estoque;

	@Campo(nome = "Disponivel", editavel = true, ordem = "{{et}}.estoque.disponivel")
	private double disponivel;

	@Campo(nome = "Valor (R$)", editavel = true, ordem = "{{et}}.valor")
	private double valor;

	@Campo(nome = "Tipo", editavel = false, ordem = "{{et}}.tipoProduto.nome")
	private String tipo;

	@Campo(nome = "Loja Virtual", editavel = false, ordem = "{{et}}.apareceLoja")
	private String loja;

	public RepresentadorProdutoCompleto(Produto p) {
		super(p);

		this.codigo = p.getCodigo_barra();
		this.nome = p.getNome();
		this.estoque = p.getEstoque().getQuantidade();
		this.disponivel = p.getEstoque().getDisponivel();
		this.valor = p.getValor();

		if (p.getTipoProduto() != null)
			this.tipo = p.getTipoProduto().getNome();
		else
			this.tipo = "SEM TIPO";

		this.loja = p.isApareceLoja() ? "SIM" : "NAO";

	}

	@Override
	public void atualizar() {

		this.objetoOriginal.setCodigo_barra(this.codigo);
		this.objetoOriginal.setNome(this.nome);
		try {
			this.objetoOriginal.getEstoque().setQuantidades(this.disponivel, this.estoque);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		this.objetoOriginal.setValor(this.valor);

	}

}
