package br.com.afgtec.notas;

import br.com.afgtec.impostos.Cofins;
import br.com.afgtec.impostos.ICMS00;
import br.com.afgtec.impostos.ICMS10;
import br.com.afgtec.impostos.ICMS20;
import br.com.afgtec.impostos.ICMS30;
import br.com.afgtec.impostos.ICMS40;
import br.com.afgtec.impostos.ICMS51;
import br.com.afgtec.impostos.ICMS70;
import br.com.afgtec.impostos.ICMS90;
import br.com.afgtec.impostos.Icms;
import br.com.afgtec.impostos.Imposto;
import br.com.afgtec.impostos.Pis;
import br.com.afgtec.impostos.TabelaAlicotas;
import br.com.afgtec.pessoa.Pessoa;
import br.com.afgtec.pessoa.PessoaJuridica;
import br.com.afgtec.produto.Produto;

public class ImpostoFactory {

	private PessoaJuridica emitente;
	private Pessoa destinatario;
	private Produto produto;
	private TabelaAlicotas ta;
	private TipoNota opl;
	
	public ImpostoFactory(TabelaAlicotas ta,TipoNota operacaoIcms) {
		
		this.ta = ta;
		this.opl = operacaoIcms;
		
	}
	
	public ImpostoFactory setEmitente(PessoaJuridica pj) {

		this.emitente = pj;

		return this;

	}

	public ImpostoFactory setDestinatario(Pessoa p) {

		this.destinatario = p;

		return this;

	}

	public ImpostoFactory setProduto(Produto p) {

		this.produto = p;

		return this;

	}

	public Imposto getImposto() {

		if (this.produto == null || this.emitente == null) {

			throw new RuntimeException("Informe todas as entides para gerar o imposto adequadamente.");

		}
		
		if(this.destinatario == null)
		{
			
			this.destinatario = this.emitente;
			
		}
		
		
		Icms icmsPadrao = (Icms) this.produto.getCategoria().getIcms().clone();
		Cofins confinsPadrao = (Cofins) this.produto.getCategoria().getCofins().clone();
		Pis pisPadrao = (Pis) this.produto.getCategoria().getPis().clone();

		if (icmsPadrao.getClass().equals(ICMS00.class)) {

			ICMS00 icms00 = (ICMS00) icmsPadrao;

			icms00.setPorcentagemICMS(this.ta.getAlicota(this.emitente.getEndereco().getCidade().getEstado(),
						this.destinatario.getEndereco().getCidade().getEstado(),this.opl));
			

		} else if (icmsPadrao.getClass().equals(ICMS10.class)) {

			ICMS10 icms10 = (ICMS10) icmsPadrao;

			icms10.setPorcentagemICMS(this.ta.getAlicota(this.emitente.getEndereco().getCidade().getEstado(),
					this.destinatario.getEndereco().getCidade().getEstado(),this.opl));


		} else if (icmsPadrao.getClass().equals(ICMS20.class)) {

			ICMS20 icms20 = (ICMS20) icmsPadrao;

			icms20.setAlicotaIcms(this.ta.getAlicota(this.emitente.getEndereco().getCidade().getEstado(),
					this.destinatario.getEndereco().getCidade().getEstado(),this.opl));


		} else if (icmsPadrao.getClass().equals(ICMS30.class)) {

			ICMS30 icms30 = (ICMS30) icmsPadrao;

			icms30.setAlicotaIcmsST(this.ta.getAlicotaSt(this.emitente.getEndereco().getCidade().getEstado(),
					this.destinatario.getEndereco().getCidade().getEstado(),this.opl));


		} else if (icmsPadrao.getClass().equals(ICMS51.class)) {

			ICMS51 icms51 = (ICMS51) icmsPadrao;

			icms51.setAlicotaIcms(this.ta.getAlicota(this.emitente.getEndereco().getCidade().getEstado(),
					this.destinatario.getEndereco().getCidade().getEstado(),this.opl));


		} else if (icmsPadrao.getClass().equals(ICMS70.class)) {

			ICMS70 icms70 = (ICMS70) icmsPadrao;

			icms70.setPorcentagemICMS(this.ta.getAlicota(this.emitente.getEndereco().getCidade().getEstado(),
					this.destinatario.getEndereco().getCidade().getEstado(),this.opl));


			icms70.setAlicotaIcmsST(this.ta.getAlicotaSt(this.emitente.getEndereco().getCidade().getEstado(),
					this.destinatario.getEndereco().getCidade().getEstado(),this.opl));


		} else if (icmsPadrao.getClass().equals(ICMS90.class)) {

			ICMS90 icms90 = (ICMS90) icmsPadrao;

			icms90.setPorcentagemICMS(this.ta.getAlicota(this.emitente.getEndereco().getCidade().getEstado(),
					this.destinatario.getEndereco().getCidade().getEstado(),this.opl));


			icms90.setAlicotaIcmsST(this.ta.getAlicotaSt(this.emitente.getEndereco().getCidade().getEstado(),
					this.destinatario.getEndereco().getCidade().getEstado(),this.opl));


		}

		try {

			PessoaJuridica pj = (PessoaJuridica) this.destinatario;

			if (pj.getSuframa().isIncetivo()) {

				ICMS40 icms40 = new ICMS40();

				icms40.setIcmsOriginal(icmsPadrao);
				icms40.setOrigem(icmsPadrao.getOrigem());
				icms40.setMotivo(MotivoDesoneracao.SUFRAMA);

				icmsPadrao = icms40;

			}

		} catch (Exception ex) {

		}

		Imposto i = new Imposto();
		i.setCofins(confinsPadrao);
		i.setIcms(icmsPadrao);
		i.setPis(pisPadrao);

		return i;

	}

}
