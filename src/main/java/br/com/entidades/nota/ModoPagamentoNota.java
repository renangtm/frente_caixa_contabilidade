package br.com.entidades.nota;

public enum ModoPagamentoNota {

	A_VISTA(0), A_PRAZO(1), OUTROS(2);

	private int id;

	private ModoPagamentoNota(int id) {

		this.setId(id);

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
