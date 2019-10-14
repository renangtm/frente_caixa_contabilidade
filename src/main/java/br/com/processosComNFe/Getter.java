package br.com.processosComNFe;

import javax.persistence.EntityManager;

import br.com.empresa.Empresa;

public interface Getter {

	public void executar(Empresa empresa,EntityManager et);
	
}
