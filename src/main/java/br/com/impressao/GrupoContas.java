package br.com.impressao;

import java.util.Date;
import java.util.List;

import br.com.conta.TipoConta;

public class GrupoContas {

	private Date data;

	private TipoConta tipo;

	private List<ContaRelatorio> contas;

	private double total = 0;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<ContaRelatorio> getContas() {
		return contas;
	}

	public void setContas(List<ContaRelatorio> contas) {
		this.contas = contas;
	}

	public TipoConta getTipo() {
		return tipo;
	}

	public void setTipo(TipoConta tipo) {
		this.tipo = tipo;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
