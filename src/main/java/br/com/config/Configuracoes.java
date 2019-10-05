package br.com.config;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class Configuracoes {

	public static final String CNPJ = "31.786.525/0001-28";
	
	public static CertificadoPFX certificado = null;

	static{
		
		try {
			certificado = new CertificadoPFX(Configuracoes.class.getClassLoader().getResourceAsStream("icones/certificado.pfx"),"123456");
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private Configuracoes() {

	}

}
