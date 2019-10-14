package br.com.processosComNFe;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.zip.GZIPInputStream;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axis2.AxisFault;

import br.com.agrofauna.stubs.CTeDistribuicaoDFeSoapStub;
import br.com.distDFeInt.DistDFeInt;
import br.com.empresa.ParametrosEmissao;
import br.com.retDistDFeInt.RetDistDFeInt;
import br.com.retDistDFeInt.RetDistDFeInt.LoteDistDFeInt.DocZip;
import br.com.utilidades.JSON;
import br.com.utilidades.JSONArray;

/*
 *
 * ultimoNsu:int
 * nsuEspecifica:int
 */

public class DistribuicaoCte{

	@SuppressWarnings("unused")
	private EntityManager et;
	
	public DistribuicaoCte(EntityManager et) {
		
		this.et = et;
		
	}
	
	public JSON executar(ParametrosEmissao gr) {
		
		return this.executar(gr,0);
		
	}
	
	public JSON executar(ParametrosEmissao gr, int nsuEspecifica) {
		
		FormataInteiro i15 = new FormataInteiro(15);
		
		DistDFeInt distDfe = new DistDFeInt();
		distDfe.setVersao("1.00");
		distDfe.setTpAmb("1");
		distDfe.setCUFAutor(gr.getConfiguracaoEstadoNFe().getCodigo()+"");
		distDfe.setCNPJ(gr.getEmpresa().getPj().getCnpj().replaceAll("\\.", "").replaceAll("-", "").replaceAll("\\/", ""));
		
		DistDFeInt.DistNSU distNsu = new DistDFeInt.DistNSU(); 
		distNsu.setUltNSU(i15.f(gr.getUltimoNSuCTe()));
		
		if(nsuEspecifica==0){
		
			distDfe.setDistNSU(distNsu);
		
		}
		
		if(nsuEspecifica>0){
			
			DistDFeInt.ConsNSU consNsu = new DistDFeInt.ConsNSU();
			consNsu.setNSU(i15.f(nsuEspecifica));
			distDfe.setConsNSU(consNsu);
			
		}
		
		try {
			
			JAXBContext context = JAXBContext.newInstance(DistDFeInt.class);
			Marshaller marshaller = context.createMarshaller();
			StringWriter writer = new StringWriter();
			marshaller.marshal(distDfe, writer);
			
			String xml = writer.toString();
			
			System.out.println(xml);
			
			OMElement ome = AXIOMUtil.stringToOM(xml);
			
			try {
				
				CTeDistribuicaoDFeSoapStub stub = new CTeDistribuicaoDFeSoapStub(ConfiguracaoEstadoNFe.AN.getcTeDistribuicaoDFe());
				
				CTeDistribuicaoDFeSoapStub.CteDadosMsg_type0 dados = new CTeDistribuicaoDFeSoapStub.CteDadosMsg_type0();
				
				dados.setExtraElement(ome);
				
				CTeDistribuicaoDFeSoapStub.CteDistDFeInteresse k = new CTeDistribuicaoDFeSoapStub.CteDistDFeInteresse();
				
				k.setCteDadosMsg(dados);
				
				CTeDistribuicaoDFeSoapStub.CteDistDFeInteresseResponse resposta = stub.cteDistDFeInteresse(k);
				
				String xmlResposta = resposta.getCteDistDFeInteresseResult().getExtraElement().toString();
				
				context = JAXBContext.newInstance(RetDistDFeInt.class);
				
				Unmarshaller unmarshaller = context.createUnmarshaller();
				
				JAXBElement<RetDistDFeInt> ret = unmarshaller.unmarshal(new StreamSource(new StringReader(xmlResposta)), RetDistDFeInt.class);

				RetDistDFeInt objRet = ret.getValue();
		
				JSON retorno = new JSON();
				
				retorno.setAttr("menor_nsu", Integer.parseInt(objRet.getUltNSU()));
				retorno.setAttr("maior_nsu", Integer.parseInt(objRet.getMaxNSU()));
				
				JSONArray dctes = new JSONArray();
				
				try{
					if(objRet.getLoteDistDFeInt() != null){
						for(DocZip doc : objRet.getLoteDistDFeInt().getDocZip()){
							
							JSON dacte = new JSON();
							
							dacte.setAttr("nsu", doc.getNSU());
						
							GZIPInputStream is = new GZIPInputStream(new ByteArrayInputStream(doc.getValue()));
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							
							byte[] buffer = new byte[1024];
							int l = 0;
							
							while((l=is.read(buffer, 0, buffer.length))>0)
								baos.write(buffer, 0, l);
							
							StringBuilder builder = new StringBuilder();
							
							for(byte b:baos.toByteArray())
								builder.append((char)(b&0xff));
				
							
							
							dacte.setAttr("xml", builder.toString().replaceAll("\"", "&asp"));
							
							dctes.addAttr(dacte);
							
						}
					}
				}catch(NullPointerException exx){
					exx.printStackTrace();
				}
				
				retorno.setAttr("documentos",dctes);
				
				return retorno;
				
			} catch (AxisFault e) {
				
				throw new RuntimeException(e);
				
			}
			
		} catch (Exception e) {
			
			throw new RuntimeException(e);
					
		}
		
	}

}
