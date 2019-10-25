package br.com.utilidades;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import br.com.conversores.Conversor;
import br.com.conversores.ConversorBooleano;
import br.com.conversores.ConversorCalendar;
import br.com.conversores.ConversorDate;

public class ListModelGenerica<T> extends AbstractTableModel {

	/**
	 * @author Renan
	 */

	private static final long serialVersionUID = 1L;

	private boolean alteracao;
	
	private List<T> listaBase;

	private List<T> listaCompleta;

	private Class<T> classe;

	private List<Field> colunas;

	private List<Filtro> filtros;
	
	private List<Filtro> filtroObjeto = new ArrayList<Filtro>();

	private ProvedorDeEventos<T> provedorDeEventos = null;
	
	@SuppressWarnings("rawtypes")
	private Comparator comparador;

	@SuppressWarnings("rawtypes")
	private List<Conversor> conversores = new ArrayList<Conversor>();
	
	private Class<? extends Representador<T>> representador;
	
	private Object representerParam;

	@SuppressWarnings("rawtypes")
	private RowSorter lastSorter;
	
	public List<Filtro> getFiltrosObjeto(){
		return this.filtroObjeto;
	}
	
	private Representador<T> getRepresentador(T objeto){
		
		@SuppressWarnings("rawtypes")
		Class c = objeto.getClass();
		
		while(!c.equals(Object.class)){
			try {
				
				Constructor<? extends Representador<T>> construtor = this.representador.getConstructor(c);
				
				Representador<T> representador = construtor.newInstance(objeto);
				
				representador.setParametro(this.representerParam);
				
				return representador;
				
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c=c.getSuperclass();
		}
		
		return null;
		
	}

	public void setLista(List<T> lista){
		
		this.listaCompleta = lista;
		this.listaBase = new ArrayList<T>(lista);
		
		this.fireTableDataChanged();
		
	}
	
	public ListModelGenerica(List<T> listaCompleta, Class<T> classe) {
		super();

		
		
		this.alteracao = true;
		this.conversores.add(new ConversorDate());
		this.conversores.add(new ConversorBooleano());
		this.conversores.add(new ConversorCalendar());

		this.listaCompleta = listaCompleta;
		this.filtros = new ArrayList<Filtro>();

		this.listaBase = new ArrayList<T>();
		for (T elemento : this.listaCompleta)
			this.listaBase.add(elemento);

		this.classe = classe;
		this.colunas = new ArrayList<Field>();

		List<Field> campos = new ArrayList<Field>();

		@SuppressWarnings("rawtypes")
		Class c = this.classe;

		do {
			campos.addAll(Arrays.asList(c.getDeclaredFields()));
			c = c.getSuperclass();
		} while (!c.equals(Object.class));

		for (Field campo : campos) {
			campo.setAccessible(true);
			if (campo.getDeclaredAnnotation(Campo.class) == null)
				continue;

			if (!campo.getDeclaredAnnotation(Campo.class).lista())
				continue;

			this.colunas.add(campo);
			this.filtros.add(null);
		}

	}
	
	public ListModelGenerica<T> setSorter(JTable tbl){
		tbl.setRowSorter(this.getSorter());
		return this;
	}

	public ListModelGenerica(List<T> listaCompleta, Class<T> classe,Class<? extends Representador<T>> representador,Object repObj) {
		super();

		this.representador = representador;
		this.representerParam = repObj;
		this.alteracao = true;
		this.conversores.add(new ConversorDate());
		this.conversores.add(new ConversorBooleano());
		this.conversores.add(new ConversorCalendar());
		
		this.listaCompleta = listaCompleta;
		this.filtros = new ArrayList<Filtro>();

		this.listaBase = new ArrayList<T>();
		for (T elemento : this.listaCompleta)
			this.listaBase.add(elemento);

		this.classe = classe;
		this.colunas = new ArrayList<Field>();

		List<Field> campos = new ArrayList<Field>();

		@SuppressWarnings("rawtypes")
		Class c = this.representador;

		do {
			campos.addAll(Arrays.asList(c.getDeclaredFields()));
			c = c.getSuperclass();
		} while (!c.equals(Object.class));

		for (Field campo : campos) {
			campo.setAccessible(true);
			if (campo.getDeclaredAnnotation(Campo.class) == null)
				continue;

			if (!campo.getDeclaredAnnotation(Campo.class).lista())
				continue;

			this.colunas.add(campo);
			this.filtros.add(null);
		}

	}
	
	public ListModelGenerica(List<T> listaCompleta, Class<T> classe,Class<? extends Representador<T>> representador) {
		super();

		this.representador = representador;
		
		this.alteracao = true;
		this.conversores.add(new ConversorDate());
		this.conversores.add(new ConversorBooleano());
		this.conversores.add(new ConversorCalendar());

		this.listaCompleta = listaCompleta;
		this.filtros = new ArrayList<Filtro>();

		this.listaBase = new ArrayList<T>();
		for (T elemento : this.listaCompleta)
			this.listaBase.add(elemento);

		this.classe = classe;
		this.colunas = new ArrayList<Field>();

		List<Field> campos = new ArrayList<Field>();

		@SuppressWarnings("rawtypes")
		Class c = this.representador;

		do {
			campos.addAll(Arrays.asList(c.getDeclaredFields()));
			c = c.getSuperclass();
		} while (!c.equals(Object.class));

		for (Field campo : campos) {
			campo.setAccessible(true);
			if (campo.getDeclaredAnnotation(Campo.class) == null)
				continue;

			if (!campo.getDeclaredAnnotation(Campo.class).lista())
				continue;

			this.colunas.add(campo);
			this.filtros.add(null);
		}

	}
	
	
	@SuppressWarnings("rawtypes")
	private Comparator getComparator(Class classe){
		
		Conversor conv = null;
		
		for(Conversor conversor:this.conversores){
			if(conversor.podeConverter(classe)){
				conv = conversor;
				break;
			}
		}
		final Conversor c = conv;
		try{
			@SuppressWarnings({ "unused", "unchecked" })
			Class<? extends Comparable> comp = classe;
					
			return new Comparator(){

				@SuppressWarnings("unchecked")
				
				public int compare(Object o1, Object o2) {
					if(c!=null){
						try {
							o1 = c.paraObjeto(o1.toString());
							o2 = c.paraObjeto(o2.toString());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}		
					// TODO Auto-generated method stub
					return ((Comparable)o1).compareTo((Comparable)o2);
				}
				
				
			};
			
		}catch(Exception ex){
			
		}
		
		if(classe.equals(int.class) || classe.equals(Integer.class)){
			return new Comparator(){

				
				public int compare(Object arg0, Object arg1) {
					
					return new Integer((Integer) arg0).compareTo(new Integer((Integer) arg1));
				}
				
			};
		}else if(classe.equals(double.class) || classe.equals(Double.class)){
			return new Comparator(){

				
				public int compare(Object arg0, Object arg1) {
					// TODO Auto-generated method stub
					return new Double((Double) arg0).compareTo(new Double((Double) arg1));
				}
				
			};
		}else if(classe.equals(long.class) || classe.equals(Long.class)){
			return new Comparator(){

				
				public int compare(Object arg0, Object arg1) {
					// TODO Auto-generated method stub
					return new Long((Long) arg0).compareTo(new Long((Long) arg1));
				}
				
			};
		}
		
		
		return null;
	}
	
	public TableRowSorter<ListModelGenerica<T>> getSorter(){
		
		
		
		final TableRowSorter<ListModelGenerica<T>> sorter = new TableRowSorter<ListModelGenerica<T>>(this);
		sorter.addRowSorterListener(new RowSorterListener(){

			
			public void sorterChanged(RowSorterEvent arg0) {
				
				lastSorter = sorter;
				
			}
			
		});
		
		int i=0;
		for(Field campo:this.colunas){
		
			sorter.setComparator(i, this.getComparator(campo.getType()));
			
			i++;
		}
		
		return sorter;
		
	}

	public void setOrdenacao(final int[] colunas_,final boolean[] crescente_){
		
		this.comparador = new Comparator<T>(){

			@SuppressWarnings("unchecked")
			
			public int compare(T arg0, T arg1) {
				
				Object a0 = arg0;
				Object a1 = arg1;
				
				if(representador != null){
					
					a0 = getRepresentador((T) a0);
					a1 = getRepresentador((T) a1);
					
				}
				
				
				try{
				
					int i=0;
					for(int coluna:colunas_){
						
						Field campo = colunas.get(coluna);
						boolean crescente = crescente_[i];
						
						Object o1 = campo.get(a0);
						Object o2 = campo.get(a1);
					
						if(o1==null && o2 != null){
							return 1;
						}else if(o2==null && o1!=null){
							return -1;
						}else if(o2==null && o1==null){
							return 0;
						}
							
							try{
								
								@SuppressWarnings("rawtypes")
								Comparable s1 = (Comparable)o1;
								@SuppressWarnings("rawtypes")
								Comparable s2 = (Comparable)o2;
								
								int c1 = s1.compareTo(s2);
								
								if(c1>0){
									return (crescente)?1:-1;
								}else if(c1<0){
									return (crescente)?-1:1;
								}
								
							}catch(Exception e2){
							
								String s1 = o1.toString();
								String s2 = o2.toString();
								
								int c1 = s1.compareTo(s2);
								
								if(c1>0){
									return (crescente)?1:-1;
								}else if(c1<0){
									return (crescente)?-1:1;
								}
							
							}
							
							//-------------
							
						
						
						i++;
					}

					return 0;
				
				}catch(Exception ex){

					return 0;
					
				}
				
				
			}
			
		};
		
		this.atualizaListaBaseConformeFiltros();
		
	}
	
	
	public ListModelGenerica(List<T> listaBase, Class<T> classe, ProvedorDeEventos<T> eventos) {
		this(listaBase, classe);
		this.provedorDeEventos = eventos;
	}
	
	public ListModelGenerica(List<T> listaBase, Class<T> classe, ProvedorDeEventos<T> eventos,Class<? extends Representador<T>> representador) {
		this(listaBase, classe,representador);
		this.provedorDeEventos = eventos;
	}
	
	public ListModelGenerica(List<T> listaBase, Class<T> classe, ProvedorDeEventos<T> eventos,Class<? extends Representador<T>> representador,Object repParam) {
		this(listaBase, classe,representador, repParam);
		this.provedorDeEventos = eventos;
	}

	public void tirarFiltros() {
		this.filtros.clear();
		
		for(@SuppressWarnings("unused") Field c:this.colunas)this.filtros.add(null);
		
		this.atualizaListaBaseConformeFiltros();
	}

	public void setFiltro(int coluna, Filtro filtro) {
		if (coluna > this.colunas.size())
			throw new RuntimeException("A coluna informada para filtragem não existe");
		this.filtros.set(coluna, filtro);
		this.atualizaListaBaseConformeFiltros();
	}
	
	public void setAlteracao(boolean alteracao){
		this.alteracao = alteracao;
	}
	
	public boolean getAlteracao(){
		return this.alteracao;
	}

	@SuppressWarnings("unchecked")
	public void atualizaListaBaseConformeFiltros() {
		this.listaBase.clear();

		l1: for (T elemento : this.listaCompleta) {

			Object ob = elemento;
			
			if(this.representador != null){
				
				ob = this.getRepresentador(elemento);
				
			}
			
			for (int i = 0; i < filtros.size(); i++) {
				if (filtros.get(i) == null)
					continue;
				try {
					Field campo = this.colunas.get(i);
					if(campo.get(ob)==null){
						
						if (!filtros.get(i).passa(""))
							continue l1;
						
					}else{				

						if (!filtros.get(i).passa(campo.get(ob)))
							continue l1;
					
					}
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			

			for (int i = 0; i < filtroObjeto.size(); i++) {
				if (filtroObjeto.get(i) == null)
					continue;
				try {
					if (!filtroObjeto.get(i).passa(elemento))
						continue l1;
				} catch (IllegalArgumentException e) {

				}
			}


			this.listaBase.add(elemento);
		}
		
		if(this.comparador!=null){
			
			Collections.sort(this.listaBase,this.comparador);
			
		}
		
		this.fireTableDataChanged();
	}

	
	public int getColumnCount() {
		return this.colunas.size();
	}

	
	public String getColumnName(int column) {
		return this.colunas.get(column).getDeclaredAnnotation(Campo.class).nome();
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c) {
		// return this.colunas.get(c).getType();
		return String.class;
	}

	
	public int getRowCount() {
		return this.listaBase.size();
	}

	public List<T> getListaCompleta() {
		return this.listaCompleta;
	}

	public List<T> getListaBase() {
		
		if(lastSorter != null){
			
			ArrayList<T> copia = new ArrayList<T>(this.listaBase);
			
			for(int i=0;i<copia.size();i++){
				copia.set(lastSorter.convertRowIndexToView(i), this.listaBase.get(i));
			}
			
			return copia;
			
		}
		
		return this.listaBase;
	}

	public boolean remover(int i) {

		T elemento = this.listaBase.get(i);
		if (this.provedorDeEventos == null) {
			this.listaBase.remove(i);
			this.listaCompleta.remove(this.listaCompleta.indexOf(elemento));
			this.fireTableDataChanged();
			return true;
		}
		try {
			this.listaBase.remove(i);
			this.listaCompleta.remove(this.listaCompleta.indexOf(elemento));
			this.provedorDeEventos.deletar(this.listaBase, elemento);
			this.fireTableDataChanged();
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	public void add(T elemento) {
		this.listaBase.add(elemento);
		this.listaCompleta.add(elemento);
		this.fireTableDataChanged();
		if (this.provedorDeEventos == null)
			return;
		try {
			this.provedorDeEventos.cadastrar(this.listaBase, elemento);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
		}
	}


	
	public boolean isCellEditable(int row, int col) {
		
		if(!this.alteracao){
			return false;
		}
		
		return this.colunas.get(col).getDeclaredAnnotation(Campo.class).editavel();
	}

	private Object converterParaTipo(Field f, Object valor) {

		try {
			if (f.getType().equals(Double.class) || f.getType().equals(double.class)) {
				return Double.parseDouble(valor.toString());
			} else if (f.getType().equals(Integer.class) || f.getType().equals(int.class)) {
				return Integer.parseInt(valor.toString());
			} else if (f.getType().equals(Float.class) || f.getType().equals(float.class)) {
				return Float.parseFloat(valor.toString());
			} else if (f.getType().equals(BigDecimal.class)) {
				return new BigDecimal(valor.toString());
			}
		} catch (Exception e) {
			throw new RuntimeException(
					"O dado '" + valor.toString() + "' não pode ser convertido para um dado numérico");
		}

		return valor.toString();
	}

	
	public void setValueAt(Object newVal, int linha, int coluna) {
		try {

			Field campo = this.colunas.get(coluna);

			if (!newVal.getClass().equals(campo.getType())) {
				boolean temConversor = false;
				for (@SuppressWarnings("rawtypes")
				Conversor conversor : conversores)
					if (conversor.podeConverter(campo.getType())) {
						if(this.representador==null){
							campo.set(this.listaBase.get(linha), conversor.paraObjeto(newVal.toString()));
						}else{
							Representador<T> rt = this.getRepresentador(this.listaBase.get(linha));
							campo.set(rt, conversor.paraObjeto(newVal.toString()));
							rt.atualizar();
						}
						temConversor = true;
						break;
					}

				if(this.representador == null){
					if (!temConversor)
						campo.set(this.listaBase.get(linha), this.converterParaTipo(campo, newVal));
				}else if(!temConversor){
					
					Representador<T> rt = this.getRepresentador(this.listaBase.get(linha));
					campo.set(rt, this.converterParaTipo(campo, newVal));
					rt.atualizar();
				}
				
			} else {
				
				if(this.representador != null){
				
					Representador<T> rt = this.getRepresentador(this.listaBase.get(linha));
					campo.set(rt, newVal);
					rt.atualizar();
				
				}else{
					
					campo.set(this.listaBase.get(linha), newVal);
					
				}
				
			}

			if (this.provedorDeEventos != null) {
				this.provedorDeEventos.atualizar(this.listaBase, this.listaBase.get(linha));
			}
			this.fireTableDataChanged();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
		}

	}

	@SuppressWarnings("unchecked")
	
	public Object getValueAt(int linha, int coluna) {
		try {

			Field campo = this.colunas.get(coluna);
			
			for (@SuppressWarnings("rawtypes")
			Conversor conversor : conversores)
				if (conversor.podeConverter(campo.getType()))
					if(this.representador == null){
						return conversor.paraString(campo.get(this.listaBase.get(linha)));
					}else{
						return conversor.paraString(campo.get(this.getRepresentador(this.listaBase.get(linha))));
					}
			
			if(this.representador == null){
				return campo.get(this.listaBase.get(linha));
			}else{
				return campo.get(this.getRepresentador(this.listaBase.get(linha)));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
