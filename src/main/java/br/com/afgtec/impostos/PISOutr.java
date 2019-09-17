package br.com.afgtec.impostos;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PISOutr extends Pis {

	@Column
	private double alicotaPis;

	@Column
	private double porcentagemBaseCalculo;

	@Column
	private double valorBaseCalculo;

	@Column
	private double quantidadeVendida;

	@Column
	private double aliquotaProduto;

	@Column
	private double valorPis;

	public PISOutr() {

		this.setCst("99");

	}

	public double getPorcentagemBaseCalculo() {
		return porcentagemBaseCalculo;
	}

	public void setPorcentagemBaseCalculo(double porcentagemBaseCalculo) {
		this.porcentagemBaseCalculo = porcentagemBaseCalculo;
	}

	public double getQuantidadeVendida() {
		return quantidadeVendida;
	}

	public void setQuantidadeVendida(double quantidadeVendida) {
		this.quantidadeVendida = quantidadeVendida;
	}

	public double getAliquotaProduto() {
		return aliquotaProduto;
	}

	public void setAliquotaProduto(double aliquotaProduto) {
		this.aliquotaProduto = aliquotaProduto;
	}

	public void setAlicotaPis(double alicotaPis) {
		this.alicotaPis = alicotaPis;
	}

	public double getAlicotaPis() {
		return alicotaPis;
	}

	public double getValorBaseCalculo() {
		return valorBaseCalculo;
	}

	@Override
	public void calcularSobre(double valor) {

		if (this.quantidadeVendida <= 0) {

			this.valorBaseCalculo = valor * (this.porcentagemBaseCalculo / 100);
			this.valorPis = this.valorBaseCalculo * (this.alicotaPis / 100);

		} else {

			this.valorPis = this.aliquotaProduto * this.quantidadeVendida;

		}

	}

	@Override
	public double getValor() {

		return this.valorPis;

	}

	@Override
	public Object clone() {

		PISOutr p = new PISOutr();
		p.alicotaPis = this.alicotaPis;
		p.porcentagemBaseCalculo = this.porcentagemBaseCalculo;
		p.valorBaseCalculo = this.valorBaseCalculo;
		p.valorPis = this.valorPis;
		p.valorBaseCalculo=this.valorBaseCalculo;
		p.quantidadeVendida=this.quantidadeVendida;
		p.aliquotaProduto=this.aliquotaProduto;
		
		return p;

	}

}
