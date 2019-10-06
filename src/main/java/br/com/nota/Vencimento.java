package br.com.nota;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import br.com.movimento_financeiro.Movimento;

@Entity
public class Vencimento {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private Calendar data;
	
	@Column
	private double valor;
	
	@ManyToOne(fetch=FetchType.LAZY,cascade={CascadeType.MERGE})
	@JoinColumn(name="id_nota")
	private Nota nota;
	
	@OneToMany(mappedBy="vencimento")
	private List<Movimento> movimentos;
	
	public Vencimento(){
		
		this.movimentos = new ArrayList<Movimento>();
		
	}
	
	
	
	public Nota getNota() {
		return nota;
	}



	public void setNota(Nota nota) {
		this.nota = nota;
	}



	public List<Movimento> getMovimentos() {
		return movimentos;
	}



	public void setMovimentos(List<Movimento> movimentos) {
		this.movimentos = movimentos;
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
