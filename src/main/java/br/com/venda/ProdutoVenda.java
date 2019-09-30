package br.com.venda;

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
import javax.persistence.Table;

import br.com.afgtec.produto.Produto;
import br.com.afgtec.unidades.TipoQuantidade;

@Entity
@Table(name="produto_venda")
public class ProdutoVenda {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_produto")
	private Produto produto;

	@Column
	protected double reservaInfluenciada;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	protected TipoQuantidade tipoReservaInfluenciada;
	
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	protected TipoQuantidade tipoQuantidade;
	
	@Column
	private double quantidade;

	@Column
	private double valor;

	@ManyToOne
	@JoinColumn(name="id_venda")
	private Venda venda;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		
		this.produto = produto;
		this.tipoQuantidade = produto.getEstoque().getTipo();
		this.tipoReservaInfluenciada = produto.getEstoque().getTipo();
		
		
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

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

}
