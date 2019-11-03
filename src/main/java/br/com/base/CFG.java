package br.com.base;

import br.com.caixa.ConfiguracaoLocalCaixa;
import br.com.emissao.SAT;
import br.com.empresa.Empresa;

public class CFG {

	public static SAT moduloSat;

	public static ConfiguracaoLocalCaixa clc;

	public static String lookAndFeel = "com.jtattoo.plaf.acryl.AcrylLookAndFeel";

	public static Empresa empresa;
	
	private CFG() {

	}
	
	public static void main(String[] args) {
		
		System.out.println(CFG.cpfValido("38659152808"));
		
	}

	public static boolean cpfValido(String cpf) {

		try {

			cpf = cpf.replaceAll("\\.", "").replaceAll("-", "");

			if (cpf.length() != 11) {

				return false;

			}

			int pd = 0;

			for (int i = 0, k = 10; i < 9; i++, k--) {

				pd += k * Integer.parseInt(cpf.charAt(i) + "");

			}

			pd = 11 - pd % 11;

			pd = (pd > 9) ? 0 : pd;

			
			if (Integer.parseInt(cpf.charAt(9) + "") != pd) {

				return false;

			}

			pd = 0;
			
			for (int i = 0, k = 11; i < 10; i++, k--) {

				pd += k * Integer.parseInt(cpf.charAt(i) + "");

			}

			pd = 11 - pd % 11;
			
			pd = (pd > 9) ? 0 : pd;
			

			if (Integer.parseInt(cpf.charAt(10) + "") != pd) {

				return false;

			}

			return true;

		} catch (Exception exx) {

			exx.printStackTrace();

			return false;

		}

	}

}
