package br.com.venda;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.afgtec.notas.Nota;
import br.com.afgtec.pessoa.Empresa;
import br.com.afgtec.usuario.Usuario;

@Entity
@Table(name="vendas")
public class Venda {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar data;
	
	@ManyToOne
	@JoinColumn(name="id_operador")
	private Usuario operador;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="id_venda")
	private List<Nota> notas;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private StatusVenda status;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="id_venda")
	private List<ProdutoVenda> produtos;
	
	@ManyToOne
	@JoinColumn(name="id_empresa")
	private Empresa empresa;
	
	public Venda() {
		
		this.produtos = new ArrayList<ProdutoVenda>();
		
	}
	

	public List<Nota> getNotas() {
		return notas;
	}

	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}



	public double getTotal() {
		
		return this.produtos.stream().map(x->x.getQuantidade()*x.getValor()).reduce(0.0, (a,b)->a+b);
		
	}
	
	public List<ProdutoVenda> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoVenda> produtos) {
		this.produtos = produtos;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Usuario getOperador() {
		return operador;
	}

	public void setOperador(Usuario operador) {
		this.operador = operador;
	}

	public StatusVenda getStatus() {
		return status;
	}

	public void setStatus(StatusVenda status) {
		this.status = status;
	}
	
	
	
}
