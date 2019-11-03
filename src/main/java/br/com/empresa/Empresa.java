package br.com.empresa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.base.ET;
import br.com.codigo_barra.PadraoCodigo;
import br.com.endereco.Endereco;
import br.com.pessoa.PessoaJuridica;
import br.com.usuario.PresetPermissao;
import br.com.usuario.Usuario;
import br.com.usuario.UsuarioService;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
	private List<PadraoCodigo> padroesCodigo;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_parametros")
	private ParametrosEmissao parametrosEmissao;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_logo")
	private Visual logo;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_pessoa")
	private PessoaJuridica pj;

	@ManyToOne
	private Contabilidade contabilidade;

	@Column
	private String tokenAPIImpostosAproximados;

	public static void main(String[] args) {

		EntityManager et = ET.nova();

		Usuario u = et.find(Usuario.class, 1);
		Endereco e = u.getPf().getEmpresa().getPj().getEndereco();

		Contabilidade c = et.find(Contabilidade.class, 2);
		c.setParametrosEmissao(new ParametrosEmissao());

		Endereco ee = new Endereco();
		ee.setBairro(e.getBairro());
		ee.setCep(e.getCep());
		ee.setCidade(e.getCidade());
		ee.setComplemento(e.getComplemento());
		ee.setNumero(e.getNumero());
		ee.setRua(e.getRua());

		Usuario us = new Usuario();

		us.setUsuario("admcontabilidade");
		us.setSenha("123456");
		us.setPermissoes(PresetPermissao.CONTADOR.getPreset());

		us.getPf().setNome("Teste Adm Usuario Contabilidade");
		us.getPf().setCpf("37484721203");
		us.getPf().setData_nascimento(Calendar.getInstance());
		us.getPf().setEmail("teste@teste.com.br");
		us.getPf().setEmpresa(c);
		us.getPf().setEndereco(ee);
		us.getPf().setRg("12344321");
		us.getPf().setUsuario(us);

		us = new UsuarioService(et).merge(us);

		et.getTransaction().begin();
		et.getTransaction().commit();

	}

	public String getTokenAPIImpostosAproximados() {
		return tokenAPIImpostosAproximados;
	}

	public Contabilidade getContabilidade() {
		return contabilidade;
	}

	public void setContabilidade(Contabilidade contabilidade) {
		this.contabilidade = contabilidade;
	}

	public void setTokenAPIImpostosAproximados(String tokenAPIImpostosAproximados) {
		this.tokenAPIImpostosAproximados = tokenAPIImpostosAproximados;
	}

	public Empresa() {

		this.pj = new PessoaJuridica();
		this.parametrosEmissao = new ParametrosEmissao();
		this.logo = new Visual();
		this.padroesCodigo = new ArrayList<PadraoCodigo>();

	}

	public ParametrosEmissao getParametrosEmissao() {
		return parametrosEmissao;
	}

	public void setParametrosEmissao(ParametrosEmissao parametrosEmissao) {
		this.parametrosEmissao = parametrosEmissao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PessoaJuridica getPj() {
		return pj;
	}

	public void setPj(PessoaJuridica pj) {
		this.pj = pj;
	}

	public Visual getLogo() {
		return logo;
	}

	public void setLogo(Visual logo) {
		this.logo = logo;
	}

	public List<PadraoCodigo> getPadroesCodigo() {
		return padroesCodigo;
	}

	public void setPadroesCodigo(List<PadraoCodigo> padroesCodigo) {
		this.padroesCodigo = padroesCodigo;
	}

	@Override
	public String toString() {

		return this.getPj().getNome() + ", CNPJ: " + this.getPj().getCnpj();

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
		Empresa other = (Empresa) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
