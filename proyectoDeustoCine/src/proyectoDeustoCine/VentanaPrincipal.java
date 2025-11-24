package src.proyectoDeustoCine;

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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

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
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        contentPane = new FondoPanel("src/imagenes/Peliculas.jpg");
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);
		
        contentPane.setLayout(new BorderLayout());

		JPanel panel = new JPanel(new GridBagLayout());
		
		//no quiero que se vea el panel para poder visualizar el fondo de contentPane
		panel.setOpaque(false);
		
		//necesito el tamaño de la pantalla para saber de que tamaño hacer los botones y el espacio entre ellos
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        
//—-->He cambiado las dimensiones de los botones.
        int buttonWidth = screenWidth / 6;  
        int buttonHeight = screenHeight / 10;
        
        int espacioHorizontal = screenWidth / 15;  
        int espacioVertical = screenHeight / 30;
        


        
       int fontSize = Math.max(16, screenWidth / 80);
       Font fuente = new Font("Impact", Font.PLAIN, fontSize);
      
       JButton btnSoyCliente = new JButton("SOY CLIENTE");
       btnSoyCliente.setFocusPainted(false);  
       btnSoyCliente.addActionListener(new ActionListener() {
       	public void actionPerformed(ActionEvent e) {
       		VentanaPrincipal.this.setVisible(false);
       		VentanaPeliculas mivent1 = new VentanaPeliculas(VentanaPrincipal.this);
       		mivent1.setVisible(true);
			
				
       	}
       });
      
       btnSoyCliente.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
       GridBagConstraints gbc1 = new GridBagConstraints();
       gbc1.insets = new Insets(espacioVertical*6, espacioHorizontal, espacioVertical, espacioHorizontal);
       gbc1.gridx = 0;
       gbc1.gridy = 0;
       panel.add(btnSoyCliente, gbc1);
      
       btnSoyCliente.setFont(new Font("Goudy Old Style", Font.PLAIN, 19));
      
       
        
        //quiero el fondo del boton beige
        btnSoyCliente.setBackground(new Color(245, 245, 240));
        //quiero la letra azul marino
        btnSoyCliente.setForeground(new Color(0,0,64));


        
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
        btnSoyTrabajador.addActionListener(new ActionListener() {
       	public void actionPerformed(ActionEvent e) {
       		VentanaPrincipal.this.setVisible(false);
       		VentanaTrabajador ventanaEmpleado = new VentanaTrabajador(VentanaPrincipal.this);
       		ventanaEmpleado.setVisible(true);
       	}
       });

        

       
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(espacioVertical*6, espacioHorizontal, espacioVertical, espacioHorizontal);
       
        
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        
        panel.add(btnSoyTrabajador, gbc2);
        Font fuenteBotones = new Font("Impact", Font.PLAIN, fontSize);
        btnSoyCliente.setFont(fuenteBotones);
        //aqui he cambiado la dimension del boton de tabajador para igualarlo al de cliente
        btnSoyTrabajador.setFont(new Font("Goudy Old Style", Font.PLAIN, 19));

        //con esto se iguala el tamaño del boton de trabajador
        btnSoyTrabajador.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        
//un cambio
        btnSoyTrabajador.setFont(fuente);
        btnSoyTrabajador.setForeground(new Color(0,0,64));
 
        btnSoyTrabajador.setBackground(new Color(245, 245, 240));

        btnSoyTrabajador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSoyTrabajador.setBackground(new Color(200, 200, 190)); 
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSoyTrabajador.setBackground(new Color(245, 245, 240));
            }
        });
        
		contentPane.add(panel, BorderLayout.CENTER);
		
		setVisible(true);

	}
	class FondoPanel extends JPanel {
	    private Image imagen;

	    public FondoPanel(String ruta) {
	        imagen = new ImageIcon(ruta).getImage();
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
	    }
	   
	}
	
}