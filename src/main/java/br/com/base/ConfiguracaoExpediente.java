package br.com.base;

import java.util.Calendar;

import javax.persistence.EntityManager;

import br.com.caixa.Caixa;
import br.com.caixa.CaixaService;
import br.com.caixa.ExpedienteCaixa;
import br.com.usuario.Usuario;

public class ConfiguracaoExpediente {

	public static ExpedienteCaixa exp;
	
	public static ExpedienteCaixa getExpedienteCaixa(Usuario usuario) throws AberturaCaixaException {
		
		if(CFG.clc == null) {
			
			throw new RuntimeException("Nao existe a configuracao local do caixa, favor efetuar o processo primeiro antes de abrir o expediente");
			
		}
		
		if(exp == null) {
			
			try {
				
				EntityManager et = ET.nova();
				
				CaixaService serv = new CaixaService(et);
				serv.setEmpresa(usuario.getPf().getEmpresa());
				
				Caixa caixa = serv.getPeloNumero(CFG.clc.getNumeroCaixa());
				
				
				ExpedienteCaixa ultimo = serv.getUltimoExpediente(caixa);
				
				if(ultimo != null) {
					if(ultimo.getFim() == null) {
						
						ultimo.setFim(Calendar.getInstance());
						et.merge(ultimo);
						
					}
				}
				
				ExpedienteCaixa novoExpediente = new ExpedienteCaixa();
				novoExpediente.setCaixa(caixa);
				novoExpediente.setUsuario(et.merge(usuario));
				novoExpediente.setSaldo_inicial(caixa.getSaldoAtual());
				novoExpediente.setSaldo_final_atual(caixa.getSaldoAtual());
						
				et.persist(novoExpediente);
				
				et.getTransaction().begin();
				et.getTransaction().commit();
				
				exp = novoExpediente;
				
				et.close();
				
				return exp;
				
			}catch(Exception ex) {
				ex.printStackTrace();
				throw new AberturaCaixaException();
				
			}
			
		}
		
		EntityManager et = ET.nova();
		exp = et.merge(exp);
		et.refresh(exp);
		et.close();
		
		return exp;
		
	}
	
	public static void fecharExpedienteCaixa() {
		
		if(exp == null) {
			
			throw new RuntimeException("O caixa nao foi aberto portanto nao pode ser feixado");
			
		}
		
		EntityManager et = ET.nova();
		
		exp.setSaldo_final_atual(exp.getCaixa().getSaldoAtual());
		exp.setFim(Calendar.getInstance());
		
		et.merge(exp);
		
		et.getTransaction().begin();
		et.getTransaction().commit();
		
		et.close();
		
		exp = null;
		
	}
	
	
	private ConfiguracaoExpediente() {
		
		
	}
	
}
