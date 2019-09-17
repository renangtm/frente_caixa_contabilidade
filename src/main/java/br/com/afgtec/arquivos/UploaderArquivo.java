package br.com.afgtec.arquivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;

import br.com.agrofauna.utilidades.LayoutRelativo;

public class UploaderArquivo extends JFrame{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LayoutRelativo lr;
	
	private JProgressBar jpb;

	private UploaderArquivo(String titulo){
		
		super(titulo);
		
		this.setLayout(null);
		this.setVisible(true);
		this.setResizable(false);
		
		this.lr = new LayoutRelativo(this,30,45,40,10);
		
		this.jpb = new JProgressBar();
		this.jpb.setMaximum(100);
		
		this.add(this.jpb);
		
		this.lr.setDimensoesComponente(this.jpb, 2, 6, 95, 57);
		
	}
	
	public static void testarConexao() throws SocketException, IOException{
		
		ConexoesFTP.getConexaoFTP().listFiles();
		
		
		
	}
	
	public static void upload(InputStream is,ActionPosUpload apu,String nomeArquivo){
		
		Runnable up = new Runnable(){

			@Override
			public void run() {
		
				try {
					
					
					FTPClient conexao = ConexoesFTP.getConexaoFTP();
					
					conexao.storeFile(nomeArquivo, is);
					
				} catch (Exception e) {
				
				}
				
			}
			
		};
		
		Thread th = new Thread(up);
		th.start();
		
	}
	
	public static void upload(File arquivo,ActionPosUpload apu){
		
		Runnable up = new Runnable(){

			@Override
			public void run() {
				
				UploaderArquivo ua = new UploaderArquivo("Aguarde uns instantes, copiando o arquivo "+arquivo.getName());
				String nomeArquivo = arquivo.getName();
				
				try {
					
					
					FTPClient conexao = ConexoesFTP.getConexaoFTP();
					
					
					conexao.setCopyStreamListener(new CopyStreamListener(){

						@Override
						public void bytesTransferred(CopyStreamEvent arg0) {
							
							
						}

						@Override
						public void bytesTransferred(long arg0, int arg1, long arg2) {
							
							ua.jpb.setValue((int)(arg0*100/arquivo.length()));
							ua.jpb.setStringPainted(true);
							ua.jpb.setString(((int)(arg0*100/arquivo.length()))+"%");
							
							ua.requestFocus();
							
							Runnable rn = new Runnable(){

								@Override
								public void run() {
									
										try {
											Thread.sleep(1000);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										ua.dispose();
										
										if(apu != null){
											
											apu.fim(ConexoesFTP.getEndereco(nomeArquivo));
											
										}
										
									
									
								}
							
							
							
							};
							
							if(arg0==arquivo.length()){
								
								Thread ts = new Thread(rn);
								ts.start();
								
							}
							
							
						}
						
					});
					
					
					InputStream is = new FileInputStream(arquivo);
					
					conexao.storeFile(nomeArquivo, is);
					
					
				} catch (Exception e) {
				
					ua.dispose();
					
					JOptionPane.showMessageDialog(null, "Falha ao se conectar com o servidor de arquivos","Ops...",JOptionPane.ERROR_MESSAGE);
					
				}
				
			}
			
		};
		
		Thread th = new Thread(up);
		th.start();
		
	}
	
}
