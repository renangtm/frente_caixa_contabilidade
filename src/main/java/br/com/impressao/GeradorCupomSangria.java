package br.com.impressao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import br.com.caixa.Reposicao;
import br.com.caixa.Sangria;
import br.com.movimento_financeiro.Movimento;
import br.com.nota.FormaPagamentoNota;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class GeradorCupomSangria {

	public void gerarCupom(Sangria sangria) throws JRException, IOException {

		JasperReport jr = JasperCompileManager.compileReport(
				GeradorCupomSATModelo1.class.getClassLoader().getResourceAsStream("icones/sangria.jrxml"));

		JasperReport subSangria  = JasperCompileManager.compileReport(
				GeradorCupomSATModelo1.class.getClassLoader().getResourceAsStream("icones/subSangria.jrxml"));
		
		BufferedImage img = ImageIO
				.read(new ByteArrayInputStream(sangria.getExpediente().getCaixa().getEmpresa().getLogo().getArquivo()));

		String nomeEmpresa = sangria.getExpediente().getCaixa().getEmpresa().getPj().getNome();
		String cnpjEmpresa = sangria.getExpediente().getCaixa().getEmpresa().getPj().getCnpj();
		String ieEmpresa = sangria.getExpediente().getCaixa().getEmpresa().getPj().getInscricao_estadual();
		String cidadeEmpresa = sangria.getExpediente().getCaixa().getEmpresa().getPj().getEndereco().getCidade()
				.getNome();
		String ruaEmpresa = sangria.getExpediente().getCaixa().getEmpresa().getPj().getEndereco().getRua();
		String cepEmpresa = sangria.getExpediente().getCaixa().getEmpresa().getPj().getEndereco().getCep();

		String operador = sangria.getExpediente().getUsuario().getPf().getNome() + " - Caixa: "
				+ sangria.getExpediente().getCaixa().getNumero();
		String gerente = sangria.getGerente().getPf().getNome();

		double total = sangria.getValor();
		
		double totalMovimentado = 0;

		double saldoInicial = sangria.getSaldo_inicial();

		double finalCaixa = saldoInicial;
		
		Map<String, ItemSangria> itens = new HashMap<String, ItemSangria>();

		for (Movimento m : sangria.getMovimentos()) {

			String str = m.getFormaPagamento().toString();

			ItemSangria it = itens.get(str);

			if (it == null) {

				it = new ItemSangria();
				it.setTipo(str);
				itens.put(str, it);

			}

			MovimentoSangria ms = new MovimentoSangria(m);
			
			if(m.getFormaPagamento().equals(FormaPagamentoNota.DINHEIRO)){
				
				finalCaixa += (m.getValor() * (m.getOperacao().isCredito() ? 1 : -1));
				
			}
			
			totalMovimentado += (m.getValor() * (m.getOperacao().isCredito() ? 1 : -1));
			it.setTotal(it.getTotal() + (m.getValor() * (m.getOperacao().isCredito() ? 1 : -1)));
			it.getMovimentos().add(ms);
			
		}

		for (Reposicao m : sangria.getReposicoes()) {

			String str = "REPOSICAO";

			ItemSangria it = itens.get(str);

			if (it == null) {

				it = new ItemSangria();
				it.setTipo(str);
				itens.put(str, it);

			}

			MovimentoSangria ms = new MovimentoSangria(m);

			finalCaixa += m.getValor();
			totalMovimentado += m.getValor();
			it.setTotal(it.getTotal() + m.getValor());
			it.getMovimentos().add(ms);
			
		}
		
		finalCaixa -= sangria.getValor();
		
		List<ItemSangria> litens = new ArrayList<ItemSangria>(itens.values());
		
		for(ItemSangria id : litens){
			
			System.out.println(id.getTotal()+"!!!!!!!!!!!!");
			
		}
		
		Map<String, Object> parametros = new HashMap<String, Object>();

		parametros.put("logo", img);
		parametros.put("nomeEmpresa", nomeEmpresa);
		parametros.put("cnpjEmpresa", cnpjEmpresa);
		parametros.put("ieEmpresa", ieEmpresa);
		parametros.put("cidadeEmpresa", cidadeEmpresa);
		parametros.put("ruaEmpresa", ruaEmpresa);
		parametros.put("cepEmpresa", cepEmpresa);
		parametros.put("total", total);
		parametros.put("caixa", operador);
		parametros.put("gerente", gerente);
		parametros.put("saldoInicial", saldoInicial);
		parametros.put("saldoFinal", finalCaixa);
		parametros.put("totalMovimentado", totalMovimentado);
		parametros.put("subReport", subSangria);
		
		JRDataSource jrd = new JRBeanCollectionDataSource(litens);
		
		JasperPrint jp = JasperFillManager.fillReport(jr, parametros,jrd);

		JasperViewer.viewReport(jp, false);

	}

}
