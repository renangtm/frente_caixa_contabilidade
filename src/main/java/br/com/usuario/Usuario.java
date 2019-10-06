package br.com.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.pessoa.PessoaFisica;

@Entity
public class Usuario{ 
 
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(fetch=FetchType.EAGER,cascade={ CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinColumn(name="id_pessoa")
	private PessoaFisica pf;
	
	@Column
	private String usuario;
	
	@Column
	private String senha;
	
	//mappedBy='usuario'
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="id_usuario")
	private List<Permissao> permissoes;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PessoaFisica getPf() {
		return this.pf;
	}

	public void setPf(PessoaFisica pf) {
		this.pf = pf;
	}

	public Usuario() {
		
		this.permissoes = new ArrayList<Permissao>();
		this.pf = new PessoaFisica();
		
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

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	
}
