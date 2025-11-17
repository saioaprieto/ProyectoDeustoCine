package proyectoDeustoCine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VentanaCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VentanaPrincipal parent;


	/**
	 * Create the frame.
	 */
	//hola
	 public VentanaCliente(VentanaPrincipal parent) {
	       this.parent = parent;
	       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       setBounds(100, 100, 750, 750);
	       //prueba jone
	      
	       contentPane = new FondoPanel("/deustoCineGrupal/src/imagenPortada/ventanaPeliculas.jpg");
	       contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	       contentPane.setLayout(new BorderLayout(0, 0));
	       setContentPane(contentPane);
	      
	       JPanel panelSuperior = new JPanel();
	       panelSuperior.setOpaque(false);
	       contentPane.add(panelSuperior, BorderLayout.NORTH);
	       JLabel lblNewLabel = new JLabel("New label");
	       lblNewLabel.setForeground(Color.BLACK); // texto visible sobre imagen
	       panelSuperior.add(lblNewLabel);
	   }
	  
	   class FondoPanel extends JPanel {
	       private Image imagen;
	       public FondoPanel(String ruta) {
	           imagen = new ImageIcon(ruta).getImage();
	       }
	       @Override
	       protected void paintComponent(Graphics g) {
	           super.paintComponent(g);
	           if (imagen != null)
	               g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
	       }
	   }
	}
