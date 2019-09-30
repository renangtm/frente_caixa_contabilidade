package br.com.inicial;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import br.com.entidades.AoFinalizar;
import br.com.entidades.FormaPagamento;
import br.com.entidades.FormaPagamentoVendaService;
import br.com.entidades.ImpressorCupomVenda;
import br.com.entidades.Visor;
import br.com.venda.StatusVenda;
import br.com.venda.Venda;
import br.com.venda.VendaService;

public class FinalizarCompra extends Tela{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Venda venda;
	
	private FormaPagamentoVendaService fps;
	
	private List<FormaPagamento> formas;
	
	private List<JLabel> lbls;
	
	private FormaPagamento selecionada;
	
	private FrenteCaixa fc;
	
	private ImpressorCupomVenda icp;
	
	
	public void finalizar() {
		
		this.setVisible(false);
		
		Runnable rn = new Runnable() {

			@Override
			public void run() {
				
				selecionada.efetuarPagamento(venda.getTotal(), new Visor() {

					@Override
					public void escrever(String str) {
						
						fc.lblNome.setText(str);
						
					}
					
					
				}, new AoFinalizar() {

					@Override
					public void sucesso() {
						
						fc.lblNome.setText("AGUARDE...");
						
						venda.setStatus(StatusVenda.FECHADA);
						
						try {
						
							new VendaService(fc.et).persistirVenda(venda);
							
							fc.et.getTransaction().begin();
							fc.et.getTransaction().commit();
							
						
						}catch(Exception ex) {
							
							ex.printStackTrace(); 
							
							fc.lblNome.setText("OCORREU UM PROBLEMA, O ESTABELECIMENTO ESTA SEM SERVIÇO...");
							
							return;
							
						}
						
						fc.lblNome.setText("TRANSACAO EFETUADA COM SUCESSO, AGUARDE O COMPROVANTE");
						icp.imprimir(venda);
						
						fc.novaVenda();
						
					}

					@Override
					public void falha() {
						
						fc.lblNome.setText("OCORREU UMA FALHA NO PAGAMENTO, TENTE NOVAMENTE");
						
					}
					
					
					
				});
				
			}
			
		};
		
		Thread th = new Thread(rn);
		
		th.start();
		
	}
	
	public FinalizarCompra(Venda venda,FrenteCaixa fc) {
		
		super("Finalizar", 25, 25, 50, 50, true);
		
		this.fc = fc;
	
		this.icp = new ImpressorCupomVenda();
		
		this.lbls = new ArrayList<JLabel>();
		
		this.venda = venda;
		
		this.fps = new FormaPagamentoVendaService();
		
		this.formas = this.fps.getFormasPagamento();
		
		this.selecionada = this.formas.get(0);
	
		this.desenhar();
		
		this.setVisible(true);

		this.setFocusable(true);

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				
				
			    int code = e.getKeyCode();
			    
			    if (code == KeyEvent.VK_DOWN){
			        
			    	selecionada = formas.get((formas.indexOf(selecionada)+1)%formas.size());
			    	
					
			    	
			    }else if (code == KeyEvent.VK_UP){
			    	
			    	selecionada = formas.get(formas.indexOf(selecionada)>0?(formas.indexOf(selecionada)-1):formas.size()-1);
			    	
			    }else if(code == KeyEvent.VK_ENTER) {
			    	
			    	finalizar();
			    	
			    }
			    
			    desenhar();
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
			
		});
		
		this.requestFocus();
		
		
	}
	
	public void desenhar() {
		
		
		for(JLabel lbl:this.lbls) {
			this.remove(lbl);
		}
		
		this.lbls.clear();
		
		
		int i=0;
		for(FormaPagamento fp:this.formas) {
			
			JLabel lbl = new JLabel(fp.getNome());
			lbl.setHorizontalAlignment(JLabel.CENTER);
			lbl.setFont(new Font("Arial",Font.BOLD,20));
			this.add(lbl);
			this.lr.setDimensoesComponente(lbl, 3, 3+i*10, 94, 10);
			
			if(fp == this.selecionada) {
				
				lbl.setOpaque(true);
				lbl.setBackground(Color.BLACK);
				lbl.setForeground(Color.white);
				
			}
			
			this.lbls.add(lbl);
			
			i++;
		}
		
	}
	

}
