package proyectoDeustoCine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import dataBase.DataBase;
public class VentanaPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;
   private Image imagen;
   private static DataBase db;
	public DataBase getDb() {
	return db;
}
   public static void setDb(DataBase db) {
	VentanaPrincipal.db = db;
   }
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					db = new DataBase();
	                db.inicializarBaseDatos();
	                DayOfWeek dia = LocalDate.now().getDayOfWeek();
	                Map<String, Integer> peliculasMap = new LinkedHashMap<>();
	                try (Connection conn = db.connect();
	                     PreparedStatement ps = conn.prepareStatement("SELECT NOMBRE, DURACION FROM PELICULAS");
	                     ResultSet rs = ps.executeQuery()) {

	                	while (rs.next()) {
	                        String titulo = rs.getString("NOMBRE");
	                        int duracion = rs.getInt("DURACION");	                       

	                        peliculasMap.put(titulo, duracion);}

	                } catch (SQLException e) {
	                    System.err.println("Error al recuperar películas de la base de datos: " + e.getMessage());
	                    e.printStackTrace();
	                }
	                db.generarProgramacionDiaRandom(peliculasMap, dia);
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {

		Dimension tamañoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int anchuraPantalla = tamañoPantalla.width;
        int alturaPantalla = tamañoPantalla.height;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel contentPane = crearPanelConFondo("imagenes/Peliculas.jpg");
        contentPane.setLayout(null); 
        setContentPane(contentPane);

        int anchuraBoton = anchuraPantalla/6;
        int alturaBoton = alturaPantalla/10;

        JLabel titulo = new JLabel("DEUSTOCINE");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Goudy Old Style", Font.BOLD, 70));
        titulo.setBounds((anchuraPantalla/2)-anchuraBoton- 30*10,(alturaPantalla/2)-(alturaBoton /2)-150,800,100);

        contentPane.add(titulo);
        JButton btnSoyCliente = new JButton("SOY CLIENTE");
        btnSoyCliente.setFocusPainted(false);
        btnSoyCliente.setBackground(new Color(245, 245, 240));
        btnSoyCliente.setForeground(new Color(0, 0, 64));
        btnSoyCliente.setFont(new Font("Goudy Old Style", Font.PLAIN, 19));
        btnSoyCliente.setBounds((anchuraPantalla/2)-anchuraBoton- 30,(alturaPantalla/2)-(alturaBoton /2),anchuraBoton,alturaBoton);
        btnSoyCliente.addActionListener(e -> {
            VentanaPrincipal.this.setVisible(false);
            VentanaPeliculas mivent1 = new VentanaPeliculas(VentanaPrincipal.this);
            mivent1.setVisible(true);
        });
        btnSoyCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSoyCliente.setBackground(new Color(200, 200, 190));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSoyCliente.setBackground(new Color(245, 245, 240));
            }
        });
        contentPane.add(btnSoyCliente);

       
        JButton btnSoyTrabajador = new JButton("SOY TRABAJADOR");
        btnSoyTrabajador.setFocusPainted(false);
        btnSoyTrabajador.setBackground(new Color(245, 245, 240));
        btnSoyTrabajador.setForeground(new Color(0, 0, 64));
        btnSoyTrabajador.setFont(new Font("Goudy Old Style",Font.PLAIN,19));
        btnSoyTrabajador.setBounds((anchuraPantalla/2)+30, (alturaPantalla/2)-(alturaBoton/2),anchuraBoton,alturaBoton);
        btnSoyTrabajador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaPrincipal.this.setVisible(false);
                VentanaTrabajador ventanaTrabajador = new VentanaTrabajador(VentanaPrincipal.this, db);
                ventanaTrabajador.setVisible(true);
            }
        });

        btnSoyTrabajador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSoyTrabajador.setBackground(new Color(200, 200, 190));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSoyTrabajador.setBackground(new Color(245, 245, 240));
            }
        });
        contentPane.add(btnSoyTrabajador);
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
