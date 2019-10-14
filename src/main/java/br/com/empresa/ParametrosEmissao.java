package br.com.empresa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import br.com.config.CertificadoPFX;
import br.com.processosComNFe.ConfiguracaoEstadoNFe;

@Entity
public class ParametrosEmissao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String senha_sat;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "parametrosEmissao", cascade=CascadeType.PERSIST)
	private Empresa empresa;

	@Column
	private String numeroSat;

	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] certificadoDigital;

	@Column
	private String senhaCertificado;

	@Column
	private int idLote;

	@Column
	private int ultimaNFe;

	@Column
	private int ultimoNSuCTe;
	
	@Column
	private int ultimoNSuNFe;
	
	
	
	public int getUltimoNSuCTe() {
		return ultimoNSuCTe;
	}

	public void setUltimoNSuCTe(int ultimoNSuCTe) {
		this.ultimoNSuCTe = ultimoNSuCTe;
	}

	public int getUltimoNSuNFe() {
		return ultimoNSuNFe;
	}

	public void setUltimoNSuNFe(int ultimoNSuNFe) {
		this.ultimoNSuNFe = ultimoNSuNFe;
	}

	public int getIdLote() {
		return idLote;
	}

	public void setIdLote(int idLote) {
		this.idLote = idLote;
	}

	public int getUltimaNFe() {
		return ultimaNFe;
	}

	public void setUltimaNFe(int ultimaNFe) {
		this.ultimaNFe = ultimaNFe;
	}

	public byte[] getCertificadoDigital() {
		return certificadoDigital;
	}

	public CertificadoPFX getCertificadoPFX()
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

		return new CertificadoPFX(new ByteArrayInputStream(this.certificadoDigital), this.senhaCertificado);

	}

	public ConfiguracaoEstadoNFe getConfiguracaoEstadoNFe() {

		for (ConfiguracaoEstadoNFe conf : ConfiguracaoEstadoNFe.values()) {

			if (conf.toString().equals(this.empresa.getPj().getEndereco().getCidade().getEstado().getSigla())) {

				return conf;

			}

		}

		return ConfiguracaoEstadoNFe.SP;

	}

	public void setCertificadoDigital(byte[] certificadoDigital) {
		this.certificadoDigital = certificadoDigital;
	}

	public String getSenhaCertificado() {
		return senhaCertificado;
	}

	public void setSenhaCertificado(String senhaCertificado) {
		this.senhaCertificado = senhaCertificado;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getNumeroSat() {
		return numeroSat;
	}

	public void setNumeroSat(String numeroSat) {
		this.numeroSat = numeroSat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSenha_sat() {
		return senha_sat;
	}

	public void setSenha_sat(String senha_sat) {
		this.senha_sat = senha_sat;
	}

}
