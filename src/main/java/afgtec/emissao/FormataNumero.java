package afgtec.emissao;

public class FormataNumero {

	private int casas;

	public FormataNumero(int casas) {

		this.casas = casas;

	}

	public String formatar(double n) {

		String[] partes = (n + "").split("\\.");

		if (partes[1].length() > this.casas) {

			partes[1] = partes[1].substring(0, this.casas);

		} else if (partes[1].length() < this.casas) {

			while (partes[1].length() < this.casas) {

				partes[1] += "0";

			}

		}

		return partes[0] + "." + partes[1];

	}

}
