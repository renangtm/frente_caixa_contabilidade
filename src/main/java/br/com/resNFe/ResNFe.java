//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2019.09.07 às 09:51:41 PM BRT 
//


package br.com.resNFe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de anonymous complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="chNFe" type="{http://www.portalfiscal.inf.br/nfe}TChNFe"/>
 *         &lt;choice>
 *           &lt;element name="CNPJ" type="{http://www.portalfiscal.inf.br/nfe}TCnpj"/>
 *           &lt;element name="CPF" type="{http://www.portalfiscal.inf.br/nfe}TCpf"/>
 *         &lt;/choice>
 *         &lt;element name="xNome">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.portalfiscal.inf.br/nfe}TString">
 *               &lt;maxLength value="60"/>
 *               &lt;minLength value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="IE" type="{http://www.portalfiscal.inf.br/nfe}TIe"/>
 *         &lt;element name="dhEmi" type="{http://www.portalfiscal.inf.br/nfe}TDateTimeUTC"/>
 *         &lt;element name="tpNF">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;whiteSpace value="preserve"/>
 *               &lt;enumeration value="0"/>
 *               &lt;enumeration value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="digVal" type="{http://www.w3.org/2000/09/xmldsig#}DigestValueType" minOccurs="0"/>
 *         &lt;element name="dhRecbto" type="{http://www.portalfiscal.inf.br/nfe}TDateTimeUTC"/>
 *         &lt;element name="nProt" type="{http://www.portalfiscal.inf.br/nfe}TProt"/>
 *       &lt;/sequence>
 *       &lt;attribute name="versao" use="required" type="{http://www.portalfiscal.inf.br/nfe}TVerResNFe" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "chNFe",
    "cnpj",
    "cpf",
    "xNome",
    "ie",
    "dhEmi",
    "tpNF",
    "digVal",
    "dhRecbto",
    "nProt"
})
@XmlRootElement(name = "resNFe", namespace = "http://www.portalfiscal.inf.br/nfe")
public class ResNFe {

    @XmlElement(namespace = "http://www.portalfiscal.inf.br/nfe", required = true)
    protected String chNFe;
    @XmlElement(name = "CNPJ", namespace = "http://www.portalfiscal.inf.br/nfe")
    protected String cnpj;
    @XmlElement(name = "CPF", namespace = "http://www.portalfiscal.inf.br/nfe")
    protected String cpf;
    @XmlElement(namespace = "http://www.portalfiscal.inf.br/nfe", required = true)
    protected String xNome;
    @XmlElement(name = "IE", namespace = "http://www.portalfiscal.inf.br/nfe", required = true)
    protected String ie;
    @XmlElement(namespace = "http://www.portalfiscal.inf.br/nfe", required = true)
    protected String dhEmi;
    @XmlElement(namespace = "http://www.portalfiscal.inf.br/nfe", required = true)
    protected String tpNF;
    @XmlElement(namespace = "http://www.portalfiscal.inf.br/nfe")
    protected byte[] digVal;
    @XmlElement(namespace = "http://www.portalfiscal.inf.br/nfe", required = true)
    protected String dhRecbto;
    @XmlElement(namespace = "http://www.portalfiscal.inf.br/nfe", required = true)
    protected String nProt;
    @XmlAttribute(name = "versao", required = true)
    protected String versao;

    /**
     * Obtém o valor da propriedade chNFe.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChNFe() {
        return chNFe;
    }

    /**
     * Define o valor da propriedade chNFe.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChNFe(String value) {
        this.chNFe = value;
    }

    /**
     * Obtém o valor da propriedade cnpj.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCNPJ() {
        return cnpj;
    }

    /**
     * Define o valor da propriedade cnpj.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCNPJ(String value) {
        this.cnpj = value;
    }

    /**
     * Obtém o valor da propriedade cpf.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCPF() {
        return cpf;
    }

    /**
     * Define o valor da propriedade cpf.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCPF(String value) {
        this.cpf = value;
    }

    /**
     * Obtém o valor da propriedade xNome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXNome() {
        return xNome;
    }

    /**
     * Define o valor da propriedade xNome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXNome(String value) {
        this.xNome = value;
    }

    /**
     * Obtém o valor da propriedade ie.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIE() {
        return ie;
    }

    /**
     * Define o valor da propriedade ie.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIE(String value) {
        this.ie = value;
    }

    /**
     * Obtém o valor da propriedade dhEmi.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDhEmi() {
        return dhEmi;
    }

    /**
     * Define o valor da propriedade dhEmi.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDhEmi(String value) {
        this.dhEmi = value;
    }

    /**
     * Obtém o valor da propriedade tpNF.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTpNF() {
        return tpNF;
    }

    /**
     * Define o valor da propriedade tpNF.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTpNF(String value) {
        this.tpNF = value;
    }

    /**
     * Obtém o valor da propriedade digVal.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDigVal() {
        return digVal;
    }

    /**
     * Define o valor da propriedade digVal.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDigVal(byte[] value) {
        this.digVal = value;
    }

    /**
     * Obtém o valor da propriedade dhRecbto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDhRecbto() {
        return dhRecbto;
    }

    /**
     * Define o valor da propriedade dhRecbto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDhRecbto(String value) {
        this.dhRecbto = value;
    }

    /**
     * Obtém o valor da propriedade nProt.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNProt() {
        return nProt;
    }

    /**
     * Define o valor da propriedade nProt.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNProt(String value) {
        this.nProt = value;
    }

    /**
     * Obtém o valor da propriedade versao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersao() {
        return versao;
    }

    /**
     * Define o valor da propriedade versao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersao(String value) {
        this.versao = value;
    }

}
