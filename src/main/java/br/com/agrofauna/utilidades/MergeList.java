package br.com.agrofauna.utilidades;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MergeList<G> implements List<G>{

	private List<G> listaBase;
	private List<G> listaSecundaria;
	
	public MergeList(List<G> l1, List<G> l2){

		this.listaBase = l1;
		this.listaSecundaria = l2;
		
	}
	
	public boolean add(G e) {
		return this.listaBase.add(e);
	}

	public void add(int index, G element) {
		if(index<this.listaBase.size()){
			this.listaBase.add(index,element);
		}else{
			this.listaSecundaria.add(index-this.listaBase.size(),element);
		}
	}

	public boolean addAll(Collection<? extends G> c) {
		for(G e:c){
			if(!this.add(e))return false;
		}
		return true;
	}

	public boolean addAll(int index, Collection<? extends G> c) {
		for(G e:c){
			this.add(index,e);
		}
		return true;
	}

	public void clear() {
		this.listaBase.clear();
		this.listaSecundaria.clear();
	}

	public boolean contains(Object o) {
		return this.listaBase.contains(o) || this.listaSecundaria.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		for(Object o:c){
			if(!this.contains(o))return false;
		}
		return true;
	}

	public G get(int index) {
		if(index<this.listaBase.size()){
			return this.listaBase.get(index);
		}else{
			return this.listaSecundaria.get(index-this.listaBase.size());
		}
	}

	public int indexOf(Object o) {
		if(this.listaBase.contains(o)){
			return this.listaBase.indexOf(o);
		}else if(this.listaSecundaria.contains(o)){
			return this.listaBase.size()+this.listaSecundaria.indexOf(o);
		}
		return -1;
	}

	public boolean isEmpty() {
		return this.size()==0;
	}

	public Iterator<G> iterator() {
		// TODO Auto-generated method stub
		return new Iterator<G>(){
			
			private int index = 0;

			public boolean hasNext() {
				// TODO Auto-generated method stub
				return index<size();
			}

			public G next() {
				G r = get(index);
				index++;
				return r;
			}
			
			
		};
	}

	public int lastIndexOf(Object o) {
		if(this.listaSecundaria.contains(o)){
			return this.listaSecundaria.lastIndexOf(o)+this.listaBase.size();
		}
		return this.listaBase.lastIndexOf(o);
	}

	public ListIterator<G> listIterator() {
		return this.listIterator(0);
	}

	public ListIterator<G> listIterator(int index) {
		
		final int i = index;
		final MergeList<G> este = this;
		return new ListIterator<G>(){
			private int index = i;
			public void add(G arg0) {
				este.add(index,arg0);
			}

			public boolean hasNext() {
				return este.size()>index;
			}

			
			public boolean hasPrevious() {
				return index>0;
			}

			
			public G next() {
				G r = este.get(index);
				index++;
				return r;
			}

			
			public int nextIndex() {
				// TODO Auto-generated method stub
				return index+1;
			}

			
			public G previous() {
				G r = este.get(index);
				index--;
				return r;
			}

			
			public int previousIndex() {
				// TODO Auto-generated method stub
				return index-1;
			}

			
			public void remove() {
				este.remove(index);
			}

			
			public void set(G arg0) {
				este.set(index, arg0);
			}
			
			
		};
	}

	
	public boolean remove(Object o) {
		if(this.listaBase.contains(o)){
			return this.listaBase.remove(o);
		}else if(this.listaSecundaria.contains(o)){
			return this.listaSecundaria.remove(o);
		}
		return false;
	}

	
	public G remove(int index) {
		if(index<this.listaBase.size()){
			return this.listaBase.remove(index);
		}else{
			index-=this.listaBase.size();
			return this.listaSecundaria.remove(index);
		}
	}

	
	public boolean removeAll(Collection<?> c) {
		boolean a = true;
		for(Object o:c){
			if(!this.remove(o))a=false;
		}
		return a;
	}

	
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	
	public G set(int index, G element) {
		if(index<this.listaBase.size()){
			return this.listaBase.set(index, element);
		}else{
			return this.listaSecundaria.set(index-this.listaBase.size(), element);
		}
	}

	
	public int size() {
		// TODO Auto-generated method stub
		return this.listaBase.size()+this.listaSecundaria.size();
	}

	
	public List<G> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

}
