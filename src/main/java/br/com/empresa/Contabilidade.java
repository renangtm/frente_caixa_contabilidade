package br.com.empresa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Contabilidade extends Empresa{

	@OneToMany(mappedBy="contabilidade")
	private List<Empresa> clientes;
	
	public Contabilidade(){
		
		super();
		
		this.clientes = new ArrayList<Empresa>();
		
	}

	public List<Empresa> getClientes() {
		return clientes;
	}

	public void setClientes(List<Empresa> clientes) {
		this.clientes = clientes;
	}
	
	
	
}
