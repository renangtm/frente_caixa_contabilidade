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
	CONFIGURACAO_EMPRESA(9);
	
	private int id;
	
	private TipoPermissao(int id) {
		
		this.id = id;
		
	}
	
	public int getId() {
		
		return this.id;
		
	}
	
}
