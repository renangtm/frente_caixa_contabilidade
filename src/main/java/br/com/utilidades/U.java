package br.com.utilidades;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

public class U {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List map(List lista, Listener lst) {

		for (int i = 0; i < lista.size(); i++) {

			Object x = lst.map(lista.get(i));

			lista.set(i, x);

		}

		return lista;

	}

	public static String getMACMaquina() {

		InetAddress ip;

		try {

			ip = InetAddress.getLocalHost();

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}

			return sb.toString();

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (SocketException e) {

			e.printStackTrace();

		}

		return "00-00-00-00-00";

	}

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

	private U() {

	}

	public static String putCnpjMask(String str) {
		return U.mask("##.###.###/####-##", str);
	}

	public static String rm(String str) {
		return str.replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("\\.", "").replaceAll("\\/", "")
				.replaceAll("\\\\", "");
	}

}
