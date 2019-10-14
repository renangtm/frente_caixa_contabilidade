package br.com.processosComNFe;

public class Base64 {

	//@author Renan Gonçalves Teixeira Miranda
	
	private static final char[] CHAR_ARRAY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
			.toCharArray();

	public String encode(byte[] b) {

		StringBuilder result = new StringBuilder();

		for (int i = 0; i < b.length; i += 3) {

			long a = 0;
			int j = 0;
			for (; j < 3 && (i + j) < b.length; j++)
				a = a << 8 | b[i + j];

			for (int c = j; c < 3; c++)
				a = a << 8;

			for (int k = 0; k < 4; k++)
				result.append((k <= j) ? CHAR_ARRAY[(int) ((a >> ((3 - k) * 6)) & 0x3f)] : "=");

		}

		return result.toString();

	}
	public static void main(String[] args) {
		
		
		byte[] bytes = "testeds".getBytes();
		
		String encoding = new Base64().encode(bytes);
		
		byte[] resposta = new Base64().decode(encoding);
		
		for(int i=0;i<bytes.length;i++){
		}
		for(int i=0;i<resposta.length;i++){
		}
		
	}

	public byte[] decode(String str) {
		
		int[] im = new int[123];
		
		for(int i=0;i<CHAR_ARRAY.length;i++){
			
			im[CHAR_ARRAY[i]] = i;
			
		}

		byte[] bytes = new byte[(str.length()/4)*3];
		int reduc = 0;
		for(int i=0;i<str.length();i+=4){
			
			long a=0;
			int l=0;
			for(int j=0;j<4;j++){
				if(str.charAt(i+j)=='='){
					a=a<<6;
					l++;
				}else{
					a=a<<6|im[str.charAt(i+j)];
				}
			}
			
			for(int k=0;k<3-l;k++){
				
				bytes[((i/4)*3)+k] = (byte) ((a>>((2-k)*8))&0xff);
				
			}
			
			reduc+=l;
			
		}
		
		if(reduc>0){
			
			byte[] resb = new byte[bytes.length-reduc];
			for(int i=0;i<resb.length;i++)resb[i]=bytes[i];
			bytes=resb;
			
		}
		
		return bytes;
	
	}

}
