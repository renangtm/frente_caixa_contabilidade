package br.com.produto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@Entity
public class ValePresente extends Produto {

	public static void main(String[] args) {


		System.out.println(ValePresente.getIdVale("09PT0tbUn9Kf0w=="));

	}

	public static int getIdVale(String codigo) {

		try {

			StringBuilder sb = new StringBuilder();

			byte[] arr = Base64.decode(codigo);

			byte b = (byte) 0xe3;

			for (int i = 0; i < arr.length; i++) {

				char c = (char) (arr[i] ^ b);

				sb.append(c);

			}

			String[] dados = sb.toString().split("\\|");

			int id = Integer.parseInt(dados[0]);

			int numero = Integer.parseInt(dados[1]);

			if (!dados[2].equals(((45654 * (numero + id)) % numero) + "")) {

				throw new RuntimeException("Codigo invalido");

			}

			return id;

		} catch (Exception ex) {

			ex.printStackTrace();
			throw new RuntimeException("Codigo invalido");

		}

	}

	public List<RepTokenVale> getTokens() {

		List<RepTokenVale> lst = new ArrayList<RepTokenVale>();

		for (int i = 0; i < this.estoqueInicial; i++) {

			RepTokenVale rep = new RepTokenVale();
			rep.setToken(this.gerarCodigo(i + 1));
			rep.setValor(this.getSaldo(rep.getToken()));

			lst.add(rep);

		}

		return lst;

	}

	@Column
	private double valorVale;

	@Column
	private int estoqueInicial;

	@OneToMany(mappedBy = "vale", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<RetiradaValePresente> retiradas;

	public String gerarCodigo(int numero) {

		String str = this.getId() + "|" + numero + "|" + ((45654 * (numero + this.getId())) % numero);

		while (str.length() < 10)
			str = "0" + str;

		byte b = (byte) 0xe3;

		byte[] arr = new byte[str.length()];

		for (int i = 0; i < str.length(); i++) {

			arr[i] = (byte) (((byte) str.charAt(i)) ^ b);

		}

		return Base64.encode(arr);

	}

	public ValePresente() {

		this.retiradas = new ArrayList<RetiradaValePresente>();

	}

	public double getSaldo(String codigo) {

		int id = ValePresente.getIdVale(codigo);

		if (id != this.getId()) {

			throw new RuntimeException("Codigo do vale diferente");

		}

		return this.valorVale - this.retiradas.parallelStream().filter(r -> r.getCodigo().equals(codigo))
				.mapToDouble(r -> r.getValor()).sum();

	}

	public int getEstoqueInicial() {
		return estoqueInicial;
	}

	public void setEstoqueInicial(int estoqueInicial) {
		this.estoqueInicial = estoqueInicial;
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

	public double getValorVale() {
		return valorVale;
	}

	public void setValorVale(double valorVale) {
		this.valorVale = valorVale;
	}

	public List<RetiradaValePresente> getRetiradas() {
		return retiradas;
	}

	public void setRetiradas(List<RetiradaValePresente> retiradas) {
		this.retiradas = retiradas;
	}

}
