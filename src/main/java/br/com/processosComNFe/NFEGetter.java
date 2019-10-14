package br.com.processosComNFe;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import br.com.capturaXML.TipoXML;
import br.com.capturaXML.XML;
import br.com.capturaXML.XMLService;
import br.com.empresa.Empresa;
import br.com.utilidades.JSON;
import br.com.utilidades.JSONArray;

public class NFEGetter implements Getter {

	private Map<Empresa, Long> tempo = new HashMap<Empresa, Long>();

	private boolean hasTag(String xml, String tag) {

		return xml.contains("<" + tag);

	}

	private String getFirstTagContent(String xml, String tag) {

		int i = xml.indexOf("<" + tag);
		i = xml.indexOf(">", i);
		int j = xml.indexOf("<", i);

		return xml.substring(i + 1, j);

	}

	private String getFirstValueConent(String xml, String att) {

		int i = xml.indexOf(att + "=");
		int j = xml.indexOf(" ", i);
		int k = xml.indexOf(">", i);

		if (j > k || j < 0) {
			j = k;
		}

		return xml.substring(i + 1, j);

	}

	private Calendar fromPattern(String str) {

		String[] partes = str.split("T");

		String[] data = partes[0].split("-");

		String[] hora = partes[1].split("-")[0].split(":");

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, Integer.parseInt(data[0]));

		cal.set(Calendar.MONTH, Integer.parseInt(data[1]) - 1);

		cal.set(Calendar.DATE, Integer.parseInt(data[2]));

		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hora[0]));

		cal.set(Calendar.MINUTE, Integer.parseInt(hora[1]));

		return cal;

	}

	@Override
	public void executar(Empresa empresa, EntityManager et) {

		if (!this.tempo.containsKey(empresa))
			this.tempo.put(empresa, 0L);

		if (System.currentTimeMillis() < this.tempo.get(empresa))
			return;

		XMLService serv = new XMLService(et);

		DistribuicaoNfe service = new DistribuicaoNfe(et);

		JSON resposta = service.executar(empresa.getParametrosEmissao());

		System.out.println("-----" + resposta);

		int max = 0;

		JSONArray documentos = resposta.getArray("documentos");

		for (int i = 0; i < documentos.size(); i++) {

			JSON doc = documentos.getJSON(i);

			int nsu = Integer.parseInt(doc.getString("nsu"));

			max = Math.max(nsu, max);

			String xml = doc.getString("xml");

			if (this.hasTag(xml, "resEvento")) {

				String chave = getFirstTagContent(xml, "chNFe");
				Calendar data = fromPattern(getFirstTagContent(xml, "dhEvento"));

				XML x = new XML();
				x.setTipo(TipoXML.EVENTO_NFe);
				x.setChave(chave);
				x.setArquivo(xml);
				x.setNsu(nsu);
				x.setEmpresa(empresa);
				x.setData(data);

				serv.merge(x);

			} else if (hasTag(xml, "nfeProc")) {

				String chave = getFirstValueConent(xml, "Id").replaceAll("\"", "").split("e")[1];
				Calendar data = fromPattern(getFirstTagContent(xml, "dhEmi"));

				XML x = new XML();
				x.setTipo(TipoXML.NFE);
				x.setChave(chave);
				x.setArquivo(xml);
				x.setNsu(nsu);
				x.setEmpresa(empresa);
				x.setData(data);

				serv.merge(x);

			} else {

				XML x = new XML();
				x.setTipo(TipoXML.NFE_REDUZIDA);
				x.setArquivo(xml);
				x.setNsu(nsu);
				x.setEmpresa(empresa);
				x.setData(Calendar.getInstance());

				serv.merge(x);

			}

		}

		if(max > 0)
			empresa.getParametrosEmissao().setUltimoNSuNFe(max);
		
		et.getTransaction().begin();
		et.getTransaction().commit();
		
		if (max == resposta.getInt("maior_nsu")) {

			this.tempo.put(empresa, System.currentTimeMillis() + (20 * 1000));
			// delay de uma hora ao atingir o maximo nsu

		}

	}

}
