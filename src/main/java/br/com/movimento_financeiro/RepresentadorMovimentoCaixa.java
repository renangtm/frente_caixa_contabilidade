package br.com.movimento_financeiro;

import br.com.conversores.ConversorDate;
import br.com.utilidades.Campo;
import br.com.utilidades.Representador;

public class RepresentadorMovimentoCaixa extends Representador<Movimento>{

	@Campo(nome="Cod",editavel=false)
	private int cod;
	
	@Campo(nome="Valor",editavel=false)
	private double valor;
	
	@Campo(nome="Data",editavel=false)
	private String data;
	
	@Campo(nome="Pag",editavel=false)
	private String pagamento;
	
	public RepresentadorMovimentoCaixa(Movimento m) {
		super(m);
		
		this.cod = m.getId();
		this.valor = m.getValor();
		this.data = new ConversorDate().paraStringComHora(m.getData());
		
		if(m.getFormaPagamento() == null) {
			
			this.pagamento = "Desconhecido";
			
		}else {
			
			this.pagamento = m.getFormaPagamento().toString();
			
		}
		
		
		
	}

}
