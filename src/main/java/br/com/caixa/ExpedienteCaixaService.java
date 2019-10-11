package br.com.caixa;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import br.com.base.Service;

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

	@Override
	public ExpedienteCaixa merge(ExpedienteCaixa e) {
		
		final ExpedienteCaixa ee = e;
		
		e.setSaldo_final_atual(e.getSaldo_inicial());
		
		e.setSangrias(e.getSangrias().stream().map(s->{
			
			if(s.getId() == 0)
				et.persist(s);
			else
				s = et.merge(s);
			
			
			ee.setSaldo_final_atual(ee.getSaldo_final_atual()-s.getValor());
			
			return s;
			
		}).collect(Collectors.toList()));
		
		e.setReposicoes(e.getReposicoes().stream().map(r->{
			
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
		
		return e;
	}

}
