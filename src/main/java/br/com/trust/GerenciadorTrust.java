package br.com.trust;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import br.com.processosComNFe.ConfiguracaoEstadoNFe;


class TrustManagerGravador implements  X509TrustManager{
	
	public X509Certificate[] certificados;

	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		
		this.certificados = arg0;
		
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[0];
	}
	
	
} 

public class GerenciadorTrust {

	private GerenciadorTrust() {

	}

	private static final String NOME_ARQUIVO_TRUST = "trust_store.jks";
	private static final String SENHA_ARQUIVO_TRUST = "123456789";

	public static void gerarTrust() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, IOException, CertificateException {
		
		TrustManagerGravador tmg = new TrustManagerGravador();
		
		ArrayList<X509Certificate> certificados = new ArrayList<X509Certificate>();
		
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, new TrustManager[]{tmg}, null);
		SSLSocketFactory factory = context.getSocketFactory();
		
		for(ConfiguracaoEstadoNFe estado:ConfiguracaoEstadoNFe.values()){
			
			for(String trust:estado.getTrusts()){
				
				try {

					tmg.certificados = new X509Certificate[0];
					SSLSocket socket = (SSLSocket) factory.createSocket(trust, 443);
					socket.startHandshake();
					socket.close();
					
				} catch (Exception e) {
	
				}
				
				for(X509Certificate certificado:tmg.certificados){
					
					MessageDigest sha1 = MessageDigest.getInstance("SHA1");
					MessageDigest md5 = MessageDigest.getInstance("MD5");
					
					sha1.update(certificado.getEncoded());
					md5.update(certificado.getEncoded());
					
					certificados.add(certificado);
					
				}
				
				tmg.certificados = new X509Certificate[0];
				
			}
			
		}
		
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(null, null);
	
		int i = 0;
		for(X509Certificate certificado:certificados){
			
			ks.setCertificateEntry("trust"+i, certificado);
			i++;

		}
		
		File arquivo = new File(NOME_ARQUIVO_TRUST);
		
		if(arquivo.exists()){
			
			arquivo.delete();
			arquivo.createNewFile();
			
		}
		
		ks.store(new FileOutputStream(arquivo),  GerenciadorTrust.SENHA_ARQUIVO_TRUST.toCharArray());
		
	}

	public static KeyStore getTrustStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
			FileNotFoundException, IOException {

		File arquivo = new File(NOME_ARQUIVO_TRUST);

		int i = 0;
		while (!arquivo.exists() && i < 3) {

			try {
				GerenciadorTrust.gerarTrust();
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			arquivo = new File(NOME_ARQUIVO_TRUST);
			i++;

		}

		if (!arquivo.exists()) {

			throw new RuntimeException("Não foi possivel pegar e nem gerar o arquivo de trust store");

		}

		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(new FileInputStream(arquivo), (GerenciadorTrust.SENHA_ARQUIVO_TRUST).toCharArray());
		
		return ks;
		
	}

}
