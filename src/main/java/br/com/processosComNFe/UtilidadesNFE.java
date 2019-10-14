package br.com.processosComNFe;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

public class UtilidadesNFE {
	
private static final String[] ESTADOS = {"AC","AL","AM","AP","BA","CE","DF","ES","GO","MA","MT","MS","MG","PA","PB","PR","PE","PI","RN","RS","RJ","RO","RR","SC","SP","SE","TO","DF"};
	
	private static final double[][] ALICOTAS = {
			{17,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,4},
			{12,18,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,4},
			{12,12,18,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,4},
			{12,12,12,18,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,4},
			{12,12,12,12,18,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,4},
			{12,12,12,12,12,18,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,4},
			{12,12,12,12,12,12,18,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,4},
			{12,12,12,12,12,12,12,17,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,4},
			{12,12,12,12,12,12,12,12,17,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,4},
			{12,12,12,12,12,12,12,12,12,18,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,4},
			{12,12,12,12,12,12,12,12,12,12,17,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,4},
			{12,12,12,12,12,12,12,12,12,12,12,17,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,4},
			{7,7,7,7,7,7,7,7,7,7,7,7,18,7,7,12,7,7,7,12,12,7,7,12,12,7,7,4},
			{12,12,12,12,12,12,12,12,12,12,12,12,12,17,12,12,12,12,12,12,12,12,12,12,12,12,12,4},
			{12,12,12,12,12,12,12,12,12,12,12,12,12,12,18,12,12,12,12,12,12,12,12,12,12,12,12,4},
			{7,7,7,7,7,7,7,7,7,7,7,7,12,7,7,18,7,7,7,12,12,7,7,12,12,7,7,4},
			{12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,18,12,12,12,12,12,12,12,12,12,12,4},
			{12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,18,12,12,12,12,12,12,12,12,12,4},
			{12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,18,12,12,12,12,12,12,12,12,4},
			{7,7,7,7,7,7,7,7,7,7,7,7,12,7,7,12,7,7,7,18,12,7,7,12,12,7,7,4},
			{7,7,7,7,7,7,7,7,7,7,7,7,12,7,7,12,7,7,7,12,20,7,7,12,12,7,7,4},
			{12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,17.5,12,12,12,12,12,4},
			{12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,17,12,12,12,12,4},
			{7,7,7,7,7,7,7,7,7,7,7,7,12,7,7,12,7,7,7,12,12,7,7,17,12,7,7,4},
			{7,7,7,7,7,7,7,7,7,7,7,7,12,7,7,12,7,7,7,12,12,7,7,12,18,7,7,4},
			{12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,18,12,4},
			{12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,18,4},
			{4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4}
	};

	private UtilidadesNFE() {

	}

	public static String toNFeString(Calendar cal) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ");
		sdf.setTimeZone(cal.getTimeZone());
		String str = sdf.format(cal.getTime());
		
		
		str = str.substring(0, str.length() - 2) + ":" + str.substring(str.length() - 2, str.length());

		return str;

	}

	public static String removerAcentos(String str) {

		String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("");

	}
	
	public static double arredondar(double numero){
		
		return arredondar(numero,2);
		
	}
	
	public static double arredondar(double numero,int casas){
		
		return Math.round((Math.pow(10, casas)*numero))/Math.pow(10, casas);
		
	}
	
	public static void main(String[] args) {
		
		System.out.println(arredondar(468.4556)+"");
		
	}

	public static double getIcms(String origem, String destino) {
		
		int o = 0;
		int d=0;
		
		for(int i=0;i<ESTADOS.length;i++){
			if(ESTADOS[i].equals(origem)){
				o=i;
				break;
			}
		}
		
		for(int i=0;i<ESTADOS.length;i++){
			if(ESTADOS[i].equals(destino)){
				d=i;
				break;
			}
		}
		
		return ALICOTAS[o][d];
		
	}

}
