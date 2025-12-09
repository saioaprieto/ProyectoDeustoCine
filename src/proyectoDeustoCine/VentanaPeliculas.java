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
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
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
	   
	    JPanel panelSuperior = new JPanel();
	    panelSuperior.setOpaque(false);
	    contentPane.add(panelSuperior, BorderLayout.NORTH);
	    
	    JLabel lblTitulo = new JLabel("ELIGE LA PELICULA QUE DESEES VER");
	    lblTitulo.setFont(new Font("Rockwell", Font.BOLD, 30));
	    lblTitulo.setForeground(Color.WHITE);
	    lblTitulo.setOpaque(false);
	    panelSuperior.add(lblTitulo);
	 
	    //el scroll con las peliculas
	    JPanel panelCartelera = new JPanel();
	    panelCartelera.setLayout(new GridLayout(0, 3, 15, 15));
	    panelCartelera.setOpaque(false);
	 
	    //titulos y duraciones guardados en un map ordenados
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
	        System.err.println("Error al recuperar películas de la base de datos: " + e.getMessage());
	        e.printStackTrace();
	    }
	  	// crear tarjetas
	    for (String titulo : peliculasMap.keySet()) {
	        int duracion = peliculasMap.get(titulo);
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
	            btnCancelar.setBackground(new Color(240, 240, 240));
	        }
	    });
	 
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.anchor = GridBagConstraints.CENTER;
	 
	    contenedorBoton.add(btnCancelar, gbc);
	    panelCartelera.add(contenedorBoton);
	 
	 
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
	    ventana.toFront();
	}
	
	private JPanel crearTarjeta(String titulo, int duracion) {
		
	       JPanel tarjeta = new JPanel(new BorderLayout());
	       tarjeta.setOpaque(false);
	    
	       JLabel imagen = new JLabel("", SwingConstants.CENTER);
	       File archivoImagen = obtenerImagen(titulo);
	       int maximaAnchura = 250;
	       int maximaAltura = 300;
	       int anchuraImagen = maximaAnchura;
	       int alturaImagen = maximaAltura;
	    
	       if (archivoImagen != null && archivoImagen.exists()) {
	           try {
	               BufferedImage imagenObtenida = ImageIO.read(archivoImagen);
	            
	               int anchuraOriginal = imagenObtenida.getWidth();
	               int alturaOriginal = imagenObtenida.getHeight();
	            
	               double visualizacionImagen = (double) anchuraOriginal / alturaOriginal;
	               anchuraImagen = maximaAnchura;
	               alturaImagen = (int) (maximaAnchura / visualizacionImagen);
	            
	               if (alturaImagen > maximaAltura) {
	                   alturaImagen = maximaAltura;
	                   anchuraImagen = (int) (maximaAltura * visualizacionImagen);
	               }
	               Image scaledImg = imagenObtenida.getScaledInstance(anchuraImagen, alturaImagen, Image.SCALE_SMOOTH);
	               imagen.setIcon(new ImageIcon(scaledImg));
	            
	           } catch (Exception e) {
	               imagen.setText("Error cargando imagen");
	           }
	       	} else {
	           imagen.setText("Sin imagen");
	       }
	    
	       imagen.setPreferredSize(new Dimension(anchuraImagen, alturaImagen));
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
	   
	       int inferiorHeight = 90;
	       JPanel panelHorarios = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6));
	       panelHorarios.setOpaque(true);
	    
	       panelHorarios.setBackground(new Color(255, 255, 255, 220));
	       panelHorarios.setPreferredSize(new Dimension(anchuraImagen, inferiorHeight));
	       boolean haySesiones = false;
	    
	       for (Proyeccion p : proyecciones) {
	    	    if (!p.getPelicula().equals(titulo)) {
	    	    	continue;
	    	    }
	    	    haySesiones = true;
	    	    String horaStr = String.format("%02d:%02d", p.getInicio().getHour(),p.getInicio().getMinute());
	    	    JButton btn = new JButton("<html><center>Sala " + p.getSala() +"<br>" + horaStr + "</center></html>");
	    	    btn.setPreferredSize(new Dimension(90, 50));
	    	    btn.addActionListener(new ActionListener() {
	    	        @Override
	    	        public void actionPerformed(ActionEvent e) {
	    	            abrirSesion(titulo, p.getInicio(), p.getSala());
	    	        }
	    	    });
	    	    btn.setBackground(new Color(240,240,240));
	    	    btn.addMouseListener(new MouseAdapter() {
	    	        @Override
	    	        public void mouseEntered(MouseEvent e) {
	    	            btn.setBackground(new Color(200, 200, 190));
	    	        }

	    	        @Override
	    	        public void mouseExited(MouseEvent e) {
	    	            btn.setBackground(new Color(240,240,240));
	    	        }
	    	    });

	    	    panelHorarios.add(btn);
	    	}

	       if (!haySesiones) {
	           JLabel sinSesiones = new JLabel("No hay sesiones hoy", SwingConstants.CENTER);
	           sinSesiones.setForeground(Color.BLACK);
	           panelHorarios.add(sinSesiones);
	       }
	    
	       JPanel panelInferiorLocal = new JPanel(new CardLayout());
	       panelInferiorLocal.setOpaque(false);
	       panelInferiorLocal.setPreferredSize(new Dimension(anchuraImagen, inferiorHeight));
	       panelInferiorLocal.add(panelTexto, "texto");
	       panelInferiorLocal.add(panelHorarios, "horarios");
	       CardLayout clLocal = (CardLayout) panelInferiorLocal.getLayout();
	    
	       panelTexto.addMouseListener(new java.awt.event.MouseAdapter() {
	         @Override
	           public void mouseEntered(java.awt.event.MouseEvent e) {
	               clLocal.show(panelInferiorLocal, "horarios");
	           }
	       });
	    
	       panelHorarios.addMouseListener(new java.awt.event.MouseAdapter() {
	         @Override
	           public void mouseExited(java.awt.event.MouseEvent e) {
	               clLocal.show(panelInferiorLocal, "texto");
	           }
	       });
	    
	       tarjeta.add(panelInferiorLocal, BorderLayout.SOUTH);
	    
	       tarjeta.setPreferredSize(new Dimension(anchuraImagen, alturaImagen + inferiorHeight));
	       return tarjeta;
	   }
private File obtenerImagen(String titulo) {
	    File carpeta = new File("imagenes");
	    if (!carpeta.exists() || carpeta.listFiles() == null) {
	        return null;
	    }
	    String safeTitulo = titulo.replaceAll("[\\\\/:*?\"<>|]", "_") + ".jpg";
	    for (File f : carpeta.listFiles()) {
	        if (!f.isFile()) continue;
	        if (f.getName().equalsIgnoreCase(safeTitulo)) {
	            return f; 
	        }
	    }
	    return null; //no encuentra nada
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



