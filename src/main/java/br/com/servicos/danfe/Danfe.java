package br.com.servicos.danfe;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import br.com.empresa.ParametrosEmissao;
import br.com.enviNFe.TNFe;
import br.com.enviNFe.TNFe.InfNFe;
import br.com.enviNFe.TNFe.InfNFe.Cobr.Dup;
import br.com.enviNFe.TNFe.InfNFe.Det;
import br.com.enviNFe.TNFe.InfNFe.Det.Imposto.ICMS;
import br.com.enviNFe.TNFe.InfNFe.Det.Imposto.ICMS.ICMS20;
import br.com.enviNFe.TNFe.InfNFe.Det.Imposto.ICMS.ICMS40;
import br.com.enviNFe.TNFe.InfNFe.Det.Imposto.ICMS.ICMS00;
import br.com.enviNFe.TNFe.InfNFe.Total;
import br.com.enviNFe.TNFe.InfNFe.Total.ICMSTot;
import br.com.enviNFe.TNFe.InfNFe.Transp;
import br.com.enviNFe.TNFe.InfNFe.Transp.Vol;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Danfe {

	private JasperReport getReportDuplicata() throws JRException {

		InputStream is = this.getClass().getResourceAsStream("/icones/danfe_duplicata.jrxml");

		return JasperCompileManager.compileReport(is);

	}

	private JasperReport getReportDANFE() throws JRException {

		InputStream is = this.getClass().getResourceAsStream("/icones/danfe.jrxml");

		return JasperCompileManager.compileReport(is);

	}

	private JasperPrint danfe;

	public Danfe() throws JRException {

		this.getReportDuplicata();

	}

	public Danfe(TNFe nota, ParametrosEmissao gr, String chave, String protocolo_autorizacao) throws JRException, IOException {

		List<Duplicata> dups = new ArrayList<Duplicata>();

		for (Dup dup : nota.getInfNFe().getCobr().getDup()) {

			Duplicata d = new Duplicata();

			d.setFatura(dup.getNDup());
			d.setValor(dup.getVDup());
			
			String[] dt = dup.getDVenc().split("-");
			String data = "";
			for (String da : dt)
				data = da + "/" + data;
			data = data.substring(0, data.length() - 1);
			
			d.setVencimento(data);

			dups.add(d);

		}

		List<ProdutoDanfe> produtos = new ArrayList<ProdutoDanfe>();

		for (Det det : nota.getInfNFe().getDet()) {

			ProdutoDanfe prod = new ProdutoDanfe();

			prod.setCodigo(det.getProd().getCProd());
			prod.setNome(det.getProd().getXProd());
			prod.setNcm(det.getProd().getNCM());
			prod.setCfop(det.getProd().getCFOP());
			prod.setUn(det.getProd().getUCom());
			prod.setVu(det.getProd().getVUnCom());
			prod.setVt(det.getProd().getVProd());
			prod.setQtd(det.getProd().getQCom());

			List<JAXBElement<?>> impostos = det.getImposto().getContent();

			for (JAXBElement<?> imposto : impostos) {

				Object imp = imposto.getValue();

				if (imp.getClass().equals(ICMS.class)) {
					
					ICMS ic = (ICMS)imp;
					
					
					if (ic.getICMS00() != null) {

						ICMS00 icms00 = (ICMS00) ic.getICMS00();

						prod.setCst(icms00.getCST());
						prod.setBase_calculo(icms00.getVBC());
						prod.setIcms(icms00.getVICMS());
						prod.setIpi("0.00");
						prod.setIpip("0.00");
						prod.setIcmsp(icms00.getPICMS());

					}

					if (ic.getICMS20() != null) {

						ICMS20 icms20 = (ICMS20) ic.getICMS20();

						prod.setCst(icms20.getCST());
						prod.setBase_calculo(icms20.getVBC());
						prod.setIcms(icms20.getVICMS());
						prod.setIpi("0.00");
						prod.setIpip("0.00");
						prod.setIcmsp(icms20.getPICMS());

					} else if (ic.getICMS40() != null) {

						ICMS40 icms40 = ic.getICMS40();

						prod.setCst(icms40.getCST());
						prod.setBase_calculo("0.00");
						prod.setIcms("0.00");
						prod.setIpi("0.00");
						prod.setIpip("0.00");
						prod.setIcmsp("0.00");

					}

				}

				prod.setInformacoes_adcionais(det.getInfAdProd());

			}

			produtos.add(prod);

		}

		Map<String, Object> parametros = new HashMap<String, Object>();

		InfNFe ife = nota.getInfNFe();

		parametros.put("serie", Integer.parseInt(ife.getIde().getSerie())+"");
		parametros.put("nome_empresa", ife.getEmit().getXNome());
		parametros.put("numero_nota", ife.getIde().getNNF());
		parametros.put("logo", gr.getEmpresa().getLogo().getArquivo());
		parametros.put("cidade", ife.getEmit().getEnderEmit().getXMun());
		parametros.put("entrada_saida", ife.getIde().getTpNF().equals("1") ? "1" : "0");
		parametros.put("chave", chave);
		parametros.put("sub_relatorio_duplicata", this.getReportDuplicata());
		parametros.put("natureza_operacao", ife.getIde().getNatOp());
		parametros.put("inscricao_estadual", ife.getEmit().getIE());
		parametros.put("protocolo", protocolo_autorizacao);
		parametros.put("cnpj", ife.getEmit().getCNPJ());

		String[] dt = ife.getIde().getDhEmi().split("T")[0].split("-");
		String data = "";
		for (String d : dt)
			data = d + "/" + data;
		data = data.substring(0, data.length() - 1);

		parametros.put("data_emissao", data);

		String hora = ife.getIde().getDhEmi().split("T")[1];

		parametros.put("hora_emissao", hora);

		parametros.put("cep", ife.getEmit().getEnderEmit().getCEP());

		parametros.put("telefone", ife.getEmit().getEnderEmit().getFone());

		parametros.put("endereco", ife.getEmit().getEnderEmit().getXLgr());

		parametros.put("bairro", ife.getEmit().getEnderEmit().getXBairro());

		parametros.put("duplicatas", dups);

		parametros.put("telefone_destino", ife.getDest().getEnderDest().getFone());

		parametros.put("nome_empresa_destino", ife.getDest().getXNome());

		parametros.put("cnpj_destino", ife.getDest().getCNPJ());

		parametros.put("cep_destino", ife.getDest().getEnderDest().getCEP());

		parametros.put("bairro_destino", ife.getDest().getEnderDest().getXBairro());

		parametros.put("endereco_destino", ife.getDest().getEnderDest().getXLgr());

		parametros.put("cidade_destino", ife.getDest().getEnderDest().getXMun());

		parametros.put("inscricao_estadual_destino", ife.getDest().getIE());

		parametros.put("estado_destino", ife.getDest().getEnderDest().getUF().toString());

		parametros.put("estado", ife.getEmit().getEnderEmit().getUF().toString());

		Total total = ife.getTotal();

		if (total.getICMSTot() != null) {

			ICMSTot it = total.getICMSTot();

			parametros.put("base_calculo", it.getVBC());
			parametros.put("valor_icms", it.getVICMS());
			parametros.put("total_produtos", it.getVProd());
			parametros.put("total_nota", it.getVNF());
			parametros.put("frete", it.getVFrete());

		}

		Transp transp = ife.getTransp();

		parametros.put("nome_transportadora", transp.getTransporta().getXNome());
		parametros.put("frete_conta", (transp.getModFrete().equals("0") ? "0-EMITENTE" : "1-DESTINATARIO"));
		parametros.put("estado_transportadora", transp.getTransporta().getUF().toString());
		parametros.put("cnpj_transportadora", transp.getTransporta().getCNPJ());
		parametros.put("inscricao_estadual_transportadora",transp.getTransporta().getIE());
		parametros.put("endereco_transportadora", transp.getTransporta().getXEnder());
		parametros.put("cidade_transportadora", transp.getTransporta().getXMun());
		
		List<Vol> vol = transp.getVol();

		int volumes = 0;
		double pesoL = 0;
		double pesoB = 0;

		for (Vol v : vol) {
			volumes += Integer.parseInt(v.getQVol());
			pesoL += Double.parseDouble(v.getPesoL());
			pesoB += Double.parseDouble(v.getPesoB());
		}

		parametros.put("volumes", volumes + "");
		parametros.put("peso_liquido", pesoL + "");
		parametros.put("peso_bruto", pesoB + "");
		
		parametros.put("informacoes_adcionais", ife.getInfAdic().getInfCpl());

		this.danfe = JasperFillManager.fillReport(this.getReportDANFE(), parametros,
				new JRBeanCollectionDataSource(produtos));

	}

	public JasperPrint getDANFE() {

		return this.danfe;

	}

}
