package br.com.afgtec.notas;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.afgtec.produto.Produto;

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
	private double valor;
	
	@Column
	private double icms;
	
	@Column
	private double base_calculo;
	
	@Column
	private double cofins;
	
	@Column
	private double frete;
	
	@Column
	private double seguro;
	
	@Column
	private double desconto;
	
	@Column
	private boolean compoeValorTotal;
	
	@Column
	private double pis;
	
	@Column
	private double ipi;
	
	@Column
	private double icms_desonerado;
	
	@Column
	private double quantidade;
	
	@Column
	protected double influenciaEstoques;
	
	@Column
	private String observacoes;

	public String  getObservacoes(){
		
		return this.observacoes;
		
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

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getIcms() {
		return icms;
	}

	public void setIcms(double icms) {
		this.icms = icms;
	}

	public double getBase_calculo() {
		return base_calculo;
	}

	public void setBase_calculo(double base_calculo) {
		this.base_calculo = base_calculo;
	}

	public double getCofins() {
		return cofins;
	}

	public void setCofins(double cofins) {
		this.cofins = cofins;
	}

	public double getPis() {
		return pis;
	}

	public void setPis(double pis) {
		this.pis = pis;
	}

	public double getIpi() {
		return ipi;
	}

	public void setIpi(double ipi) {
		this.ipi = ipi;
	}

	public double getIcms_desonerado() {
		return icms_desonerado;
	}

	public void setIcms_desonerado(double icms_desonerado) {
		this.icms_desonerado = icms_desonerado;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}
	
	
	
}
