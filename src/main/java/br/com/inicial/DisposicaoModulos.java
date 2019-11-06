package br.com.inicial;

import java.util.ArrayList;
import java.util.List;

import br.com.base.CFG;
import br.com.empresa.Contabilidade;
import br.com.usuario.TipoPermissao;
import br.com.usuario.Usuario;

public class DisposicaoModulos {

	public static List<Object> getDisposicao(Usuario usuario) {

		List<Object> disposicao = new ArrayList<Object>();

		if (CFG.clc != null) {

			if (usuario.getPermissao(TipoPermissao.FRENTE_CAIXA).isConsultar()) {

				disposicao.add(FrenteCaixa.class);

			}

		}

		if (usuario.getPf().getEmpresa().getClass().equals(Contabilidade.class)) {

			disposicao.add(ControleEmpresa.class);

		}

		if (usuario.getPermissao(TipoPermissao.CADASTRO_PRODUTO).isConsultar()) {

			disposicao.add(Produtos.class);

			disposicao.add(CadastroTipoProduto.class);
			
			disposicao.add(CadastroValePresente.class);
			
			disposicao.add(CadastroBandeiraCartao.class);

		}

		if (usuario.getPermissao(TipoPermissao.ASSOCIACAO_COMANDA).isConsultar()) {

			disposicao.add(AssociacaoComanda.class);

		}

		if (usuario.getPermissao(TipoPermissao.CADASTRO_CFOP).isConsultar()) {

			disposicao.add(CadastroCFOP.class);

		}

		if (usuario.getPermissao(TipoPermissao.CADASTRO_PESSOA).isConsultar()) {

			disposicao.add(CadastroPessoa.class);

		}

		if (usuario.getPermissao(TipoPermissao.CADASTRO_NCM).isConsultar()) {

			disposicao.add(CadastroNcm.class);

		}

		if (usuario.getPermissao(TipoPermissao.CONTRATAR_CONTABILIDADE).isConsultar()) {

			disposicao.add(SelecaoContabilidade.class);

		}

		disposicao.add(CadastroCheque.class);

		if (usuario.getPermissao(TipoPermissao.CADASTRO_CODIGO_BARRA).isConsultar()) {

			Object[] menu_codigos = new Object[2];
			menu_codigos[0] = "Codigos de Barra";

			List<Object> _menu_codigos = new ArrayList<Object>();
			_menu_codigos.add(CodigosExternos.class);

			menu_codigos[1] = _menu_codigos;

			disposicao.add(menu_codigos);

		}

		if (usuario.getPermissao(TipoPermissao.NOTAS).isConsultar()) {

			Object[] menu_notas = new Object[2];
			menu_notas[0] = "Fiscal";

			List<Object> _menu_notas = new ArrayList<Object>();
			_menu_notas.add(Notas.class);

			if (usuario.getPermissao(TipoPermissao.APROVACAO_XML).isConsultar())
				_menu_notas.add(AprovacaoXml.class);

			menu_notas[1] = _menu_notas;

			disposicao.add(menu_notas);

		}

		List<Object> _menu_financeiro = new ArrayList<Object>();
		if (usuario.getPermissao(TipoPermissao.CADASTRO_HISTORICO).isConsultar())
			_menu_financeiro.add(CadastroHistorico.class);
		if (usuario.getPermissao(TipoPermissao.CADASTRO_OPERACAO).isConsultar())
			_menu_financeiro.add(CadastroOperacao.class);
		if (usuario.getPermissao(TipoPermissao.FINANCEIRO).isConsultar())
			_menu_financeiro.add(CadastroMovimento.class);

		if (_menu_financeiro.size() > 0) {

			Object[] menu_financeiro = new Object[2];
			menu_financeiro[0] = "Financeiro";

			menu_financeiro[1] = _menu_financeiro;

			disposicao.add(menu_financeiro);

		}

		if (usuario.getPermissao(TipoPermissao.CONFIGURACAO_EMPRESA).isConsultar()) {

			disposicao.add(ConfiguracoesEmpresa.class);

		}

		if (usuario.getPermissao(TipoPermissao.GERENCIA_CAIXAS).isConsultar()) {

			disposicao.add(ConfiguracaoCaixa.class);

		}

		return disposicao;

	}

	private DisposicaoModulos() {

	}

}
