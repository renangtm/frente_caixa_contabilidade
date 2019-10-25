package br.com.caixa;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import br.com.base.ET;
import br.com.base.Service;
import br.com.movimento_financeiro.Movimento;
import br.com.nota.FormaPagamentoNota;

public class ExpedienteCaixaService implements Service<ExpedienteCaixa>{

	private EntityManager et;
	
	public ExpedienteCaixaService(EntityManager et) {
		
		this.et = et;
		
	}
	
	@Override
	public int getCount(String filtro) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ExpedienteCaixa> getElementos(int x1, int x2, String filtro, String ordem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExpedienteCaixa getPeloCodigo(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lixeira(ExpedienteCaixa obj) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		
		EntityManager et = ET.nova();
		
		ExpedienteCaixaService serv = new ExpedienteCaixaService(et);
		
		ExpedienteCaixa exp = et.find(ExpedienteCaixa.class, 1);
		
		List<Movimento> movimentos = serv.getMovimentosSemSangriaEReposicao(exp);

		System.out.println(movimentos.size());
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Movimento> getMovimentosSemSangriaEReposicao(ExpedienteCaixa exp){
		
		return 
				(List<Movimento>)
				(List<?>)
				et.createQuery("SELECT m FROM Movimento m WHERE m.expediente=:expediente AND m.id NOT IN (SELECT ms.id FROM Sangria s JOIN FETCH s.movimentos ms) AND m.id NOT IN (SELECT mr.id FROM Reposicao r JOIN FETCH r.movimentos mr)")
				.setParameter("expediente", exp)
				.getResultList();
		
	}

	@Override
	public ExpedienteCaixa merge(ExpedienteCaixa e) {
		
		final ExpedienteCaixa ee = e;
		
		e.setSaldo_final_atual(e.getSaldo_inicial());
		
		e.setSangrias(e.getSangrias().stream().map(s->{
			
			s.setGerente(et.merge(s.getGerente()));
			
			if(s.getId() == 0)
				et.persist(s);
			else
				s = et.merge(s);
			
			
			ee.setSaldo_final_atual(ee.getSaldo_final_atual()-s.getValor());
			
			return s;
			
		}).collect(Collectors.toList()));
		
		e.setReposicoes(e.getReposicoes().stream().map(r->{
			
			r.setGerente(et.merge(r.getGerente()));
			
			if(r.getId() == 0)
				et.persist(r);
			else
				r = et.merge(r);
			
			ee.setSaldo_final_atual(ee.getSaldo_final_atual()+r.getValor());
			
			return r;
			
		}).collect(Collectors.toList()));
		
		e.setAjustes(e.getAjustes().stream().map(a->{
		
			if(a.getId() == 0)
				et.persist(a);
			else
				a = et.merge(a);
			
			
			ee.setSaldo_final_atual(ee.getSaldo_final_atual()+(a.getValor()*(a.getTipo()==TipoAjuste.FISICAMENTE_AMAIOR?1:-1)));
			
			return a;
			
		}).collect(Collectors.toList()));
		
		
		e.getMovimentos().forEach(m->{
			
			if(m.getFormaPagamento().equals(FormaPagamentoNota.DINHEIRO)) {
				
				
				ee.setSaldo_final_atual(ee.getSaldo_final_atual()+(m.getValor()*(m.getOperacao().isCredito()?1:-1)));
				
			}
			
		});
		
		e.getCaixa().setSaldoAtual(e.getSaldo_final_atual());
		
		e.setCaixa(et.merge(e.getCaixa()));
		
		if(e.getId() == 0) {
			
			et.persist(e);
			
		}else {
			
			e = et.merge(e);
			
		}
		
		final ExpedienteCaixa ne = e;
		
		e.getAjustes().forEach(a->a.setExpediente(ne));
		e.getSangrias().forEach(s->s.setExpediente(ne));
		e.getReposicoes().forEach(r->r.setExpediente(ne));
		e.getMovimentos().forEach(m->m.setExpediente(ne));
		
		return e;
	}

}
