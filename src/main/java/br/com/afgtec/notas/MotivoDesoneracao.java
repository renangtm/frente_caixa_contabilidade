package br.com.afgtec.notas;

public enum MotivoDesoneracao {

	USO_AGROPECUARIA(3), OUTROS(9), ORGAO_FOMENTO_DESENVOLVIMENTO_AGROPECUARIO(12);

	private int id;

	private MotivoDesoneracao(int id) {

		this.id = id;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
