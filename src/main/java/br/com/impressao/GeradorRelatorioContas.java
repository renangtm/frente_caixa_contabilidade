package br.com.impressao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;

import br.com.base.ET;
import br.com.conta.Conta;
import br.com.conta.ContaService;
import br.com.conta.TipoConta;
import br.com.empresa.Empresa;
import br.com.usuario.Usuario;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class GeradorRelatorioContas {

	public static void main(String[] args) throws JRException, IOException {

		EntityManager et = ET.nova();

		Usuario usuario = et.find(Usuario.class, 1);

		ContaService cs = new ContaService(et);
		cs.setEmpresa(usuario.getPf().getEmpresa());
		cs.setTipo(TipoConta.PAGAR);

		Calendar antes = Calendar.getInstance();
		antes.add(Calendar.DATE, -60);

		List<Conta> contas = cs.getContas(antes, Calendar.getInstance());

		new GeradorRelatorioContas().gerarReltorio(contas, AgrupadoresData.MES.getAgrupador(),
				usuario.getPf().getEmpresa(), antes, Calendar.getInstance());

	}

	public void gerarReltorio(List<Conta> contas, AgrupadorData agrupador, Empresa empresa, Calendar inicio,
			Calendar fim) throws JRException, IOException {

		HashMap<Calendar, List<Conta>> grupos = new HashMap<Calendar, List<Conta>>();

		lbl: for (Conta conta : contas) {

			for (Calendar cal : grupos.keySet()) {

				if (agrupador.agrupa(cal, conta.getVencimento().getData())
						&& grupos.get(cal).get(0).getTipo() == conta.getTipo()) {

					grupos.get(cal).add(conta);

					continue lbl;

				}

			}

			List<Conta> grupo = new ArrayList<Conta>();
			grupo.add(conta);

			grupos.put(conta.getVencimento().getData(), grupo);

		}

		List<GrupoContas> gruposConta = new ArrayList<GrupoContas>();

		for (Calendar cal : grupos.keySet()) {

			GrupoContas gc = new GrupoContas();

			gc.setData(cal.getTime());
			gc.setContas(grupos.get(cal).stream().map(c -> new ContaRelatorio(c)).collect(Collectors.toList()));
			gc.setTipo(grupos.get(cal).get(0).getTipo());

			gc.setTotal(gc.getContas().stream().mapToDouble(c -> c.getPendencia()).sum());

			gruposConta.add(gc);

		}

		JasperReport relatorio = JasperCompileManager.compileReport(
				GeradorCupomSATModelo1.class.getClassLoader().getResourceAsStream("icones/contas.jrxml"));

		JasperReport subRelatorio = JasperCompileManager.compileReport(
				GeradorCupomSATModelo1.class.getClassLoader().getResourceAsStream("icones/subRelatorioContas.jrxml"));

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
		parametros.put("padraoData", agrupador.dateFormat());

		JRDataSource jrd = new JRBeanCollectionDataSource(gruposConta);

		JasperPrint jp = JasperFillManager.fillReport(relatorio, parametros, jrd);

		JasperViewer.viewReport(jp, false);

	}

}
