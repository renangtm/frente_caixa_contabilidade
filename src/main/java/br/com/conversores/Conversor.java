package br.com.conversores;

public interface Conversor<T> {

	public boolean podeConverter(@SuppressWarnings("rawtypes") Class classe);

	public String paraString(T objeto);

	public T paraObjeto(String string) throws Exception;

}
