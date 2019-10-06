package br.com.impostos.icms;

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
import javax.persistence.OneToOne;

import br.com.endereco.Estado;
import br.com.entidades.nota.TipoNota;
import br.com.utilidades.Campo;

@Entity
public class EstadoAliquota {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinColumn(name="origem")
	@Campo(nome="Origem",editavel=false)
	private Estado origem;
	
	@OneToOne(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinColumn(name="destino")
	@Campo(nome="Destino",editavel=false)
	private Estado destino;
	
	
	@Enumerated(EnumType.ORDINAL)
	@Column
	@Campo(nome="Op",editavel=false)
	private TipoNota opl;
	
	@Column
	@Campo(nome="Aliq",editavel=true)
	private double aliquota;
	
	@Column
	@Campo(nome="Aliq ST",editavel=true)
	private double aliquota_st;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Estado getOrigem() {
		return origem;
	}

	public void setOrigem(Estado origem) {
		this.origem = origem;
	}

	public Estado getDestino() {
		return destino;
	}

	public void setDestino(Estado destino) {
		this.destino = destino;
	}

	public double getAliquota() {
		return aliquota;
	}

	public void setAliquota(double aliquota) {
		this.aliquota = aliquota;
	}

	public double getAliquota_st() {
		return aliquota_st;
	}

	public void setAliquota_st(double aliquota_st) {
		this.aliquota_st = aliquota_st;
	}

	public TipoNota getOpl() {
		return opl;
	}

	public void setOpl(TipoNota opl) {
		this.opl = opl;
	}
	
	@Override
	public Object clone() {
		
		EstadoAliquota e = new EstadoAliquota();
		e.id = 0;
		e.aliquota = this.aliquota;
		e.aliquota_st = this.aliquota_st;
		e.destino = this.destino;
		e.opl = this.opl;
		e.origem = this.origem;
		
		return e;
	}
	
}
