package br.com.nota;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.base.Service;
import br.com.empresa.Empresa;

public class VencimentoService implements Service<Vencimento> {

	private Empresa empresa;

	private EntityManager et;

	public void setEmpresa(Empresa emp) {

		this.empresa = emp;

	}

	public VencimentoService(EntityManager et) {

		this.et = et;

	}
	
	@Override
	public int getCount(String filtro) {

		Query qr = et.createQuery("SELECT COUNT(*) FROM Vencimento n WHERE "
				+ (this.empresa != null ? "n.nota.empresa.id = :empresa AND " : "") + " (n.nota.emitente.nome LIKE :nome_c)");

		if (this.empresa != null)
			qr.setParameter("empresa", this.empresa.getId());

		qr.setParameter("nome_c", "%" + filtro + "%");

		@SuppressWarnings("unchecked")
		List<Long> lista = (List<Long>) (List<?>) qr.getResultList();

		return Integer.parseInt(lista.get(0) + "");

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vencimento> getElementos(int x1, int x2, String filtro, String ordem) {

		ordem = ordem.replaceAll("\\{\\{et\\}\\}", "n");

		Query qr = et.createQuery(
				"SELECT n FROM Vencimento n WHERE " + (this.empresa != null ? "n.nota.empresa.id = :empresa AND " : "")
						+ " (n.nota.emitente.nome LIKE :nome_c)" + (ordem != "" ? " ORDER BY " + ordem : ""));

		if (this.empresa != null)
			qr.setParameter("empresa", this.empresa.getId());

		qr.setParameter("nome_c", "%" + filtro + "%");

		qr.setFirstResult(x1);
		qr.setMaxResults(x2 - x1);
		
		List<Vencimento> v = (List<Vencimento>) (List<?>) qr.getResultList();

		
		return v;

	}

	@Override
	public Vencimento getPeloCodigo(String str) {

		@SuppressWarnings("unchecked")
		List<Vencimento> notas = (List<Vencimento>) (List<?>) et.createQuery("SELECT n FROM Vencimento n WHERE n.id = :codigo")
				.setParameter("codigo", Integer.parseInt(str)).getResultList();

		if (notas.size() == 0) {

			return null;

		}

		return (Vencimento) notas.get(0);

	}

	@Override
	public void lixeira(Vencimento obj) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public Vencimento merge(Vencimento obj) {
		
		if(obj.getId() == 0) {
			
			et.persist(obj);
			
			return obj;
			
		}
		
		return et.merge(obj);
		
	}

	

}
