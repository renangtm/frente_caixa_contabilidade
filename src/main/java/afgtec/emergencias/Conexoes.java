package afgtec.emergencias;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexoes {

	public static Connection getConexao() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://www.rtcagro.com.br/novo_rtc", "root", "4gft3cm4st3r");
	}
	
	private Conexoes(){
		
	}
	
	
}
