package br.com.arquivos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ArquivosLocais {

	public static InputStream getIS(String nome) throws IOException{
		
		return ArquivosLocais.class.getResourceAsStream("/arquivos/" + nome);
		
	}
	
	public static String getArquivoTexto(String nome,String encode) throws IOException {
		
		InputStream is = ArquivosLocais.getIS(nome);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int l = 0;
		byte[] buffer = new byte[1024];

		while ((l = is.read(buffer, 0, buffer.length)) > 0)
			baos.write(buffer, 0, l);
		
		return new String(baos.toByteArray(),encode);
	}
	
	public static byte[] getArquivo(String nome) throws IOException {

		InputStream is = ArquivosLocais.class.getClassLoader().getResource("arquivos/" + nome).openStream();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int l = 0;
		byte[] buffer = new byte[1024];

		while ((l = is.read(buffer, 0, buffer.length)) > 0)
			baos.write(buffer, 0, l);

		return baos.toByteArray();

	}

}
