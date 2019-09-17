package br.com.afgtec.produto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.afgtec.unidades.TipoQuantidade;

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
	
	public void setQuantidades(double disponivel, double quantidade) throws Exception{
		
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
		
		this.quantidade += qtd;
		
		return this.quantidade;
		
	}

	
	public double addDisponivel(double qtd) {
		
		if(this.disponivel + qtd > this.quantidade) {
			
			throw new RuntimeException("Esse valor excede a quantidade total");
			
		}
		
		this.disponivel += qtd;
		
		return this.disponivel;
		
	}
	
	public double rmvDisponivel(double qtd) {
		
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
