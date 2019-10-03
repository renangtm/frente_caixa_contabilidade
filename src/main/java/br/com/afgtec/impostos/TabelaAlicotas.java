package br.com.afgtec.impostos;

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

import afgtec.endereco.Estado;
import br.com.afgtec.notas.TipoNota;

@Entity
public class TabelaAlicotas {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="id_tabela")
	private List<EstadoAliquota> alicotas;

	public TabelaAlicotas() {
		
		this.alicotas = new ArrayList<EstadoAliquota>();
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<EstadoAliquota> getAlicotas() {
		return alicotas;
	}

	public void setAlicotas(List<EstadoAliquota> alicotas) {
		this.alicotas = alicotas;
	}
	
	public double getAlicota(Estado origem, Estado destino,TipoNota opl) {
		
		for(EstadoAliquota est:this.alicotas) {
			if(est.getOrigem().equals(origem) && est.getDestino().equals(destino) && est.getOpl().equals(opl)) {
				
				return est.getAliquota();
				
			}
		}
		
		return TabelaIcms.getIcms(origem, destino);
		
	}
	
	public double getAlicotaSt(Estado origem, Estado destino,TipoNota opl) {
		
		for(EstadoAliquota est:this.alicotas) {
			if(est.getOrigem().equals(origem) && est.getDestino().equals(destino)  && est.getOpl().equals(opl)) {
				
				return est.getAliquota_st();
				
			}
		}
		
		return TabelaIcms.getIcms(origem, destino);
		
	}
	
	@Override
	public Object clone() {
		
		TabelaAlicotas t = new TabelaAlicotas();
		t.id = 0;
		t.alicotas = this.alicotas.stream().map(i->(EstadoAliquota)i.clone()).collect(Collectors.toList());
	
		return t;
		
	}
	
}
