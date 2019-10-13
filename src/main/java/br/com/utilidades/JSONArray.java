package br.com.utilidades;

import java.io.IOException;
import java.util.ArrayList;

public class JSONArray {

	private ArrayList<Object> objetos;
	
	
	public JSONArray(){
		
		this.objetos = new ArrayList<Object>();
		
	}
	
	public JSONArray(String str) throws IOException{
		
		this();

		ArrayList<String> params = new ArrayList<String>();
		
		int k = 0;
		int asp = 0;
		String buffer = "";
		
		for(int i=1;i<str.length();i++){
			
			char c = str.charAt(i);
		
			if(c==JSON.OPEN || c==JSON.OPEN_ARRAY){
				
				k++;
				
			}else if(c==JSON.CLOSE || c==JSON.CLOSE_ARRAY){
				
				k--;
				
			}else if(c==JSON.STR){
				
				asp++;
				
			}
			
			if(k<0){
				if(!buffer.equals("")){
					params.add(buffer);
					break;
				}
			}
			
			
			if(c==JSON.SEP && k==0 && asp%2==0){
				if(!buffer.equals("")){
					params.add(buffer);
					buffer="";
				}
				continue;
			}
			
			buffer+=c+"";
			
		}
		
		for(String param:params){
			
			String atr = param;
			
			if(atr.charAt(0)==JSON.OPEN){
				
				this.objetos.add(new JSON(atr));
				
			}else if(atr.equals("true") || atr.equals("false")){
				
				this.objetos.add(Boolean.parseBoolean(atr));
				
			}else if(atr.charAt(0)=='"'){
				
				this.objetos.add(atr.replaceAll("\"", ""));
				
			}else if(atr.charAt(0)==JSON.OPEN_ARRAY){
				
				this.objetos.add(new JSONArray(atr));
				
			}else{
				
				if(atr.equals("null")){
					
					this.objetos.add(null);
					
					continue;
					
				}
				
				if(atr.contains("\\.")){
					
					this.objetos.add(Double.parseDouble(atr));
					
				}else{
				
					try{
						
						this.objetos.add(Integer.parseInt(atr));
						
					}catch(Exception ex){
						
						this.objetos.add(Long.parseLong(atr));
						
					}
					
				}
				
			}
			
		}
		
	}
	
	public JSONArray addAttr(Object atr){
		
		this.objetos.add(atr);
		return this;
		
	}
	public int size(){
		
		return this.objetos.size();
		
	}
	
	public long getLong(int id){
		
		if(this.objetos.get(id)==null){
			return 0L;
		}
		
		return (long)this.objetos.get(id);
		
	}
	
	public String getString(int id){
		
		if(this.objetos.get(id)==null){
			return null;
		}
		
		return this.objetos.get(id).toString();
		
	}
	
	public int getInt(int id){
		
		if(this.objetos.get(id)==null){
			return 0;
		}
		
		return (int)this.objetos.get(id);
		
	}
		
	public double getDouble(int id){
		
		if(this.objetos.get(id)==null){
			return 0.0;
		}
		
		return (double)this.objetos.get(id);
		
	}
	
	public JSON getJSON(int id){
		
		if(this.objetos.get(id)==null){
			return null;
		}
		
		return (JSON)this.objetos.get(id);
		
	}
	
	public JSONArray getArray(int id){
		
		return (JSONArray)this.objetos.get(id);
		
	}
	
	@Override
	public String toString(){
		
		String str = "[";
		
		boolean a=false;
		for(Object elemento:this.objetos){
			
			if(elemento==null)continue;
			
			if(a)str+=",";
			
			
			
			if(elemento.getClass().equals(String.class)){
				
				str+='"'+elemento.toString()+'"';
				
			}else{
				
				str+=elemento.toString();
				
			}
			
			a=true;
			
		}
		
		str+="]";
		
		
		return str.replaceAll("\\r\\n|\\r|\\n", " ");
		
		
	}
	
}
