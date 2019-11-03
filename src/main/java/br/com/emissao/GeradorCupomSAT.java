package br.com.emissao;

import java.util.List;

import br.com.inicial.Pagamento;
import br.com.nota.Nota;

public interface GeradorCupomSAT {

	public void gerarCupomFiscal(Nota nota, double impostosAproximados, List<Pagamento> pagamentos, String base64);
	
}
