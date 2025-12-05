package dominio;
public class Empleado {
	private String codigoDni;
	private String nombre;
	private String apellido;
	private String telefono;
	private int sueldo;
	private Tipo tipo;
	private String contrasena;
	
	public Empleado(String codigoDni, String nombre, String apellido, String telefono, int sueldo, Tipo tipo,
			String contrasena) {
		super();
		this.codigoDni = codigoDni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.sueldo = sueldo;
		this.tipo = tipo;
		this.contrasena = contrasena;
	}
	public String getCodigoDni() {
		return codigoDni;
	}
	public void setCodigoDni(String codigoDni) {
		this.codigoDni = codigoDni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public int getSueldo() {
		return sueldo;
	}
	public void setSueldo(int sueldo) {
		this.sueldo = sueldo;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	
	
	
	
}

