package br.com.produto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.empresa.Empresa;
import br.com.utilidades.Campo;

@Entity
public class Tipo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Campo(nome="Cod")
	private int id;
	
	@Column
	@Campo(nome="Nome")
	private String nome;
	
	@ManyToOne
	private Empresa empresa;
	

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Tipo other = (Tipo) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
