package br.com.impressao;

import java.util.Date;

import br.com.conta.Conta;
import br.com.conta.TipoConta;

public class ContaRelatorio {

	private Date vencimento;

	private String pessoa;

	private String destino;

	private int nota;

	private double pendencia;

	private double total;

	private TipoConta tipo;
	
	public ContaRelatorio(Conta conta) {

		this.vencimento = conta.getVencimento().getData().getTime();
		this.pessoa = conta.getPessoa().getNome();

		if (conta.getDestino() != null) {

			this.destino = conta.getDestino().getNome();

		}

		this.nota = conta.getVencimento().getNota().getNumero();

		this.pendencia = conta.getPendencia();

		this.total = conta.getVencimento().getValor();

		this.tipo = conta.getTipo();
		
	}
	
	

	public TipoConta getTipo() {
		return tipo;
	}



	public void setTipo(TipoConta tipo) {
		this.tipo = tipo;
	}



	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public String getPessoa() {
		return pessoa;
	}

	public void setPessoa(String pessoa) {
		this.pessoa = pessoa;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public double getPendencia() {
		return pendencia;
	}

	public void setPendencia(double pendencia) {
		this.pendencia = pendencia;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
