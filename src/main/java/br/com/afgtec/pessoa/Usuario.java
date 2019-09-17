package br.com.afgtec.pessoa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.afgtec.usuario.Permissao;
import br.com.afgtec.usuario.TipoPermissao;

@Entity
public class Usuario extends PessoaFisica{ 
 
	@Column
	private String usuario;
	
	@Column
	private String senha;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="id_usuario")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Permissao> permissoes;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_empresa")
	private Empresa empresa;
	
	public Usuario() {
		
		this.permissoes = new ArrayList<Permissao>();
		
	}
	
	public Permissao getPermissao(TipoPermissao tp) {
		
		
		List<Permissao> p = this.permissoes.stream().filter(x->x.getTipo().equals(tp)).collect(Collectors.toList());
		
		if(p.size()>0)
			return p.get(0);
		
		Permissao falsa = new Permissao();
		falsa.setTipo(tp);
		
		return falsa;
		
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	
}
