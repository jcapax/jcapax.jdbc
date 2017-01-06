package jcapax.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jcapax.jdbc.conexion.Conexion;
import jcapax.jdbc.modelo.Articulo;

public class ArticuloDaoImpl implements ArticuloDAO{
	
	final String INSERT = "INSERT INTO articulos(nombreArticulo, unidad) VALUES(?, ?)";
	final String GETONE = "SELECT idArticulo, nombreArticulo, unidad FROM articulos WHERE idArticulo = ? ";
	final String GETALL = "SELECT idArticulo, nombreArticulo, unidad FROM articulos";
	
	static Connection conn = null;
	
	public static void main(String[] args){
		int i = 1;
		String url = "jdbc:mysql://localhost:3306/dbdelfin";
		
		Articulo a, b = null;
		
		try {
			conn = DriverManager.getConnection(url, "root", "");
			conn.setAutoCommit(false);
			
			ArticuloDAO dao = new ArticuloDaoImpl(conn);
			List<Articulo> articulo = dao.listAll();
			for (Articulo art : articulo) {
				System.out.println(i++ + " " + art.getIdArticulo() +" " +art.getNombreArticulo() + " " + art.getUnidad());
			}
			
			System.out.println("");
			System.out.println("");
			
			a = dao.listOne(43L);
			System.out.println(a.getIdArticulo() + " -- " + a.getNombreArticulo() + " -- " + a.getUnidad());
			
			b = new Articulo(null, "tv", "pza");
			
			System.out.println("");
			
			dao.insert(b);
			
			System.out.println("final del programa");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public ArticuloDaoImpl(Connection conn) {
		super();
		this.conn = conn;
	}

	public void insert(Articulo a) {
		PreparedStatement pstt = null;
		try {
			pstt = conn.prepareStatement(INSERT);
			
			pstt.setString(1, a.getNombreArticulo());
			pstt.setString(2, a.getUnidad());

			pstt.executeUpdate();
			conn.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				pstt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	
	public void delete(Articulo a) {
		
	}

	public void update(Articulo a) {
		
	}

	public List<Articulo> listAll() {
		PreparedStatement pstt = null;
		ResultSet set = null;
		List<Articulo> articulo = new ArrayList<Articulo>();
		try {
			pstt = conn.prepareStatement(GETALL);
			set = pstt.executeQuery();
			
			while(set.next()){
				Long idArticulo = set.getLong("idArticulo");
				String nombreArticulo = set.getString("nombreArticulo");
				String unidad = set.getString("unidad");
				
				articulo.add(new Articulo(idArticulo, nombreArticulo, unidad));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(set != null){
				try {
					set.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return articulo;
	}

	public Articulo listOne(Long K) {
		PreparedStatement pstt = null;
		ResultSet set = null;
		Articulo articulo = null;
		try {
			pstt = conn.prepareStatement(GETONE);
			pstt.setLong(1, K);
			set = pstt.executeQuery();
			while(set.next()){
				Long idArticulo = set.getLong("idArticulo");
				String nombreArticulo = set.getString("nombreArticulo");
				String unidad = set.getString("unidad");
				
				articulo = new Articulo(idArticulo, nombreArticulo, unidad);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(set != null){
				try {
					set.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return articulo;
	}

}
