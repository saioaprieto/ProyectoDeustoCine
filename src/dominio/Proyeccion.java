package dominio;

import java.time.LocalTime;

public class Proyeccion {
	 public int sala; // 1,2,3
     public String pelicula;
     public LocalTime inicio;
     public LocalTime fin;
     
     public Proyeccion(int sala, String pelicula, LocalTime inicio, LocalTime fin) {
         this.sala = sala;
         this.pelicula = pelicula;
         this.inicio = inicio;
         this.fin = fin;
     }
 }

