package br.com.afgtec.base;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ET {
	
	@SuppressWarnings("unused")
	private static HashMap<EntityManager,EntityManagerFactory> mp = new HashMap<EntityManager,EntityManagerFactory>(); 

	private static EntityManager em;
	
	private ET(){
		
		
	}
	
	public static EntityManager nova(){
		
		if(em == null) {
		
			EntityManagerFactory etf = Persistence.createEntityManagerFactory("rtc_contabil");
			EntityManager et = etf.createEntityManager();
			
			em = et;
			
		}
		
		return em;
		
	}
	
}
