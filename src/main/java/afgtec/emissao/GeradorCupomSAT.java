package afgtec.emissao;

import br.com.afgtec.notas.Nota;

public interface GeradorCupomSAT {

	public void gerarCupomFiscal(Nota nota, String base64);
	
}
