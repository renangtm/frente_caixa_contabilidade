package br.com.afgtec.financeiro;

import br.com.afgtec.notas.Vencimento;
import br.com.afgtec.pessoa.Pessoa;

public class Conta {

	private TipoConta tipo;
	
	private Pessoa destino;
	
	private Pessoa pessoa;
	
	private Vencimento vencimento;

	private double pendencia;
	
	
	
	public double getPendencia() {
		return pendencia;
	}

	public void setPendencia(double pendencia) {
		this.pendencia = pendencia;
	}

	public TipoConta getTipo() {
		return tipo;
	}

	public void setTipo(TipoConta tipo) {
		this.tipo = tipo;
	}

	public Pessoa getDestino() {
		return destino;
	}

	public void setDestino(Pessoa destino) {
		this.destino = destino;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Vencimento getVencimento() {
		return vencimento;
	}

	public void setVencimento(Vencimento vencimento) {
		this.vencimento = vencimento;
	}
	
	
	
}
