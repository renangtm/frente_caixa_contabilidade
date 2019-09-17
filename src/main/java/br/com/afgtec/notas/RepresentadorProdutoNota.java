package br.com.afgtec.notas;


import br.com.agrofauna.utilidades.Campo;
import br.com.agrofauna.utilidades.Representador;

public class RepresentadorProdutoNota extends Representador<ProdutoNota>{

	@Campo(nome="Codigo",editavel=false)
	private String codigo;
	
	@Campo(nome="Nome",editavel=false)
	private String nome;
	
	@Campo(nome="Valor",editavel=false)
	private double valor;
	
	@Campo(nome="Base Calculo",editavel=false)
	private double base_calculo;
	
	@Campo(nome="Icms",editavel=false)
	private double icms;
	
	@Campo(nome="Ipi",editavel=false)
	private double ipi;
	
	@Campo(nome="Pis",editavel=false)
	private double pis;
	
	@Campo(nome="Cofins",editavel=false)
	private double cofins;
	
	
	public RepresentadorProdutoNota(ProdutoNota v) {
		super(v);
		
		this.codigo = v.getId()+"";
		this.nome = v.getProduto().getNome();
		this.valor = v.getValor();
		this.base_calculo = v.getBase_calculo();
		this.icms = v.getIcms();
		this.ipi = v.getIpi();
		this.pis = v.getPis();
		this.cofins = v.getCofins();
		
	}
	
	@Override
	public void atualizar(){
	
	}

}
