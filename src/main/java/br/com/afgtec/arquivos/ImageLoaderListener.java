package br.com.afgtec.arquivos;

import javax.swing.ImageIcon;

public interface ImageLoaderListener {

	public void onLoad(ImageIcon imagem);
	
	public void onFail();
	
}
