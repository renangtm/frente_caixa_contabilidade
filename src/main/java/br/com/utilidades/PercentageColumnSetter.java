package br.com.utilidades;

import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JTable;

public class PercentageColumnSetter {
	
	private ArrayList<Integer> porcentagens;
	
	public PercentageColumnSetter(@SuppressWarnings("rawtypes") Class classe){
		
		this.porcentagens = new ArrayList<Integer>();
		
		for(Field campo:classe.getDeclaredFields()){
			
			campo.setAccessible(true);
			
			Campo anCampo = campo.getDeclaredAnnotation(Campo.class);
			
			if(anCampo != null){
				
				if(!anCampo.lista())continue;
				
				this.porcentagens.add(anCampo.tamanho());
				
			}
			
		}
		
		int total = 0;
		for(Integer i:this.porcentagens)total+=i;
		
		if(total==0){
			
			for(int i=0;i<this.porcentagens.size();i++){
				
				this.porcentagens.set(i, 100/this.porcentagens.size());
				
			}
			
		}
		
		
	}
	
	public void resize(JTable tbl,JComponent base){
		
		int i=0;
		for(Integer w:this.porcentagens){
		
			int width = (int)(((double)w/(double)100)*base.getWidth());
			
			try{

				tbl.getColumnModel().getColumn(i).setPreferredWidth(width);
			
			}catch(Exception ex){
				
			}
			
			i++;
		}
		

	}

}
