package br.com.agrofauna.utilidades;

import java.util.Calendar;

public class Tempo {

	private Tempo(){
		
	}
	
	public static String getDataAtual(){
		
		Calendar agora = Calendar.getInstance();
		
		
		return agora.get(Calendar.DAY_OF_MONTH)+"/"+(agora.get(Calendar.MONTH)+1)+"/"+agora.get(Calendar.YEAR);
		
		
	}
	
	public static String getHorarioAtual(){
		
		Calendar agora = Calendar.getInstance();
		
		return agora.get(Calendar.HOUR_OF_DAY)+":"+agora.get(Calendar.MINUTE);
		
	}
	
}
