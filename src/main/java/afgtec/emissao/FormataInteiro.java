package afgtec.emissao;

public class FormataInteiro {

	private int casas;

	public FormataInteiro(int casas) {

		this.casas = casas;

	}

	public String formatar(int n) {

		String nn = ""+n;

		if (nn.length() > this.casas) {

			nn = nn.substring(0, this.casas);

		} else if (nn.length() < this.casas) {

			while (nn.length() < this.casas) {

				nn = "0"+nn;

			}

		}

		return nn;

	}

}
