package br.com.produto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@Entity
public class ValePresente extends Produto {

	public static int getIdVale(String codigo) {

		StringBuilder sb = new StringBuilder();

		byte[] arr = Base64.decode(codigo);

		byte b = (byte) 0xe3;

		for (int i = 0; i < arr.length; i++) {

			char c = (char) (arr[i] ^ b);

			sb.append(c);

		}

		String[] dados = sb.toString().split("\\|");

		if (!dados[2].equals("45852123")) {

			throw new RuntimeException("Codigo invalido");

		}

		return Integer.parseInt(dados[0]);

	}

	@Column
	private String token;

	@Column
	private double valorVale;

	@OneToMany(mappedBy = "vale", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<RetiradaValePresente> retiradas;

	public String gerarCodigo(int numero) {

		String str = this.getId() + "|" + numero + "|45852123";

		while (str.length() < 50)
			str = "0" + str;

		byte b = (byte) 0xe3;

		byte[] arr = new byte[str.length()];

		for (int i = 0; i < str.length(); i++) {

			arr[i] = (byte) (((byte) str.charAt(i)) ^ b);

		}

		return Base64.encode(arr);

	}

	public double getSaldo(String codigo) {

		int id = ValePresente.getIdVale(codigo);

		if (id != this.getId()) {

			throw new RuntimeException("Codigo do vale diferente");

		}

		return this.valorVale - this.retiradas.parallelStream().filter(r -> r.getCodigo().equals(codigo))
				.mapToDouble(r -> r.getValor()).sum();

	}

	public RetiradaValePresente descontarValor(double valor, String codigo) {

		int id = ValePresente.getIdVale(codigo);

		if (id != this.getId()) {

			throw new RuntimeException("Codigo do vale diferente");

		}

		if (this.getSaldo(codigo) < valor) {

			throw new RuntimeException("Saldo menor que a retirada");

		}

		RetiradaValePresente rvp = new RetiradaValePresente();
		rvp.setCodigo(codigo);
		rvp.setVale(this);
		rvp.setValor(valor);

		this.retiradas.add(rvp);

		return rvp;

	}

}
