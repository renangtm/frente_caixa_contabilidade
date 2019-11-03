package br.com.usuario;

import java.util.ArrayList;
import java.util.List;

public enum PresetPermissao {

	CAIXA(new Object[][] { new Object[] { TipoPermissao.FRENTE_CAIXA, true, true, true, true } }), OPERADOR_COMANDA(
			new Object[][] { new Object[] { TipoPermissao.ASSOCIACAO_COMANDA, true, true, true, true } }), GERENTE(
					new Object[][] { new Object[] { TipoPermissao.FRENTE_CAIXA, true, true, true, true },
							new Object[] { TipoPermissao.NOTAS, true, true, true, true },
							new Object[] { TipoPermissao.GERENCIA_CAIXAS, true, true, true, true },
							new Object[] { TipoPermissao.FINANCEIRO, true, true, true, true },
							new Object[] { TipoPermissao.CADASTRO_PESSOA, true, true, true, true },
							new Object[] { TipoPermissao.ASSOCIACAO_COMANDA, true, true, true, true },
							new Object[] { TipoPermissao.CADASTRO_PRODUTO, true, true, true, true },
							new Object[] { TipoPermissao.CADASTRO_OPERACAO, true, true, true, true },
							new Object[] { TipoPermissao.CADASTRO_HISTORICO, true, true, true, true },
							new Object[] { TipoPermissao.CONFIGURACAO_EMPRESA, true, true, true, true },
							new Object[] { TipoPermissao.CONTRATAR_CONTABILIDADE, true, true, true, true },
							new Object[] { TipoPermissao.APROVACAO_XML, true, true, true, true },
							new Object[] { TipoPermissao.DADOS_CONTABEIS_PRODUTO, true, true, true, true } }), CONTADOR(
									new Object[][] {
											new Object[] { TipoPermissao.APROVACAO_XML, true, true, true, true },
											new Object[] { TipoPermissao.FINANCEIRO, true, true, true, true },
											new Object[] { TipoPermissao.CADASTRO_OPERACAO, true, true, true, true },
											new Object[] { TipoPermissao.CADASTRO_HISTORICO, true, true, true, true },
											new Object[] { TipoPermissao.ASSOCIACAO_COMANDA, true, true, true, true },
											new Object[] { TipoPermissao.CADASTRO_PRODUTO, true, true, true, true },
											new Object[] { TipoPermissao.CADASTRO_CFOP, true, true, true, true },
											new Object[] { TipoPermissao.CADASTRO_NCM, true, true, true, true },
											new Object[] { TipoPermissao.DADOS_CONTABEIS_PRODUTO, true, true, true,
													true },
											new Object[] { TipoPermissao.CONFIGURACAO_EMPRESA, true, true, true, true },
											new Object[] { TipoPermissao.CADASTRO_PESSOA, true, true, true, true } });

	private List<Permissao> preset;

	public static PresetPermissao getMaisProximo(List<Permissao> perms) {

		PresetPermissao ps = null;
		double px = 0;

		for (PresetPermissao p : PresetPermissao.values()) {

			double pp = p.encaixe(perms);

			if (pp > px || ps == null) {

				ps = p;
				px = pp;

			}

		}

		return ps;

	}

	private PresetPermissao(Object[][] obj) {

		this.preset = new ArrayList<Permissao>();

		for (Object[] ln : obj) {

			Permissao p = new Permissao();
			p.setTipo((TipoPermissao) ln[0]);
			p.setAlterar((Boolean) ln[1]);
			p.setIncluir((Boolean) ln[2]);
			p.setConsultar((Boolean) ln[3]);
			p.setExcluir((Boolean) ln[4]);

			this.preset.add(p);

		}

	}

	public List<Permissao> getPreset() {
		return preset;
	}

	public double encaixe(List<Permissao> perms) {

		double ok = 0;
		double div = 0;

		lbl: for (Permissao p : perms) {

			for (Permissao pp : this.preset) {

				if (pp.getTipo().equals(p.getTipo())) {

					if (pp.isAlterar() == p.isAlterar())
						ok++;
					else
						div++;

					if (pp.isExcluir() == p.isExcluir())
						ok++;
					else
						div++;

					if (pp.isConsultar() == p.isConsultar())
						ok++;
					else
						div++;

					if (pp.isIncluir() == p.isIncluir())
						ok++;
					else
						div++;

					continue lbl;

				}

			}

			div += 4;

		}

		lbl: for (Permissao p : this.preset) {
			for (Permissao pp : perms) {
				if (pp.getTipo().equals(p.getTipo()))
					continue lbl;
			}
			div += 4;
		}

		return ok / (ok + div);

	}

}
