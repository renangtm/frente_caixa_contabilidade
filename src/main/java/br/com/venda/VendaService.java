package br.com.venda;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import br.com.afgtec.produto.Estoque;

public class VendaService {

	private EntityManager et;

	public VendaService(EntityManager et) {

		this.et = et;

	}

	public synchronized void persistirVenda(Venda venda) throws SemEstoqueException {

		Map<ProdutoVenda, Double[]> alteracoes = new HashMap<ProdutoVenda, Double[]>();

		for (ProdutoVenda pv : venda.getProdutos()) {

			double meta = (venda.getStatus().isDisponivel() ? pv.getQuantidade() : 0);

			double infres = meta - pv.reservaInfluenciada;

			Estoque estoque = pv.getProduto().getEstoque();
			et.refresh(estoque);

			double influenciaRealRes = pv.tipoQuantidade.para(estoque.getTipo(), pv.getProduto(), infres);

			try {

				estoque.rmvDisponivel(influenciaRealRes);
				
				Double[] alteracao = {
						influenciaRealRes,
						pv.reservaInfluenciada
				};
				
				alteracoes.put(pv, alteracao);
				
				pv.reservaInfluenciada = meta;
				
			} catch (Exception ex) {
				
				//Ao ter um erro, voltamos o que havia sido feito
				
				for(ProdutoVenda pa:alteracoes.keySet()){
					
					double removidoDisponivel = alteracoes.get(pa)[0];
					double influenciaAnterior = alteracoes.get(pa)[1];
					
					pa.getProduto().getEstoque().addDisponivel(removidoDisponivel);
					pa.reservaInfluenciada = influenciaAnterior;
					
				}

				throw new SemEstoqueException(pv.getProduto());

			}

		}

		if (venda.getId() == 0) {

			et.persist(venda);

		} else {

			et.merge(venda);

		}

	}

}
