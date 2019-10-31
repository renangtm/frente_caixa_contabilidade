package br.com.impressao;

import java.util.ArrayList;
import java.util.List;

public class ItemSangria {

	private String tipo;
	
	private double total;
	
	private List<MovimentoSangria> movimentos;

	public ItemSangria(){
		
		this.movimentos = new ArrayList<MovimentoSangria>();
		
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public List<MovimentoSangria> getMovimentos() {
		return movimentos;
	}

	public void setMovimentos(List<MovimentoSangria> movimentos) {
		this.movimentos = movimentos;
	}
	
	
	
}
