package br.com.inicial;

import java.util.ArrayList;
import java.util.List;

import br.com.base.CFG;
import br.com.usuario.TipoPermissao;
import br.com.usuario.Usuario;

public class DisposicaoModulos {

	public static List<Object> getDisposicao(Usuario usuario){
	
		List<Object> disposicao = new ArrayList<Object>();
		
		if(CFG.clc != null) {
			
			if(usuario.getPermissao(TipoPermissao.FRENTE_CAIXA).isConsultar()) {
			
				disposicao.add(FrenteCaixa.class);
			
			}
			
		}
		
		if(usuario.getPermissao(TipoPermissao.CADASTRO_PRODUTO).isConsultar()){
		
			disposicao.add(Produtos.class);
		
		}
		
		if(usuario.getPermissao(TipoPermissao.CADASTRO_CFOP).isConsultar()) {
		
			disposicao.add(CadastroCFOP_.class);
		
		}
		
		if(usuario.getPermissao(TipoPermissao.CADASTRO_PESSOA).isConsultar()) {
		
			disposicao.add(CadastroPessoa.class);
		
		}
		
		if(usuario.getPermissao(TipoPermissao.CADASTRO_NCM).isConsultar()) {
		
			disposicao.add(CadastroNcm.class);
	
		}
		
		if(usuario.getPermissao(TipoPermissao.CADASTRO_CODIGO_BARRA).isConsultar()) {
		
			Object[] menu_codigos = new Object[2];
			menu_codigos[0] = "Codigos de Barra";
				
				List<Object> _menu_codigos = new ArrayList<Object>();
				_menu_codigos.add(CodigosExternos.class);
				
				menu_codigos[1] = _menu_codigos;
				
				disposicao.add(menu_codigos);
			
		}
			
		if(usuario.getPermissao(TipoPermissao.NOTAS).isConsultar()) {
			
			Object[] menu_notas = new Object[2];
			menu_notas[0] = "Notas";
				
				List<Object> _menu_notas = new ArrayList<Object>();
				_menu_notas.add(Notas.class);
				
			menu_notas[1] = _menu_notas;
			
			disposicao.add(menu_notas);
			
		}
		
		
			Object[] menu_financeiro = new Object[2];
			menu_financeiro[0] = "Financeiro";
			
				List<Object> _menu_financeiro = new ArrayList<Object>();
				_menu_financeiro.add(CadastroHistorico.class);
				_menu_financeiro.add(CadastroOperacao.class);
				_menu_financeiro.add(CadastroMovimento.class);
				
			menu_financeiro[1] = _menu_financeiro;
				
			disposicao.add(menu_financeiro);
			
		
		
		if(usuario.getPermissao(TipoPermissao.CONFIGURACAO_EMPRESA).isConsultar()) {
		
			disposicao.add(ConfiguracoesEmpresa.class);
		
		}
		
		if(usuario.getPermissao(TipoPermissao.GERENCIA_CAIXAS).isConsultar()) {
		
			disposicao.add(ConfiguracaoCaixa.class);
		
		}
		
		return disposicao;
		
	}
	
	
	private DisposicaoModulos(){
		
		
	}
	
}
