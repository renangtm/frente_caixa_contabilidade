package br.com.empresa;

public enum Look {
	
	ACRYL("com.jtattoo.plaf.acryl.AcrylLookAndFeel"),
	ALUMINIO("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel"),
	PRETO("com.jtattoo.plaf.hifi.HiFiLookAndFeel"),
	AZULLADO("com.jtattoo.plaf.azulado.McWinLookAndFeel"),
	SMART("com.jtattoo.plaf.smart.SmartLookAndFeel"),
	AZUL("com.jtattoo.plaf.luna.LunaLookAndFeel");
	
	private String lookAndFell;
	
	private Look(String look){
		
		this.lookAndFell = look;
		
	}

	public String getLookAndFell() {
		return lookAndFell;
	}

	public void setLookAndFell(String lookAndFell) {
		this.lookAndFell = lookAndFell;
	}

	
	
}
