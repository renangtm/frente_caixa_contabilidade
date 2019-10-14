package br.com.processosComNFe;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.httpclient.protocol.Protocol;

import br.com.base.ET;
import br.com.capturaXML.TipoXML;
import br.com.capturaXML.XML;
import br.com.capturaXML.XMLService;
import br.com.empresa.Empresa;
import br.com.empresa.EmpresaService;
import br.com.trust.GerenciadorTrust;
import br.com.utilidades.JSON;

public class GetDocumentosEmpresasRTC {

	public static void main(String[] args) throws SQLException, UnrecoverableKeyException, NoSuchAlgorithmException,
			KeyStoreException, CertificateException, FileNotFoundException, IOException {

		EntityManager et = ET.nova();
		
		XMLService xmlService = new XMLService(et);
		
		EmpresaService serv = new EmpresaService(et);
		
		Getter[] getters = { new NFEGetter(), new CTEGetter() };

		while (true) {

			for (Empresa empresa : serv.getEmpresas("")) {

				Protocol.registerProtocol("https",
						new Protocol("https",
								new TLSv12SocketFactory(
										empresa.getParametrosEmissao(),
										GerenciadorTrust.getTrustStore()),
								443));

				for (Getter getter : getters) {
					
					getter.executar(empresa, et);

				}
				
				// Ciencia Da Operacao com intencao de download do XML;
				
				List<XML> xmls = xmlService.getXMLsNaoVistadosSistema(TipoXML.NFE_REDUZIDA, empresa);

				for(XML x: xmls) {

					String xml = x.getArquivo().replaceAll("&asp", "\"");

					String chave = GetDocumentosEmpresasRTC.getFirstTagContent(xml, "chNFe");

					CienciaOperacao co = new CienciaOperacao(et);

					@SuppressWarnings("unused")
					JSON resultado = co.executar(empresa,chave);

					x.setVistoSistema(true);
					
					xmlService.merge(x);
					
				}
				
				/*
				 * 
				 * AO MANIFESTAR COMO CIÊNCIA DA OPERAÇÃO, NA PRÓXIMA CONSULTA AUTOMATICAMENTE É LIBERADO O XMLPROC
				 * ISSO NÃO FICA MUITO CLARO, MAS DEIXA O CODIGO AQUI PARA EVENTUAIS CONSULTAS
				 * 
				 * 
				ps = Conexoes.getConexao().prepareStatement(
						"SELECT id,xml FROM documentos_nfe WHERE xml like '<resNFe%' AND id_empresa=? AND ciencia_emissal AND NOT importado LIMIT 0,20");
				ps.setInt(1, empresa.getId());

				rs = ps.executeQuery();

				while (rs.next()) {

					int id = rs.getInt("id");
					String xml = rs.getString("xml").replaceAll("&asp", "\"");

					String chave = GetDocumentosEmpresasRTC.getFirstTagContent(xml, "chNFe");

					JSON parametros = new JSON();
					parametros.setAttr("chave", chave);

					DistribuicaoNfe dist = new DistribuicaoNfe();

					JSON resp = dist.executar(empresa.getRegistro(), parametros);

					if (resp.getArray("documentos") != null) {
						if (resp.getArray("documentos").size() > 0) {
							// ==========================

							JSON doc = resp.getArray("documentos").getJSON(0);

							Timestamp data = fromPattern(getFirstTagContent(doc.getString("xml"), "dhEmi"));

							while (true) {
								try {

									PreparedStatement psx = Conexoes.getConexao().prepareStatement(
											"INSERT INTO xml_nota(data_emissao,chave,xml,nsu,id_empresa) VALUES(?,?,?,?,?)");
									psx.setTimestamp(1, data);
									psx.setString(2, chave);
									psx.setString(3, doc.getString("xml"));
									psx.setInt(4, Integer.parseInt(doc.getString("nsu")));
									psx.setInt(5, empresa.getId());
									psx.execute();

									break;

								} catch (Exception ex) {

									ex.printStackTrace();

									try {
										Thread.sleep(10000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}
							}

							// ==========================
						}

					}

					PreparedStatement psu = Conexoes.getConexao()
							.prepareStatement("UPDATE documentos_nfe SET importado=? WHERE id=?");
					psu.setBoolean(1, true);
					psu.setInt(2, id);
					psu.execute();

				}
				 */
				// ------------------------------------------------------

				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			et.getTransaction().begin();
			et.getTransaction().commit();

		}

	}

	private static String getFirstTagContent(String xml, String tag) {

		int i = xml.indexOf("<" + tag);
		i = xml.indexOf(">", i);
		int j = xml.indexOf("<", i);

		return xml.substring(i + 1, j);

	}

	@SuppressWarnings("unused")
	private static Timestamp fromPattern(String str) {

		String[] partes = str.split("T");

		String[] data = partes[0].split("-");

		String[] hora = partes[1].split("-")[0].split(":");

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, Integer.parseInt(data[0]));

		cal.set(Calendar.MONTH, Integer.parseInt(data[1]) - 1);

		cal.set(Calendar.DATE, Integer.parseInt(data[2]));

		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hora[0]));

		cal.set(Calendar.MINUTE, Integer.parseInt(hora[1]));

		return new Timestamp(cal.getTimeInMillis());

	}

}
