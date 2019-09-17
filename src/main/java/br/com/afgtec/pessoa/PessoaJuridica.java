package br.com.afgtec.pessoa;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="idPessoa")
public class PessoaJuridica extends Pessoa{

	@Column
	private String cnpj;
	
	@Column
	private String inscricao_estadual;

	@Column
	private String inscricao_estadual_st;
	
	@Column
	private Calendar abertura;
	
	@OneToOne
	@JoinColumn(name="id_suframa")
	private Suframa suframa;
	
	@Enumerated(EnumType.ORDINAL)
	@Column
	private CodigoRegimeTributario crt;
	

	public Suframa getSuframa() {
		return suframa;
	}

	public void setSuframa(Suframa suframa) {
		this.suframa = suframa;
	}

	public CodigoRegimeTributario getCrt() {
		return crt;
	}

	public void setCrt(CodigoRegimeTributario crt) {
		this.crt = crt;
	}

	public String getInscricao_estadual_st() {
		return inscricao_estadual_st;
	}

	public void setInscricao_estadual_st(String inscricao_estadual_st) {
		this.inscricao_estadual_st = inscricao_estadual_st;
	}

	public Calendar getAbertura() {
		return abertura;
	}

	public void setAbertura(Calendar abertura) {
		this.abertura = abertura;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricao_estadual() {
		return inscricao_estadual;
	}

	public void setInscricao_estadual(String inscricao_estadual) {
		this.inscricao_estadual = inscricao_estadual;
	}

	
	
}
