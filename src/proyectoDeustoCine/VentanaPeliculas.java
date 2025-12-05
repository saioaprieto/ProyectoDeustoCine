package proyectoDeustoCine;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import dataBase.DataBase;
import dominio.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
public class VentanaPeliculas extends JFrame {
	private static final long serialVersionUID = 1L;
	private VentanaPrincipal parent;
	private List<Proyeccion> proyecciones;
	private Map<String, JFrame> sesionesAbiertas = new HashMap<>();
	public VentanaPeliculas(VentanaPrincipal parent) {
		this.parent = parent;
	    this.proyecciones = parent.getDb().getProyecciones();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		JPanel contentPane = crearPanelConFondo("imagenes/fondoPeliculas.jpg");
	    contentPane.setBorder(new EmptyBorder(50, 5, 5, 5));
	    setContentPane(contentPane);
	    contentPane.setLayout(new BorderLayout());
	    
	    //titulo
	    JPanel panelSuperior = new JPanel();
	    panelSuperior.setOpaque(false);
	    contentPane.add(panelSuperior, BorderLayout.NORTH);
	      
	    JLabel lblTitulo = new JLabel("ELIGE LA PELICULA QUE DESEES");
	    lblTitulo.setFont(new Font("Rockwell", Font.BOLD, 30));
	    lblTitulo.setForeground(Color.WHITE);
	    lblTitulo.setOpaque(false);
	    panelSuperior.add(lblTitulo);
	   
	    //scroll para el panel 
	    JPanel panelCartelera = new JPanel();
	    panelCartelera.setLayout(new GridLayout(0, 3, 15, 15));
	    panelCartelera.setOpaque(false);
	   
	    //cogemos  de la base de datos
	    Map<String, Integer> peliculasMap = new LinkedHashMap<>();
	    DataBase db = parent.getDb();
	    try (Connection conn = db.connect();
	         PreparedStatement ps = conn.prepareStatement("SELECT NOMBRE, DURACION FROM PELICULAS");
	         ResultSet rs = ps.executeQuery()) {
	    	while (rs.next()) {
	            String titulo = rs.getString("NOMBRE");
	            int duracion = rs.getInt("DURACION");
	            peliculasMap.put(titulo, duracion);
	        }
	    } catch (SQLException e) {
	        System.err.println(e.getMessage());
	        e.printStackTrace();
	    }
	    
	    // crear tarjetas para la cartelera
	    for (Map.Entry<String, Integer> entry : peliculasMap.entrySet()) {
	        String titulo = entry.getKey();
	        int duracion = entry.getValue();
	        JPanel tarjeta = crearTarjeta(titulo, duracion);
	        tarjeta.setOpaque(false);
	        panelCartelera.add(tarjeta);
	    }
	 
	    
	    JPanel contenedorBoton = new JPanel(new GridBagLayout());
	    contenedorBoton.setOpaque(false);
	    JButton btnCancelar = new JButton("Cancelar");
	    btnCancelar.setForeground(new Color(0, 0, 0));
	    btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    btnCancelar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		VentanaPeliculas.this.dispose();
	    		VentanaPeliculas.this.parent.setVisible(true);
	    	}
	    });
	    
	    btnCancelar.setPreferredSize(new Dimension(200, 50));
	    btnCancelar.setBackground(new Color(240, 240, 240));
	    btnCancelar.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseEntered(MouseEvent e) {
	            btnCancelar.setBackground(new Color(200, 200, 190));
	        }
	        @Override
	        public void mouseExited(MouseEvent e) {
	            btnCancelar.setBackground(new Color(245, 245, 240));
	        }
	    });
	    
	    // centrar el boton
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.anchor = GridBagConstraints.CENTER;
	   
	    contenedorBoton.add(btnCancelar, gbc);
	    panelCartelera.add(contenedorBoton);
	   
	    //scroll vertical
	    JScrollPane scroll = new JScrollPane(panelCartelera, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    scroll.setOpaque(false); 
	    scroll.getViewport().setOpaque(false);
	    getContentPane().add(scroll);
	    setVisible(true);  
	 
	}
	
	private void abrirSesion(String titulo, LocalTime inicio, int sala) {
	    String clave = titulo + "_" + inicio + "_Sala" + sala;
	    JFrame ventana = sesionesAbiertas.get(clave);
	    if (ventana == null) {
	       ventana = new VentanaSala(parent, titulo, inicio, sala);
	       sesionesAbiertas.put(clave, ventana);
	    }
	    ventana.setVisible(true);
	    this.setVisible(false);
	}
	
	private JPanel crearTarjeta(String titulo, int duracion) {
		
		JPanel tarjeta = new JPanel(new BorderLayout());
		tarjeta.setOpaque(false);

		JLabel imagen = new JLabel("", SwingConstants.CENTER);
		File archivoImagen = obtenerImagen(titulo);

		int anchuraMax = 250;
		int alturaMax = 300;

		int anchuraVisual = anchuraMax;
		int alturaVisual = alturaMax;

		if (archivoImagen != null && archivoImagen.exists()) {
		    try {
		        BufferedImage imgOriginal = ImageIO.read(archivoImagen);

		        double escala = Math.min((double)anchuraMax/imgOriginal.getWidth(),(double)alturaMax/imgOriginal.getHeight());

		        anchuraVisual = (int) (imgOriginal.getWidth() * escala);
		        alturaVisual = (int) (imgOriginal.getHeight() * escala);

		        Image imgEscalada = imgOriginal.getScaledInstance(anchuraVisual, alturaVisual, Image.SCALE_SMOOTH);
		        imagen.setIcon(new ImageIcon(imgEscalada));

		    } catch (Exception e) {
		        imagen.setText("Error cargando imagen");
		    }
		} else {
		    imagen.setText("Sin imagen");
		}
		imagen.setPreferredSize(new Dimension(anchuraVisual, alturaVisual));
		tarjeta.add(imagen, BorderLayout.CENTER);

	   
	       JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
	       lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
	       lblTitulo.setForeground(Color.WHITE); 
	       lblTitulo.setOpaque(false);
	     
	       JLabel lblDuracion = new JLabel(duracion + " min", SwingConstants.CENTER);
	       lblDuracion.setForeground(Color.WHITE);
	       lblDuracion.setOpaque(false);
	     
	       JPanel panelTexto = new JPanel(new GridLayout(2, 1));
	       panelTexto.setOpaque(false);
	       panelTexto.add(lblTitulo);
	       panelTexto.add(lblDuracion);
	    
	       int anchuraInferior = 90;
	       JPanel panelHorarios = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6));
	       panelHorarios.setOpaque(true);
	     
	       panelHorarios.setBackground(new Color(255, 255, 255, 220));
	       panelHorarios.setPreferredSize(new Dimension(anchuraVisual, anchuraInferior));
	       boolean haySesiones = false;
	     
	       for (Proyeccion p : proyecciones) {
	           if (!p.pelicula.equals(titulo)) continue;
	           haySesiones = true;
	           final Proyeccion proy = p;
	           String hora = String.format("%02d:%02d", proy.inicio.getHour(), proy.inicio.getMinute());
	           JButton btn = new JButton("<html><center>Sala " + proy.sala + "<br>" + hora + "</center></html>");
	           btn.setPreferredSize(new Dimension(90, 50));
	           btn.addActionListener(e -> abrirSesion(titulo, proy.inicio, proy.sala));
	           panelHorarios.add(btn);
	       }
	       if (!haySesiones) {
	           JLabel lblsinSesiones = new JLabel("No hay sesiones hoy", SwingConstants.CENTER);
	           lblsinSesiones.setForeground(Color.BLACK);
	           panelHorarios.add(lblsinSesiones);
	       }
	     
	       JPanel panelInferior = new JPanel(new CardLayout());
	       panelInferior.setOpaque(false);
	       panelInferior.setPreferredSize(new Dimension(anchuraVisual, anchuraInferior));
	       panelInferior.add(panelTexto, "texto");
	       panelInferior.add(panelHorarios, "horarios");

	       panelTexto.addMouseListener(new java.awt.event.MouseAdapter() {
	           @Override
	           public void mouseEntered(java.awt.event.MouseEvent e) {
	               ((CardLayout) panelInferior.getLayout()).show(panelInferior, "horarios");
	           }
	       });
	       panelHorarios.addMouseListener(new java.awt.event.MouseAdapter() {
	           @Override
	           public void mouseExited(java.awt.event.MouseEvent e) {
	               ((CardLayout) panelInferior.getLayout()).show(panelInferior, "texto");
	           }
	       });

	       tarjeta.add(panelInferior, BorderLayout.SOUTH);
	       tarjeta.setPreferredSize(new Dimension(anchuraVisual, alturaVisual + anchuraInferior));
	       return tarjeta;
	   }

private File obtenerImagen(String titulo) {
	    File carpeta = new File("imagenes");
	    if (!carpeta.exists() || carpeta.listFiles() == null) {
	        return null;
	    }
	    String titulofijo = titulo.replaceAll("[\\\\/:*?\"<>|]", "_") + ".jpg";
	    for (File file : carpeta.listFiles()) {
	        if (!file.isFile()) {
	        	continue;
	        }
	        if (file.getName().equalsIgnoreCase(titulofijo)) {
	            return file; 
	        }
	    }
	    return null;
	}
private JPanel crearPanelConFondo(String ruta) {
		Image img = new ImageIcon(ruta).getImage();
	       return new JPanel() {
	       @Override
	           protected void paintComponent(Graphics g) {
	               super.paintComponent(g);
	               g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	           }
	       };
	}
}



