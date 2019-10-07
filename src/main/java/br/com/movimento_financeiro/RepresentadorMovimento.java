package br.com.movimento_financeiro;

import br.com.conversores.ConversorDate;
import br.com.utilidades.Campo;
import br.com.utilidades.Representador;

public class RepresentadorMovimento extends Representador<Movimento>{

	@Campo(nome="Cod",editavel=false)
	private int cod;
	
	@Campo(nome="Banco",editavel=false)
	private String banco;
	
	@Campo(nome="Saldo",editavel=false)
	private double saldoAnterior;
	
	@Campo(nome="Valor",editavel=false)
	private double valor;
	
	@Campo(nome="Data",editavel=false)
	private String data;
	
	@Campo(nome="Pag",editavel=false)
	private String pagamento;
	
	@Campo(nome="Caixa",editavel=false)
	private String caixa;
	
	public RepresentadorMovimento(Movimento m) {
		super(m);
		
		this.cod = m.getId();
		this.banco = m.getBanco().getPj().getNome();
		this.saldoAnterior = m.getSaldo();
		this.valor = m.getValor();
		this.data = new ConversorDate().paraString(m.getData());
		
		if(m.getFormaPagamento() == null) {
			
			this.pagamento = "Desconhecido";
			
		}else {
			
			this.pagamento = m.getFormaPagamento().toString();
			
		}
		
		if(m.getExpediente() == null) {
			
			this.caixa = "Desconhecido";
			
		}else {
			
			this.caixa = m.getExpediente().getUsuario().getPf().getNome()+" - "+m.getExpediente().getCaixa().getNumero();
			
		}
		
		
	}

}
