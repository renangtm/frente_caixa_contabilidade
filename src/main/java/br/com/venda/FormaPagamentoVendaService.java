package br.com.venda;

import java.util.ArrayList;
import java.util.List;

import br.com.produto.BandeiraCartaoService;

public class FormaPagamentoVendaService {
	
	
	public List<FormaPagamento> getFormasPagamento(BandeiraCartaoService serv){
		
		FormaPagamento cheque = new FormaPagamento() {

			public String toString(){
				
				return "Cheque";
				
			}
			
			public String getNome() {
				// TODO Auto-generated method stub
				return "Cheque";
			}

			@Override
			public int codigoCredenciadoraCartao() {
				// TODO Auto-generated method stub
				return -1;
			}

			@Override
			public br.com.nota.FormaPagamentoNota getFormaPagamento() {
				// TODO Auto-generated method stub
				return br.com.nota.FormaPagamentoNota.CHEQUE;
			}

			@Override
			public String cnpjCredenciadoraCartao() {
				// TODO Auto-generated method stub
				return "";
			}
			
		};
		
		FormaPagamento dinheiro = new FormaPagamento() {

			public String toString(){
				
				return "Dinheiro";
				
			}
			
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
			public br.com.nota.FormaPagamentoNota getFormaPagamento() {
				// TODO Auto-generated method stub
				return br.com.nota.FormaPagamentoNota.DINHEIRO;
			}

			@Override
			public String cnpjCredenciadoraCartao() {
				// TODO Auto-generated method stub
				return "";
			}
			
		};
		
		
		FormaPagamento debitoVisa = new FormaPagamento() {

			public String toString(){
				
				return "Debito Visa";
				
			}
			
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
			public br.com.nota.FormaPagamentoNota getFormaPagamento() {
				// TODO Auto-generated method stub
				return br.com.nota.FormaPagamentoNota.CARTAO_DEBITO_VISA;
			}

			@Override
			public String cnpjCredenciadoraCartao() {
				// TODO Auto-generated method stub
				return "";
			}
			
		};
		
		FormaPagamento debitoMastercard = new FormaPagamento() {
			
			public String toString(){
				
				return "Debito	Mastercard";
				
			}

			public String getNome() {
				// TODO Auto-generated method stub
				return "Debito Mastercard";
			}
			
			@Override
			public int codigoCredenciadoraCartao() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public br.com.nota.FormaPagamentoNota getFormaPagamento() {
				// TODO Auto-generated method stub
				return br.com.nota.FormaPagamentoNota.CARTAO_DEBITO_MASTERCARD;
			}

			@Override
			public String cnpjCredenciadoraCartao() {
				// TODO Auto-generated method stub
				return "";
			}
			
		};
		
		FormaPagamento valePresente = new FormaPagamento() {
			
			public String toString(){
				
				return "Vale Presente";
				
			}

			public String getNome() {
				// TODO Auto-generated method stub
				return "Vale Presente";
			}
			
			@Override
			public int codigoCredenciadoraCartao() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public br.com.nota.FormaPagamentoNota getFormaPagamento() {
				// TODO Auto-generated method stub
				return br.com.nota.FormaPagamentoNota.VALE_PRESENTE;
			}

			@Override
			public String cnpjCredenciadoraCartao() {
				// TODO Auto-generated method stub
				return "";
			}
			
		};
		
		ArrayList<FormaPagamento> fp = new ArrayList<FormaPagamento>();
		fp.add(dinheiro);
		fp.add(debitoVisa);
		fp.add(debitoMastercard);
		fp.add(cheque);
		fp.add(valePresente);
		
		serv.getElementos(0, 1000, "", "").stream().forEach(a->{
			
			FormaPagamento f = new FormaPagamento() {
				
				public String toString(){
					
					return a.getNome()+" Credito";
					
				}

				public String getNome() {
					// TODO Auto-generated method stub
					return this.toString();
				}
				
				@Override
				public int codigoCredenciadoraCartao() {
					// TODO Auto-generated method stub
					return 0;
				}

				@Override
				public br.com.nota.FormaPagamentoNota getFormaPagamento() {
					// TODO Auto-generated method stub
					return br.com.nota.FormaPagamentoNota.CARTAO_VISA;
				}

				@Override
				public String cnpjCredenciadoraCartao() {
					// TODO Auto-generated method stub
					return a.getCnpjCredenciadora();
				}
				
			};
			
			fp.add(f);
			
		});
		
		return fp;
		
	}
	

}
