package br.com.venda;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.utilidades.Campo;
import br.com.utilidades.Representador;

public class RepresentadorProdutoVenda extends Representador<ProdutoVenda> {

	@Campo(nome = "codigo", editavel = false)
	private String codigo;

	@Campo(nome = "Nome", editavel = false)
	private String nome;

	@Campo(nome = "Qtde", editavel = false)
	private double quantidade;

	@Campo(nome = "UN", editavel = false)
	private String unidade;

	@Campo(nome = "Valor", editavel = false)
	private String valor;

	public RepresentadorProdutoVenda(ProdutoVenda p) {
		super(p);

		this.codigo = p.getProduto().getCodigo_barra();
		this.nome = p.getProduto().getNome();
		this.quantidade = p.getQuantidade();
		this.unidade = p.getTipoQuantidade().toString();
		this.valor = new BigDecimal(p.getValor()).setScale(2, RoundingMode.HALF_UP).toString()
				.replaceAll("\\.", ",");

	}

}
