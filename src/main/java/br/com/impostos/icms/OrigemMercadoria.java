package br.com.impostos.icms;

public enum OrigemMercadoria {
	
	NACIONAL(0),
	ESTRANGEIRA_IMPORTACAO_DIRETA(1),
	ESTRANGEIRA_MERCADO_INTERNO(2),
	NACIONAL_CONTEUDO_IMPORTACAO_SUPERIOR_40(3),
	NACIONAL_CONFORMIDADE_PROCESSOS_PRODUTIVOS(4),
	NACIONAL_CONTEUDO_IMPORTACAO_INFERIOR_40(5),
	ESTRANGEIRA_IMPORTACAO_DIRETA_SEM_SIMILAR(6),
	ESTRANGEIRA_MERCADO_INTERNO_SEM_SIMILAR(7),
	NACIONAL_IMPORTACAO_SUPERIOR_70(8);
	
	private int id;
	
	private OrigemMercadoria(int id) {
		
		this.setId(id);
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	
}
