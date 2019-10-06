package br.com.pessoa;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.empresa.Empresa;
import br.com.entidades.ncm.NCM;
import br.com.produto.Categoria;
import br.com.produto.Estoque;
import br.com.quantificacao.ItemQuantificavel;
import br.com.quantificacao.UnidadePeso;
import br.com.quantificacao.UnidadeVolume;
import br.com.utilidades.Campo;

@Entity
@Table(name="produtos")
public class Produto implements ItemQuantificavel{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	@Campo(nome="Codigo",editavel=false,tamanho=20)
	private String codigo_barra;
	
	@Column
	@Campo(nome="Nome",editavel=false,tamanho=80)
	private String nome;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_ncm")
	@Fetch(FetchMode.JOIN)
	private NCM ncm;
	
	@Column
	private double custo;
	
	@Column
	private double valor;
	
	@Column
	private boolean apareceLoja;
	
	@Column
	private double porcentagemLoja;
	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="id_quantidade")
	@Fetch(FetchMode.JOIN)
	private Estoque estoque;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_empresa")
	@Fetch(FetchMode.JOIN)
	private Empresa empresa;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private UnidadePeso unidade_peso;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private UnidadeVolume unidade_volume;
	
	@Column
	private double peso;

	@Column
	private double volume;
	
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="id_categoria")
	private Categoria categoria;
	
	@Column
	private String imagem;
	
	public NCM getNcm() {
		return ncm;
	}

	public void setNcm(NCM ncm) {
		this.ncm = ncm;
	}

	public String getCodigo_barra() {
		return codigo_barra;
	}
	
	public void setImagem(String img){
		
		this.imagem = img;
		
	}
	
	public String getImagem(){
		
		return this.imagem;
		
	}
	
	

	public boolean isApareceLoja() {
		return apareceLoja;
	}

	public void setApareceLoja(boolean apareceLoja) {
		this.apareceLoja = apareceLoja;
	}

	public double getPorcentagemLoja() {
		return porcentagemLoja;
	}

	public void setPorcentagemLoja(double porcentagemLoja) {
		this.porcentagemLoja = porcentagemLoja;
	}

	public void setCodigo_barra(String codigo_barra) {
		this.codigo_barra = codigo_barra;
	}

	public Estoque getQuantidade() {
		return estoque;
	}

	public void setQuantidade(Estoque quantidade) {
		this.estoque = quantidade;
	}

	public UnidadePeso getUnidade_peso() {
		return unidade_peso;
	}

	public void setUnidade_peso(UnidadePeso unidade_peso) {
		this.unidade_peso = unidade_peso;
	}

	
	public UnidadeVolume getUnidade_volume() {
		return unidade_volume;
	}

	public void setUnidade_volume(UnidadeVolume unidade_volume) {
		this.unidade_volume = unidade_volume;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public double getPeso() {
		
		if(peso == 0)
			return 1;
		
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getVolume() {
		
		if(this.volume == 0) {
			return 1;
		}
		
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public UnidadeVolume getUnidadeVolume() {
		return unidade_volume;
	}

	public void setUnidadeVolume(UnidadeVolume unidade_volume) {
		this.unidade_volume = unidade_volume;
	}

	public UnidadePeso getUnidadePeso() {
		return unidade_peso;
	}

	public void setUnidadePeso(UnidadePeso unidade) {
		this.unidade_peso = unidade;
	}
	
	public Produto(){
		
		this.estoque = new Estoque();
		this.unidade_peso = UnidadePeso.GR;
		this.unidade_volume = UnidadeVolume.CM3;
		this.categoria = new Categoria();
		
	}

	
	
	public double getCusto() {
		return custo;
	}

	public void setCusto(double custo) {
		this.custo = custo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque quantidade) {
		this.estoque = quantidade;
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

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	
	
}
