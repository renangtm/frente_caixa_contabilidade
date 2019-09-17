package br.com.afgtec.base;

import java.text.ParseException;
import java.util.Calendar;

import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

public class Masks {

	private Masks() {

	};

	public static MaskFormatter cpf() {

		try {

			return new MaskFormatter("###.###.###-##");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public static MaskFormatter ie() {

		try {

			return new MaskFormatter("###.###.###.###");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public static MaskFormatter data() {

		try {

			return new MaskFormatter("##/##/####");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public static MaskFormatter cnpj() {

		try {

			return new MaskFormatter("###.###.###/####-##");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public static NumberFormatter moeda() {

		return new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"));

	}

	public static double moeda(String txt) {

		return Double.parseDouble(txt.replaceAll("\\.", "").replaceAll(",", "."));

	}

	public static Calendar getData(String txt) {

		String[] str = txt.replaceAll(" ", "").split("/");

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.DATE, Integer.parseInt(str[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(str[1]) - 1);
		cal.set(Calendar.YEAR, Integer.parseInt(str[2]));

		return cal;

	}

	public static MaskFormatter telefone() {

		try {
			return new MaskFormatter("(##)####-####");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}
