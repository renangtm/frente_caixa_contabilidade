package br.com.emissao;

import br.com.nota.Nota;

public interface GeradorCupomSAT {

	public void gerarCupomFiscal(Nota nota, String base64);
	
}
