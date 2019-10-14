package br.com.agrofauna.arquivos;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class UploaderArquivo {

	private UploaderArquivo() {

	}

	public static String upload(InputStream arquivo, String nomeArquivo) throws IOException{

		File arq = new File(nomeArquivo);
		arq.createNewFile();
		@SuppressWarnings("resource")
		FileOutputStream fos = new FileOutputStream(arq);
		
		byte[] bf = new byte[1024];
		int l = 0;
		while((l=arquivo.read(bf, 0, bf.length))>0){
			
			fos.write(bf, 0, l);
			
		}
		
		fos.flush();
		
		return nomeArquivo;
		
	}

}
