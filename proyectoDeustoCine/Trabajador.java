package proyectoDeustoCine;

public class Trabajador {
	private String codico_dni;
	private String nombre;
	private String apellido;
	private String telefono;
	private String salario;
	public Trabajador(String codico_dni, String nombre, String apellido, String telefono, String salario) {
		super();
		this.codico_dni = codico_dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.salario = salario;
	}
	public String getCodico_dni() {
		return codico_dni;
	}
	public void setCodico_dni(String codico_dni) {
		this.codico_dni = codico_dni;
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
	public String getSalario() {
		return salario;
	}
	public void setSalario(String salario) {
		this.salario = salario;
	}
	
	
	
	

}
