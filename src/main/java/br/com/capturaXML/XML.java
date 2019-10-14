package br.com.capturaXML;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import br.com.empresa.Empresa;

@Entity
public class XML {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private int nsu;
	
	@Column
	private String chave;
	
	@Column
	private String chaveDocumentoReferenciado;
	
	@Column
	private Calendar data;
	
	@OneToOne(fetch=FetchType.EAGER)
	private Empresa empresa;
	
	@Enumerated(EnumType.ORDINAL)
	@Column
	private TipoXML tipo;
	
	@Column
	private boolean visto;
	
	@Column
	private boolean vistoSistema;

	@Column(columnDefinition="TEXT")
	private String arquivo;
	
	public String getChave() {
		return chave;
	}

	
	
	public boolean isVistoSistema() {
		return vistoSistema;
	}



	public void setVistoSistema(boolean vistoSistema) {
		this.vistoSistema = vistoSistema;
	}



	public String getChaveDocumentoReferenciado() {
		return chaveDocumentoReferenciado;
	}



	public void setChaveDocumentoReferenciado(String chaveDocumentoReferenciado) {
		this.chaveDocumentoReferenciado = chaveDocumentoReferenciado;
	}



	public String getArquivo() {
		return arquivo;
	}



	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}



	public void setChave(String chave) {
		this.chave = chave;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNsu() {
		return nsu;
	}

	public void setNsu(int nsu) {
		this.nsu = nsu;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public TipoXML getTipo() {
		return tipo;
	}

	public void setTipo(TipoXML tipo) {
		this.tipo = tipo;
	}

	public boolean isVisto() {
		return visto;
	}

	public void setVisto(boolean visto) {
		this.visto = visto;
	}

	
	
}
