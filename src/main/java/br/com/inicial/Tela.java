package br.com.inicial;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import br.com.agrofauna.utilidades.LayoutRelativo;

public class Tela extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LayoutRelativo lr;
	protected final Tela este = this;
	
	
	protected boolean option(String str){
		
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog(this, str, "", dialogButton);

		return dialogResult==JOptionPane.YES_OPTION;
		
	}
	
	protected void separatorH(int y){
		
		JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
		this.add(sep);
		this.lr.setDimensoesComponente(sep, 2, y, 96, 1);
		
	}
	
	protected String console(String mensagem){
		
		return JOptionPane.showInputDialog(this, mensagem);
		
	}
	
	protected void erro(String mensagem){
		
		JOptionPane.showMessageDialog(this,mensagem, "Erro",JOptionPane.ERROR_MESSAGE);
		
	}
	
	protected void alerta(String mensagem){
		
		JOptionPane.showMessageDialog(this,mensagem, "Alerta",JOptionPane.WARNING_MESSAGE);
		
	}
	
	protected void info(String mensagem){
		
		JOptionPane.showMessageDialog(this,mensagem, "Mensagem do sistema",JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	public Tela(){
		super();
	};
	
	public Tela(String titulo,int x,int y, int width, int height,boolean res){
		
		super(titulo);
		
		this.setLayout(null);
		this.lr = new LayoutRelativo(this, x, y, width, height);
		
		
		
		this.addComponentListener(new ComponentAdapter() 
		{  
		        public void componentResized(ComponentEvent evt) {
		            
		        	lr.reload();
		        	
		        }
		});
		
		
		this.setResizable(res);
		this.setVisible(false);
		
	}
}
