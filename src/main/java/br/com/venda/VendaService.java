package br.com.venda;

import javax.persistence.EntityManager;

import br.com.afgtec.base.ET;
import br.com.afgtec.produto.Estoque;

public class VendaService {
	
	public synchronized void persistirVenda(Venda venda) throws SemEstoqueException{
		
		EntityManager et = ET.nova();		
		
		for(ProdutoVenda pv:venda.getProdutos()){
			
			pv.quantidadeInfluenciada = pv.tipoQuantidadeInfluenciada.para(pv.tipoQuantidade, pv.getProduto(), pv.quantidadeInfluenciada);
			pv.tipoQuantidadeInfluenciada = pv.tipoQuantidade;
			
			pv.reservaInfluenciada = pv.tipoReservaInfluenciada.para(pv.tipoQuantidade, pv.getProduto(), pv.reservaInfluenciada);
			pv.tipoReservaInfluenciada = pv.tipoReservaInfluenciada;
			
			
			double influenciaEst = venda.getStatus().isQuantidade()?pv.getQuantidade():0 - pv.quantidadeInfluenciada;
			double influenciaRes = venda.getStatus().isDisponivel()?pv.getQuantidade():0 - pv.reservaInfluenciada;
			
			Estoque estoque = et.merge(pv.getProduto().getEstoque());
			et.refresh(estoque);
			
			double influenciaRealEst = pv.tipoQuantidade.para(estoque.getTipo(), pv.getProduto(), influenciaEst);
			double influenciaRealRes = pv.tipoQuantidade.para(estoque.getTipo(), pv.getProduto(), influenciaRes);
			
			try{
				
				estoque.rmvDisponivel(influenciaRealRes);
				estoque.rmvQuantidade(influenciaRealEst);
				
			}catch(Exception ex){
			
				throw new SemEstoqueException(pv.getProduto());
				
			}
			
		}
		
		for(ProdutoVenda pv:venda.getProdutos()){
			
			pv.reservaInfluenciada = venda.getStatus().isDisponivel()?pv.getQuantidade():0;
			pv.quantidadeInfluenciada = venda.getStatus().isQuantidade()?pv.getQuantidade():0;
			
		}
		
		et.getTransaction().begin();
		
		@SuppressWarnings("unused")
		Venda mv = et.merge(venda);
		
		et.getTransaction().commit();
		
		
	}

}
