package br.com.inicial;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.UIManager;

import br.com.afgtec.pessoa.Usuario;

public class Modulo extends Tela{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static List<Modulo> modulos = new ArrayList<Modulo>();
	
	protected String fixedTitle = "";
	
	public boolean validarFormulario(){
		
		return this.validarContainer(this.getContentPane());
		
	}
	
	public boolean vc(Component comp){
		
		try{
			
			JTextField txt = (JTextField)comp;
			
			if(!txt.getText().equals("")){
				if(txt.getText().charAt(0) != ' '){
					
					txt.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
					return true;
			
				}
			}
			
			txt.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			txt.requestFocus();
			erro("Preencha os campos adequadamente");
			return false;
			
		}catch(Exception ex){
			
		}

		return true;
		
	}
	
	public boolean validarContainer(Container comp){
		
		for(Component c:comp.getComponents()){
			
			try{
				
				if(!this.vc(c)){
					
					return false;
					
				}
				
				
			}catch(Exception ex2){
				
				
			}
			
			try{
				
				Container f = (Container)c;
				
				if(!this.validarContainer(f)){
					
					return false;
					
				}
				
			}catch(Exception ex){
				
			}
			
			
		}
		
		return true;
		
	}
	
	@Override
	public void setTitle(String str){
		
		if(!this.fixedTitle.equals("")){
		
			super.setTitle(this.fixedTitle+" - "+str);
		
		}else{
			
			super.setTitle(str);
			
		}
		
	}
	
	public Modulo(String titulo, int x, int y, int width, int height, boolean res) {
		super(titulo, x, y, width, height, res);
		
		this.fixedTitle = titulo;
		
		modulos.add(this);
		/*
		for(Modulo modulo:Modulo.modulos){
			
			if(modulo.isVisible()){
				
				modulo.requestFocus();
				
			}
			
		}
		*/
	
	
		this.setVisible(true);
	}
	
	public void init(Usuario usuario){
		
		
	};
	
	public Modulo(){
		super();
		this.setVisible(true);
	}
	
	public Modulo(String titulo, int x, int y, int width, int height, boolean res,boolean contrato) {
		super(titulo, x, y, width, height, res);
	

		this.setVisible(true);
	}
	
	
	public static ImageIcon logo(){
		
		
		return null;
		
	}

	public static String nome(){
		
		return "";
		
	}
	
}
