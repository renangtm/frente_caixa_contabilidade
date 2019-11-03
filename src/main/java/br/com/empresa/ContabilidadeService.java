package br.com.empresa;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.base.Service;

public class ContabilidadeService implements Service<Contabilidade> {

	private EntityManager et;

	public ContabilidadeService(EntityManager et) {

		this.et = et;

	}

	@Override
	public int getCount(String filtro) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Contabilidade> getElementos(int x1, int x2, String filtro, String ordem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contabilidade getPeloCodigo(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lixeira(Contabilidade obj) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public List<Contabilidade> getEmpresas(String filtro) {

		Query q = this.et.createQuery("SELECT e FROM Contabilidade e WHERE e.pj.nome like :filtro");
		q.setParameter("filtro", "%" + filtro + "%");

		return (List<Contabilidade>) (List<?>) q.getResultList();

	}

	@Override
	public Contabilidade merge(Contabilidade obj) {

		if (obj.getLogo() != null)
			obj.setLogo(et.merge(obj.getLogo()));

		obj.setPadroesCodigo(obj.getPadroesCodigo().stream().map(p -> et.merge(p)).collect(Collectors.toList()));

		obj.setClientes(obj.getClientes().stream().map(p -> et.merge(p)).collect(Collectors.toList()));

		if (obj.getParametrosEmissao() != null)
			obj.setParametrosEmissao(et.merge(obj.getParametrosEmissao()));
		
		if(obj.getContabilidade() != null)
			obj.setContabilidade(et.merge(obj.getContabilidade()));

		if (obj.getId() == 0) {

			et.persist(obj);

			return obj;

		} else {

			Contabilidade emp = et.merge(obj);

			obj.getPadroesCodigo().forEach(p -> p.setEmpresa(emp));

			if (emp.getParametrosEmissao() != null)
				emp.getParametrosEmissao().setEmpresa(emp);

			obj.getPj().setEmpresa(emp);

			return emp;

		}

	}

}
