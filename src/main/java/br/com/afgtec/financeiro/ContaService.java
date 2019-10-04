package br.com.afgtec.financeiro;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;

import br.com.afgtec.base.ET;
import br.com.afgtec.base.Service;
import br.com.afgtec.notas.Nota;
import br.com.afgtec.notas.SaidaEntrada;
import br.com.afgtec.notas.Vencimento;
import br.com.afgtec.pessoa.Empresa;
import br.com.afgtec.pessoa.Pessoa;
import br.com.afgtec.usuario.Usuario;

public class ContaService implements Service<Conta>{

	private EntityManager et;
	
	private TipoConta tipo;
	
	private Empresa empresa;
	
	public ContaService(EntityManager et) {
		
		this.et = et;
		
	}
	
	
	public TipoConta getTipo() {
		return tipo;
	}



	public void setTipo(TipoConta tipo) {
		this.tipo = tipo;
	}



	public Empresa getEmpresa() {
		return empresa;
	}



	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public static void main(String[] args) {
		
		EntityManager et = ET.nova();
		
		Usuario u = et.find(Usuario.class, 1);
		
		ContaService cs = new ContaService(et);
		cs.setEmpresa(u.getPf().getEmpresa());
		cs.setTipo(TipoConta.RECEBER);
		
		int count = cs.getCount("");
		
		List<Conta> contas = cs.getElementos(0, 10, "", "");
		
		System.out.println("OK"+contas.size()+"--------"+count);
		
		for(Conta c:contas) {
			
			System.out.println(c.getPendencia()+"----"+c.getDestino());
			
		}
		
	}

	@Override
	public int getCount(String filtro) {
		
		Query q = this.et.createQuery(
				"SELECT v.valor "
				+ "FROM Vencimento v LEFT JOIN Movimento m ON m.vencimento=v "
				+ "LEFT JOIN Pessoa p ON p.id = v.nota.destinatario.id "
				+ "WHERE v.nota.empresa.id = :empresa AND v.nota.operacao = :tipo AND (p.nome like :nome OR p.nome IS NULL) "
				+ "GROUP BY v HAVING (v.valor-SUM(COALESCE(m.valor,0)))>0");
		q.setParameter("empresa", this.empresa.getId());
		
		if(this.tipo == TipoConta.PAGAR) {
			q.setParameter("tipo", SaidaEntrada.ENTRADA);
		}else if(this.tipo == TipoConta.RECEBER){
			q.setParameter("tipo", SaidaEntrada.SAIDA);
		}
		
		q.setParameter("nome", "%"+filtro+"%");
		
		return q.getResultList().size();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Conta> getElementos(int x1, int x2, String filtro, String ordem) {
		
		Query q = this.et.createQuery(
				"SELECT v,v.valor-SUM(COALESCE(m.valor,0)),p,v.nota "
				+ "FROM Vencimento v LEFT JOIN Movimento m ON m.vencimento=v "
				+ "LEFT JOIN Pessoa p ON p.id = v.nota.destinatario.id "
				+ "WHERE v.nota.empresa.id = :empresa AND v.nota.operacao = :tipo AND (p.nome like :nome OR p.nome IS NULL) "
				+ "GROUP BY v HAVING (v.valor-SUM(COALESCE(m.valor,0)))>0");
		q.setParameter("empresa", this.empresa.getId());
		
		if(this.tipo == TipoConta.PAGAR) {
			q.setParameter("tipo", SaidaEntrada.ENTRADA);
		}else if(this.tipo == TipoConta.RECEBER){
			q.setParameter("tipo", SaidaEntrada.SAIDA);
		}
		
		q.setParameter("nome", "%"+filtro+"%");
		
		q.setFirstResult(x1);
		q.setMaxResults(x2-x1);
	
		
		List<Conta> contas = new ArrayList<Conta>();
		
		for(Object[] obj:(List<Object[]>)(List<?>)q.getResultList()) {
			
			Vencimento v = (Vencimento)obj[0];
			double valor = (Double)obj[1];
			Pessoa destinatario = (Pessoa)obj[2];
			Nota n = (Nota)obj[3];
			v.setNota(n);
			
			
			Conta c = new Conta();
			
			if(destinatario != null) {
				c.setDestino(destinatario);
			}
			
			c.setPessoa(this.empresa.getPj());
			c.setTipo(this.tipo);
			c.setVencimento(v);
			c.setPendencia(valor);
			
			contas.add(c);
			
		}
		
		return contas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Conta getPeloCodigo(String str) {
		
		Query q = this.et.createQuery(
				"SELECT v,v.valor-SUM(m.valor+m.juros-m.descontos),p,v.nota "
				+ "FROM Vencimento v LEFT JOIN Movimento m ON m.vencimento=v "
				+ "LEFT JOIN Pessoa p ON p.id = v.nota.destinatario.id "
				+ "WHERE v.nota.id = :id "
				+ "GROUP BY v HAVING (v.valor-SUM(m.valor+m.juros-m.descontos))>0");
		q.setParameter("id",Integer.parseInt(str));
		
		for(Object[] obj:(List<Object[]>)(List<?>)q.getResultList()) {
			
			Vencimento v = (Vencimento)obj[0];
			double valor = (Double)obj[1];
			Pessoa destinatario = (Pessoa)obj[2];
			Nota n = (Nota)obj[3];
			v.setNota(n);
			
			
			Conta c = new Conta();
			
			if(destinatario != null) {
				c.setDestino(destinatario);
			}
			
			c.setPessoa(n.getEmpresa().getPj());
			c.setTipo(n.getOperacao().equals(SaidaEntrada.SAIDA)?TipoConta.RECEBER:TipoConta.PAGAR);
			c.setVencimento(v);
			c.setPendencia(valor);
			
			return c;
			
		}
		
		return null;
	}

	@Override
	public void lixeira(Conta obj) {
		// TODO Auto-generated method stub
		((Session)et.getDelegate()).evict(obj.getVencimento());
		
	}

}
