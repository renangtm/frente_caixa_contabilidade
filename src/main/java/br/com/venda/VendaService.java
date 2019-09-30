package br.com.venda;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.afgtec.produto.Estoque;

public class VendaService {
	
	private EntityManager et;
	
	public VendaService(EntityManager et) {
		
		this.et = et;
		
	}

	public synchronized void persistirVenda(Venda venda) throws SemEstoqueException {

		List<Estoque> alterados = new ArrayList<Estoque>();

		for (ProdutoVenda pv : venda.getProdutos()) {

			pv.reservaInfluenciada = pv.tipoReservaInfluenciada.para(pv.getProduto().getEstoque().getTipo(),
					pv.getProduto(), pv.reservaInfluenciada);
			pv.tipoReservaInfluenciada = pv.tipoReservaInfluenciada;

			double influenciaRes = (venda.getStatus().isDisponivel() ? pv.getQuantidade() : 0) - pv.reservaInfluenciada;

			Estoque estoque = pv.getProduto().getEstoque();
			et.refresh(estoque);

			double influenciaRealRes = pv.tipoQuantidade.para(estoque.getTipo(), pv.getProduto(), influenciaRes);

			try {

				estoque.rmvDisponivel(influenciaRealRes);
				alterados.add(estoque);

			} catch (Exception ex) {

				for (Estoque est : alterados) {

					et.refresh(est);
					et.detach(est);

				}

				throw new SemEstoqueException(pv.getProduto());

			}

		}

		for (ProdutoVenda pv : venda.getProdutos()) {

			pv.reservaInfluenciada = venda.getStatus().isDisponivel() ? pv.getQuantidade() : 0;

		}
		
		if(venda.getId() == 0) {
			
			et.persist(venda);
			
		}else {
			
			et.merge(venda);
			
		}

	}

}
