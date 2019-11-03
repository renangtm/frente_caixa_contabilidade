package br.com.impressao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import br.com.emissao.GeradorCupomSAT;
import br.com.nota.Nota;
import br.com.nota.ProdutoNota;
import br.com.pessoa.PessoaFisica;
import br.com.pessoa.PessoaJuridica;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import br.com.inicial.Pagamento;

public class GeradorCupomSATModelo1 implements GeradorCupomSAT {

	@Override
	public void gerarCupomFiscal(Nota nota, double impostosAproximados, List<Pagamento> pagamentos, String base64) {

		try {

			String nomeEmpresa = nota.getEmitente().getNome();

			String cidadeEmpresa = nota.getEmitente().getEndereco().getCidade().getNome() + " - "
					+ nota.getEmitente().getEndereco().getCidade().getEstado().getSigla();
			
			String cepEmpresa = nota.getEmitente().getEndereco().getCep();
			
			String ruaEmpresa = nota.getEmitente().getEndereco().getRua() + ", " + nota.getEmitente().getEndereco().getNumero();

			String cnpjEmpresa = nota.getEmitente().getCnpj();

			String ieEmpresa = nota.getEmitente().getInscricao_estadual();

			String numeroCupom = nota.getNumero() + "";

			String cnpjcpfConsumidor = "------------";

			if(nota.getDestinatario() == null) {
				
				if(nota.getCpfNotaSemDestinatario() != null) {
					
					if(!nota.getCpfNotaSemDestinatario().isEmpty()) {
					
						cnpjcpfConsumidor = nota.getCpfNotaSemDestinatario();
					
					}
					
				}
				
			}
			
			if (nota.getDestinatario() != null) {

				if (nota.getDestinatario().getClass().equals(PessoaFisica.class)) {

					PessoaFisica pf = (PessoaFisica) nota.getDestinatario();
					cnpjcpfConsumidor = pf.getCpf();

				} else {

					PessoaJuridica pj = (PessoaJuridica) nota.getDestinatario();
					cnpjcpfConsumidor = pj.getCnpj();

				}

			}

			double total = nota.getValorTotalNota() + nota.getDescontoTotal();
			double descontos = nota.getDescontoTotal();

			String formaPagamento = nota.getForma_pagamento().toString();

			double valorPagamento = nota.getValorMeioDePagamento();

			double troco = nota.getTroco();

			List<ProdutoSATModelo1> produtos = new ArrayList<ProdutoSATModelo1>();

			for (ProdutoNota pn : nota.getProdutos()) {

				ProdutoSATModelo1 p = new ProdutoSATModelo1();

				p.setCodigo(pn.getProduto().getCodigo_barra());
				p.setDesconto(pn.getDesconto());

				double aliq = ((pn.getImposto().getIcms().getValorIcms() + pn.getImposto().getIcms().getValorIcmsST())
						/ (pn.getQuantidade() * pn.getValor() + pn.getOutro() + pn.getSeguro() + pn.getFrete()))*100;

				p.setIcms(aliq);

				p.setDesconto(pn.getDesconto());

				p.setUnidade(pn.getProduto().getEstoque().getTipo().toString());

				p.setNome(pn.getProduto().getNome());

				p.setQuantidade(pn.getQuantidade());

				p.setValor_unitario(pn.getValor() + (pn.getOutro() + pn.getSeguro() + pn.getFrete()) / pn.getQuantidade());

				p.setValor(p.getQuantidade()*p.getValor_unitario());
				
				produtos.add(p);
				
				
				
			}

			String numeroSat = nota.getEmpresa().getParametrosEmissao().getNumeroSat();

			String qrCode = base64;

			String chave = nota.getChave();

			JasperReport jr = JasperCompileManager.compileReport(
					GeradorCupomSATModelo1.class.getClassLoader().getResourceAsStream("icones/cupom_modelo_1.jrxml"));

			JasperReport subPagamentos = JasperCompileManager.compileReport(
					GeradorCupomSATModelo1.class.getClassLoader().getResourceAsStream("icones/subPagamentos.jrxml"));

			
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(nota.getEmpresa().getLogo().getArquivo()));

			Map<String, Object> parametros = new HashMap<String, Object>();

			parametros.put("qrCode", qrCode);
			parametros.put("logo", img);
			parametros.put("nomeEmpresa", nomeEmpresa);
			parametros.put("cnpjEmpresa", cnpjEmpresa);
			parametros.put("ieEmpresa", ieEmpresa);
			parametros.put("cidadeEmpresa", cidadeEmpresa);
			parametros.put("ruaEmpresa", ruaEmpresa);
			parametros.put("cepEmpresa", cepEmpresa);
			parametros.put("numeroCupom", numeroCupom);
			parametros.put("cpfCnpjConsumidor", cnpjcpfConsumidor);
			parametros.put("total", total);
			parametros.put("desconto", descontos);
			parametros.put("chave", chave);
			parametros.put("numeroSat", numeroSat);
			parametros.put("troco", troco);
			parametros.put("formaPagamento", formaPagamento);
			parametros.put("valorPagamento", valorPagamento);
			parametros.put("valorImpostos", impostosAproximados);
			parametros.put("subReportPagamentos", subPagamentos);
			parametros.put("pagamentos", pagamentos);

			JRDataSource jrd = new JRBeanCollectionDataSource(produtos);

			JasperPrint jp = JasperFillManager.fillReport(jr, parametros, jrd);

			JasperViewer.viewReport(jp,false);

		} catch (Exception ex) {

			throw new RuntimeException(ex);

		}

	}

}
