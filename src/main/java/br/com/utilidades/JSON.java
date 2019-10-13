package br.com.utilidades;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class JSON {

	protected static final char STR = '"';
	protected static final char OPEN = '{';
	protected static final char CLOSE = '}';
	protected static final char OPEN_ARRAY = '[';
	protected static final char CLOSE_ARRAY = ']';
	protected static final char SEP = ',';
	protected static final char SEPJ = ':';

	public HashMap<String, Object> atributos;

	public JSON() {

		this.atributos = new LinkedHashMap<String, Object>();

	}

	private Object conversor(String str){
		
		if(str.charAt(0) == JSON.STR){
			
			return str.substring(1, str.length()-1);
			
		}else if(str.equals("true") || str.equals("false")){
			
			return Boolean.parseBoolean(str);
			
		}else if(str.equals("null")){
			
			return null;
			
		}else if(str.contains(".")){
			
			return Double.parseDouble(str);
			
		}else{
			
			return Integer.parseInt(str);
			
		}
		
		
	}
	
	private void lerJson(String str){
		
		LinkedList<Object> objs = new LinkedList<Object>();
		
		
		LinkedList<Object> buffers = new LinkedList<Object>();
		
		StringBuilder buffer = new StringBuilder();
		
		Object ultimoRemovido = null;
		
		int k = 0;
		boolean append = false;
		
		for(int i=0;i<str.length();i++){
			
			char c = str.charAt(i);
			char ca = (char)-1;
			@SuppressWarnings("unused")
			char cp = (char)-1;
			
			if(c == '\\'){
				continue;
			}
			
			
			boolean asint = false;
			
			if(i>0){
				ca = str.charAt(i-1);
				if(ca == '\\'){
					asint = true;
				}
			}
			
			
			
			if(ca == '\\' && c == 't'){
				continue;
			}
			
			if(i<str.length()-1){
				cp = str.charAt(i+1);
			}
			
			
			
			if(c == JSON.STR && !asint){
				buffer.append(JSON.STR);
				append = false;
				k++;
				if(k%2==0){
					buffers.add(this.conversor(buffer.toString()));
					buffer.delete(0, buffer.length());
				}
				continue;
			}
			
			if(append){
				if((c==JSON.OPEN || c==JSON.CLOSE || c==JSON.OPEN_ARRAY || c==JSON.CLOSE_ARRAY || c==JSON.SEP) && !asint){
					append = false;
				}
			}
			
			if(k%2 == 1 || asint || append) {
				
				buffer.append(c);
				
			}else if(c == JSON.SEPJ){
				
				append = true;
				
			}else if(c==JSON.OPEN){
				
				if(buffers.size() == 0){
					
				
					objs.add(new JSON());
					
					
				}else{
					
					Object o = objs.getLast();
					
					JSON nobj = new JSON();
					
					try{
						
						JSON obj = (JSON)o;
						obj.setAttr(buffers.getLast().toString(),nobj);
						
					}catch(Exception ex){
						
						JSONArray arr = (JSONArray)o;
						arr.addAttr(nobj);
						
					}
					
					objs.add(nobj);
					
				}
				
			}else if(c==JSON.CLOSE){
				
				if(buffer.length()>0){
					buffers.add(this.conversor(buffer.toString()));
					buffer.delete(0, buffer.length());
				}
				
				ultimoRemovido = objs.removeLast();
				
				if(ca == JSON.CLOSE || ca == JSON.CLOSE_ARRAY || ca == JSON.OPEN){
					continue;
				}
				
				JSON json = (JSON)ultimoRemovido;
				
				json.setAttr(buffers.get(buffers.size()-2).toString(), buffers.getLast());
				
			}else if(c==JSON.OPEN_ARRAY){
			
				if(buffers.size() == 0){
					
					objs.add(new JSONArray());
					
				}else{
					
					JSONArray nobj = new JSONArray();
					
					Object o = objs.getLast();
					
					try{
						
						JSON obj = (JSON)o;
						obj.setAttr(buffers.getLast().toString(),nobj);
						
					}catch(Exception ex){
						
						JSONArray arr = (JSONArray)o;
						arr.addAttr(nobj);
					}
					
					objs.add(nobj);
					
				}
				
			}else if(c == JSON.CLOSE_ARRAY){
				
				if(buffer.length()>0){
					buffers.add(this.conversor(buffer.toString()));
					buffer.delete(0, buffer.length());
				}
				
				ultimoRemovido = objs.removeLast();
				
				if(ca == JSON.CLOSE || ca == JSON.CLOSE_ARRAY || ca == JSON.OPEN_ARRAY){
					continue;
				}
				
				JSONArray json = (JSONArray)ultimoRemovido;
				json.addAttr(buffers.getLast());
				
			}else if(c == JSON.SEP){
				
				if(buffer.length()>0){
					buffers.add(this.conversor(buffer.toString()));
					buffer.delete(0, buffer.length());
				}
				
				if(ca == JSON.CLOSE || ca == JSON.CLOSE_ARRAY){
					continue;
				}
				
				try{
					
					JSON obj = (JSON)objs.getLast();
					obj.setAttr(buffers.get(buffers.size()-2).toString(), buffers.getLast());
					
				}catch(Exception ex){
					
					JSONArray arr = (JSONArray)objs.getLast();
					arr.addAttr(buffers.getLast());
					append = true;
					
				}
				
			}
			
		}
		
		
		JSON objeto = (JSON)ultimoRemovido;
		
		this.atributos = objeto.atributos;
		
	}
	
	public JSON(String str) throws IOException {

		this();
		
		this.lerJson(str);

	}

	public JSON setAttr(String key, Object atr) {

		this.atributos.put(key, atr);
		return this;

	}

	public long getLong(String key) {

		if (!this.atributos.containsKey(key))
			return 0L;
		
		if(this.atributos.get(key)==null){
			return 0L;
		}

		return (long) this.atributos.get(key);

	}

	public int getInt(String key) {

		if (!this.atributos.containsKey(key))
			return 0;
		
		if(this.atributos.get(key)==null){
			return 0;
		}

		return (int) this.atributos.get(key);

	}

	public double getDouble(String key) {

		if (!this.atributos.containsKey(key))
			return 0.0;
		
		if(this.atributos.get(key)==null){
			return 0.0;
		}

		try {

			return (double) this.atributos.get(key);

		} catch (Exception e) {

			return Double.parseDouble(this.atributos.get(key).toString());

		}

	}

	public boolean getBoolean(String key) {

		if (!this.atributos.containsKey(key))
			return false;
		
		if(this.atributos.get(key)==null){
			return false;
		}

		return (boolean) this.atributos.get(key);

	}

	public String getString(String key) {

		if (!this.atributos.containsKey(key))
			return null;

		if(this.atributos.get(key)==null){
			return "";
		}
		
		return this.atributos.get(key).toString();

	}

	public JSON getJSON(String key) {

		if (!this.atributos.containsKey(key))
			return null;
		

		return (JSON) this.atributos.get(key);

	}

	public JSONArray getArray(String key) {

		if (!this.atributos.containsKey(key))
			return null;

		return (JSONArray) this.atributos.get(key);

	}

	@Override
	public String toString() {

		String str = "{";

		boolean a = false;
		
		ArrayList<String> keys = new ArrayList<String>(this.atributos.keySet());
		keys.sort(new Comparator<String>(){

			@Override
			public int compare(String o1, String o2) {
				
				
				if(atributos.get(o1) != null){
					if(atributos.get(o1).getClass().equals(JSON.class) || 
							atributos.get(o1).getClass().equals(JSONArray.class)){
						if(atributos.get(o2) == null){
							return 1;
						}
						if(!(atributos.get(o2).getClass().equals(JSON.class) || 
								atributos.get(o2).getClass().equals(JSONArray.class))){
							return 1;
						}
					}
				}
				
				if(atributos.get(o2) != null){
					if(atributos.get(o2).getClass().equals(JSON.class) || 
							atributos.get(o2).getClass().equals(JSONArray.class)){
						if(atributos.get(o1) == null){
							return -1;
						}
						if(!(atributos.get(o1).getClass().equals(JSON.class) || 
								atributos.get(o1).getClass().equals(JSONArray.class))){
							return -1;
						}
					}
				}
				
				return o1.compareTo(o2);
			}
			
			
		});
		
		for (String atributo : this.atributos.keySet()) {

			Object valor = this.atributos.get(atributo);

			if (valor == null)
				continue;

			if (a) {

				str += ",";

			}

			str += '"' + atributo + '"' + ':';

			if (valor.getClass().equals(String.class)) {

				str += '"' + valor.toString() + '"';

			} else {

				str += valor.toString();

			}

			a = true;

		}

		str += "}";

		return str.replaceAll("\\r\\n|\\r|\\n", " ");

	}

}
