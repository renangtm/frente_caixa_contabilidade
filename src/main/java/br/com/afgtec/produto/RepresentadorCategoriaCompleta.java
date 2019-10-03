package br.com.afgtec.produto;

import br.com.agrofauna.utilidades.Campo;
import br.com.agrofauna.utilidades.Representador;

public class RepresentadorCategoriaCompleta extends Representador<Categoria>{

	@Campo(nome="Codigo",editavel=false,ordem="{{et}}.id")
	private int codigo;
	
	@Campo(nome="Nome",editavel=false,ordem="{{et}}.nome")
	private String nome;
	
	@Campo(nome="CST ICMS",editavel=false,ordem="{{et}}.icms.cst")
	private String cstIcms;
	
	@Campo(nome="CST PIS",editavel=false,ordem="{{et}}.pis.cst")
	private String cstPis;
	
	@Campo(nome="CST COFINS",editavel=false,ordem="{{et}}.cofins.cst")
	private String cstCofins;
	
	
	public RepresentadorCategoriaCompleta(Categoria p) {
		super(p);
		
		this.codigo = p.getId();
		this.cstIcms = p.getIcms().getCst();
		this.cstCofins = p.getCofins().getCst();
		this.cstPis = p.getPis().getCst();
		
	}

}
