package br.com.codigo;

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

import br.com.afgtec.pessoa.Empresa;
import br.com.afgtec.unidades.TipoQuantidade;
import br.com.agrofauna.utilidades.Campo;

@Entity
@Table(name="padrao_codigo")
public class PadraoCodigo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Campo(nome="Codigo",editavel=false)
	private int id;
	
	@Column
	@Campo(nome="Nome")
	private String nome;
	
	@Column
	@Campo(nome="Digito Inicial",editavel=true)
	private String digitoInicial;
	
	@Column
	@Campo(nome="Digitos Codigo Produto",editavel=true)
	private int digitosCodigoProduto;
	
	@Column
	@Campo(nome="Digitos Unidade",editavel=true)
	private int digitosUnidade;
	
	@Column
	@Campo(nome="Casas Decimais",editavel=true)
	private int casasDecimais;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_empresa")
	private Empresa empresa;
	
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public int getCasasDecimais() {
		return casasDecimais;
	}

	public void setCasasDecimais(int casasDecimais) {
		this.casasDecimais = casasDecimais;
	}

	@Column
	@Enumerated(EnumType.ORDINAL)
	private TipoQuantidade tipo;

	public PadraoCodigo(){
		
		this.tipo = TipoQuantidade.GR;
		
	}
	
	public int getTamanhoCodigo(){
		
		return this.digitoInicial.length()+this.digitosCodigoProduto+this.digitosUnidade;
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDigitoInicial() {
		return digitoInicial;
	}

	public void setDigitoInicial(String digitoInicial) {
		this.digitoInicial = digitoInicial;
	}

	public int getDigitosCodigoProduto() {
		return digitosCodigoProduto;
	}

	public void setDigitosCodigoProduto(int digitosCodigoProduto) {
		this.digitosCodigoProduto = digitosCodigoProduto;
	}

	public int getDigitosUnidade() {
		return digitosUnidade;
	}

	public void setDigitosUnidade(int digitosUnidade) {
		this.digitosUnidade = digitosUnidade;
	}

	public TipoQuantidade getTipo() {
		return tipo;
	}

	public void setTipo(TipoQuantidade tipo) {
		this.tipo = tipo;
	}
	
}
