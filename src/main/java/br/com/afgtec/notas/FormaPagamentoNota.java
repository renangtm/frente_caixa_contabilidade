package br.com.afgtec.notas;

public enum FormaPagamentoNota {

	A_VISTA(0), A_PRAZO(1), OUTROS(2);

	private int id;

	private FormaPagamentoNota(int id) {

		this.setId(id);

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
