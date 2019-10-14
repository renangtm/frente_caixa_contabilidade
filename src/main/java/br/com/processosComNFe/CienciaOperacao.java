package br.com.processosComNFe;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;

import br.com.config.AssinadorXML;
import br.com.empresa.Empresa;
import br.com.empresa.ParametrosEmissao;
import br.com.empresa.ParametrosEmissaoService;
import br.com.envEventoCancNFe.ObjectFactory;
import br.com.envEventoCancNFe.TEnvEvento;
import br.com.envEventoCancNFe.TEvento;
import br.com.envEventoCancNFe.TRetEnvEvento;
import br.com.utilidades.JSON;
import br.com.envEventoCancNFe.TEvento.InfEvento;
import br.com.envEventoCancNFe.TEvento.InfEvento.DetEvento;
import br.inf.portalfiscal.nfe_4.wsdl.NFeRecepcaoEvento4Stub;
import br.inf.portalfiscal.nfe_4.wsdl.NFeRecepcaoEvento4Stub.NfeResultMsg;

/*
 * 
 * chave:String
 * 
 */

public class CienciaOperacao{

	private EntityManager et;
	
	public CienciaOperacao(EntityManager et) {
		
		this.et = et;
		
	}
	
	public JSON executar(Empresa emp,String chave) {

		try {

			Calendar agora = Calendar.getInstance();
			agora.add(Calendar.MINUTE, -10);

			TEnvEvento envEvento = new TEnvEvento();

			envEvento.setVersao("1.00");
			envEvento.setIdLote(emp.getParametrosEmissao().getIdLote() + "");

			TEvento evento = new TEvento();
			evento.setVersao("1.00");

			InfEvento infEvento = new InfEvento();

			infEvento.setCOrgao(ConfiguracaoEstadoNFe.AN.getCodigo()+"");
			infEvento.setTpAmb("1");
			infEvento.setCNPJ(emp.getPj().getCnpj().replaceAll("\\.", "").replaceAll("-", "").replaceAll("\\/", ""));
			infEvento.setChNFe(chave);
			infEvento.setDhEvento(UtilidadesNFE.toNFeString(agora));
			infEvento.setTpEvento("210210");
			infEvento.setNSeqEvento("1");
			infEvento.setVerEvento("1.00");

			DetEvento detEvento = new DetEvento();
			detEvento.setDescEvento("Ciencia da Operacao");
			detEvento.setVersao("1.00");

			infEvento.setDetEvento(detEvento);

			evento.setInfEvento(infEvento);

			envEvento.getEvento().add(evento);

			FormataInteiro i2 = new FormataInteiro(2);

			String id = "ID" + infEvento.getTpEvento() + infEvento.getChNFe()
					+ i2.f(Integer.parseInt(infEvento.getNSeqEvento()));

			infEvento.setId(id);

			JAXBElement<TEnvEvento> objEnvEvento = new ObjectFactory().createEnvEvento(envEvento);

			JAXBContext context = JAXBContext.newInstance(TEnvEvento.class);

			Marshaller marshaller = context.createMarshaller();

			StringWriter writer = new StringWriter();

			marshaller.marshal(objEnvEvento, writer);

			String xmlEnvEvento = writer.toString();

			xmlEnvEvento = xmlEnvEvento.replaceAll(" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");

			AssinadorXML assinador = new AssinadorXML(emp.getParametrosEmissao().getCertificadoPFX());

			String xmlAssinado = assinador.assinar(xmlEnvEvento, "evento|infEvento", "Id");

			OMElement ome = AXIOMUtil.stringToOM(xmlAssinado);

			URL url = new URL(ConfiguracaoEstadoNFe.AN.getRecepcaoEvento());

			NFeRecepcaoEvento4Stub stub = new NFeRecepcaoEvento4Stub(url.toString());

			NFeRecepcaoEvento4Stub.NfeDadosMsg dados = new NFeRecepcaoEvento4Stub.NfeDadosMsg();

			dados.setExtraElement(ome);

			NfeResultMsg resultado = stub.nfeRecepcaoEvento(dados);

			String xmlResultado = resultado.getExtraElement().toString();

			System.out.println(xmlResultado);
			
			context = JAXBContext.newInstance(TRetEnvEvento.class);
			
			Unmarshaller unmarshaller = context.createUnmarshaller();

			JAXBElement<TRetEnvEvento> objRetorno = unmarshaller
					.unmarshal(new StreamSource(new StringReader(xmlResultado)), TRetEnvEvento.class);

			TRetEnvEvento retorno = objRetorno.getValue();

			ParametrosEmissao gr = emp.getParametrosEmissao();
			
			if (retorno.getRetEvento().size() > 0) {

				if(retorno.getRetEvento().get(0).getInfEvento().getXEvento()!=null){
				
						
					gr.setIdLote(gr.getIdLote()+1);
					
					new ParametrosEmissaoService(et).merge(gr);
					
					et.getTransaction().begin();
					et.getTransaction().commit();	
					
					
					return new JSON()
							.setAttr("sucesso", true)
							.setAttr("mensagem", UtilidadesNFE.removerAcentos(retorno.getRetEvento().get(0).getInfEvento().getXEvento()))
							.setAttr("protocolo", retorno.getRetEvento().get(0).getInfEvento().getNProt());

				}else{
					
				
						
					gr.setIdLote(gr.getIdLote()+1);
					
					new ParametrosEmissaoService(et).merge(gr);
					
					et.getTransaction().begin();
					et.getTransaction().commit();	
						
					
					
					return new JSON()
							.setAttr("sucesso", true)
							.setAttr("mensagem", UtilidadesNFE.removerAcentos(retorno.getRetEvento().get(0).getInfEvento().getXMotivo()))
							.setAttr("protocolo", retorno.getRetEvento().get(0).getInfEvento().getNProt());
					
				}
				
			} else {

				return new JSON()
						.setAttr("sucesso", false)
						.setAttr("mensagem", UtilidadesNFE.removerAcentos(retorno.getXMotivo()));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		throw new RuntimeException("Falha ao manifestar NFe");

	}

}
