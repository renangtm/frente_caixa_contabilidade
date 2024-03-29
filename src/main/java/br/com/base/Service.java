package br.com.base;

import java.util.List;

public interface Service<T> {

	public int getCount(String filtro);
	
	public List<T> getElementos(int x1, int x2, String filtro,String ordem);
	
	public T getPeloCodigo(String str);
	
	public void lixeira(T obj);
	
	public T merge(T obj);
	
}
