package br.com.impressao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import br.com.caixa.Sangria;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class GeradorCupomSangria {

	public void gerarCupom(Sangria sangria) throws JRException, IOException {

		JasperReport jr = JasperCompileManager.compileReport(
				GeradorCupomSATModelo1.class.getClassLoader().getResourceAsStream("icones/sangria.jrxml"));

		BufferedImage img = ImageIO.read(new ByteArrayInputStream(sangria.getExpediente().getCaixa().getEmpresa().getLogo().getArquivo()));
		
		String nomeEmpresa = sangria.getExpediente().getCaixa().getEmpresa().getPj().getNome();
		String cnpjEmpresa = sangria.getExpediente().getCaixa().getEmpresa().getPj().getCnpj();
		String ieEmpresa = sangria.getExpediente().getCaixa().getEmpresa().getPj().getInscricao_estadual();
		String cidadeEmpresa = sangria.getExpediente().getCaixa().getEmpresa().getPj().getEndereco().getCidade().getNome();
		String ruaEmpresa = sangria.getExpediente().getCaixa().getEmpresa().getPj().getEndereco().getRua();
		String cepEmpresa = sangria.getExpediente().getCaixa().getEmpresa().getPj().getEndereco().getCep();
		
		String operador = sangria.getExpediente().getUsuario().getPf().getNome()+" - Caixa: "+sangria.getExpediente().getCaixa().getNumero();
		String gerente = sangria.getGerente().getPf().getNome();
		
		double total = sangria.getValor();
		
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
		


		JasperPrint jp = JasperFillManager.fillReport(jr, parametros);

		JasperViewer.viewReport(jp,false);

	}
	
}
