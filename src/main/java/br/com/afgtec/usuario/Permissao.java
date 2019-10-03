package br.com.afgtec.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="permissoes")
public class Permissao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	@Enumerated(EnumType.ORDINAL)
	@Column
	private TipoPermissao tipo;
	
	@Column
	private boolean incluir;
	
	@Column
	private boolean alterar;
	
	@Column
	private boolean excluir;
	
	@Column
	private boolean consultar;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public TipoPermissao getTipo() {
		return tipo;
	}

	public void setTipo(TipoPermissao tipo) {
		this.tipo = tipo;
	}

	public boolean isIncluir() {
		return incluir;
	}

	public void setIncluir(boolean incluir) {
		this.incluir = incluir;
	}

	public boolean isAlterar() {
		return alterar;
	}

	public void setAlterar(boolean alterar) {
		this.alterar = alterar;
	}

	public boolean isExcluir() {
		return excluir;
	}

	public void setExcluir(boolean excluir) {
		this.excluir = excluir;
	}

	public boolean isConsultar() {
		return consultar;
	}

	public void setConsultar(boolean consultar) {
		this.consultar = consultar;
	}
	
	

}
