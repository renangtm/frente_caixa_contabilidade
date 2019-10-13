package br.com.pessoa;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.cliente.Cliente;
import br.com.empresa.Empresa;
import br.com.endereco.Endereco;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String nome;
	
	@Column
	private String telefone;
	
	@Column
	private String email;
	
	@Column
	private String whatsapp;
	
	@Column
	private String skype;
	
	@OneToOne(mappedBy="pessoa",fetch=FetchType.LAZY,cascade={CascadeType.MERGE,CascadeType.PERSIST})
	private Cliente cliente;
	
	@OneToOne(fetch=FetchType.EAGER,cascade={CascadeType.MERGE,CascadeType.PERSIST})
	@JoinColumn(name="id_empresa")
	private Empresa empresa;
	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="id_endereco")
	private Endereco endereco;
	
	public Pessoa() {
		
	}
	
	

	public Cliente getCliente() {
		return cliente;
	}



	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}



	public Empresa getEmpresa() {
		return empresa;
	}



	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}



	public String getWhatsapp() {
		return whatsapp;
	}



	public void setWhatsapp(String whatsapp) {
		this.whatsapp = whatsapp;
	}



	public String getSkype() {
		return skype;
	}



	public void setSkype(String skype) {
		this.skype = skype;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	
	
}
