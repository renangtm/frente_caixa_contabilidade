package br.com.utilidades;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

public class U {

	public static BufferedImage resizeImage(final Image image, int width, int height) {
		final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setComposite(AlphaComposite.Src);
		// below three lines are for RenderingHints for better image quality at
		// cost of higher processing time
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.drawImage(image, 0, 0, width, height, null);
		graphics2D.dispose();
		return bufferedImage;
	}

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
