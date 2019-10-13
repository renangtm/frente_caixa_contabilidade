package br.com.emissao;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateException;

import javax.persistence.EntityManager;

import br.com.base.ET;
import br.com.impressao.GeradorCupomSATModelo1;
import br.com.nota.Nota;
import br.com.nota.NotaService;
import br.com.webServices.TabelaImpostoAproximado;

public class Testes {
	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException, SignatureException, IOException {
		
		EntityManager et = ET.nova();
		
		Nota nota = et.find(Nota.class, 1);
		
		SAT sat = new SAT(nota.getEmpresa());
		
		sat.iniciar();
		
		ValidadorDocumento vd = new ValidadorDocumento(new NotaService(et),sat,new GeradorCupomSATModelo1(), new TabelaImpostoAproximado());
		
		vd.validarFiscalmente(nota);
		
		
	}

}
