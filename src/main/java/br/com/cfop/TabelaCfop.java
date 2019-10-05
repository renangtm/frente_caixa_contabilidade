package br.com.cfop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import br.com.entidades.nota.OperacaoLogistica;

@Entity
public class TabelaCfop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tabela")
	private List<ItemTabelaCfop> itens;

	public TabelaCfop() {

		this.itens = new ArrayList<ItemTabelaCfop>();

	}
	
	public CFOP getCFOP(OperacaoLogistica op){
		
		try{
		
			return this.itens.stream().filter(i->i.getOperacao().equals(op)).findFirst().get().getCfop();
		
		}catch(Exception ex){
		
			throw new RuntimeException("CFOP nao encontrado");
			
		}
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ItemTabelaCfop> getItens() {
		return itens;
	}

	public void setItens(List<ItemTabelaCfop> itens) {
		this.itens = itens;
	}
	
	@Override
	public Object clone() {

		TabelaCfop t = new TabelaCfop();
		t.id = 0;
		t.itens = this.itens.stream().map(i -> (ItemTabelaCfop) i.clone()).collect(Collectors.toList());

		return t;
		
	}

}
