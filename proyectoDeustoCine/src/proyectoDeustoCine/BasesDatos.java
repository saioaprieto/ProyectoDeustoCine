package proyectoDeustoCine;

import java.sql.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class BasesDatos {

	private Connection conn;
	
	public void connect() {
		String url = "jdbc:sqlite:c:/Users/saioa.prieto/Downloads/chinook/chinook.db";
		try {
			conn = DriverManager.getConnection(url);
			System.out.println("Connection to SQLite has been stablished.");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			
	
	}

	public static void main(String[] args) {
		BasesDatos bd = new  BasesDatos();
		bd.connect();
		bd.verificarRegistros();
		
	}

	private void verificarRegistros() {
		try {
			Statement stent = conn.createStatement();
			String sql = "Select * from artists";
			ResultSet rs = stent.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString("Name");
				Integer id = rs.getInt("ArtistId");
				System.out.println("Nombre:" + name + "ID: " + id);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	

	}

	
}
