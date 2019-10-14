package br.com.impressao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;

import br.com.base.ET;
import br.com.empresa.Empresa;
import br.com.produto.ProdutoRelatorio;
import br.com.produto.ProdutoService;
import br.com.usuario.Usuario;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class GeradorRelatorioProdutos {

	public static void main(String[] args) throws JRException, IOException {

		EntityManager et = ET.nova();

		Usuario usuario = et.find(Usuario.class, 1);

		ProdutoService ps = new ProdutoService(et);
		ps.setEmpresa(usuario.getPf().getEmpresa());

		Calendar antes = Calendar.getInstance();
		antes.add(Calendar.DATE, -60);

		List<ProdutoRelatorio> produtos = ps.getProdutoRelatorio("", antes, Calendar.getInstance());

		System.out.println(produtos.size());
		
		new GeradorRelatorioProdutos().gerarReltorio(produtos, usuario.getPf().getEmpresa(), antes,
				Calendar.getInstance());

	}

	public void gerarReltorio(List<ProdutoRelatorio> produtos, Empresa empresa, Calendar inicio, Calendar fim)
			throws JRException, IOException {

		JasperReport relatorio = JasperCompileManager.compileReport(
				GeradorCupomSATModelo1.class.getClassLoader().getResourceAsStream("icones/produtos.jrxml"));

		JasperReport subRelatorio = JasperCompileManager.compileReport(
				GeradorCupomSATModelo1.class.getClassLoader().getResourceAsStream("icones/subRelatorioProdutos.jrxml"));

		BufferedImage img = ImageIO.read(new ByteArrayInputStream(empresa.getLogo().getArquivo()));
		String nomeEmpresa = empresa.getPj().getNome();
		String cidadeEmpresa = empresa.getPj().getEndereco().getCidade().getNome() + " - "
				+ empresa.getPj().getEndereco().getCidade().getEstado().getSigla();
		String cepEmpresa = empresa.getPj().getEndereco().getCep();
		String ruaEmpresa = empresa.getPj().getEndereco().getRua() + ", " + empresa.getPj().getEndereco().getNumero();
		String cnpjEmpresa = empresa.getPj().getCnpj();
		String ieEmpresa = empresa.getPj().getInscricao_estadual();

		Map<String, Object> parametros = new HashMap<String, Object>();

		parametros.put("logo", img);
		parametros.put("nomeEmpresa", nomeEmpresa);
		parametros.put("cnpjEmpresa", cnpjEmpresa);
		parametros.put("ieEmpresa", ieEmpresa);
		parametros.put("cidadeEmpresa", cidadeEmpresa);
		parametros.put("ruaEmpresa", ruaEmpresa);
		parametros.put("cepEmpresa", cepEmpresa);
		parametros.put("dataInicial", inicio.getTime());
		parametros.put("dataFinal", fim.getTime());
		parametros.put("subReport", subRelatorio);

		JRDataSource jrd = new JRBeanCollectionDataSource(produtos);

		JasperPrint jp = JasperFillManager.fillReport(relatorio, parametros, jrd);

		JasperViewer.viewReport(jp, false);

	}

}
