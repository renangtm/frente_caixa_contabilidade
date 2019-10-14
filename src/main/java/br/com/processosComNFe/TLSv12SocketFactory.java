package br.com.processosComNFe;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;

import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

import br.com.empresa.ParametrosEmissao;

public class TLSv12SocketFactory implements ProtocolSocketFactory {

	private TrustManager[] tms;
	private KeyManager[] kms;

	public TLSv12SocketFactory() {

	}

	public TLSv12SocketFactory(ParametrosEmissao cm)
			throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {

		this.setKeys(cm.getCertificadoPFX().getPublicKey(), cm.getCertificadoPFX().getPrivateKey());

	}

	public TLSv12SocketFactory(ParametrosEmissao cm, KeyStore trusts)
			throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {

		this.setKeys(cm.getCertificadoPFX().getPublicKey(), cm.getCertificadoPFX().getPrivateKey());

		this.setTrusts(trusts);

	}

	public TLSv12SocketFactory setTrusts(KeyStore trust) throws NoSuchAlgorithmException, KeyStoreException {

		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(trust);

		this.tms = tmf.getTrustManagers();

		return this;

	}

	public TLSv12SocketFactory setKeys(X509Certificate certificado, PrivateKey pk) {

		this.kms = new KeyManager[] { new X509KeyManager() {

			@Override
			public String chooseClientAlias(String[] arg0, Principal[] arg1, Socket arg2) {
				return certificado.getIssuerDN().getName();
			}

			@Override
			public String chooseServerAlias(String arg0, Principal[] arg1, Socket arg2) {
				return null;
			}

			@Override
			public X509Certificate[] getCertificateChain(String arg0) {
				return new X509Certificate[] { certificado };
			}

			@Override
			public String[] getClientAliases(String arg0, Principal[] arg1) {
				return new String[] { certificado.getIssuerDN().getName() };
			}

			@Override
			public PrivateKey getPrivateKey(String arg0) {
				return pk;
			}

			@Override
			public String[] getServerAliases(String arg0, Principal[] arg1) {
				return null;
			}

		} };

		return this;
	}

	private SSLContext getContext() throws NoSuchAlgorithmException, KeyManagementException {

		if (this.kms == null || this.tms == null) {

			throw new RuntimeException("Atribua primeiro o trust e o private key");

		}

		SSLContext ctx = SSLContext.getInstance("TLSv1.2");
		ctx.init(this.kms, this.tms, null);

		return ctx;
	}

	@Override
	public Socket createSocket(String arg0, int arg1) throws IOException, UnknownHostException {

		try {

			SSLSocketFactory factory = this.getContext().getSocketFactory();

			return factory.createSocket(arg0, arg1);

		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Socket createSocket(String arg0, int arg1, InetAddress arg2, int arg3)
			throws IOException, UnknownHostException {

		try {

			SSLSocketFactory factory = this.getContext().getSocketFactory();

			return factory.createSocket(arg0, arg1, arg2, arg3);

		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Socket createSocket(String arg0, int arg1, InetAddress arg2, int arg3, HttpConnectionParams arg4)
			throws IOException, UnknownHostException {
		try {

			if (arg4 == null) {

				throw new RuntimeException("Parametros nulos");

			}

			SSLSocketFactory factory = this.getContext().getSocketFactory();

			if (arg4.getConnectionTimeout() == 0) {

				return factory.createSocket(arg0, arg1, arg2, arg3);

			}

			Socket socket = factory.createSocket();
			SocketAddress localaddr = new InetSocketAddress(arg2, arg3);
			SocketAddress remoteaddr = new InetSocketAddress(arg0, arg1);
			socket.bind(localaddr);

			try {

				socket.connect(remoteaddr, arg4.getConnectionTimeout());

			} catch (Exception e) {

				throw new RuntimeException(e);

			}

			return socket;

		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
