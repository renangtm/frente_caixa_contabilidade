package afgtec.emissao;

import java.io.IOException;
import java.io.StringWriter;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import afgtec.config.Configuracoes;
import br.com.afgtec.CFe.EnvCFe.LoteCFe.CFe;
import br.com.afgtec.CFe.EnvCFe.LoteCFe.CFe.InfCFe;
import br.com.afgtec.CFe.EnvCFe.LoteCFe.CFe.InfCFe.Dest;
import br.com.afgtec.CFe.EnvCFe.LoteCFe.CFe.InfCFe.Det;
import br.com.afgtec.CFe.EnvCFe.LoteCFe.CFe.InfCFe.Det.Imposto;
import br.com.afgtec.CFe.EnvCFe.LoteCFe.CFe.InfCFe.Det.Imposto.COFINS;
import br.com.afgtec.CFe.EnvCFe.LoteCFe.CFe.InfCFe.Det.Imposto.COFINS.COFINSAliq;
import br.com.afgtec.CFe.EnvCFe.LoteCFe.CFe.InfCFe.Det.Imposto.ICMS;
import br.com.afgtec.CFe.EnvCFe.LoteCFe.CFe.InfCFe.Det.Imposto.PIS;
import br.com.afgtec.CFe.EnvCFe.LoteCFe.CFe.InfCFe.Det.Imposto.PIS.PISAliq;
import br.com.afgtec.CFe.EnvCFe.LoteCFe.CFe.InfCFe.Det.Prod;
import br.com.afgtec.CFe.EnvCFe.LoteCFe.CFe.InfCFe.Emit;
import br.com.afgtec.CFe.EnvCFe.LoteCFe.CFe.InfCFe.Ide;
import br.com.afgtec.CFe.EnvCFe.LoteCFe.CFe.InfCFe.InfAdic;
import br.com.afgtec.CFe.EnvCFe.LoteCFe.CFe.InfCFe.Pgto;
import br.com.afgtec.CFe.EnvCFe.LoteCFe.CFe.InfCFe.Pgto.MP;
import br.com.afgtec.CFe.EnvCFe.LoteCFe.CFe.InfCFe.Total;
import br.com.afgtec.CFe.EnvCFe.LoteCFe.CFe.InfCFe.Total.DescAcrEntr;
import br.com.afgtec.impostos.COFINSAliq1;
import br.com.afgtec.impostos.COFINSAliq2;
import br.com.afgtec.impostos.ICMS00;
import br.com.afgtec.impostos.ICMS10;
import br.com.afgtec.impostos.ICMS20;
import br.com.afgtec.impostos.ICMS30;
import br.com.afgtec.impostos.ICMS40;
import br.com.afgtec.impostos.ICMS41;
import br.com.afgtec.impostos.ICMS50;
import br.com.afgtec.impostos.ICMS60;
import br.com.afgtec.impostos.ICMS70;
import br.com.afgtec.impostos.ICMS90;
import br.com.afgtec.impostos.PISAliq1;
import br.com.afgtec.impostos.PISAliq2;
import br.com.afgtec.notas.FormaPagamento;
import br.com.afgtec.notas.ModeloNota;
import br.com.afgtec.notas.Nota;
import br.com.afgtec.notas.NotaService;
import br.com.afgtec.notas.ProdutoNota;
import br.com.afgtec.pessoa.PessoaFisica;
import br.com.afgtec.pessoa.PessoaJuridica;

public class ValidadorDocumento {

	private NotaService service;
	private SAT moduloSat;

