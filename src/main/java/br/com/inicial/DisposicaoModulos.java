package br.com.inicial;

import java.util.ArrayList;
import java.util.List;

import br.com.base.CFG;
import br.com.usuario.Usuario;

public class DisposicaoModulos {

	public static List<Object> getDisposicao(Usuario usuario){
	
		List<Object> disposicao = new ArrayList<Object>();
		
		if(CFG.clc != null) {
		
			disposicao.add(FrenteCaixa.class);
		
		}
		
		disposicao.add(Produtos.class);
		
		disposicao.add(CadastroCFOP_.class);
		
		disposicao.add(CadastroPessoa.class);
		
		disposicao.add(CadastroNcm.class);
	
		Object[] menu_codigos = new Object[2];
		menu_codigos[0] = "Codigos de Barra";
			
			List<Object> _menu_codigos = new ArrayList<Object>();
			_menu_codigos.add(CodigosExternos.class);
		
		menu_codigos[1] = _menu_codigos;
		
		disposicao.add(menu_codigos);
		
		Object[] menu_notas = new Object[2];
		menu_notas[0] = "Notas";
			
			List<Object> _menu_notas = new ArrayList<Object>();
			_menu_notas.add(Notas.class);
			
		menu_notas[1] = _menu_notas;
		
		disposicao.add(menu_notas);
		
		Object[] menu_financeiro = new Object[2];
		menu_financeiro[0] = "Financeiro";
		
			List<Object> _menu_financeiro = new ArrayList<Object>();
			_menu_financeiro.add(CadastroHistorico.class);
			_menu_financeiro.add(CadastroOperacao.class);
			_menu_financeiro.add(CadastroMovimento.class);
			
		menu_financeiro[1] = _menu_financeiro;
			
		disposicao.add(menu_financeiro);
			
		disposicao.add(ConfiguracoesEmpresa.class);
		
		disposicao.add(ConfiguracaoCaixa.class);
		
		return disposicao;
		
	}
	
	
	private DisposicaoModulos(){
		
		
	}
	
}
