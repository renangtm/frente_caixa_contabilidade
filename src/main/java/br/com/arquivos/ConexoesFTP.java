package br.com.arquivos;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class ConexoesFTP {
	
	private static FTPClient conexao = null;

	public static String getEndereco(String nome){
		
		return "http://www.faunasystem.com.br:8080/rtc/"+nome;
		
	}
	
	public static FTPClient getConexaoFTP() throws SocketException, IOException{
		
		if(conexao==null){
		
			conexao = new FTPClient();
			conexao.setConnectTimeout(0);
			conexao.connect("ftp.faunasystem.com.br");
			conexao.enterLocalPassiveMode();
			if (!FTPReply.isPositiveCompletion(conexao.getReplyCode())) {
	            conexao.disconnect();
	            throw new RuntimeException("Falha na conexao FTP");
	        }
			
			conexao.login("tomcat8","Agro@@Fauna");
			conexao.changeWorkingDirectory("webapps/rtc");
			conexao.setFileType(FTPClient.BINARY_FILE_TYPE); 
			
			
		}else{
			
			if(!conexao.isAvailable() || !conexao.isConnected()){
				conexao = null;
				return getConexaoFTP();
			}
			
		}
		
	
		return conexao;
		
	}
	
}
