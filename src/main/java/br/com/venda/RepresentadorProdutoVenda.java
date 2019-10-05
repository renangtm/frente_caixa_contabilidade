package br.com.venda;

import br.com.utilidades.Campo;
import br.com.utilidades.Representador;

public class RepresentadorProdutoVenda extends Representador<ProdutoVenda>{

	@Campo(nome="codigo",editavel=false)
	private String codigo;
	
	@Campo(nome="Nome",editavel=false)
	private String nome;
	
	@Campo(nome="Qtde",editavel=false)
	private double quantidade;
	
	@Campo(nome="UN",editavel=false)
	private String unidade;
	
	@Campo(nome="Valor",editavel=false)
	private double valor;
	
	
	
	
	public RepresentadorProdutoVenda(ProdutoVenda p) {
		super(p);
		
		this.codigo = p.getProduto().getCodigo_barra();
		this.nome = p.getProduto().getNome();
		this.quantidade = p.getQuantidade();
		this.unidade = p.getTipoQuantidade().toString();
		this.valor = p.getValor();
		
	}

	
	
}
