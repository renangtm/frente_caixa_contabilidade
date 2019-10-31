package br.com.emissao;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateException;

import javax.persistence.EntityManager;

import br.com.base.ET;
import br.com.nota.Nota;

public class Testes {
	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException, SignatureException, IOException {
		
		EntityManager et = ET.nova();
		
		Nota nota = et.find(Nota.class, 1);
		
		SAT sat = new SAT(nota.getEmpresa());
		
		sat.iniciar();
		
		System.out.println(sat.isOperavel()?"OPERAVEL":"NAO OPERAVEL");
		
		
	}

}
