package br.com.entidades;

public interface FormaPagamento {
	
	public String cnpjCredenciadoraCartao();
	
	public int codigoCredenciadoraCartao();
	
	public br.com.afgtec.notas.FormaPagamento getFormaPagamento();
	
	public String getNome();
	
	
}