	public CFe notaParaCFe(Nota nota) throws InvalidKeyException, NoSuchAlgorithmException, KeyStoreException,
			CertificateException, SignatureException, IOException {

		FormataNumero f2 = new FormataNumero(2);

		FormataNumero f3 = new FormataNumero(3);
		
		FormataNumero f4 = new FormataNumero(4);
		
		FormataInteiro i2 = new FormataInteiro(2);

		FormataInteiro i3 = new FormataInteiro(3);

		CFe cfe = new CFe();

		InfCFe inf = new InfCFe();

		inf.setVersaoDadosEnt("0.07");

		Ide ide = new Ide();

		ide.setCNPJ(Configuracoes.CNPJ.replaceAll("\\.", "").replaceAll("/", "").replaceAll("-", ""));
		ide.setSignAC(this.moduloSat.getCSR());
		ide.setNumeroCaixa(i3.formatar(1));

		inf.setIde(ide);

		Emit emit = new Emit();
		
		emit.setCNPJ(nota.getEmitente().getCnpj().replaceAll("\\.", "").replaceAll("/", "").replaceAll("-", ""));
		emit.setIE(nota.getEmitente().getInscricao_estadual().replaceAll("\\.", "").replaceAll("/", "").replaceAll("-",
				""));

		//emit.setXNome(nota.getEmitente().getNome());
		//emit.setXFant(nota.getEmitente().getNome());
		emit.setIndRatISSQN("N");

		inf.setEmit(emit);

		Dest dest = new Dest();

		if (nota.getDestinatario() == null) {

			dest.setCPF("");

		} else if (nota.getDestinatario().getClass().equals(PessoaFisica.class)) {

			PessoaFisica d = (PessoaFisica) nota.getDestinatario();

			dest.setCPF(d.getCpf().replaceAll("\\.", "").replaceAll("/", "").replaceAll("-", ""));
			dest.setXNome(d.getNome());

		} else if (nota.getDestinatario().getClass().equals(PessoaJuridica.class)) {

			PessoaJuridica d = (PessoaJuridica) nota.getDestinatario();

			dest.setCNPJ(d.getCnpj().replaceAll("\\.", "").replaceAll("/", "").replaceAll("-", ""));
			dest.setXNome(d.getNome());

		}

		inf.setDest(dest);

		
		int ni = 1;
		for (ProdutoNota pn : nota.getProdutos()) {

			Det det = new Det();
			
			det.setNItem(ni+"");
			ni++;
			
			Prod prod = new Prod();

			prod.setCProd(pn.getProduto().getCodigo_barra());

			prod.setXProd(pn.getProduto().getNome());

			prod.setNCM(pn.getProduto().getNcm().getNumero());

			prod.setCFOP(pn.getCfop().getNumero().replaceAll("\\.", ""));

			prod.setUCom("UN");

			prod.setVUnCom(f3.formatar(pn.getValor()));

			prod.setQCom(f4.formatar(pn.getQuantidade()) + "");

			prod.setIndRegra("A");

			prod.setVDesc(f2.formatar(pn.getDesconto()));

			//prod.setCEAN("SEM GTIN");

			prod.setVOutro(f2.formatar(pn.getOutro() + pn.getSeguro()));

			prod.setIndRegra("A");
			
			det.setProd(prod);

			det.setInfAdProd("Produto emitido referente a venda no sistema RTC Contabil");

			Imposto imp = new Imposto();

			det.setImposto(imp);

			PIS pis = new PIS();

			try {

				PISAliq1 ca = (PISAliq1) pn.getImposto().getPis();

				PISAliq c = new PISAliq();

				c.setCST(ca.getCst());
				c.setPPIS(f4.formatar(ca.getAlicotaPis()));
				c.setVBC(f2.formatar(ca.getValorBaseCalculo()));
				
				pis.setPISAliq(c);

			} catch (Exception ex) {

			}

			try {

				PISAliq2 ca = (PISAliq2) pn.getImposto().getPis();

				PISAliq c = new PISAliq();

				c.setCST(ca.getCst());
				c.setPPIS(f4.formatar(ca.getAlicotaPis()));
				c.setVBC(f2.formatar(ca.getValorBaseCalculo()));

				pis.setPISAliq(c);

			} catch (Exception ex) {

			}

			imp.setPIS(pis);

			COFINS cof = new COFINS();

			try {

				COFINSAliq1 ca = (COFINSAliq1) pn.getImposto().getCofins();

				COFINSAliq c = new COFINSAliq();

				c.setCST(ca.getCst());
				c.setPCOFINS(f4.formatar(ca.getAliquotaCofins()));
				c.setVBC(f2.formatar(ca.getValorBaseCalculo()));

				cof.setCOFINSAliq(c);

			} catch (Exception ex) {

			}

			try {

				COFINSAliq2 ca = (COFINSAliq2) pn.getImposto().getCofins();

				COFINSAliq c = new COFINSAliq();

				c.setCST(ca.getCst());
				c.setPCOFINS(f4.formatar(ca.getAliquotaCofins()));
				c.setVBC(f2.formatar(ca.getValorBaseCalculo()));

				cof.setCOFINSAliq(c);

			} catch (Exception ex) {

			}

			imp.setCOFINS(cof);

			ICMS icms = new ICMS();

			try {

				ICMS00 i00 = (ICMS00) pn.getImposto().getIcms();

				ICMS.ICMS00 icms00 = new ICMS.ICMS00();

				icms00.setCST(i00.getCst());
				icms00.setOrig(i00.getOrigem().getId() + "");
				icms00.setPICMS(f2.formatar(i00.getPorcentagemICMS()));

				icms.setICMS00(icms00);

			} catch (Exception ex) {

			}

			try {

				ICMS10 i00 = (ICMS10) pn.getImposto().getIcms();

				ICMS.ICMS00 icms00 = new ICMS.ICMS00();

				icms00.setCST(i00.getCst());
				icms00.setOrig(i00.getOrigem().getId() + "");
				icms00.setPICMS(f2.formatar(i00.getPorcentagemICMS()));

				icms.setICMS00(icms00);

			} catch (Exception ex) {

			}

			try {

				ICMS20 i00 = (ICMS20) pn.getImposto().getIcms();

				ICMS.ICMS00 icms00 = new ICMS.ICMS00();

				icms00.setCST(i00.getCst());
				icms00.setOrig(i00.getOrigem().getId() + "");
				icms00.setPICMS(f2.formatar(i00.getAlicotaIcms()));

				icms.setICMS00(icms00);

			} catch (Exception ex) {

			}

			try {

				ICMS30 i00 = (ICMS30) pn.getImposto().getIcms();

				ICMS.ICMS00 icms00 = new ICMS.ICMS00();

				icms00.setCST(i00.getCst());
				icms00.setOrig(i00.getOrigem().getId() + "");
				icms00.setPICMS(f2.formatar(i00.getAlicotaIcmsST()));

				icms.setICMS00(icms00);

			} catch (Exception ex) {

			}

			try {

				ICMS40 i00 = (ICMS40) pn.getImposto().getIcms();

				ICMS.ICMS40 icms40 = new ICMS.ICMS40();

				icms40.setCST(i00.getCst());
				icms40.setOrig(i00.getOrigem().getId() + "");

				icms.setICMS40(icms40);

			} catch (Exception ex) {

			}

			try {

				ICMS41 i00 = (ICMS41) pn.getImposto().getIcms();

				ICMS.ICMS40 icms40 = new ICMS.ICMS40();

				icms40.setCST(i00.getCst());
				icms40.setOrig(i00.getOrigem().getId() + "");

				icms.setICMS40(icms40);

			} catch (Exception ex) {

			}

			try {

				ICMS50 i00 = (ICMS50) pn.getImposto().getIcms();

				ICMS.ICMS40 icms40 = new ICMS.ICMS40();

				icms40.setCST(i00.getCst());
				icms40.setOrig(i00.getOrigem().getId() + "");

				icms.setICMS40(icms40);

			} catch (Exception ex) {

			}

			try {

				ICMS60 i00 = (ICMS60) pn.getImposto().getIcms();

				ICMS.ICMS00 icms00 = new ICMS.ICMS00();

				icms00.setCST(i00.getCst());
				icms00.setOrig(i00.getOrigem().getId() + "");
				icms00.setPICMS(f2.formatar((i00.getValorIcms()
						/ (pn.getValor() * pn.getQuantidade() + pn.getOutro() + pn.getSeguro())
						* 100)));

				icms.setICMS00(icms00);

			} catch (Exception ex) {

			}

			try {

				ICMS70 i00 = (ICMS70) pn.getImposto().getIcms();

				ICMS.ICMS00 icms00 = new ICMS.ICMS00();

				icms00.setCST(i00.getCst());
				icms00.setOrig(i00.getOrigem().getId() + "");
				icms00.setPICMS(f2.formatar(i00.getPorcentagemICMS()));

				icms.setICMS00(icms00);

			} catch (Exception ex) {

			}

			try {

				ICMS90 i00 = (ICMS90) pn.getImposto().getIcms();

				ICMS.ICMS00 icms00 = new ICMS.ICMS00();

				icms00.setCST(i00.getCst());
				icms00.setOrig(i00.getOrigem().getId() + "");
				icms00.setPICMS(f2.formatar(i00.getPorcentagemICMS()));

				icms.setICMS00(icms00);

			} catch (Exception ex) {

			}

			imp.setICMS(icms);

			imp.setVItem12741(f2.formatar(nota.getProdutos().stream().mapToDouble(i->i.getImposto().getTotalImpostos()).sum()));

			inf.getDet().add(det);

		}

		Total total = new Total();

		DescAcrEntr dae = new DescAcrEntr();

		if (nota.getDescontoSubtotalItens() > 0) {

			dae.setVDescSubtot(f2.formatar(nota.getDescontoSubtotalItens()));

		} else {

			dae.setVAcresSubtot(f2.formatar(nota.getAcrescimoSubtotalItens()));

		}

		total.setVCFeLei12741(f2.formatar(
				nota.getProdutos().stream().map(x -> x.getImposto()).mapToDouble(i -> i.getTotalImpostos()).sum()));

		inf.setTotal(total);

		Pgto pg = new Pgto();

		MP mp = new MP();
		
		if(Arrays.asList(
				FormaPagamento.CARTAO_CREDITO
				,FormaPagamento.CARTAO_CREDITO).contains(nota.getForma_pagamento())){
		
			mp.setCAdmC(i3.formatar(nota.getCredenciadoraCartao()));

		}
		
		mp.setVMP(f2.formatar(nota.getValorMeioDePagamento()));
		mp.setCMP(i2.formatar(nota.getForma_pagamento().getId()));

		pg.getMP().add(mp);

		inf.setPgto(pg);

		InfAdic ia = new InfAdic();
		ia.setInfCpl("Cupom fiscal emitido referente a venda em balcao no sistema RTC Contabil");

		inf.setInfAdic(ia);

		cfe.setInfCFe(inf);

		return cfe;

	}

