package br.com.produto;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.base.Service;
import br.com.empresa.Empresa;

public class ValePresenteService implements Service<ValePresente> {

	private EntityManager et;

	private Empresa empresa;

	public ValePresenteService(EntityManager et) {

		this.et = et;

	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@SuppressWarnings("unchecked")
	public List<ValePresente> getTodosComEstoque() {

		return (List<ValePresente>) this.et
				.createQuery("SELECT v FROM ValePresente v WHERE v.estoque.disponivel > 0 AND v.empresa = :empresa")
				.setParameter("empresa", this.empresa).getResultList();

	}

	@Override
	public int getCount(String filtro) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ValePresente> getElementos(int x1, int x2, String filtro, String ordem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValePresente getPeloCodigo(String str) {

		@SuppressWarnings("unchecked")
		List<ValePresente> lst = this.et.createQuery("SELECT v FROM ValePresente v WHERE v.id = :id")
				.setParameter("id", Integer.parseInt(str)).getResultList();

		return lst.size() == 0 ? null : lst.get(0);
	}

	@Override
	public void lixeira(ValePresente obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public ValePresente merge(ValePresente obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
