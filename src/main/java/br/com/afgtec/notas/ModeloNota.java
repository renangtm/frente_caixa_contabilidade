package br.com.afgtec.notas;

public enum ModeloNota {

	NFE(55),
	NFCE(65);
	
	private int id;
	
	private ModeloNota(int id){
		
		this.id = id;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
