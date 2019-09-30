package br.com.afgtec.notas;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.afgtec.financeiro.Movimento;

@Entity
public class Vencimento {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private Calendar data;
	
	@Column
	private double valor;
	
	@OneToMany(mappedBy="vencimento")
	private List<Movimento> movimentos;
	
	public Vencimento(){
		
		this.movimentos = new ArrayList<Movimento>();
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getPendencia(){
		
		return this.valor-this.movimentos.stream().mapToDouble(m->m.getValor()).sum();
		
	}
	
}
