package br.com.agrofauna.utilidades;

import java.util.List;

public interface ProvedorDeEventos<T> {

	public void atualizar(List<T> lista,T elemento) throws Exception;
	public void deletar(List<T> lista,T elemento) throws Exception;
	public void cadastrar(List<T> lista,T elemento) throws Exception;
	
}
