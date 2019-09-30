package br.com.afgtec.produto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.afgtec.impostos.COFINSAliq1;
import br.com.afgtec.impostos.Cofins;
import br.com.afgtec.impostos.ICMS00;
import br.com.afgtec.impostos.Icms;
import br.com.afgtec.impostos.PISAliq1;
import br.com.afgtec.impostos.Pis;
import br.com.afgtec.impostos.TabelaAlicotas;
import br.com.afgtec.impostos.TabelaCfop;
import br.com.agrofauna.utilidades.Campo;

@Entity
@Table(name = "categoria")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Campo(nome = "Codigo", lista = true, editavel = false)
	private int id;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_icms")
	private Icms icms;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_pis")
	private Cofins cofins;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_cofins")
	private Pis pis;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tabela_alicota")
	private TabelaAlicotas tabelaAlicota;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tabela_cfop")
	private TabelaCfop tabelaCfop;

	@Column
	private boolean loja_virtual;

	public Categoria() {

		this.tabelaAlicota = new TabelaAlicotas();

		this.tabelaCfop = new TabelaCfop();

		this.pis = new PISAliq1();

		this.cofins = new COFINSAliq1();

		this.icms = new ICMS00();

	}

	public TabelaAlicotas getTabelaAlicota() {
		return tabelaAlicota;
	}

	public void setTabelaAlicota(TabelaAlicotas tabelaAlicota) {
		this.tabelaAlicota = tabelaAlicota;
	}

	public TabelaCfop getTabelaCfop() {
		return tabelaCfop;
	}

	public void setTabelaCfop(TabelaCfop tabelaCfop) {
		this.tabelaCfop = tabelaCfop;
	}

	public Cofins getCofins() {
		return cofins;
	}

	public void setCofins(Cofins cofins) {
		this.cofins = cofins;
	}

	public Pis getPis() {
		return pis;
	}

	public void setPis(Pis pis) {
		this.pis = pis;
	}

	public Icms getIcms() {
		return icms;
	}

	public void setIcms(Icms icms) {
		this.icms = icms;
	}

	public boolean isLoja_virtual() {
		return loja_virtual;
	}

	public void setLoja_virtual(boolean loja_virtual) {
		this.loja_virtual = loja_virtual;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public Object clone() {

		Categoria c = new Categoria();
		c.id = 0;
		c.cofins = (Cofins) this.cofins.clone();
		c.icms = (Icms) this.icms.clone();
		c.id = this.id;
		c.pis = (Pis) this.pis.clone();
		c.loja_virtual = this.loja_virtual;
		c.tabelaAlicota = (TabelaAlicotas) this.tabelaAlicota.clone();
		c.tabelaCfop = (TabelaCfop) this.tabelaCfop.clone();

		return c;

	}

}
