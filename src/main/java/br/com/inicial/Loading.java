package br.com.inicial;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;


public class Loading extends Tela{

	/**
	 * 
	 */
	
	private JLabel jpb;
	
	private static final long serialVersionUID = 1L;

	private static ArrayList<Loading> loads = new ArrayList<Loading>();
	
	
	
	public static int getLoading(Runnable rn2){
		
		final int i=loads.size();
		loads.add(null);
		
		Runnable rn = new Runnable(){

			@Override
			public void run() {
				
				loads.set(i, new Loading());
				
				java.awt.EventQueue.invokeLater(new Runnable(){

					@Override
					public void run() {
						
						
						
						rn2.run();
						
						Runnable rns = new Runnable(){
						
							public void run(){
							
								Loading.hide(i);
							
							}
							
						};
						
						Thread trns = new Thread(rns);
					
						trns.start();
						
					}
					
				});
				
				
				
			}
			
			
		};

		
		Thread th = new Thread(rn);
		
		th.start();
		
		return i;
		
	}
	
	private Loading() {
		
		super("Aguarde", 40, 45, 20, 10, false);
		this.setVisible(true);
		
		this.jpb = new JLabel("Aguarde um momento...");
		this.jpb.setFont(new Font("Arial",Font.BOLD,16));
		this.jpb.setHorizontalAlignment(JLabel.CENTER);
		
		this.add(this.jpb);
		
		this.lr.setDimensoesComponente(this.jpb, 2, 2, 96, 96);
		
	}
	
	public static void hide(int l){
		
		Loading.loads.get(l).dispose();
		Loading.loads.get(l).setVisible(false);
	}

}
