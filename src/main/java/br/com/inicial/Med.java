package br.com.inicial;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Med {
	
	private static final boolean HABILITADO = false;
	
	private long tempo;
	private String titulo;
	private int numero = 0;
	
	private static JFrame frmLog;
	private static JTextArea txtLog;
	
	private long teste = -1;
	
	public Med(String titulo){
		
		this.tempo = System.currentTimeMillis();
		this.titulo = titulo;
		
	}
	
	public Med(String titulo,int teste){
		
		this.tempo = System.currentTimeMillis();
		this.titulo = titulo;
		this.teste = teste;
		
	}
	
	
	public void c(){
		
		
		
		long t = System.currentTimeMillis();
		
		long dif = t-this.tempo;
		
		double seg = dif;
		
		if(seg<this.teste){
			throw new RuntimeException("AQUIIIIIIIIIIII");
		}
		
		if(!Med.HABILITADO)return;
		
		if(frmLog==null){
			
			frmLog = new JFrame();
			txtLog = new JTextArea();
			
			frmLog.setVisible(true);
			frmLog.setLayout(null);
			frmLog.setBounds(0, 0, 500, 500);
			frmLog.add(txtLog);
			txtLog.setBounds(0, 0, 500, 500);
			
		}
		txtLog.append(this.numero+" "+this.titulo+" - "+seg+" \n");
		frmLog.setVisible(true);
		frmLog.requestFocus();
		this.numero++;
		this.tempo = System.currentTimeMillis();
		
	}

}
