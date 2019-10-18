package br.com.empresa;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.base.Resources;

@Table(name="Logo")
@Entity
public class Visual {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] arquivo;

	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] fundo;
	
	@Enumerated(EnumType.ORDINAL)
	@Column
	private Look look;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getFundo() {
		
		if(this.fundo == null){
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			try {
				
				ImageIO.write(Resources.getFundo(),"png",baos);
				
				return baos.toByteArray();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return fundo;
	}

	public void setFundo(byte[] fundo) {
		this.fundo = fundo;
	}

	public Look getLook() {
		return look;
	}

	public void setLook(Look look) {
		this.look = look;
	}

	public byte[] getArquivo() throws IOException {
		
		if(this.arquivo == null){
			
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream is = Visual.class.getClassLoader().getResourceAsStream("icones/logo.png");
			
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
