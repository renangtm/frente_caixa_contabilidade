package br.com.produto;

import java.util.List;

import br.com.quantificacao.TipoQuantidade;

public class ProdutoRelatorio {
	
	private int id;
	
	private String codigo;
	
	private String nome;
	
	private double quantidade;
	
	private TipoQuantidade unidade;

	private List<MovimentoProduto> movimentos;

	private double valor;
	
	private double lucro;
	
	private double icms;
	
	public double getIcms() {
		return icms;
	}

	public void setIcms(double icms) {
		this.icms = icms;
	}

	public double getLucro() {
		return lucro;
	}

	public void setLucro(double lucro) {
		this.lucro = lucro;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public TipoQuantidade getUnidade() {
		return unidade;
	}

	public void setUnidade(TipoQuantidade unidade) {
		this.unidade = unidade;
	}

	public List<MovimentoProduto> getMovimentos() {
		return movimentos;
	}

	public void setMovimentos(List<MovimentoProduto> movimentos) {
		this.movimentos = movimentos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutoRelatorio other = (ProdutoRelatorio) obj;
		if (id != other.id)
			return false;
		return true;
	} 
	
	
	
}
