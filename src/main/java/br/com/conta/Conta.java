package br.com.conta;

import br.com.nota.Vencimento;
import br.com.pessoa.Pessoa;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destino == null) ? 0 : destino.hashCode());
		long temp;
		temp = Double.doubleToLongBits(pendencia);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((pessoa == null) ? 0 : pessoa.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((vencimento == null) ? 0 : vencimento.hashCode());
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
		Conta other = (Conta) obj;
		if (destino == null) {
			if (other.destino != null)
				return false;
		} else if (!destino.equals(other.destino))
			return false;
		if (Double.doubleToLongBits(pendencia) != Double.doubleToLongBits(other.pendencia))
			return false;
		if (pessoa == null) {
			if (other.pessoa != null)
				return false;
		} else if (!pessoa.equals(other.pessoa))
			return false;
		if (tipo != other.tipo)
			return false;
		if (vencimento == null) {
			if (other.vencimento != null)
				return false;
		} else if (!vencimento.equals(other.vencimento))
			return false;
		return true;
	}
	
	
	
}
