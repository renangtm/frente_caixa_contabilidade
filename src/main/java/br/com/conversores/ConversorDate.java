package br.com.conversores;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ConversorDate implements Conversor<Date> {
	
	
	private static long milenio_atual = 0;
	
	static{
		
		milenio_atual = ((Calendar.getInstance().getTimeInMillis()/1000/60/525600)+1970)/1000;

	}

	public boolean podeConverter(@SuppressWarnings("rawtypes") Class classe) {
		return classe.equals(Date.class);
	}

	public String paraString(Date data) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		return (calendario.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "") + calendario.get(Calendar.DAY_OF_MONTH) + " / "
				+ ((calendario.get(Calendar.MONTH) + 1) < 10 ? "0" : "") + (calendario.get(Calendar.MONTH) + 1) + " / "
				+ calendario.get(Calendar.YEAR);
	}

	public String paraString(Calendar calendario) {
		return (calendario.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "") + calendario.get(Calendar.DAY_OF_MONTH) + " / "
				+ ((calendario.get(Calendar.MONTH) + 1) < 10 ? "0" : "") + (calendario.get(Calendar.MONTH) + 1) + " / "
				+ calendario.get(Calendar.YEAR);
	}

	@SuppressWarnings("deprecation")
	public Date paraObjeto(String string) throws Exception {

		String[] dados = string.split("/", 3);

		int dia;
		int mes;
		int ano;

		try {
			dia = Integer.parseInt(dados[0].replaceAll(" ", ""));

			mes = Integer.parseInt(dados[1].replaceAll(" ", ""));

			ano = Integer.parseInt(dados[2].replaceAll(" ", ""));

		} catch (Exception e) {
			throw new Exception("Data em formato incorreto");
		}

		if (dia < 1 || dia > 31)
			throw new Exception("Data em formato incorreto");

		if (mes < 1 || mes > 12)
			throw new Exception("Data em formato incorreto");

		if (ano < 0)
			throw new Exception("Data em formato incorreto");

		if (ano < 1000)
			ano += ConversorDate.milenio_atual*1000;

		mes -= 1;
		return new Date(ano - 1900, mes, dia);

	}

}
