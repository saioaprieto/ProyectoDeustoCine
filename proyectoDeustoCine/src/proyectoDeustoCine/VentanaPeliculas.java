

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import proyectoDeustoCine.VentanaPrincipal.FondoPanel;
import src.proyectoDeustoCine.VentanaPrincipal;

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

        contentPane = new FondoPanel("C://Users//saioa.prieto//git//ProyectoDeustoCine//proyectoDeustoCine//src////imagenCineee.png");
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);
        setBounds(100, 100, 450, 300);
        contentPane.setLayout(new BorderLayout());
		
		JLabel labelTitulo = new JLabel("¡ELIGE TU PELICULA!\r\n");
		labelTitulo.setFont(new Font("Perpetua Titling MT", Font.PLAIN, 12));
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(labelTitulo, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
	}
	
	   
	}

}

