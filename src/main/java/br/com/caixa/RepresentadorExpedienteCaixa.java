package br.com.caixa;

import java.util.Calendar;

import br.com.conversores.ConversorCalendar;
import br.com.utilidades.Campo;
import br.com.utilidades.Representador;

public class RepresentadorExpedienteCaixa extends Representador<ExpedienteCaixa>{

	@Campo(nome="Sal. Ini")
	private double saldoInicial;
	
	@Campo(nome="Sal. Fim")
	private double saldoFinal;
	
	@Campo(nome="Inicio")
	private Calendar inicio;
	
	@Campo(nome="Fim")
	private String fim;
	
	@Campo(nome="Operador")
	private String operador;
	
	
	
	public RepresentadorExpedienteCaixa(ExpedienteCaixa o) {
		super(o);
		
		this.saldoInicial = o.getSaldo_inicial();
		this.saldoFinal = o.getSaldo_final_atual();
		
		this.inicio = o.getInicio();
		
		if(o.getFim() == null) {
			
			this.fim =  "------";
			
		}else {
			
			this.fim = new ConversorCalendar().paraString(o.getFim());
			
		}
		
		this.operador = o.getUsuario().getPf().getNome();
		
	}

	
	
}
