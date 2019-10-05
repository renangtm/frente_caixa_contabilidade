package br.com.entidades.nota;

import br.com.utilidades.Campo;
import br.com.utilidades.Representador;

public class RepresentadorProdutoNota extends Representador<ProdutoNota> {

	@Campo(nome = "Codigo", editavel = false)
	private String codigo;

	@Campo(nome = "Nome", editavel = false)
	private String nome;

	@Campo(nome = "Valor", editavel = true)
	private double valor;

	@Campo(nome = "Quantidade", editavel = true)
	private double quantidade;

	@Campo(nome = "Base Calculo", editavel = false)
	private double base_calculo;

	@Campo(nome = "Icms", editavel = false)
	private double icms;

	@Campo(nome = "CFOP", editavel=false)
	private String cfop;

	@Campo(nome = "Pis", editavel = false)
	private double pis;
	
	@SuppressWarnings("unused")
	private String cstCofins;

	@Campo(nome = "Cofins", editavel = false)
	private double cofins;
	
	@SuppressWarnings("unused")
	private double subTotal;

	public RepresentadorProdutoNota(ProdutoNota v) {
		super(v);

		this.codigo = v.getId() + "";
		this.nome = v.getProduto().getNome();
		this.valor = v.getValor();
		this.base_calculo = v.getImposto().getIcms().getValorBaseCalculo()
				+ v.getImposto().getIcms().getBaseCalculoST();
		this.icms = v.getImposto().getIcms().getValorIcms();
		this.pis = v.getImposto().getPis().getValor();
		this.cofins = v.getImposto().getCofins().getValor();

		this.cstCofins = v.getImposto().getCofins().getCst();
		this.quantidade = v.getQuantidade();
		this.cfop = v.getCfop().getNumero();
		
		this.subTotal = this.valor*this.quantidade;
		
	}

	@Override
	public void atualizar() {

		this.objetoOriginal.setQuantidade(this.quantidade);
		this.objetoOriginal.setValor(this.valor);
		this.subTotal = this.valor*this.quantidade;
		
	}

}
