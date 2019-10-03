package br.com.afgtec.financeiro;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;

import br.com.afgtec.base.Service;

public class OperacaoService implements Service<Operacao> {

	private EntityManager et;

	
	public void mergeMovimento(Movimento movimento){
		
		
		
	}

	public OperacaoService(EntityManager et) {
		
		this.et = et;

	}

	@Override
	public int getCount(String filtro) {
		
		Query qr = et.createQuery("SELECT COUNT(*) FROM Operacao n WHERE (n.nome LIKE :nome_c)");

		qr.setParameter("nome_c", "%" + filtro + "%");

		@SuppressWarnings("unchecked")
		List<Long> lista = (List<Long>) (List<?>) qr.getResultList();

		return Integer.parseInt(lista.get(0) + "");

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operacao> getElementos(int x1, int x2, String filtro, String ordem) {

		ordem = ordem.replaceAll("\\{\\{et\\}\\}", "n");

		Query qr = et.createQuery(
				"SELECT n FROM Operacao n WHERE  (n.nome LIKE :nome_c)" + (ordem != "" ? " ORDER BY " + ordem : ""));

		qr.setParameter("nome_c", "%" + filtro + "%");

		qr.setFirstResult(x1);
		qr.setMaxResults(x2 - x1);

		return (List<Operacao>) (List<?>) qr.getResultList();

	}

	@Override
	public Operacao getPeloCodigo(String str) {

		@SuppressWarnings("unchecked")
		List<Operacao> notas = (List<Operacao>) (List<?>) et.createQuery("SELECT n FROM Operacao n WHERE n.id = :codigo")
				.setParameter("codigo", Integer.parseInt(str)).getResultList();

		if (notas.size() == 0) {

			return null;

		}

		return (Operacao) notas.get(0);

	}

	@Override
	public void lixeira(Operacao obj) {
		// TODO Auto-generated method stub
		((Session)this.et.getDelegate()).evict(obj);
	}

}
