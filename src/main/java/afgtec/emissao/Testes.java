package afgtec.emissao;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateException;

import javax.persistence.EntityManager;

import afgtec.geradoresCupom.GeradorCupomSATModelo1;
import br.com.afgtec.base.ET;
import br.com.afgtec.notas.Nota;
import br.com.afgtec.notas.NotaService;

public class Testes {
	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException, SignatureException, IOException {
		
		EntityManager et = ET.nova();
		
		Nota nota = et.find(Nota.class, 1);
		
		SAT sat = new SAT(nota.getEmpresa());
		
		sat.iniciar();
		
		ValidadorDocumento vd = new ValidadorDocumento(new NotaService(et),sat,new GeradorCupomSATModelo1());
		
		vd.validarFiscalmente(nota);
		
		
	}

}
