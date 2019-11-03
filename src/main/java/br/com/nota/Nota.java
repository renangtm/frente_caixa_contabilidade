package br.com.nota;

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

import br.com.arquivos.Arquivo;
import br.com.empresa.Empresa;
import br.com.imposto.Imposto;
import br.com.imposto.ImpostoFactory;
import br.com.pessoa.Pessoa;
import br.com.pessoa.PessoaJuridica;
import br.com.transportadora.Transportadora;

@Entity
public class Nota {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private int ficha;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pessoa")
	private PessoaJuridica emitente;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_transportadora")
	private Transportadora transportadora;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
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

	@Column
	private String cpfNotaSemDestinatario;

	@ManyToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_xml")
	private Arquivo xml;

	@ManyToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_danfe")
	private Arquivo danfe;

	@Enumerated(EnumType.ORDINAL)
	@Column
	private SaidaEntrada operacao;

	// mappedBy='nota' por conta do join column nao precisa
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

	// mappedBy='nota'
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_nota")
	private List<Vencimento> vencimentos;

	@Column
	private String natureza_operacao;

	@Column
	private double valorMeioDePagamento;

	@Column
	private int credenciadoraCartao;

	@Column
	private String numeroAutorizacaoOperacaoCartaoCreditoDebito;

	@Column
	private String cnpjCredenciadoraCartao;

	@ManyToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;

	public OperacaoLogistica getOperacaoLogistica() {

		boolean dentro_estado = true;

		if (this.getDestinatario() != null) {

			dentro_estado = this.getDestinatario().getEndereco().getCidade().getEstado()
					.equals(this.getEmitente().getEndereco().getCidade().getEstado());

		}

		if (this.getTipo().equals(TipoNota.NORMAL)) {

			if (dentro_estado) {

				return OperacaoLogistica.VENDA_DENTRO_ESTADO;

			} else {

				return OperacaoLogistica.VENDA_FORA_ESTADO;

			}

		} else if (this.getTipo().equals(TipoNota.DEVOLUCAO)) {

			return OperacaoLogistica.DEVOLUCAO;

		}

		return OperacaoLogistica.VENDA_DENTRO_ESTADO;

	}

	public String getCpfNotaSemDestinatario() {
		return cpfNotaSemDestinatario;
	}

	public void setCpfNotaSemDestinatario(String cpfNotaSemDestinatario) {
		this.cpfNotaSemDestinatario = cpfNotaSemDestinatario;
	}

	public double getTotalIpi() {

		return this.produtos.stream()
				.mapToDouble(x -> (x.getProduto().getNcm().getAlicota_ipi() / 100) * x.getValor() * x.getQuantidade())
				.sum();

	}

	public void atualizarRegrasFiscais() {

		for (ProdutoNota p : this.produtos) {

			ImpostoFactory impf = new ImpostoFactory(p.getProduto().getCategoria().getTabelaAlicota(), this.tipo)
					.setDestinatario(this.getDestinatario()).setEmitente(this.getEmitente()).setProduto(p.getProduto());

			Imposto imp = impf.getImposto();

			p.setImposto(imp);

			imp.calcularSobre(p.getValor() * p.getQuantidade() + p.getFrete() + p.getOutro());

			p.setCfop(p.getProduto().getCategoria().getTabelaCfop().getCFOP(this.getOperacaoLogistica()));

		}
		

	}

