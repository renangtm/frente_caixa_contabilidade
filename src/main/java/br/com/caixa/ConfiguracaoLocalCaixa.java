package br.com.caixa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import br.com.config.Configuracoes;
import br.com.empresa.Empresa;
import br.com.utilidades.U;

public class ConfiguracaoLocalCaixa {

	private static final String arquivo = "caixa_{{id_empresa}}.xml";
	
	private String numeroCaixa;
	
	private String empresa;
	
	private String id_empresa;
	
	private String assinatura;
	
	public ConfiguracaoLocalCaixa() {
		
		
	}
	
	public ConfiguracaoLocalCaixa(Empresa empresa){
		
		this.empresa = empresa.getPj().getNome();
		this.id_empresa = empresa.getId()+"";
		
	}
	

	public static ConfiguracaoLocalCaixa getConfiguracaoLocalCaixa(Empresa empresa) {
		
		String nome_arquivo = arquivo.replaceAll("\\{\\{id_empresa\\}\\}", empresa.getId()+"");

		File f = new File(nome_arquivo);

		if (!f.exists()) {

			return null;

		}

		XStream x = new XStream(new DomDriver());

		try {

			return (ConfiguracaoLocalCaixa) x.fromXML(new FileInputStream(f));

		} catch (Exception ex) {

			ex.printStackTrace();
			
			return null;

		}
		
		
	}
	
	public void salvar() throws InvalidKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, SignatureException, IOException {
		
		this.assinar();
		
		XStream x = new XStream(new DomDriver());

		String nome_arquivo = arquivo.replaceAll("\\{\\{id_empresa\\}\\}", this.id_empresa);

		File f = new File(nome_arquivo);

		if (!f.exists()) {

			f.createNewFile();

		}

		x.toXML(this, new FileOutputStream(f));
		
	}
	
	public boolean verificarAssinatura() throws NoSuchAlgorithmException, InvalidKeyException, CertificateException, KeyStoreException, IOException, SignatureException {
		
		Signature s = Signature.getInstance("SHA256withRSA");
		s.initVerify(Configuracoes.certificado.getPublicKey());
		s.update((this.empresa+this.id_empresa+this.numeroCaixa+U.getMACMaquina()).getBytes());
		
		return s.verify(Base64.decode(this.assinatura));
		
	}
	
	private void assinar() throws InvalidKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, SignatureException {
		
		Signature s = Signature.getInstance("SHA256withRSA");
		s.initSign(Configuracoes.certificado.getPrivateKey());
		
		s.update((this.empresa+this.id_empresa+this.numeroCaixa+U.getMACMaquina()).getBytes());
	
		byte[] as = s.sign();
		
		this.assinatura = Base64.encode(as);
		
	}
	
	public int getNumeroCaixa() throws InvalidKeyException, NoSuchAlgorithmException, CertificateException, KeyStoreException, SignatureException, IOException {
		
		if(!this.verificarAssinatura()) {
			
			throw new RuntimeException("Arquivo sem integridade");
			
		}
		
		return Integer.parseInt(this.numeroCaixa);
		
	}
	
	public void setNumeroCaixa(int numero) throws InvalidKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, SignatureException, IOException {
		
		String n = numero+"";
		
		while(n.length()<10)
			n= "0" + n;
		
		this.numeroCaixa = n;
		
		this.assinar();
		
	}
	
}
