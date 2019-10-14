package br.com.capturaXML;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.base.Service;
import br.com.empresa.Empresa;

public class XMLService implements Service<XML> {

	private EntityManager et;

	public XMLService(EntityManager et) {

		this.et = et;

	}

	@Override
	public int getCount(String filtro) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<XML> getElementos(int x1, int x2, String filtro, String ordem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XML getPeloCodigo(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lixeira(XML obj) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public List<XML> getXMLsNaoVistadosSistema(TipoXML tipo, Empresa empresa) {

		Query q = this.et.createQuery(
				"SELECT x FROM XML x WHERE x.tipo = :tipo AND x.empresa = :empresa AND x.vistoSistema = false");
		q.setParameter("tipo", tipo);
		q.setParameter("empresa", empresa);

		return (List<XML>) (List<?>) q.getResultList();

	}

	@Override
	public XML merge(XML obj) {

		obj.setEmpresa(et.merge(obj.getEmpresa()));

		if (obj.getId() == 0) {

			et.persist(obj);
			return obj;

		}

		XML xml = et.merge(obj);

		return xml;

	}

}
