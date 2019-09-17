package br.com.afgtec.pessoa;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.afgtec.notas.Nota;
import br.com.afgtec.produto.Categoria;
import br.com.afgtec.produto.Produto;
import br.com.codigo.PadraoCodigo;
import br.com.entidades.Logo;
import br.com.venda.Venda;

@Entity
public class Empresa extends PessoaJuridica{
	

	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="id_empresa")
	private List<PadraoCodigo> padroesCodigo;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="id_empresa")
	private List<Usuario> usuarios;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="id_empresa")
	private List<Produto> produtos;

	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="id_empresa")
	private List<Venda> vendas;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="id_empresa")
	private List<Categoria> categorias;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="id_destinatario")
	private List<Nota> notas;
	
	@OneToOne
	@JoinColumn(name="id_logo")
	private Logo logo;
	
	public List<Nota> getNotas() {
		return notas;
	}

	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
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

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	
}
