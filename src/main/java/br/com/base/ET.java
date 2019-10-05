package br.com.base;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ET {
	
	@SuppressWarnings("unused")
	private static HashMap<EntityManager,EntityManagerFactory> mp = new HashMap<EntityManager,EntityManagerFactory>(); 

	private static EntityManagerFactory etf = Persistence.createEntityManagerFactory("rtc_contabil");
	
	private ET(){
		
		
	}
	
	public static EntityManager nova(){
		
		return etf.createEntityManager();
		
	}
	
}
