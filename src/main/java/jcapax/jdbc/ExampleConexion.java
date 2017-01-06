package jcapax.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ExampleConexion {
	
	static Connection conn = null;

	public static void main(String[] args) throws SQLException {
		
		try {
			
			conectar();
			listar();
			System.out.println("");
			consultar(3);
			transaccion("ALIMENTO BALANCEADO","KGS");
			transaccion("VENENO PARA RATAS","KGS");
			transaccion("HARINA INTEGRAL","KGS");
			transaccion("VEGETALES","KGS");
			transaccion("LEGUMBRES","KGS");
			transaccion("ARROZ","KGS");
			transaccion("SALES DE REHIDRATACION","LTS");
			cerrar();
			
			
		} catch (SQLException e) {

		}
		
		
		
		
	}

	private static void transaccion(String nombreArticulo, String unidad) {
		String sql = "INSERT INTO articulos(nombreArticulo, unidad) VALUES(?, ?)";
		PreparedStatement psArticulo = null;
		try {
			psArticulo = conn.prepareStatement(sql);
			psArticulo.setString(1, nombreArticulo);
			psArticulo.setString(2, unidad);
			psArticulo.executeUpdate();
			conn.commit();
			
			psArticulo.close();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally{
			if(psArticulo != null){
				try {
					psArticulo.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
	}

	private static void consultar(int id) {
		String sql = "select nombreArticulo from articulos where idArticulo = ? ";
		
		PreparedStatement pstt;
		try {
			pstt = conn.prepareStatement(sql);
			pstt.setLong(1, id);
			
			ResultSet set = pstt.executeQuery();
			
			while(set.next()){
				String nombreAticulo = set.getString("nombreArticulo");
				
				System.out.println("Articulo: "+ nombreAticulo);
			}
			pstt.close();
			set.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	private static void listar() {
		String sql = "SELECT login, codigoUsuario, nombreUsuario, contrasena, estado FROM usuarios";
		
		String login = null;
		String codigoUsuario = null;
		
		try {
			Statement stt = conn.createStatement();
			ResultSet rs = stt.executeQuery(sql);
			while(rs.next()){
				login = rs.getString("login");
				codigoUsuario = rs.getString("codigoUsuario");
				
				System.out.println("login and codUser: "+login + " - "+ codigoUsuario);
			}
			rs.close();
			stt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void cerrar() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void conectar() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/dbdelfin";
		conn = DriverManager.getConnection(url, "root", "");
		conn.setAutoCommit(false);
		
		
	}

}
