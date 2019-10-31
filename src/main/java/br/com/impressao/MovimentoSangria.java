package br.com.impressao;

import java.util.Date;

import br.com.caixa.Reposicao;
import br.com.movimento_financeiro.Movimento;

public class MovimentoSangria {
	
	public MovimentoSangria(Reposicao r){
		
		this.data = r.getMomento().getTime();
		this.pessoa = "------";
		this.valor = r.getValor();
		
	}
	
	public MovimentoSangria(Movimento m){
		
		this.data = m.getData().getTime();
		this.pessoa = m.getVencimento().getNota().getDestinatario() == null?"---------":m.getVencimento().getNota().getDestinatario().getNome();
		this.valor = m.getValor();
		
	}

	private Date data;
	
	private String pessoa;
	
	private double valor;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getPessoa() {
		return pessoa;
	}

	public void setPessoa(String pessoa) {
		this.pessoa = pessoa;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
	
	
}
