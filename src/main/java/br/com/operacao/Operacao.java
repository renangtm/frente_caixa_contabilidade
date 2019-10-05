package br.com.operacao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.agrofauna.utilidades.Campo;

@Entity
public class Operacao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Campo(nome="Cod",editavel=false,ordem="{{et}}.id")
	private int id;
	
	@Column
	@Campo(nome="Credito",editavel=false,ordem="{{et}}.credito")
	private boolean credito;
	
	@Column
	@Campo(nome="Nome",editavel=false,ordem="{{et}}.nome")
	private String nome;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isCredito() {
		return credito;
	}

	public void setCredito(boolean credito) {
		this.credito = credito;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		
		return this.nome;
		
	}
	
}
