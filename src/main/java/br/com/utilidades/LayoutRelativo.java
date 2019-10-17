package br.com.utilidades;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

public class LayoutRelativo {

	Container conteiner;
	
	private List<Component> componentes;
	private List<Rectangle> reg; 
	
	private int width;
	private int height;
	private int x;
	private int y;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int widthTela = (int) screenSize.getWidth();
	private int heightTela = (int) screenSize.getHeight();


	public LayoutRelativo(Container conteiner, int x, int y, int width, int height) {
		this.conteiner = conteiner;
		this.width = p(widthTela, width);
		this.height = p(heightTela, height);
		this.x = p(widthTela, x);
		this.y = p(heightTela, y);
		this.conteiner.setBounds(this.x,this.y,this.width,this.height);
		this.componentes = new ArrayList<Component>();
		this.reg = new ArrayList<Rectangle>();

	}
	
	public LayoutRelativo(Container conteiner, int x, int y, int width, int height, LayoutRelativo base) {
		this.conteiner = conteiner;
		this.width = p(base.getWidth()-5, width);
		this.height = p(base.getHeight()-30, height);
		this.x = p(base.getWidth()-5, x);
		this.y = p(base.getHeight()-30, y);
		this.conteiner.setBounds(this.x,this.y,this.width,this.height);
		this.componentes = new ArrayList<Component>();
		this.reg = new ArrayList<Rectangle>();
		
	}

	private int p(double x, double y) {
		return (int)Math.round(((double)x * (double)y)/100);
	}
	
	public void setDimensoesComponente(Component componente, double x, double y, double width, double height){
		componente.setBounds(p(this.width-5, x), p(this.height-30, y), p(this.width-5, width),
				p(this.height-30, height));
		
		int i=0;
		for(Component componente_:this.componentes){
			if(componente_==componente){
				
				this.componentes.set(i,componente);
				this.reg.set(i,componente.getBounds());
				
			}
			i++;
		}
		
		
		
		this.reload();
	}
	
	public void reload(){
		
		double x_fat = (double)(this.conteiner.getWidth())/(double)this.width;
		double y_fat = (double)(this.conteiner.getHeight())/(double)this.height;
		
		int i = 0;
		for(Component componente:this.componentes){
			
			Rectangle dimensoes = this.reg.get(i);
			
			Rectangle d2 = new Rectangle();
			d2.setBounds((int)Math.round((double)dimensoes.getX()*x_fat),(int)Math.round((double)dimensoes.getY()*y_fat), (int)((double)dimensoes.getWidth()*x_fat), (int)Math.round((double)dimensoes.getHeight()*y_fat));
			
			componente.setBounds(d2);
			
			i++;
		}
		
	}

}
