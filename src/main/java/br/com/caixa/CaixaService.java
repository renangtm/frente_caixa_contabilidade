package br.com.caixa;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;

import br.com.base.Service;
import br.com.empresa.Empresa;

public class CaixaService implements Service<Caixa> {

	private EntityManager et;

	private Empresa empresa;

	public CaixaService(EntityManager et) {

		this.et = et;

	}

	@Override
	public int getCount(String filtro) {
		
		if (this.empresa == null) {

			throw new RuntimeException("Sem empresa");

		}
		
		Query q = this.et.createQuery("SELECT COUNT(*) FROM Caixa c WHERE c.empresa=:empresa");
		q.setParameter("empresa", this.empresa);
		
		return Integer.parseInt(q.getResultList().get(0).toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Caixa> getElementos(int x1, int x2, String filtro, String ordem) {
		
		if (this.empresa == null) {

			throw new RuntimeException("Sem empresa");

		}

		ordem = ordem.replaceAll("\\{\\{et\\}\\}", "p");
		
		Query q = this.et.createQuery("SELECT c FROM Caixa c WHERE c.empresa = :empresa "+(!ordem.isEmpty()?"ORDER BY "+ordem:""));
		q.setParameter("empresa", this.empresa);
		
		return (List<Caixa>)(List<?>)q.getResultList();
		
	}
	
	public ExpedienteCaixa getUltimoExpediente(Caixa caixa) {
		
		Query q = this.et.createQuery("SELECT e FROM ExpedienteCaixa e WHERE e.caixa=:caixa ORDER BY e.inicio DESC");
		q.setParameter("caixa", caixa);
		q.setMaxResults(1);
		
		if (q.getResultList().size() > 0) {
			return (ExpedienteCaixa) q.getResultList().get(0);
		} else {
			return null;
		}
		
	}
	
	public Caixa getPeloNumero(int numero) {

		Query q = this.et.createQuery("SELECT c FROM Caixa c WHERE c.numero=:numero AND c.empresa=:empresa");
		q.setParameter("numero", numero);
		q.setParameter("empresa", this.empresa);
		
		if (q.getResultList().size() > 0) {
			return (Caixa) q.getResultList().get(0);
		} else {
			return null;
		}

	}
	
	@Override
	public Caixa getPeloCodigo(String str) {

		Query q = this.et.createQuery("SELECT c FROM caixa c WHERE c.id=:id");
		q.setParameter("id", Integer.parseInt(str));

		if (q.getResultList().size() > 0) {
			return (Caixa) q.getResultList().get(0);
		} else {
			return null;
		}

	}

	@Override
	public void lixeira(Caixa obj) {
		// TODO Auto-generated method stub
		((Session) et.getDelegate()).evict(obj);
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public Caixa merge(Caixa c) {
		
		if(c.getId() == 0) {
			
			et.persist(c);
		
		}else {
			
			c = et.merge(c);
			
		}
		
		final Caixa cc = c;
		
		c.setEmpresa(et.merge(c.getEmpresa()));
		
		c.setExpedientes(c.getExpedientes().stream().map(e->{
			
			e.setCaixa(cc);
			
			return et.merge(e);
			
		}).collect(Collectors.toList()));
		
		
		
		return c;
		
	}

}
