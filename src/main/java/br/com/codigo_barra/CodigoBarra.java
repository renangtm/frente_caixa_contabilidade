package br.com.codigo_barra;

import java.util.List;
import java.util.stream.Collectors;

import br.com.produto.Produto;
import br.com.produto.ProdutoService;

public class CodigoBarra {

	private String codigoBarra;

	private double quantidade;
	
	private ProdutoService ps;

	private Produto produto;
	
	public CodigoBarra(String codigo,ProdutoService ps) {

		this.ps = ps;

		this.codigoBarra = codigo;
		this.quantidade = 1;
		
		this.produto = this.ps.getProduto(this.codigoBarra);
		
		if(this.produto == null){
			
			throw new RuntimeException("Produto inexistente");
			
		}
		
	}

	public CodigoBarra(String codigo, List<PadraoCodigo> padroes,ProdutoService ps) {
		
		this.ps = ps;
		
		try{
			
			PadraoCodigo padrao = padroes.get(padroes.stream().map(x -> codigo.indexOf(x.getDigitoInicial()) == 0)
					.collect(Collectors.toList()).indexOf(true));
			
			if(codigo.length() != padrao.getTamanhoCodigo()){
				
				throw new RuntimeException("Padrao invalido");
				
			}
			
			this.codigoBarra = codigo.substring(padrao.getDigitoInicial().length(),padrao.getDigitosCodigoProduto());
			
			double quantidade = Double.parseDouble(codigo.substring(padrao.getDigitoInicial().length()+padrao.getDigitosCodigoProduto(),padrao.getDigitosUnidade()))/Math.pow(10, padrao.getCasasDecimais());
			
			this.produto = this.ps.getProduto(this.codigoBarra);
			
			if(this.produto == null){
				
				throw new RuntimeException("Produto inexistente");
				
			}
			
			this.quantidade = padrao.getTipo().para(this.produto.getEstoque().getTipo(), produto, quantidade);
			
		}catch(Exception ex){
			
			this.codigoBarra = codigo;
			this.quantidade = 1;
			
			this.produto = this.ps.getProduto(this.codigoBarra);
			
			if(this.produto == null){
				
				throw new RuntimeException("Produto inexistente");
				
			}
			
		}

	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

}
