package br.com.emissao;

import com.sun.jna.Native;

public class InterfaceSatFactory {
	
	private static String[] dlls = {
		"BemaSAT32.dll",
		"BemaSAT64.dll"
	};
	
	private static InterfaceSat its = null;
	
	private InterfaceSatFactory() {
		
		
		
	}
	
	public static InterfaceSat get() {
		
		if(its == null) {
			
			for(String dll:dlls) {
				
				try {
					
					its = (InterfaceSat)Native.load(dll, InterfaceSat.class);
					
					return its;
					
				}catch(UnsatisfiedLinkError exx) {
					
					
					
				}
				
				
			}
			
		}
		
		return its;
		
	}
	
}
