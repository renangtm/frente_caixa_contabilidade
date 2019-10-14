package br.com.config;

/*
 * @author Renan  Gonçalves Teixeira Miranda
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

interface OnSelect {

	public void acao(Node pai, Node filho) throws Exception;

}

public class AssinadorXML {

	private CertificadoPFX cm;

	public AssinadorXML(CertificadoPFX cm) {

		this.cm = cm;

	}

	private void selecionar(int index, String[] vals, Node pai, Node atual, OnSelect evento) {

		if (index == vals.length) {

			try {

				evento.acao(pai, atual);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return;

		}

		String el = vals[index];

		NodeList lst = atual.getChildNodes();

		for (int i = 0; i < lst.getLength(); i++) {

			Node n = lst.item(i);

			if (n.getNodeName().equals(el)) {

				this.selecionar(index + 1, vals, atual, n, evento);

			}

		}

	}

	public String assinar(String xml, String tag, String refAtr) throws ParserConfigurationException, SAXException,
			IOException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, TransformerException, CertificateException, KeyStoreException {

		String[] tags = tag.split("\\|");

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder df = dbf.newDocumentBuilder();
		Document documento = df.parse(new ByteArrayInputStream(xml.getBytes()));
		
		final XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");

		ArrayList<Transform> transformList = new ArrayList<Transform>();
		TransformParameterSpec tps = null;
		Transform envelopedTransform = signatureFactory.newTransform(Transform.ENVELOPED, tps);
		Transform c14NTransform = signatureFactory.newTransform("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps);

		transformList.add(envelopedTransform);
		transformList.add(c14NTransform);

		KeyInfoFactory keyInfoFactory = signatureFactory.getKeyInfoFactory();
		ArrayList<X509Certificate> x509Content = new ArrayList<X509Certificate>();

		x509Content.add(this.cm.getPublicKey());
		X509Data x509Data = keyInfoFactory.newX509Data(x509Content);
		KeyInfo ki = keyInfoFactory.newKeyInfo(Collections.singletonList(x509Data));

		final OnSelect assina = new OnSelect() {

			@Override
			public void acao(Node pai, Node elemento) throws NoSuchAlgorithmException,
					InvalidAlgorithmParameterException, MarshalException, XMLSignatureException, KeyStoreException, CertificateException, IOException {
			
				org.w3c.dom.Element elemento_ = (org.w3c.dom.Element) elemento;
				elemento_.setIdAttribute(refAtr,true);
				
				Reference ref = signatureFactory.newReference("#" + elemento_.getAttribute(refAtr),
						signatureFactory.newDigestMethod(DigestMethod.SHA1, null), transformList, null, null);

				SignedInfo si = signatureFactory.newSignedInfo(
						signatureFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
								(C14NMethodParameterSpec) null),
						signatureFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
						Collections.singletonList(ref));

				XMLSignature assinatura = signatureFactory.newXMLSignature(si, ki);

				DOMSignContext context = new DOMSignContext(cm.getPrivateKey(), pai);

				assinatura.sign(context);

			}

		};

		this.selecionar(0, tags, null, documento.getChildNodes().item(0), assina);

		return this.outputDocument(documento);

	}

	private String outputDocument(Document doc) throws TransformerException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));
		String xml = os.toString();
		if ((xml != null) && (!"".equals(xml))) {
			xml = xml.replaceAll("\\r\\n", "");
			xml = xml.replaceAll(" standalone=\"no\"", "");
		}

		return xml;
	}

}
