package deustoCineGrupal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

