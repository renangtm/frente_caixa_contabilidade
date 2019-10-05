package br.com.entidades.ncm;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;

import br.com.base.Service;

public class NCMService implements Service<NCM> {

	private EntityManager et;
	
	public NCMService(EntityManager et){
		
		this.et = et;
		
	}
	
	@Override
	public int getCount(String filtro) {
		
		Query q = this.et.createQuery("SELECT COUNT(*) FROM NCM n WHERE n.numero LIKE :numero OR n.descricao LIKE :desc");
		q.setParameter("numero", "%"+filtro+"%");
		q.setParameter("desc", "%"+filtro+"%");
		
		return Integer.parseInt(q.getResultList().get(0)+"");
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NCM> getElementos(int x1, int x2, String filtro, String ordem) {
		
		ordem = ordem.replaceAll("\\{\\{et\\}\\}", "n");
		
		Query q = this.et.createQuery("SELECT n FROM NCM n WHERE n.numero LIKE :numero OR n.descricao LIKE :desc"+(!ordem.equals("")?" ORDER BY "+ordem:""));
		q.setParameter("numero", "%"+filtro+"%");
		q.setParameter("desc", "%"+filtro+"%");
		q.setFirstResult(x1);
		q.setMaxResults(x2-x1);
		
		return (List<NCM>)(List<?>)q.getResultList();
	}

	@Override
	public NCM getPeloCodigo(String str) {
		
		Query q = this.et.createQuery("SELECT n FROM NCM n WHERE n.id = :id");
		q.setParameter("id", Integer.parseInt(str));
		
		if(q.getResultList().isEmpty())
			return null;
		
		return (NCM)q.getResultList().get(0);
		
	}

	@Override
	public void lixeira(NCM obj) {
		// TODO Auto-generated method stub
		((Session)this.et.getDelegate()).evict(obj);
	}

}
