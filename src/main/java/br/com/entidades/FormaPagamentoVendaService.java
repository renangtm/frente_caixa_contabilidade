package br.com.entidades;

import java.util.ArrayList;
import java.util.List;

public class FormaPagamentoVendaService {
	
	
	public List<FormaPagamento> getFormasPagamento(){
		
		FormaPagamento dinheiro = new FormaPagamento() {

			public String getNome() {
				// TODO Auto-generated method stub
				return "Dinheiro";
			}

			public void efetuarPagamento(double valor, Visor visor, AoFinalizar aoFinalizar) {
				
				aoFinalizar.sucesso();
				
			}
			
		};
		
		
		FormaPagamento debito = new FormaPagamento() {

			public String getNome() {
				// TODO Auto-generated method stub
				return "Debito";
			}

			public void efetuarPagamento(double valor, Visor visor, AoFinalizar aoFinalizar) {
				
				visor.escrever("Insira o cartao");
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				aoFinalizar.falha();
				
			}
			
		};
		
		FormaPagamento credito = new FormaPagamento() {

			public String getNome() {
				// TODO Auto-generated method stub
				return "Credito Visa";
			}

			public void efetuarPagamento(double valor, Visor visor, AoFinalizar aoFinalizar) {
				
				visor.escrever("Insira o cartao");
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				aoFinalizar.falha();
				
			}
			
		};
		
		ArrayList<FormaPagamento> fp = new ArrayList<FormaPagamento>();
		fp.add(dinheiro);
		fp.add(debito);
		fp.add(credito);
		
		return fp;
		
	}
	

}
