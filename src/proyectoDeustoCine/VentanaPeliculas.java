package proyectoDeustoCine;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import proyectoDeustoCine.VentanaPrincipal;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;


public class VentanaPeliculas extends JFrame {

	private static final long serialVersionUID = 1L;
	private VentanaPrincipal parent; 

	
	/**
	 * Create the frame.
	 */
	public VentanaPeliculas(VentanaPrincipal parent) {
		this.parent= parent; 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //  setExtendedState(JFrame.MAXIMIZED_BOTH);

		
		JPanel contentPane = crearPanelConFondo("imagenes/Pelimag.png");

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);
        setBounds(100, 100, 450, 550);
        
        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.NORTH);
        
        JLabel labelTit = new JLabel("ELIGE TU PELICULA FAVORITA");
        panel.add(labelTit);
        contentPane.setLayout(new BorderLayout());
		
	
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
	   
	



