package br.com.capturaXML;


import br.com.jaxb.CTeProc.CteProc;
import br.com.procNFe.TNfeProc;
import br.com.utilidades.Campo;
import br.com.utilidades.Representador;

public class RepresentadorXML extends Representador<XML>{

	@Campo(nome="Tipo")
	private String tipo;
	
	@Campo(nome="Chave")
	private String chave;
	
	@Campo(nome="Num.")
	private int numero;
	
	@Campo(nome="Emit.")
	private String emitente;
	
	@Campo(nome="CNPJ")
	private String cnpjEmitente;
	
	@Campo(nome="UF")
	private String estadoEmitente;
	
	@Campo(nome="Val (R$)")
	private double valor;
	
	@Campo(nome="OBS")
	private String obs;
	
	public RepresentadorXML(XML xml) {
		super(xml);
		
		this.tipo = xml.getTipo().toString();
		
		this.chave = xml.getChave();
		
		try {
			
			TNfeProc nfe = (TNfeProc)xml.getObjetoReferente();
			
			this.numero = Integer.parseInt(nfe.getNFe().getInfNFe().getIde().getNNF());
			
			this.emitente = nfe.getNFe().getInfNFe().getEmit().getXNome();
			
			this.cnpjEmitente = nfe.getNFe().getInfNFe().getEmit().getCNPJ();
			
			this.estadoEmitente = nfe.getNFe().getInfNFe().getEmit().getEnderEmit().getUF().toString();
			
			try {
			
				this.valor = Double.parseDouble(nfe.getNFe().getInfNFe().getTotal().getICMSTot().getVNF());
			
			}catch(Exception ex) {
				
				this.valor = Double.parseDouble(nfe.getNFe().getInfNFe().getTotal().getISSQNtot().getVServ());
				
			}
			
			if(xml.getAssociado() != null) {
				
				try {
					
					CteProc cte = (CteProc)xml.getAssociado().getObjetoReferente();
					
					this.obs = "CTE "+cte.getCTe().getInfCte().getIde().getNCT()+", R$ "+cte.getCTe().getInfCte().getVPrest().getVTPrest();
					
				}catch(Exception ex) {
					
					ex.printStackTrace();
					
				}
				
			}
			
		}catch(Exception ex) {
			
			try {
				
				CteProc cte = (CteProc)xml.getObjetoReferente();
				
				this.numero = Integer.parseInt(cte.getCTe().getInfCte().getIde().getNCT());
				
				this.emitente = cte.getCTe().getInfCte().getEmit().getXNome();
				
				this.cnpjEmitente = cte.getCTe().getInfCte().getEmit().getCNPJ();
				
				this.estadoEmitente = cte.getCTe().getInfCte().getEmit().getEnderEmit().getUF().toString();
				
				this.valor = Double.parseDouble(cte.getCTe().getInfCte().getVPrest().getVTPrest());
				
				
				
			} catch (Exception exx) {
				// TODO Auto-generated catch block
				exx.printStackTrace();
			}
			
			
		}
		
	}

}
