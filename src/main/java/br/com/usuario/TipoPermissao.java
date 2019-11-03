package br.com.usuario;

public enum TipoPermissao {
	
	FRENTE_CAIXA(1),
	NOTAS(2),
	GERENCIA_CAIXAS(3),
	CADASTRO_PESSOA(4),
	FINANCEIRO(5),
	CADASTRO_PRODUTO(6),
	CADASTRO_OPERACAO(7),
	CADASTRO_HISTORICO(8),
	CONFIGURACAO_EMPRESA(9),
	DADOS_CONTABEIS_PRODUTO(10),
	CADASTRO_CFOP(11),
	CADASTRO_NCM(12),
	CADASTRO_CODIGO_BARRA(13),
	APROVACAO_XML(14),
	ASSOCIACAO_COMANDA(15),
	CONTRATAR_CONTABILIDADE(16);
	
	private int id;
	
	private TipoPermissao(int id) {
		
		this.id = id;
		
	}
	
	public int getId() {
		
		return this.id;
		
	}
	
}
