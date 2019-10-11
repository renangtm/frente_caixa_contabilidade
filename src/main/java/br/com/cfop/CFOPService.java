package br.com.cfop;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;

import br.com.base.Service;

public class CFOPService implements Service<CFOP> {

	private EntityManager et;
	
	public CFOPService(EntityManager et){
		
		this.et = et;
		
	}
	
	@Override
	public int getCount(String filtro) {
		
		Query q = this.et.createQuery("SELECT COUNT(*) FROM CFOP n WHERE n.numero LIKE :numero OR n.descricao LIKE :desc");
		q.setParameter("numero", "%"+filtro+"%");
		q.setParameter("desc", "%"+filtro+"%");
		
		return Integer.parseInt(q.getResultList().get(0)+"");
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CFOP> getElementos(int x1, int x2, String filtro, String ordem) {
		
		ordem = ordem.replaceAll("\\{\\{et\\}\\}", "n");
		
		Query q = this.et.createQuery("SELECT n FROM CFOP n WHERE n.numero LIKE :numero OR n.descricao LIKE :desc"+(!ordem.equals("")?" ORDER BY "+ordem:""));
		q.setParameter("numero", "%"+filtro+"%");
		q.setParameter("desc", "%"+filtro+"%");
		q.setFirstResult(x1);
		q.setMaxResults(x2-x1);
		
		return (List<CFOP>)(List<?>)q.getResultList();
	}

	@Override
	public CFOP getPeloCodigo(String str) {
		
		Query q = this.et.createQuery("SELECT n FROM CFOP n WHERE n.id = :id");
		q.setParameter("id", Integer.parseInt(str));
		
		if(q.getResultList().isEmpty())
			return null;
		
		return (CFOP)q.getResultList().get(0);
		
	}

	@Override
	public void lixeira(CFOP obj) {
		// TODO Auto-generated method stub
		((Session)this.et.getDelegate()).evict(obj);
	}

	@Override
	public CFOP merge(CFOP obj) {
		
		if(obj.getId() == 0){
			
			et.persist(obj);
			return obj;
			
		}
		
		return et.merge(obj);
		
	}

}