	private GeradorCupomSAT gc;

	public ValidadorDocumento(NotaService service, SAT moduloSat, GeradorCupomSAT gc) {

		this.service = service;
		this.moduloSat = moduloSat;
		this.gc = gc;

	}

	public boolean validarFiscalmente(Nota nota) {

		if (!this.service.verificarIntegridadeNota(nota)) {

			throw new RuntimeException("A nota nao esta integra");

		}

		if (nota.getModelo().equals(ModeloNota.NFCE)) {

			if (this.gc == null) {

				throw new RuntimeException("E necessario um gerador de cupom para emitir o CFe");

			}

			if (this.moduloSat != null) {

				try {

					this.moduloSat.iniciar();

					CFe cfe = this.notaParaCFe(nota);

					JAXBContext context = JAXBContext.newInstance(CFe.class);
					Marshaller m = context.createMarshaller();

					StringWriter wr = new StringWriter();

					m.marshal(cfe, wr);

					String xml = wr.toString();

					String ret = this.moduloSat.getInterface().EnviarDadosVenda(this.moduloSat.gerarNumeroSessao(),
							nota.getEmpresa().getParametrosEmissao().getSenha_sat(), xml);

					System.out.println(xml);
					System.out.println(ret);

					String[] retorno = ret.split("\\|");

					if (retorno[0].equalsIgnoreCase("erro")) {

						throw new RuntimeException("Erro ao emitir CFe");

					}


					String chave = retorno[8];

					nota.setNumero(0);

					nota.setChave(chave);
					
					int numero = Integer.parseInt(nota.getChave().substring(26, 35));
					nota.setNumero(numero);
					
					String b64 = retorno[8].replaceAll("CFe", "")+"|"+retorno[7];
					
					for(int i=9;i<retorno.length;i++){
						
						b64 += "|"+retorno[i];
						
					}

					this.gc.gerarCupomFiscal(nota, b64);

				} catch (Exception e) {

					throw new RuntimeException(e);

				}

			} else {

				throw new RuntimeException("Modulo SAT necessario para emissao de CFe");

			}

		}

		return false;

	}

}
