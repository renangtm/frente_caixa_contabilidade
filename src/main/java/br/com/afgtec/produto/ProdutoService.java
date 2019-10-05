package br.com.afgtec.produto;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;

import br.com.afgtec.base.ET;
import br.com.afgtec.base.Service;
import br.com.afgtec.pessoa.Produto;
import br.com.afgtec.usuario.Usuario;
import br.com.afgtec.usuario.UsuarioService;
import br.com.empresa.Empresa;

public class ProdutoService implements Service<Produto>{

	private EntityManager et;
	
	private boolean comEstoque = false;
	
	public ProdutoService(EntityManager et) {
		
		this.et = et;
		
	}
	
	
	
	public boolean isComEstoque() {
		return comEstoque;
	}



	public void setComEstoque(boolean comEstoque) {
		this.comEstoque = comEstoque;
	}



	public Produto getProduto(String codigo) {
		
		@SuppressWarnings("unchecked")
		List<Produto> produtos = (List<Produto>)(List<?>)et.createQuery("SELECT p FROM Produto p WHERE p.codigo_barra = :codigo")
		.setParameter("codigo", codigo)
		.getResultList();
		
		if(produtos.size() == 0) {
			
			return null;
			
		}
		
		return (Produto)produtos.get(0);
		
	}
	
	

	private Empresa empresa;
	
	public void setEmpresa(Empresa emp) {
		
		this.empresa = emp;
		
	}
	
	public static void main(String[] args) {
		
		UsuarioService us = new UsuarioService(ET.nova());
		
		Usuario u = us.getUsuario("teste", "teste");
		
		ProdutoService ps = new ProdutoService(ET.nova());
		ps.setEmpresa(u.getPf().getEmpresa());
		
		System.out.println(ps.getCount("")+"");
		
	}
	
	@Override
	public int getCount(String filtro) {
		
		if(this.empresa == null) {
			
			throw new RuntimeException("Service sem empresa");
			
		}
		
		Query qr = et.createQuery("SELECT COUNT(*) FROM Produto p WHERE p.empresa.id = :empresa AND (p.nome LIKE :nome) "+(this.isComEstoque()?" AND p.estoque.disponivel>0":""));
		qr.setParameter("empresa", this.empresa.getId());
		qr.setParameter("nome", "%"+filtro+"%");
		
		@SuppressWarnings("unchecked")
		List<Long> lista = (List<Long>)(List<?>)qr.getResultList();
		
		return Integer.parseInt(lista.get(0)+"");
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Produto> getElementos(int x1, int x2, String filtro,String ordem) {
		
		if(this.empresa == null) {
			
			throw new RuntimeException("Service sem empresa");
			
		}	
		
		ordem = ordem.replaceAll("\\{\\{et\\}\\}", "p");
		
		
		Query qr = et.createQuery("SELECT p FROM Produto p WHERE p.empresa.id = :empresa AND (p.nome LIKE :nome)"+(this.isComEstoque()?" AND p.estoque.disponivel>0":"")+(ordem != ""?" ORDER BY "+ordem:""));
		qr.setParameter("empresa", this.empresa.getId());
		qr.setParameter("nome", "%"+filtro+"%");
		qr.setFirstResult(x1);
		qr.setMaxResults(x2-x1);
		
		return (List<Produto>)(List<?>)qr.getResultList();
		
	}

	@Override
	public Produto getPeloCodigo(String str) {
		// TODO Auto-generated method stub
		return this.getProduto(str);
	}

	@Override
	public void lixeira(Produto obj) {
		// TODO Auto-generated method stub
		((Session)this.et.getDelegate()).evict(obj);
	}

	
	
	
}


