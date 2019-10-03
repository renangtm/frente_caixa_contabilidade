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

			@Override
			public int codigoCredenciadoraCartao() {
				// TODO Auto-generated method stub
				return -1;
			}

			@Override
			public br.com.afgtec.notas.FormaPagamento getFormaPagamento() {
				// TODO Auto-generated method stub
				return br.com.afgtec.notas.FormaPagamento.DINHEIRO;
			}

			@Override
			public String cnpjCredenciadoraCartao() {
				// TODO Auto-generated method stub
				return "";
			}
			
		};
		
		
		FormaPagamento debito = new FormaPagamento() {

			public String getNome() {
				// TODO Auto-generated method stub
				return "Debito";
			}

			@Override
			public int codigoCredenciadoraCartao() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public br.com.afgtec.notas.FormaPagamento getFormaPagamento() {
				// TODO Auto-generated method stub
				return br.com.afgtec.notas.FormaPagamento.OUTROS;
			}

			@Override
			public String cnpjCredenciadoraCartao() {
				// TODO Auto-generated method stub
				return "";
			}
			
		};
		
		FormaPagamento credito = new FormaPagamento() {

			public String getNome() {
				// TODO Auto-generated method stub
				return "Credito Visa";
			}
			
			@Override
			public int codigoCredenciadoraCartao() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public br.com.afgtec.notas.FormaPagamento getFormaPagamento() {
				// TODO Auto-generated method stub
				return br.com.afgtec.notas.FormaPagamento.OUTROS;
			}

			@Override
			public String cnpjCredenciadoraCartao() {
				// TODO Auto-generated method stub
				return "";
			}
			
		};
		
		ArrayList<FormaPagamento> fp = new ArrayList<FormaPagamento>();
		fp.add(dinheiro);
		fp.add(debito);
		fp.add(credito);
		
		return fp;
		
	}
	

}
