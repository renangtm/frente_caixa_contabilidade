package br.com.empresa;

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

import br.com.codigo_barra.PadraoCodigo;
import br.com.entidades.nota.Nota;
import br.com.pessoa.Pessoa;
import br.com.pessoa.PessoaJuridica;
import br.com.produto.Produto;
import br.com.venda.Venda;

@Entity
public class Empresa{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@OneToMany(fetch=FetchType.LAZY,mappedBy="empresa")
	private List<PadraoCodigo> padroesCodigo;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="empresa")
	private List<Pessoa> pessoas;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="empresa")
	private List<Produto> produtos;

	@OneToMany(fetch=FetchType.LAZY,mappedBy="empresa")
	private List<Venda> vendas;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="empresa")
	private List<Nota> notas;
	
	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="id_parametros")
	private ParametrosEmissao parametrosEmissao;
	
	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="id_logo")
	private Logo logo;
	
	@OneToOne(fetch=FetchType.EAGER,cascade= {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="id_pessoa")
	private PessoaJuridica pj;
	
	public Empresa(){
		
		this.pj = new PessoaJuridica();
		this.logo = new Logo();
		
	}
	


	public ParametrosEmissao getParametrosEmissao() {
		return parametrosEmissao;
	}



	public void setParametrosEmissao(ParametrosEmissao parametrosEmissao) {
		this.parametrosEmissao = parametrosEmissao;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PessoaJuridica getPj() {
		return pj;
	}

	public void setPj(PessoaJuridica pj) {
		this.pj = pj;
	}

	public List<Nota> getNotas() {
		return notas;
	}

	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}


	public Logo getLogo() {
		return logo;
	}

	public void setLogo(Logo logo) {
		this.logo = logo;
	}


	public List<Venda> getVendas() {
		return vendas;
	}

	public List<PadraoCodigo> getPadroesCodigo() {
		return padroesCodigo;
	}

	public void setPadroesCodigo(List<PadraoCodigo> padroesCodigo) {
		this.padroesCodigo = padroesCodigo;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	
}