	public Nota() {

		this.tipo = TipoNota.NORMAL;
		this.status = StatusNota.ATIVA;
		this.operacao = SaidaEntrada.ENTRADA;
		this.produtos = new ArrayList<ProdutoNota>();
		this.vencimentos = new ArrayList<Vencimento>();

		this.forma_pagamento = FormaPagamentoNota.DINHEIRO;

		this.modelo = ModeloNota.NFCE;

		this.emitente = new PessoaJuridica();

		this.data_emissao = Calendar.getInstance();
		this.data_saida_entrada = Calendar.getInstance();

	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public int getFicha() {
		return ficha;
	}

	public void setFicha(int ficha) {
		this.ficha = ficha;
	}

	public String getNumeroAutorizacaoOperacaoCartaoCreditoDebito() {
		return numeroAutorizacaoOperacaoCartaoCreditoDebito;
	}

	public void setNumeroAutorizacaoOperacaoCartaoCreditoDebito(String numeroAutorizacaoOperacaoCartaoCreditoDebito) {
		this.numeroAutorizacaoOperacaoCartaoCreditoDebito = numeroAutorizacaoOperacaoCartaoCreditoDebito;
	}

	public String getCnpjCredenciadoraCartao() {
		return cnpjCredenciadoraCartao;
	}

	public void setCnpjCredenciadoraCartao(String cnpjCredenciadoraCartao) {
		this.cnpjCredenciadoraCartao = cnpjCredenciadoraCartao;
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

	// ----------------------

	public double getValorMeioDePagamento() {
		return valorMeioDePagamento;
	}

	public void setValorMeioDePagamento(double valorMeioDePagamento) {
		this.valorMeioDePagamento = valorMeioDePagamento;
	}

	public int getCredenciadoraCartao() {
		return credenciadoraCartao;
	}

	public void setCredenciadoraCartao(int credenciadoraCartao) {
		this.credenciadoraCartao = credenciadoraCartao;
	}

	public double getIcmsTotal() {

		return this.produtos.stream().mapToDouble(p -> p.getImposto().getIcms().getValorIcms()).sum();

	}

	public double getBaseCalculoIcmsTotal() {

		return this.produtos.stream().mapToDouble(p -> p.getImposto().getIcms().getValorBaseCalculo()).sum();

	}

	public double getIcmsDesoneradoTotal() {

		return this.produtos.stream().mapToDouble(p -> p.getImposto().getIcms().getValorIcmsDesonerado()).sum();

	}

	public double getBaseCalculoSTTotal() {

		return this.produtos.stream().mapToDouble(p -> p.getImposto().getIcms().getBaseCalculoST()).sum();

	}

	public double getIcmsSTTotal() {

		return this.produtos.stream().mapToDouble(p -> p.getImposto().getIcms().getValorIcmsST()).sum();

	}

	public double getValorTotalProdutosServicos() {

		return this.produtos.stream().mapToDouble(p -> p.getValor() * p.getQuantidade()).sum();

	}

	public double getValorTotalNota() {

		return this.produtos.stream().mapToDouble(
				p -> p.getValor() * p.getQuantidade() - p.getDesconto() + p.getOutro() + p.getSeguro() + p.getFrete())
				.sum();

	}

	public double getFreteTotal() {

		return this.produtos.stream().mapToDouble(p -> p.getFrete()).sum();

	}

	public double getSeguroTotal() {

		return this.produtos.stream().mapToDouble(p -> p.getSeguro()).sum();

	}

	public double getAcrescimoTotal() {

		return this.produtos.stream().mapToDouble(p -> p.getOutro()).sum();

	}

	public double getDescontoTotal() {

		return this.produtos.stream().mapToDouble(p -> p.getDesconto()).sum();

	}

	public double getPisTotal() {

		return this.produtos.stream().mapToDouble(p -> p.getImposto().getPis().getValor()).sum();

	}

	public double getCofinsTotal() {

		return this.produtos.stream().mapToDouble(p -> p.getImposto().getCofins().getValor()).sum();

	}

	public double valorTributos() {

		return this.produtos.stream().mapToDouble(p -> p.getImposto().getTotalImpostos()).sum();

	}

	public double getDescontoSubtotalItens() {

		return Math.max(0, this.getDescontoTotal() - this.getAcrescimoTotal());

	}

	public double getAcrescimoSubtotalItens() {

		return Math.max(0, this.getAcrescimoTotal() - this.getDescontoTotal());

	}

	public double getTroco() {

		return this.valorMeioDePagamento - this.getValorTotalNota();

	}

	public double getISSQNtot() {

		return 0;

	}

	public double getBaseCalculoISSQNTotal() {

		return 0;

	}

	public double getTotalISS() {

		return 0;

	}

}
