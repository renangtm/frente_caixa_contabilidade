package br.com.capturaXML;

import java.io.StringReader;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;

import br.com.base.ET;
import br.com.empresa.Empresa;
import br.com.jaxb.CTe.TCTe;
import br.com.jaxb.CTeProc.CteProc;
import br.com.nfe.TNFe;
import br.com.procNFe.TNfeProc;
import br.com.usuario.Usuario;

@Entity
public class XML {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private int nsu;

	@Column
	private String chave;

	@Column
	private String chaveDocumentoReferenciado;

	@Column
	private Calendar data;

	@OneToOne(fetch = FetchType.EAGER)
	private Empresa empresa;

	@Enumerated(EnumType.ORDINAL)
	@Column
	private TipoXML tipo;

	@Transient
	private XML associado;
	
	@Column
	private boolean visto;

	@Column
	private boolean vistoSistema;
	
	@Enumerated(EnumType.STRING)
	@Column
	private TipoVisto tipoVisto;

	@Transient
	private Object conversionCache;
	
	@Column(columnDefinition = "TEXT")
	private String arquivo;

	public XML getAssociado() {
		return associado;
	}

	public void setAssociado(XML associado) {
		this.associado = associado;
	}

	public static void main(String[] args) {

		EntityManager et = ET.nova();

		Usuario u = et.find(Usuario.class, 1);

		Empresa e = u.getPf().getEmpresa();

		XMLService serv = new XMLService(et);

		List<XML> xmls = serv.getXMLsNaoVistadosUsuario(e);

		for (XML xml : xmls) {

			try {

				System.out.println(xml.getObjetoReferente());

			} catch (JAXBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	
	
	public TipoVisto getTipoVisto() {
		return tipoVisto;
	}

	public void setTipoVisto(TipoVisto tipoVisto) {
		this.tipoVisto = tipoVisto;
	}

	public Object getConversionCache() {
		return conversionCache;
	}

	public void setConversionCache(Object conversionCache) {
		this.conversionCache = conversionCache;
	}

	public Object getObjetoReferente() throws JAXBException {

		if(this.conversionCache != null)
			return this.conversionCache;
		
		if (this.tipo.equals(TipoXML.NFE)) {

			try {

				this.conversionCache = JAXB.unmarshal(new StringReader(this.getArquivo().replaceAll("&asp", "\"")), TNfeProc.class);
				
				return this.conversionCache; 

			} catch (Exception e1) {

				e1.printStackTrace();

				this.conversionCache = JAXB.unmarshal(new StringReader(this.getArquivo().replaceAll("&asp", "\"")), TNFe.class); 
				
				return this.conversionCache;

			}

		} else if (this.tipo.equals(TipoXML.CTE)) {

			try {

				this.conversionCache = JAXB.unmarshal(new StringReader(this.getArquivo().replaceAll("&asp", "\"")), CteProc.class);
				
				return this.conversionCache;

			} catch (Exception ex) {

				this.conversionCache = JAXB.unmarshal(new StringReader(this.getArquivo().replaceAll("&asp", "\"")), TCTe.class);
				
				return this.conversionCache;

			}

		}

		return null;

	}

	public String getChave() {
		return chave;
	}

	public boolean isVistoSistema() {
		return vistoSistema;
	}

	public void setVistoSistema(boolean vistoSistema) {
		this.vistoSistema = vistoSistema;
	}

	public String getChaveDocumentoReferenciado() {
		return chaveDocumentoReferenciado;
	}

	public void setChaveDocumentoReferenciado(String chaveDocumentoReferenciado) {
		this.chaveDocumentoReferenciado = chaveDocumentoReferenciado;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNsu() {
		return nsu;
	}

	public void setNsu(int nsu) {
		this.nsu = nsu;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public TipoXML getTipo() {
		return tipo;
	}

	public void setTipo(TipoXML tipo) {
		this.tipo = tipo;
	}

	public boolean isVisto() {
		return visto;
	}

	public void setVisto(boolean visto) {
		this.visto = visto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XML other = (XML) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
}
