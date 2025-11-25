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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
    private Image imagen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
       
		JPanel panelVertical = new JPanel();
		panelVertical.setOpaque(false);
		panelVertical.setLayout(new BoxLayout(panelVertical, BoxLayout.Y_AXIS));
        
       contentPane.add(panelVertical, BorderLayout.CENTER);
		
       panelVertical.add(Box.createVerticalGlue());
       panelVertical.add(Box.createRigidArea(new Dimension(0, 120)));
       JPanel panelHorizontal = new JPanel();
       panelHorizontal.setOpaque(false);
       panelHorizontal.setLayout(new BoxLayout(panelHorizontal, BoxLayout.X_AXIS));
        
       int anchuraBoton = anchuraPantalla / 6;  
       int alturaBoton = alturaPantalla / 10;
               
       JButton btnSoyCliente = new JButton("SOY CLIENTE");
       btnSoyCliente.setFocusPainted(false);
       btnSoyCliente.setBackground(new Color(245,245,240));
	   btnSoyCliente.setForeground(new Color(0,0,64));
	   btnSoyCliente.setPreferredSize(new Dimension(anchuraBoton, alturaBoton));
	   btnSoyCliente.setMaximumSize(new Dimension(anchuraBoton, alturaBoton));
	   btnSoyCliente.setMinimumSize(new Dimension(anchuraBoton, alturaBoton));
	   btnSoyCliente.addActionListener(new ActionListener() {
       	public void actionPerformed(ActionEvent e) {
       		VentanaPrincipal.this.setVisible(false);
       		VentanaPeliculas mivent1 = new VentanaPeliculas(VentanaPrincipal.this);
       		mivent1.setVisible(true);
			
				
       	}
       });
      
      btnSoyCliente.setFont(new Font("Goudy Old Style", Font.PLAIN, 19));
      btnSoyCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSoyCliente.setBackground(new Color(200, 200, 190)); 
                // más oscuro al pasar el ratón
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSoyCliente.setBackground(new Color(245, 245, 240));
            }
        });
     
        
        JButton btnSoyTrabajador = new JButton("SOY TRABAJADOR");
        btnSoyTrabajador.setFocusPainted(false); 
        btnSoyTrabajador.setPreferredSize(new Dimension(anchuraBoton, alturaBoton));
 	    btnSoyTrabajador.setMaximumSize(new Dimension(anchuraBoton, alturaBoton));
 	    btnSoyTrabajador.setMinimumSize(new Dimension(anchuraBoton, alturaBoton));        
 	    btnSoyTrabajador.setBackground(new Color(245,245,240));
	    btnSoyTrabajador.setForeground(new Color(0,0,64));
        btnSoyTrabajador.addActionListener(new ActionListener() {
       	public void actionPerformed(ActionEvent e) {
       		VentanaPrincipal.this.setVisible(false);
       		VentanaTrabajador ventanaTrabajador = new VentanaTrabajador(VentanaPrincipal.this);
       		ventanaTrabajador.setVisible(true);
       	}
       });

      btnSoyTrabajador.setFont(new Font("Goudy Old Style", Font.PLAIN, 19));

      btnSoyTrabajador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSoyTrabajador.setBackground(new Color(200, 200, 190)); 
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSoyTrabajador.setBackground(new Color(245, 245, 240));
            }
        });
        
        panelHorizontal.add(Box.createHorizontalGlue()); // empuja al centro desde la izquierda
	    panelHorizontal.add(btnSoyCliente);
	    panelHorizontal.add(Box.createHorizontalStrut(100)); // espacio entre botones
	    panelHorizontal.add(btnSoyTrabajador);
	    panelHorizontal.add(Box.createHorizontalGlue()); // empuja al centro desde la derecha
		panelVertical.add(panelHorizontal);
		panelVertical.add(Box.createVerticalGlue());
		
		setVisible(true);

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