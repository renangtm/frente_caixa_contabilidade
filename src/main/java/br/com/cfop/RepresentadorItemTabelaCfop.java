package br.com.cfop;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.agrofauna.utilidades.Campo;
import br.com.agrofauna.utilidades.Representador;

public class RepresentadorItemTabelaCfop extends Representador<ItemTabelaCfop> {

	@Campo(nome = "CFOP", editavel = true)
	private String cfop;

	@Campo(nome = "Oper.", editavel = false)
	private String operacaoLogistica;

	public RepresentadorItemTabelaCfop(ItemTabelaCfop o) {
		super(o);

		if (o.getCfop() != null)
			this.cfop = o.getCfop().getNumero();

		this.operacaoLogistica = o.getOperacao().toString();

	}

	@Override
	public void atualizar() {

		if (this.cfop.length() < 4 || this.cfop.length() > 5) {

			this.cfop = this.objetoOriginal.getCfop().getNumero();
			return;

		}

		EntityManager etm = (EntityManager) this.parametro;

		Query q = etm.createQuery("SELECT c FROM CFOP c WHERE numero IN(:n1,:n2)");
		q.setParameter("n1", this.cfop);
		q.setParameter("n2", this.cfop.replaceAll("\\.", ""));

		@SuppressWarnings("unchecked")
		List<CFOP> c = (List<CFOP>) (List<?>) q.getResultList();

		if (c.size() > 0) {

			this.objetoOriginal.setCfop(c.get(0));
			this.cfop = this.objetoOriginal.getCfop().getNumero();

		} else {

			CFOP ncfop = new CFOP();
			ncfop.setDescricao("");
			ncfop.setNumero(this.cfop);
			ncfop.setSubnumero("0");

			etm.persist(ncfop);

			etm.getTransaction().begin();
			etm.getTransaction().commit();

			System.out.println(ncfop.getId() + "=================");

			this.objetoOriginal.setCfop(ncfop);

		}

	}

}
