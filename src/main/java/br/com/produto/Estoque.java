package br.com.produto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.quantificacao.TipoQuantidade;

@Entity
@Table(name="estoque")
public class Estoque {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private TipoQuantidade tipo;
	
	@Column
	private double quantidade;
	
	@Column
	private double disponivel;
	
	public void addQuantidades(double disponivel, double quantidade) throws Exception{
			
		this.setQuantidades(this.disponivel+disponivel, this.quantidade+quantidade);
		
	}
	
	public void rmvQuantidades(double disponivel, double quantidade) throws Exception{
		
		this.setQuantidades(this.disponivel-disponivel, this.quantidade-quantidade);
		
	}
	
	public void setQuantidades(double disponivel, double quantidade) throws Exception{
		
		if(disponivel < 0){
			
			throw new Exception("Disponivel < que 0");
			
		}
		
		if(quantidade < 0){
			
			throw new Exception("Quantidade < que 0");
			
		}
		
		if(quantidade<disponivel){
			
			throw new Exception("Quantidade menor do que o disponivel");
			
		}
		
		this.quantidade = quantidade;
		this.disponivel = disponivel;
		
	}
	
	public Estoque() {
		
		this.tipo = TipoQuantidade.UN;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TipoQuantidade getTipo() {
		return tipo;
	}

	public void setTipo(TipoQuantidade tipo) {
		this.tipo = tipo;
	}

	public double getQuantidade() {
		return quantidade;
	}
	
	public double getDisponivel() {
		return disponivel;
	}
	
	public double rmvQuantidade(double qtd) {
		
		if(qtd < 0) {
			
			return this.addQuantidade(qtd*-1);
			
		}
		
		if(qtd > this.quantidade) {
			
			throw new RuntimeException("Essa quantidade excede o limite");
			
		}
		
		if(this.quantidade - qtd < this.disponivel) {
			
			throw new RuntimeException("Remova o disponivel primeiro");
			
		}
		
		this.quantidade -= qtd;
		
		return this.quantidade;
		
	}
	
	public double addQuantidade(double qtd) {
		
		if(qtd < 0) {
			
			return this.rmvQuantidade(qtd*-1);
			
		}
		
		this.quantidade += qtd;
		
		return this.quantidade;
		
	}

	
	public double addDisponivel(double qtd) {
		
		if(qtd < 0) {
			
			return this.rmvDisponivel(qtd*-1);
			
		}
		
		if(this.disponivel + qtd > this.quantidade) {
			
			throw new RuntimeException("Esse valor excede a quantidade total");
			
		}
		
		this.disponivel += qtd;
		
		return this.disponivel;
		
	}
	
	public double rmvDisponivel(double qtd) {
		
		if(qtd < 0) {
			
			return this.addDisponivel(qtd*-1);
			
		}
		
		if(qtd > this.disponivel) {
			
			throw new RuntimeException("Nao existe essa quantidade");
			
		}
		
		
		this.disponivel -= qtd;
		
		return this.disponivel;
		
	}
	
	public Estoque clone() {
		
		Estoque quantidade = new Estoque();
		quantidade.tipo = this.tipo;
		quantidade.disponivel = this.disponivel;
		quantidade.quantidade = this.quantidade;
		
		return quantidade;
		
	}
	
}
