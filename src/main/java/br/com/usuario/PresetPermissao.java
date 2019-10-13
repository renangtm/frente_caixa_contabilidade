package br.com.usuario;

import java.util.ArrayList;
import java.util.List;

public enum PresetPermissao {

	CAIXA(new Object[][] { 
		  new Object[] { TipoPermissao.FRENTE_CAIXA, true, true, true, true } }),
	GERENTE(new Object[][] { 
			new Object[] { TipoPermissao.FRENTE_CAIXA, true, true, true, true },
			new Object[] { TipoPermissao.NOTAS, true, true, true, true },
			new Object[] { TipoPermissao.GERENCIA_CAIXAS, true, true, true, true },
			new Object[] { TipoPermissao.FINANCEIRO, true, true, true, true },
			new Object[] { TipoPermissao.CADASTRO_PESSOA, true, true, true, true },
			new Object[] { TipoPermissao.CADASTRO_PRODUTO, true, true, true, true },
			new Object[] { TipoPermissao.CADASTRO_OPERACAO, true, true, true, true },
			new Object[] { TipoPermissao.CADASTRO_HISTORICO, true, true, true, true },
			new Object[] { TipoPermissao.CONFIGURACAO_EMPRESA, true, true, true, true },
			new Object[] { TipoPermissao.DADOS_CONTABEIS_PRODUTO, true, true, true, true } });

	private List<Permissao> preset;

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

		lbl:
		for (Permissao p : perms) {

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
