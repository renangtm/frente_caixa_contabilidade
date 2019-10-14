package br.com.processosComNFe;

public enum ConfiguracaoEstadoNFe {

	
	AN(91,
		new String[]{"hom.sefazvirtual.fazenda.gov.br","hom.nfe.fazenda.gov.br","hom.cte.fazenda.gov.br"},
		"https://www.sefazvirtual.fazenda.gov.br/NFeInutilizacao4/NFeInutilizacao4.asmx",
		"https://www.sefazvirtual.fazenda.gov.br/NFeConsultaProtocolo4/NFeConsultaProtocolo4.asmx",
		"https://www.sefazvirtual.fazenda.gov.br/NFeStatusServico4/NFeStatusServico4.asmx",
		"https://www.nfe.fazenda.gov.br/NFeConsultaDest/NFeConsultaDest.asmx",
		"https://www.nfe.fazenda.gov.br/NFeRecepcaoEvento4/NFeRecepcaoEvento4.asmx",
		"https://www.sefazvirtual.fazenda.gov.br/NFeAutorizacao4/NFeAutorizacao4.asmx",
		"https://www.sefazvirtual.fazenda.gov.br/NFeRetAutorizacao4/NFeRetAutorizacao4.asmx",
		"https://www.sefazvirtual.fazenda.gov.br/NFeInutilizacao4/NFeInutilizacao4.asmx",
		"https://www1.cte.fazenda.gov.br/CTeDistribuicaoDFe/CTeDistribuicaoDFe.asmx",
		"https://www1.nfe.fazenda.gov.br/NFeDistribuicaoDFe/NFeDistribuicaoDFe.asmx"
	),
	SP(35,
		new String[]{"homologacao.nfe.fazenda.sp.gov.br"},
		"https://nfe.fazenda.sp.gov.br/ws/nfeinutilizacao4.asmx",
		"https://nfe.fazenda.sp.gov.br/ws/nfeconsultaprotocolo4.asmx",
		"https://nfe.fazenda.sp.gov.br/ws/nfestatusservico4.asmx",
		"https://nfe.fazenda.sp.gov.br/ws/cadconsultacadastro4.asmx",
		"https://nfe.fazenda.sp.gov.br/ws/nferecepcaoevento4.asmx",
		"https://nfe.fazenda.sp.gov.br/ws/nfeautorizacao4.asmx",
		"https://nfe.fazenda.sp.gov.br/ws/nferetautorizacao4.asmx",
		"https://nfe.fazenda.sp.gov.br/ws/nfeinutilizacao4.asmx",
		"sem esse servico",
		"sem esse servico");
	
	private int codigo;
	private String[] trusts;
	private String nfeInutilizacao;
	private String nfeConsultaProtocolo;
	private String nfeStatusServico;
	private String nfeConsultaCadastro;
	private String recepcaoEvento;
	private String nFeAutorizacao;
	private String nFeRetAutorizacao;
	private String nFeInut;
	private String cTeDistribuicaoDFe;
	private String nFeDistribuicaoDFe;
	
	private ConfiguracaoEstadoNFe(int codigo, String[] trusts, String nfeInutilizacao, String nfeConsultaProtocolo,String nFeStatusServico,
			String nfeConsultaCadastro, String recepcaoEvento, String nFeAutorizacao, String nFeRetAutorizacao,String nfeInut,String ctEDistribuicaoDFe,
			String nfEDistribuicaoDFe) {

			this.codigo = codigo;
			this.trusts = trusts;
			this.nfeInutilizacao = nfeInutilizacao;
			this.nfeConsultaProtocolo = nfeConsultaProtocolo;
			this.nfeStatusServico = nFeStatusServico;
			this.nfeConsultaCadastro = nfeConsultaCadastro;
			this.recepcaoEvento = recepcaoEvento;
			this.nFeAutorizacao = nFeAutorizacao;
			this.nFeRetAutorizacao = nFeRetAutorizacao;
			this.nFeInut = nfeInut;
			this.cTeDistribuicaoDFe = ctEDistribuicaoDFe;
			this.nFeDistribuicaoDFe = nfEDistribuicaoDFe;
			
		
	}
	
	public String getnFeDistribuicaoDFe() {
		return nFeDistribuicaoDFe;
	}

	public int getCodigo() {
		return codigo;
	}

	public String[] getTrusts() {
		return trusts;
	}

	public String getNfeInutilizacao() {
		return nfeInutilizacao;
	}

	public String getNfeConsultaProtocolo() {
		return nfeConsultaProtocolo;
	}

	public String getNfeStatusServico() {
		return nfeStatusServico;
	}

	public String getNfeConsultaCadastro() {
		return nfeConsultaCadastro;
	}

	public String getRecepcaoEvento() {
		return recepcaoEvento;
	}

	public String getnFeAutorizacao() {
		return nFeAutorizacao;
	}
	
	public String getnFeInut(){
		return nFeInut;
	}

	public String getnFeRetAutorizacao() {
		return nFeRetAutorizacao;
	}
	/**
	 * @return the cTeDistribuicaoDFe
	 */
	public String getcTeDistribuicaoDFe() {
		return cTeDistribuicaoDFe;
	}

}
