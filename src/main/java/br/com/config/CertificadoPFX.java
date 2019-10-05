package br.com.config;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class CertificadoPFX {

	private char[] senha;
	private KeyStore ks;

	public CertificadoPFX(InputStream is, String senha)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

		this.senha = senha.toCharArray();

		this.ks = KeyStore.getInstance("PKCS12");
		this.ks.load(is, this.senha);

	}

	public byte[] assinar(byte[] conteudo) throws NoSuchAlgorithmException, InvalidKeyException, KeyStoreException,
			CertificateException, IOException, SignatureException {

		Signature s = Signature.getInstance("SHA1withRSA");
		s.initSign(this.getPrivateKey());
		s.update(conteudo);

		return s.sign();

	}

	public X509Certificate getPublicKey()
			throws NoSuchAlgorithmException, CertificateException, IOException, KeyStoreException {

		Enumeration<String> aliases = ks.aliases();

		while (aliases.hasMoreElements()) {

			String alias = aliases.nextElement();

			try {

				X509Certificate pk = (X509Certificate) ks.getCertificate(alias);

				return pk;

			} catch (Exception ex) {

			}

		}

		return null;

	}

	public PrivateKey getPrivateKey()
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

		Enumeration<String> aliases = ks.aliases();

		while (aliases.hasMoreElements()) {

			String alias = aliases.nextElement();

			try {

				PrivateKey pk = (PrivateKey) ks.getKey(alias, this.senha);

				return pk;

			} catch (Exception ex) {

			}

		}

		return null;

	}

}
