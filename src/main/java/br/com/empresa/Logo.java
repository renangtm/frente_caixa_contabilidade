package br.com.empresa;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Logo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] arquivo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getArquivo() throws IOException {
		
		if(this.arquivo == null){
			
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream is = Logo.class.getClassLoader().getResourceAsStream("icones/logo.png");
			
			byte[] buffer = new byte[1024];
			int l = 0;
			
			while((l=is.read(buffer, 0, buffer.length))>0){
				
				baos.write(buffer, 0, l);
				
			}
			
			return baos.toByteArray();
			
		}
		
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}
	
	

}
