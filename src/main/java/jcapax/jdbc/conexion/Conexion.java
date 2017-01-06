package jcapax.jdbc.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	private Connection conn = null;
	
	public Conexion() {
		super();
		
		boolean aux = false;
		
		String url = "jdbc:mysql://localhost:3306/dbdelfin";
		try {
			String msj = "conexion realizada con exito";
			this.conn = DriverManager.getConnection(url, "root", "");
			this.conn.setAutoCommit(aux);
			System.out.println(msj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getConn() {
		return conn;
	}

}
