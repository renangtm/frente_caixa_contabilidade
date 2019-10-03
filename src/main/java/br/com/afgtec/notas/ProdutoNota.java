package br.com.afgtec.notas;

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

import br.com.afgtec.impostos.Imposto;
import br.com.afgtec.produto.Produto;
import br.com.afgtec.unidades.TipoQuantidade;

@Entity
public class ProdutoNota {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_nota")
	private Nota nota;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_produto")
	private Produto produto;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_cfop")
	private CFOP cfop;

	@Column
	private double quantidade;     
	
	@Column
	private double valor;
	
	@Column
	private double frete;
	
	@Column
	private double seguro;
	
	@Column
	private double desconto;
	
	@Column
	private double outro;
	
	@Column
	private boolean compoeValorTotal;
	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="id_imposto")
	private Imposto imposto;
	
	@Column
	protected double influenciaEstoques;
	
	@Enumerated(EnumType.ORDINAL)
	@Column
	protected TipoQuantidade tipoInfluenciaEstoque;
	
	@Column
	private String observacoes;

	public String  getObservacoes(){
		
		return this.observacoes;
		
	}
	
	

	public double getInfluenciaEstoques() {
		return influenciaEstoques;
	}



	public TipoQuantidade getTipoInfluenciaEstoque() {
		return tipoInfluenciaEstoque;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Nota getNota() {
		return nota;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public CFOP getCfop() {
		return cfop;
	}

	public void setCfop(CFOP cfop) {
		this.cfop = cfop;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getFrete() {
		return frete;
	}

	public void setFrete(double frete) {
		this.frete = frete;
	}

	public double getSeguro() {
		return seguro;
	}

	public void setSeguro(double seguro) {
		this.seguro = seguro;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public double getOutro() {
		return outro;
	}

	public void setOutro(double outro) {
		this.outro = outro;
	}

	public boolean isCompoeValorTotal() {
		return compoeValorTotal;
	}

	public void setCompoeValorTotal(boolean compoeValorTotal) {
		this.compoeValorTotal = compoeValorTotal;
	}

	public Imposto getImposto() {
		return imposto;
	}

	public void setImposto(Imposto imposto) {
		this.imposto = imposto;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	
}
