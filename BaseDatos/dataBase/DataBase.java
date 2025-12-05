package dataBase;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import dominio.Proyeccion;
import dominio.Trabajador;
public class DataBase {
  private String URL = "jdbc:sqlite:DataBase.db";
  private DefaultTableModel modeloHorario;
  public DefaultTableModel getModeloHorario() {
      return modeloHorario;
  }


  public void setModeloHorario(DefaultTableModel modeloHorario) {
      this.modeloHorario = modeloHorario;
  }

private List<Proyeccion> proyecciones = new ArrayList<>();
  
public List<Proyeccion> getProyecciones() {
		return proyecciones;
	}
	public void setProyecciones(List<Proyeccion> proyecciones) {
		this.proyecciones = proyecciones;
	}
	
	private final Random random = new Random();
 
  public Connection connect() throws SQLException {
      return DriverManager.getConnection(URL);
  }
  public void inicializarBaseDatos() {
      File archivoBd = new File("DataBase.db");
      if (archivoBd.exists()) {
          archivoBd.delete();
      }
      try (Connection conexion = connect(); Statement stmt = conexion.createStatement()) {
          String sqlTrabajadores = "CREATE TABLE IF NOT EXISTS TRABAJADORES (" +
                  "COD_DNI TEXT PRIMARY KEY," +
                  "NOMBRE TEXT NOT NULL," +
                  "APELLIDO TEXT NOT NULL," +
                  "TEL INTEGER NOT NULL," +
                  "SUELDO INTEGER NOT NULL," +
                  "TIPO TEXT NOT NULL," +
                  "CONTRASENA TEXT NOT NULL)";
          stmt.executeUpdate(sqlTrabajadores);
          String sqlPeliculas = "CREATE TABLE IF NOT EXISTS PELICULAS (" +
                  "NOMBRE TEXT PRIMARY KEY," +
                  "DURACION INTEGER)";
          stmt.executeUpdate(sqlPeliculas);
          String sqlSolicitudes = "CREATE TABLE IF NOT EXISTS SOLICITUDES (" +
      	        "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
      	        "DNI_EMPLEADO TEXT NOT NULL," +
      	        "MENSAJE TEXT NOT NULL," +
      	        "NOMBRE TEXT NOT NULL," +
      	        "ESTADO TEXT NOT NULL)";
      	stmt.executeUpdate(sqlSolicitudes);
          insertarTrabajadoresIniciales(conexion);
          String[] titulos = obtenerTitulosSensacine();
          insertarPeliculasTMDb(conexion, "3c82fb463f7ae3cb5535a11509f97de2", titulos);
      } catch (SQLException e) {
          System.err.println(e.getMessage());
      }
  }
  private void insertarTrabajadoresIniciales(Connection conexion) {
	    String datosTrabajadores = "INSERT INTO TRABAJADORES VALUES " +
	            "('12345678A','Marta','García',688112233,2500,'Supervisor','marta123')," +
	            "('23456789B','Luis','Fernández',677998877,1600,'Empleado','luis123')," +
	            "('34567890C','Ana','Martínez',699223344,1550,'Empleado','ana123')," +
	            "('45678901D','Carlos','López',655778899,1500,'Empleado','carlos123')," +
	            "('56789012E','Lucía','Santos',666889900,1500,'Empleado','lucia123')," +
	            "('67890123F','David','Ruiz',644556677,1450,'Empleado','david123')";
	    try (PreparedStatement sentenciaInsertar = conexion.prepareStatement(datosTrabajadores)) {
	        sentenciaInsertar.executeUpdate();
	    } catch (SQLException e) {
	        System.err.println("Error insertando trabajadores: " + e.getMessage());
	    }
	}
  public String[] loginTrabajador(String dni, String contrasena) {
      try (Connection conexion = connect()) {
          String consultaLogin = "SELECT NOMBRE, TIPO FROM TRABAJADORES WHERE COD_DNI = ? AND CONTRASENA = ?";
          try (PreparedStatement sentenciaLogin = conexion.prepareStatement(consultaLogin)) {
              sentenciaLogin.setString(1, dni);
              sentenciaLogin.setString(2, contrasena);
              try (ResultSet resultadoLogin = sentenciaLogin.executeQuery()) {
           	   if (resultadoLogin.next()) {
                      String nombre = resultadoLogin.getString("NOMBRE");
                      String tipo = resultadoLogin.getString("TIPO");
                      return new String[]{nombre, tipo};
                  }
              }
          }
      } catch (SQLException e) {
          System.err.println(e.getMessage());
      }
      return null;
  }
  public List<Trabajador> getTrabajadores() {
      List<Trabajador> lista = new ArrayList<>();
      String consultaSql = "SELECT COD_DNI, NOMBRE, APELLIDO, TEL, SUELDO, TIPO, CONTRASENA FROM TRABAJADORES";
      try (Connection conexion = connect();
           PreparedStatement sentencia = conexion.prepareStatement(consultaSql);
           ResultSet resultados = sentencia.executeQuery()) {
          while (resultados.next()) {
              Trabajador trabajador = new Trabajador(
                      resultados.getString("COD_DNI"),
                      resultados.getString("NOMBRE"),
                      resultados.getString("APELLIDO"),
                      resultados.getString("TEL"),
                      resultados.getString("SUELDO"),
                      resultados.getString("TIPO")
              );
              lista.add(trabajador);
          }
      } catch (SQLException e) {
          System.err.println(e.getMessage());
      }
      return lista;
  }
  public void guardarSolicitud(String dni, String mensaje,String nombre) {
	    String sql = "INSERT INTO SOLICITUDES (DNI_EMPLEADO, MENSAJE, NOMBRE, ESTADO) " +
	                 "VALUES (?, ?, ?, 'pendiente')";
	    try (Connection conexion = connect();
	         PreparedStatement stmt = conexion.prepareStatement(sql)) {


	        stmt.setString(1, dni);
	        stmt.setString(2, mensaje);
	        stmt.setString(3, nombre);
	        stmt.executeUpdate();


	    } catch (SQLException e) {
	        System.err.println("Error guardando solicitud: " + e.getMessage());
	    }
	}
public List<String> getSolicitudesPendientes() {
	    List<String> lista = new ArrayList<>();


	    String sql = "SELECT DNI_EMPLEADO, MENSAJE,NOMBRE FROM SOLICITUDES WHERE ESTADO = 'pendiente'";


	    try (Connection conexion = connect();
	         PreparedStatement stmt = conexion.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {


	        while (rs.next()) {
	            String dni = rs.getString("DNI_EMPLEADO");
	            String mensaje = rs.getString("MENSAJE");
	            String nombre = rs.getString("NOMBRE");
	            lista.add(mensaje + ":"+ dni + " --> " + nombre);
	        }


	    } catch (SQLException e) {
	        System.err.println("Error leyendo solicitudes: " + e.getMessage());
	    }


	    return lista;
	}



  public static List<Integer> insertarPeliculasTMDb(Connection conexion, String apiClave, String[] titulos) {
	    List<Integer> duraciones = new ArrayList<>();
	    try {
	        String insertarSql = "INSERT OR IGNORE INTO PELICULAS (NOMBRE, DURACION) VALUES (?, ?)";
	        try (PreparedStatement sentencia = conexion.prepareStatement(insertarSql)) {
	            Gson gson = new Gson();
	            File carpetaImagenes = new File("imagenes");
	            if (!carpetaImagenes.exists()) {
	                carpetaImagenes.mkdirs();
	            }

	            for (String titulo : titulos) {
	                String rutaPoster = null;
	                int duracion = 100; // valor por defecto

	                try {
	                    // Buscar película en TMDb
	                    String tituloParaUrl = titulo.replace(" ", "%20");
	                    URL url = new URL("https://api.themoviedb.org/3/search/movie?api_key=" + apiClave + "&query=" + tituloParaUrl);
	                    HttpURLConnection conexionHttp = (HttpURLConnection) url.openConnection();
	                    conexionHttp.setRequestMethod("GET");
	                    conexionHttp.connect();

	                    try (InputStream flujoRespuesta = conexionHttp.getInputStream();
	                         InputStreamReader lectorRespuesta = new InputStreamReader(flujoRespuesta)) {

	                        JsonObject respuestajson = gson.fromJson(lectorRespuesta, JsonObject.class);
	                        JsonArray listaResultados = respuestajson.getAsJsonArray("results");

	                        if (listaResultados.size() > 0) {
	                            JsonObject peli = listaResultados.get(0).getAsJsonObject();
	                            int idPeli = peli.get("id").getAsInt();
	                            if (peli.has("poster_path") && !peli.get("poster_path").isJsonNull()) {
	                                rutaPoster = peli.get("poster_path").getAsString();
	                            }

	                            // Obtener detalles para la duración
	                            URL urlDetalles = new URL("https://api.themoviedb.org/3/movie/" + idPeli + "?api_key=" + apiClave + "&language=es-ES");
	                            HttpURLConnection conexionDetalles = (HttpURLConnection) urlDetalles.openConnection();
	                            conexionDetalles.setRequestMethod("GET");
	                            conexionDetalles.connect();

	                            try (InputStream flujoDetalles = conexionDetalles.getInputStream();
	                                 InputStreamReader lectorDetalles = new InputStreamReader(flujoDetalles)) {

	                                JsonObject respuestaDetallesjson = gson.fromJson(lectorDetalles, JsonObject.class);
	                                if (respuestaDetallesjson.has("runtime") && !respuestaDetallesjson.get("runtime").isJsonNull()) {
	                                    duracion = respuestaDetallesjson.get("runtime").getAsInt();
	                                }
	                            }
	                        }
	                    }

	                    // Asegurar duración válida
	                    if (duracion <= 0) duracion = 100;

	                    // Insertar en base de datos
	                    sentencia.setString(1, titulo);
	                    sentencia.setInt(2, duracion);
	                    sentencia.executeUpdate();
	                    duraciones.add(duracion);

	                    // Descargar imagen si existe
	                    if (rutaPoster != null) {
	                        try {
	                            String urlImagen = "https://image.tmdb.org/t/p/w500" + rutaPoster;
	                            if (urlImagen.endsWith(".jpg") || urlImagen.endsWith(".jpeg") || urlImagen.endsWith(".png")) {
	                                BufferedImage imagen = ImageIO.read(new URL(urlImagen));
	                                if (imagen != null) {
	                                    String nombreArchivo = titulo.replaceAll("[\\\\/:*?\"<>|]", "_") + ".jpg";
	                                    File archivoImagen = new File(carpetaImagenes, nombreArchivo);
	                                    ImageIO.write(imagen, "jpg", archivoImagen);
	                                }
	                            }
	                        } catch (Exception e) {
	                            System.out.println("Error descargando imagen: " + e.getMessage());
	                        }
	                    }

	                } catch (Exception e) {
	                    System.err.println("Error con la película '" + titulo + "': " + e.getMessage());
	                }
	            }
	        }
	    } catch (Exception e) {
	        System.err.println("Error general: " + e.getMessage());
	    }
	    return duraciones;
	}

 
public static String[] obtenerTitulosSensacine() {
      try {
          Document doc = Jsoup.connect("https://www.sensacine.com/cines/cine/E0296/")
                  .userAgent("Mozilla/5.0")
                  .get();
          Elements elementosTitulo = doc.select("div.card.entity-card.movie-card-theater h2.meta-title a.meta-title-link");
          List<String> titulos = new ArrayList<>();
         
          for (org.jsoup.nodes.Element e : elementosTitulo) {
              String titulo = e.text().trim();
              if (!titulo.isEmpty() && !titulos.contains(titulo)) {
                  //no se duplican titulos
           	   titulos.add(titulo);
              }
          }
          return titulos.toArray(new String[0]);
      } catch (Exception e) {
          e.printStackTrace();
          return new String[0];
      }
  }
public void generarProgramacionDiaRandom(Map<String, Integer> peliculasMap, DayOfWeek dia) {
    proyecciones.clear();
    if (peliculasMap.isEmpty()) return;
    LocalTime apertura;
    LocalTime cierre;

    if (dia == DayOfWeek.TUESDAY || dia == DayOfWeek.WEDNESDAY) {
        apertura = LocalTime.of(16, 0);
        cierre = LocalTime.of(20, 0);
    } else if (dia == DayOfWeek.THURSDAY || dia == DayOfWeek.FRIDAY
            || dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY) {
        apertura = LocalTime.of(16, 0);
        cierre = LocalTime.of(23, 0);
    } else {
        return;
    }

    LocalTime limiteFinal = cierre;
    List<Map.Entry<String, Integer>> listaPeliculas = new ArrayList<>(peliculasMap.entrySet());
    Random rand = new Random();

    LocalTime[] horariosSalas = new LocalTime[3];
    for (int i = 0; i < 3; i++) horariosSalas[i] = apertura;

    boolean hayEspacio = true;

    while (hayEspacio) {
        hayEspacio = false;

        for (int sala = 0; sala < 3; sala++) {
            LocalTime t = horariosSalas[sala];
            if (t.isAfter(limiteFinal)) continue;

            boolean agregada = false;

            for (int intento = 0; intento < listaPeliculas.size(); intento++) {
                int indice = rand.nextInt(listaPeliculas.size());
                Map.Entry<String, Integer> peli = listaPeliculas.get(indice);

                String titulo = peli.getKey();
                int duracion = peli.getValue();
                LocalTime inicio = t;
                LocalTime finProyec = inicio.plusMinutes(duracion + 15);

                if (finProyec.isAfter(cierre)) continue;

                boolean yaExiste = false;
                for (Proyeccion p : proyecciones) {
                    if (p.pelicula.equals(titulo) && p.inicio.equals(inicio)) {
                        yaExiste = true;
                        break;
                    }
                }

                boolean colisionSala = false;
                for (Proyeccion p : proyecciones) {
                    if (p.sala == (sala + 1)) {
                        if (!(finProyec.isBefore(p.inicio) || inicio.isAfter(p.fin))) {
                            colisionSala = true;
                            break;
                        }
                    }
                }

                if (!yaExiste && !colisionSala) {
                    proyecciones.add(new Proyeccion(sala + 1, titulo, inicio, finProyec));
                    horariosSalas[sala] = finProyec; 
                    agregada = true;
                    hayEspacio = true;
                    break;
                }
            }
        }
    }
}







  private Map<String, Set<String>> reservas = new HashMap<>();
  public void ocuparButacas(String claveSesion, Set<JButton> botonesSeleccionados) {
      Set<String> ocupadas = reservas.getOrDefault(claveSesion, new HashSet<>());
      for (JButton b : botonesSeleccionados) {
          ocupadas.add(b.getText());
      }
      reservas.put(claveSesion, ocupadas);
  }
 
  public Set<String> getButacasOcupadas(String claveSesion) {
      return reservas.getOrDefault(claveSesion, new HashSet<>());
  }
}



