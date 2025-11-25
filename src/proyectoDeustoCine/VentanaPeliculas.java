package proyectoDeustoCine;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import proyectoDeustoCine.VentanaPrincipal;
import proyectoDeustoCine.VentanaPrincipal.FondoPanel;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;


public class VentanaPeliculas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VentanaPrincipal parent; 

	
	/**
	 * Create the frame.
	 */
	public VentanaPeliculas(VentanaPrincipal parent) {
		this.parent= parent; 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //  setExtendedState(JFrame.MAXIMIZED_BOTH);

		contentPane = new FondoPanel("imagenes/Pelimag.png");

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
	class FondoPanel extends JPanel {
	    private Image imagen;

	    public FondoPanel(String ruta) {
	        imagen = new ImageIcon(getClass().getClassLoader().getResource(ruta)).getImage();
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
	    }
	}

}
	   
	



