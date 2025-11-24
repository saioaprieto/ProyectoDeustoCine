package proyectoDeustoCine;

import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
import java.util.ArrayList;

	public class Conexion {

	private Connection conn;

	public void connect() {
	    String url = "jdbc:mysql://localhost:3306/DEUSTOCINE";
	    String usuario = "root";      
	    String contrasena = "";       
	    try {
	        conn = DriverManager.getConnection(url, usuario, contrasena);
	        System.out.println("Conexión con MySQL establecida.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public static void main(String[] args) {
	    Conexion bd = new Conexion();
	    bd.connect();
	    bd.verificarRegistros();
	}

	private void verificarRegistros() {
	    try {
	        Statement stent = conn.createStatement();
	        String sql = "SELECT * FROM PELICULAS"; 
	        ResultSet rs = stent.executeQuery(sql);

	        while (rs.next()) {
	            String nombre = rs.getString("NOMBRE");
	            String genero = rs.getString("GENERO");
	            System.out.println("Nombre: " + nombre + " | Genero: " + genero);
	        }
	        rs.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	public ArrayList<Trabajador> obtenerPersonas() {
		ArrayList<Trabajador> lista = new ArrayList<Trabajador>();
		try {
			 Statement stent = conn.createStatement();
		        String sql = "SELECT * FROM TRABAJADORES"; 
		        ResultSet rs = stent.executeQuery(sql);
		        while (rs.next()) {
		        	String dni = rs.getString("COD_DNI");
		            String nombre = rs.getString("NOMBRE");
		            String apellido = rs.getString("APELLIDO");
		            String telefono = rs.getString("TEL");
		            String salario = rs.getString("SUELDO");

		            Trabajador t = new Trabajador(dni, nombre, apellido, telefono, salario);
		            lista.add(t);

		        }
		        rs.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		return lista;	
	}

	public Connection getConnection() {
		   return conn;
		}
		public void close() {
		   try {
		       if (conn != null && !conn.isClosed()) {
		           conn.close();
		           System.out.println(" Conexión cerrada correctamente.");
		       }
		   } catch (SQLException e) {
		       e.printStackTrace();
		   }
		}

	}



