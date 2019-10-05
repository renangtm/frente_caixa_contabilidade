package afgtec.emissao;

import br.com.entidades.nota.Nota;

public interface GeradorCupomSAT {

	public void gerarCupomFiscal(Nota nota, String base64);
	
}
