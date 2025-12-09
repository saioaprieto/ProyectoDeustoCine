package dominio;

import java.time.LocalTime;

public class Proyeccion {
	 public int sala; // 1,2,3
     public String pelicula;
     public LocalTime inicio;
     public LocalTime fin;
     
     public int getSala() {
		return sala;
	}

	 public void setSala(int sala) {
		 this.sala = sala;
	 }

	 public String getPelicula() {
		 return pelicula;
	 }

	 public void setPelicula(String pelicula) {
		 this.pelicula = pelicula;
	 }

	 public LocalTime getInicio() {
		 return inicio;
	 }

	 public void setInicio(LocalTime inicio) {
		 this.inicio = inicio;
	 }

	 public LocalTime getFin() {
		 return fin;
	 }

	 public void setFin(LocalTime fin) {
		 this.fin = fin;
	 }

	 public Proyeccion(int sala, String pelicula, LocalTime inicio, LocalTime fin) {
         this.sala = sala;
         this.pelicula = pelicula;
         this.inicio = inicio;
         this.fin = fin;
     }
     
     
     
 }

