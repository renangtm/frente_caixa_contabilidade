package br.com.emissao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;

import org.apache.commons.net.util.Base64;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import br.com.config.Configuracoes;
import br.com.empresa.Empresa;

public class SAT {

	private static String arquivo = "config_sat_{{id_empresa}}.xml";

	private Empresa empresa;

	public int gerarNumeroSessao() {

		return (int) (Math.random() * 999999);

	}

	private void salvarConf(SatConf sc) throws IOException {

		XStream x = new XStream(new DomDriver());

		String nome_arquivo = arquivo.replaceAll("\\{\\{id_empresa\\}\\}", this.empresa.getId() + "");

		File f = new File(nome_arquivo);

		if (!f.exists()) {

			f.createNewFile();

		}

		x.toXML(sc, new FileOutputStream(f));

	}
	
	

	private SatConf getSatConf() throws IOException {

		String nome_arquivo = arquivo.replaceAll("\\{\\{id_empresa\\}\\}", this.empresa.getId() + "");

		File f = new File(nome_arquivo);

		if (!f.exists()) {

			return new SatConf();

		}

		XStream x = new XStream(new DomDriver());

		try {

			return (SatConf) x.fromXML(new FileInputStream(f));

		} catch (Exception ex) {

			return null;

		}

	}

	private boolean iniciado;

	public SAT(Empresa empresa) {

		this.empresa = empresa;
		this.iniciado = false;

	}

	public InterfaceSat getInterface() {

		if (!this.iniciado) {

			throw new RuntimeException("Nao e possivel pegar a interface, o aparelho esta desativado");

		}

		return InterfaceSatFactory.get();

	}

	public String getCnpjValue() throws UnsupportedEncodingException {

		return new String((Configuracoes.CNPJ + this.empresa.getPj().getCnpj()).replaceAll("\\.", "")
				.replaceAll("/", "").replaceAll("-", "").getBytes("UTF-8"));

	}

	public String getCSR() throws NoSuchAlgorithmException, InvalidKeyException, KeyStoreException,
			CertificateException, IOException, SignatureException {

		Signature s = Signature.getInstance("SHA256withRSA");
		s.initSign(Configuracoes.certificado.getPrivateKey());

		String str = this.getCnpjValue();
		byte[] dados = str.getBytes();

		s.update(dados);

		byte[] assinado = s.sign();

		return Base64.encodeBase64String(assinado,false);

	}

	public boolean isOperavel() {

		try {

			InterfaceSat is = InterfaceSatFactory.get();

			String r = is.ConsultarSAT(this.gerarNumeroSessao());
			System.out.println(r);
			String[] ret = r.split("\\|");

			if (ret[1].equals("08000")) {
				
				return true;

			}

		} catch (Exception ex) {

			
			ex.printStackTrace();
			
		}

		return false;

	}

	public boolean iniciar() throws IOException, InvalidKeyException, NoSuchAlgorithmException, KeyStoreException,
			CertificateException, SignatureException {

		if (!this.isOperavel()) {

			throw new RuntimeException("O equipamento este inoperante");

		}

		SatConf conf = this.getSatConf();

		if (!conf.isAtivo()) {

			InterfaceSat is = InterfaceSatFactory.get();

			int sa = this.gerarNumeroSessao();

			String ret = is.AssociarAssinatura(sa, this.empresa.getParametrosEmissao().getSenha_sat(),
					this.getCnpjValue(), this.getCSR());

			System.out.println(ret);
			
			String[] retorno = ret.split("\\|");

			if (!retorno[1].equals("13000")) {

				conf.setAtivo(false);

				this.salvarConf(conf);

				throw new RuntimeException("Problema ao vincular assinatura do AC com o S@T");

			} else {

				conf.setAtivo(true);

				this.salvarConf(conf);

				return false;
				
			}

		}

		this.iniciado = true;
		
		return true;

	}

}
