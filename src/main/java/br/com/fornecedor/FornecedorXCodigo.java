package br.com.fornecedor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.produto.Produto;
import br.com.quantificacao.TipoQuantidade;

@Entity
public class FornecedorXCodigo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private Fornecedor fornecedor;

	@Column
	private int codigoFornecedor;

	@Enumerated(EnumType.STRING)
	@Column
	private TipoQuantidade tp;

	@ManyToOne
	private Produto produto;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public int getCodigoFornecedor() {
		return codigoFornecedor;
	}

	public void setCodigoFornecedor(int codigoFornecedor) {
		this.codigoFornecedor = codigoFornecedor;
	}

	public TipoQuantidade getTp() {
		return tp;
	}

	public void setTp(TipoQuantidade tp) {
		this.tp = tp;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
