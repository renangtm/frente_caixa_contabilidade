package br.com.historico;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;

import br.com.afgtec.base.Service;
import br.com.movimento_financeiro.Movimento;

public class HistoricoService implements Service<Historico> {

	private EntityManager et;
	
	public void mergeMovimento(Movimento movimento){
		
		
		
	}

	public HistoricoService(EntityManager et) {

		this.et = et;

	}

	@Override
	public int getCount(String filtro) {

		Query qr = et.createQuery("SELECT COUNT(*) FROM Historico n WHERE (n.nome LIKE :nome_c)");

		qr.setParameter("nome_c", "%" + filtro + "%");

		@SuppressWarnings("unchecked")
		List<Long> lista = (List<Long>) (List<?>) qr.getResultList();

		return Integer.parseInt(lista.get(0) + "");

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Historico> getElementos(int x1, int x2, String filtro, String ordem) {

		ordem = ordem.replaceAll("\\{\\{et\\}\\}", "n");

		Query qr = et.createQuery(
				"SELECT n FROM Historico n WHERE (n.nome LIKE :nome_c)" + (ordem != "" ? " ORDER BY " + ordem : ""));


		qr.setParameter("nome_c", "%" + filtro + "%");

		qr.setFirstResult(x1);
		qr.setMaxResults(x2 - x1);

		return (List<Historico>) (List<?>) qr.getResultList();

	}

	@Override
	public Historico getPeloCodigo(String str) {

		@SuppressWarnings("unchecked")
		List<Historico> notas = (List<Historico>) (List<?>) et.createQuery("SELECT n FROM Historico n WHERE n.id = :codigo")
				.setParameter("codigo", Integer.parseInt(str)).getResultList();

		if (notas.size() == 0) {

			return null;

		}

		return (Historico) notas.get(0);

	}

	@Override
	public void lixeira(Historico obj) {
		// TODO Auto-generated method stub
		((Session)this.et.getDelegate()).evict(obj);
	}

}
