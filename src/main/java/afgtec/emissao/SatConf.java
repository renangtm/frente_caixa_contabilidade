package afgtec.emissao;

import java.util.Calendar;

public class SatConf {

	private Calendar ultimaAlteracao;
	
	private boolean ativo;
	
	public SatConf(){
		
		this.ultimaAlteracao = Calendar.getInstance();
		
	}
	
	public Calendar getUltimaAlteracao() {
		return ultimaAlteracao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ultimaAlteracao = Calendar.getInstance();
		this.ativo = ativo;
	}
	
	
	
}
