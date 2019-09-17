package br.com.inicial;

import java.util.ArrayList;
import java.util.List;

import br.com.afgtec.pessoa.Usuario;

public class DisposicaoModulos {

	public static List<Object> getDisposicao(Usuario usuario){
	
		List<Object> disposicao = new ArrayList<Object>();
		
		disposicao.add(FrenteCaixa.class);
		
		disposicao.add(Produtos.class);
		
		disposicao.add(Categorias.class);
	
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
			_menu_notas.add(ImportarXML.class);
			
		menu_notas[1] = _menu_notas;
		
		disposicao.add(menu_notas);
		
		return disposicao;
		
	}
	
	
	private DisposicaoModulos(){
		
		
	}
	
}
