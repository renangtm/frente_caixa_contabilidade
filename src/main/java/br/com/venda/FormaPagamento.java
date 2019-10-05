package br.com.venda;

public interface FormaPagamento {
	
	public String cnpjCredenciadoraCartao();
	
	public int codigoCredenciadoraCartao();
	
	public br.com.entidades.nota.FormaPagamentoNota getFormaPagamento();
	
	public String getNome();
	
	
}
