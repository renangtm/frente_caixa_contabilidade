package br.com.fornecedor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.pessoa.PessoaJuridica;

@Entity
public class Fornecedor {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.PERSIST)
	@JoinColumn(name="id_pessoa")
	private PessoaJuridica pj;
	
	@OneToMany(mappedBy="fornecedor")
	private List<FornecedorXCodigo> codigosEntrada;

	public Fornecedor(){
		
		this.codigosEntrada = new ArrayList<FornecedorXCodigo>();
		
	}
	
	public List<FornecedorXCodigo> getCodigosEntrada() {
		return codigosEntrada;
	}

	public void setCodigosEntrada(List<FornecedorXCodigo> codigosEntrada) {
		this.codigosEntrada = codigosEntrada;
	}

	public PessoaJuridica getPj() {
		return pj;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PessoaJuridica getPJ() {
		return pj;
	}

	public void setPj(PessoaJuridica pessoa) {
		this.pj = pessoa;
	}
	
	
	
}
