package dominio;
public class Peliculas {
	private String nombre_Pelicula;
	private int duracion;
	
	public Peliculas(String nombre_Pelicula, int duracion) {
		super();
		this.nombre_Pelicula = nombre_Pelicula;
		this.duracion = duracion;
		
	}
	public String getNombre_Pelicula() {
		return nombre_Pelicula;
	}
	public void setNombre_Pelicula(String nombre_Pelicula) {
		this.nombre_Pelicula = nombre_Pelicula;
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	
}
