package br.com.impressao;

public class ProdutoSATModelo1 {

	private String codigo;
	
	private String nome;
	
	private double quantidade;
	
	private String unidade;
	
	private double icms;
	
	private double valor;
	
	private double valor_unitario;
	
	private double desconto;

	public String getCodigo() {
		return codigo;
	}
	
	

	public double getValor_unitario() {
		return valor_unitario;
	}



	public void setValor_unitario(double valor_unitario) {
		this.valor_unitario = valor_unitario;
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

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public double getIcms() {
		return icms;
	}

	public void setIcms(double icms) {
		this.icms = icms;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}
	
	
	
}
