package br.com.impressao;

import java.util.Calendar;

public interface AgrupadorData {

	public boolean agrupa(Calendar c1, Calendar c2);
	
	public String dateFormat();
	
}
