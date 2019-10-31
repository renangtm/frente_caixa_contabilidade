package br.com.produto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.base.ET;
import br.com.base.Service;
import br.com.empresa.Empresa;
import br.com.nota.SaidaEntrada;
import br.com.pessoa.Pessoa;
import br.com.quantificacao.TipoQuantidade;
import br.com.usuario.Usuario;
import br.com.usuario.UsuarioService;

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

	@SuppressWarnings("unchecked")
	public List<ProdutoRelatorio> getProdutoRelatorio(String filtro, Calendar inicio, Calendar fim) {
		
		Query q = et.createQuery("SELECT p, pn.nota.numero,pn.quantidade,pn.valor,pn.tipoInfluenciaEstoque,"
				+ "pn.nota.data_emissao,pes,pn.nota.operacao FROM Produto p "
				+ "LEFT JOIN ProdutoNota pn ON pn.produto=p AND pn.nota.id IN (SELECT n.id FROM Nota n WHERE n.data_emissao >= :inicio) "
				+ "LEFT JOIN Pessoa pes ON pn.nota.destinatario.id = pes.id "
				+ "WHERE p.empresa = :empresa AND p.nome like :filtro");
		q.setParameter("empresa", this.empresa);
		q.setParameter("inicio", inicio);
		q.setParameter("filtro", "%"+filtro+"%");
		
		List<ProdutoRelatorio> produtos = new ArrayList<ProdutoRelatorio>();
		
		for(Object[] linha:(List<Object[]>)(List<?>)q.getResultList()) {
			
			Produto produto = (Produto)linha[0];
			
			ProdutoRelatorio pr = new ProdutoRelatorio();
			pr.setCodigo(produto.getCodigo_barra());
			pr.setValor(produto.getValor());
			pr.setId(produto.getId());
			pr.setMovimentos(new ArrayList<MovimentoProduto>());
			pr.setNome(produto.getNome());
			pr.setQuantidade(produto.getEstoque().getQuantidade());
			pr.setUnidade(produto.getEstoque().getTipo());
			
			if(!produtos.contains(pr)) {
				
				produtos.add(pr);
				
			}else {
				
				pr = produtos.get(produtos.indexOf(pr));
				
			}
			try {
			
				int numero = (int) linha[1];
				double quantidade = (double) linha[2];
				double valor = (double) linha[3];
				TipoQuantidade tp = (TipoQuantidade) linha[4];
				Calendar emissao  = (Calendar) linha[5];
				Pessoa destinatario  = (Pessoa) linha[6];
				SaidaEntrada operacao = (SaidaEntrada) linha[7];
				
				MovimentoProduto mp = new MovimentoProduto();
				mp.setData(emissao.getTime());
				
				if(destinatario != null)
					mp.setDestinatario(destinatario.getNome());
				
				mp.setLucro(((valor/produto.getCusto())-1)*100);
				
				mp.setNumeroNota(numero);
				
				mp.setPr(pr);
				
				mp.setQuantidade(tp.para(pr.getUnidade(), produto, quantidade));
			
				mp.setTipoQuantidade(pr.getUnidade());
				
				mp.setOperacao(operacao);
				
				mp.setValor((quantidade*valor)/mp.getQuantidade());
				
				if(mp.getData().getTime() > fim.getTimeInMillis()) {
					
					pr.setQuantidade(pr.getQuantidade()+((mp.getOperacao().equals(SaidaEntrada.SAIDA)?1:-1)*mp.getQuantidade()));
					
				}else {
					
					pr.getMovimentos().add(mp);
					
				}
				
				
			}catch(Exception ex) {
				
				
			}
			
		}
		
		return produtos;
		
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
		
		Calendar antes = Calendar.getInstance();
		antes.add(Calendar.DATE, -60);
		
		@SuppressWarnings("unused")
		List<ProdutoRelatorio> pr = ps.getProdutoRelatorio("", antes, antes);
		
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
		
	}



	@Override
	public Produto merge(Produto obj) {
	
		Produto produto = obj;
		
		if(produto.getId() == 0) {
			
			et.persist(produto);
			
		}else {
			
			produto = et.merge(produto);
			
		}
		
		Categoria cat = et.merge(produto.getCategoria());
		
		cat.setTabelaAlicota(et.merge(cat.getTabelaAlicota()));
		
		cat.setTabelaCfop(et.merge(cat.getTabelaCfop()));
		
		cat.setCofins(et.merge(cat.getCofins()));
		
		cat.setIcms(et.merge(cat.getIcms()));
		
		cat.setPis(et.merge(cat.getPis()));
		
		produto.setCategoria(cat);
		
		return produto;
		
	}

	
	
	
}


