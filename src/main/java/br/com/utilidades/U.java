package br.com.utilidades;

public class U {
	
	private static String mask(String pattern, String txt) {

		String str = "";

		for (int i = 0; i < pattern.length(); i++) {
			char c = pattern.charAt(i);
			if (c != '#') {
				txt = txt.replaceAll("\\" + c + "", "");
			}
		}

		for (int i = 0, j = 0; i < pattern.length(); i++) {
			char c = pattern.charAt(i);
			if (c == '#') {
				str += ((i - j) < txt.length()) ? txt.charAt(i - j) : ' ';
			} else if (c != (((i - j) < txt.length()) ? txt.charAt(i - j) : ' ')) {
				str += c;
				j++;
			} else {
				str += c;
			}
		}

		return str;
	}
	
	private U(){
		
	}
	
	public static String putCnpjMask(String str){
		return U.mask("##.###.###/####-##", str);
	}
	
	public static String rm(String str){
		return str.replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("\\.", "").replaceAll("\\/", "").replaceAll("\\\\", "");
	}

}
