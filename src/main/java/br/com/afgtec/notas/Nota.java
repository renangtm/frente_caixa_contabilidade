package br.com.afgtec.notas;

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

import br.com.afgtec.arquivos.Arquivo;
import br.com.afgtec.pessoa.Pessoa;
import br.com.afgtec.pessoa.PessoaJuridica;
import br.com.afgtec.pessoa.Transportadora;

@Entity
public class Nota {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pessoa")
	private PessoaJuridica emitente;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_transportadora")
	private Transportadora transportadora;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_destinatario")
	private Pessoa destinatario;

	@Column
	private Calendar data_emissao;

	@Column
	private Calendar data_saida_entrada;
	
	@Column
	private int serie;

	@Column
	private int numero;

	@Column
	private String chave;

	@Column
	private String chave_referenciada;
	
	@ManyToOne
	@JoinColumn(name="id_xml")
	private Arquivo xml;

	@ManyToOne
	@JoinColumn(name="id_danfe")
	private Arquivo danfe;


	@Column
	private SaidaEntrada operacao;
	
	@OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name = "id_nota")
	private List<ProdutoNota> produtos;

	@Column
	private String chaveReferenciada;

	@Column
	private String observacoes;

	@Enumerated(EnumType.ORDINAL)
	@Column
	private StatusNota status;
	
	@Enumerated(EnumType.ORDINAL)
	@Column
	private ModeloNota modelo;

	@Column
	private String protocolo;

	@Enumerated(EnumType.ORDINAL)
	@Column
	private TipoNota tipo;
	
	@Enumerated(EnumType.ORDINAL)
	@Column
	private FormaPagamentoNota forma_pagamento;

	@OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name = "id_nota")
	private List<Vencimento> vencimentos;

	@Column
	private String natureza_operacao;

	
	public Nota() {
		
		
		this.tipo = TipoNota.NORMAL;
		this.status = StatusNota.ATIVA;
		this.produtos = new ArrayList<ProdutoNota>();
		this.vencimentos = new ArrayList<Vencimento>();
		
		this.transportadora = new Transportadora();
		this.emitente = new PessoaJuridica();
		
		this.data_emissao = Calendar.getInstance();
		
	}
	
	
	
	public String getChave_referenciada() {
		return chave_referenciada;
	}



	public void setChave_referenciada(String chave_referenciada) {
		this.chave_referenciada = chave_referenciada;
	}



	public SaidaEntrada getOperacao() {
		return operacao;
	}

	public void setOperacao(SaidaEntrada operacao) {
		this.operacao = operacao;
	}



	public Calendar getData_saida_entrada() {
		return data_saida_entrada;
	}



	public void setData_saida_entrada(Calendar data_saida_entrada) {
		this.data_saida_entrada = data_saida_entrada;
	}



	public ModeloNota getModelo() {
		return modelo;
	}



	public void setModelo(ModeloNota modelo) {
		this.modelo = modelo;
	}



	public FormaPagamentoNota getForma_pagamento() {
		return forma_pagamento;
	}



	public void setForma_pagamento(FormaPagamentoNota forma_pagamento) {
		this.forma_pagamento = forma_pagamento;
	}



	public Pessoa getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Pessoa dest) {
		this.destinatario = dest;
	}

	public double getTotal() {

		return this.produtos.stream().map(x -> x.getQuantidade() * x.getValor()).mapToDouble(x -> (double) x).sum();

	}

	public double getBaseCalculo() {

		return this.produtos.stream().map(x -> x.getBase_calculo()).mapToDouble(x -> (double) x).sum();

	}

	public double getIcms() {

		return this.produtos.stream().map(x -> x.getIcms()).mapToDouble(x -> (double) x).sum();

	}

	public double getPis() {

		return this.produtos.stream().map(x -> x.getPis()).mapToDouble(x -> (double) x).sum();

	}

	public double getCofins() {

		return this.produtos.stream().map(x -> x.getCofins()).mapToDouble(x -> (double) x).sum();

	}

	public double getIpi() {

		return this.produtos.stream().map(x -> x.getIpi()).mapToDouble(x -> (double) x).sum();

	}

	public double getIcmsDesonerado() {

		return this.produtos.stream().map(x -> x.getIcms_desonerado()).mapToDouble(x -> (double) x).sum();

	}

	public String getNatureza_operacao() {
		return natureza_operacao;
	}

	public void setNatureza_operacao(String natureza_operacao) {
		this.natureza_operacao = natureza_operacao;
	}

	public int getSerie() {
		return serie;
	}

	public void setSerie(int serie) {
		this.serie = serie;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PessoaJuridica getEmitente() {
		return emitente;
	}

	public void setEmitente(PessoaJuridica emitente) {
		this.emitente = emitente;
	}

	public Transportadora getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(Transportadora transportadora) {
		this.transportadora = transportadora;
	}

	public Calendar getData_emissao() {
		return data_emissao;
	}

	public void setData_emissao(Calendar data_emissao) {
		this.data_emissao = data_emissao;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public Arquivo getXml() {
		return xml;
	}

	public void setXml(Arquivo xml) {
		this.xml = xml;
	}

	public Arquivo getDanfe() {
		return danfe;
	}

	public void setDanfe(Arquivo danfe) {
		this.danfe = danfe;
	}


	public List<ProdutoNota> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoNota> produtos) {
		this.produtos = produtos;
	}

	public String getChaveReferenciada() {
		return chaveReferenciada;
	}

	public void setChaveReferenciada(String chaveReferenciada) {
		this.chaveReferenciada = chaveReferenciada;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public StatusNota getStatus() {
		return status;
	}

	public void setStatus(StatusNota status) {
		this.status = status;
	}

	public TipoNota getTipo() {
		return tipo;
	}

	public void setTipo(TipoNota tipo) {
		this.tipo = tipo;
	}

	public List<Vencimento> getVencimentos() {
		return vencimentos;
	}

	public void setVencimentos(List<Vencimento> vencimentos) {
		this.vencimentos = vencimentos;
	}

}
