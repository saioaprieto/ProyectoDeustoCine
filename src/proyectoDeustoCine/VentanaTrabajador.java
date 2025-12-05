package proyectoDeustoCine;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import dataBase.DataBase;
import dominio.Trabajador;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class VentanaTrabajador extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static VentanaPrincipal parent;
	private JTextField textdni;
	private JPasswordField contrasenya;
	private JButton btnAcceder;
	private DataBase bases;
	
	public VentanaTrabajador(VentanaPrincipal parent, DataBase bases) {
		this.parent = parent;
		
		if (bases == null) {
	        this.bases = new DataBase();
	    } else {
	        this.bases = bases;
	    }
		this.bases.inicializarBaseDatos();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
//		contentPane = new JPanel();
		contentPane = crearPanelConFondo("imagenes/fondoPeliculas.jpg");
		contentPane.setBorder(new EmptyBorder(100, 200, 100, 200));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(150, 150));
		
		JPanel panelSuperior = new JPanel();
		contentPane.add(panelSuperior, BorderLayout.NORTH);
		
		JLabel lblIntroducir = new JLabel("INTRODUZCA SUS DATOS");
		lblIntroducir.setForeground(new Color(255, 255, 255));
		lblIntroducir.setFont(new Font("Verdana", Font.PLAIN, 16));
		panelSuperior.add(lblIntroducir);
		panelSuperior.setOpaque(false);
		
		JPanel panelInferior = new JPanel();
		contentPane.add(panelInferior, BorderLayout.SOUTH);
		
		btnAcceder = new JButton("Acceder") ;
		btnAcceder.addActionListener(new ActionListener() {
		@Override
		    public void actionPerformed(ActionEvent e) {
		       String dni = textdni.getText();
		       String contrasena = new String(contrasenya.getPassword());
		       String[] datos = bases.loginTrabajador(dni, contrasena);
		       if (datos == null) {
		          JOptionPane.showMessageDialog(null, "DNI o contraseña incorrectos.");
		          textdni.setText("");
		          contrasenya.setText("");
		            return;
		        }
		       String nombre = datos[0];
		       String tipo = datos[1];
		       if (tipo.equalsIgnoreCase("Supervisor")) {
		          JOptionPane.showMessageDialog(null, "Bienvenido, supervisor " + nombre);
		           new VentanaSupervisor(VentanaTrabajador.this, nombre,VentanaTrabajador.this.bases).setVisible(true);
		           textdni.setText("");
		           contrasenya.setText("");
		       }else {
		    	  JOptionPane.showMessageDialog(null, "Bienvenido, empleado " + nombre);
		    	  VentanaSupervisor vs = new VentanaSupervisor(VentanaTrabajador.this, nombre, VentanaTrabajador.this.bases);
		    	  VentanaEmpleado vempl = new VentanaEmpleado(VentanaTrabajador.this, nombre,dni, vs);
		    	  vempl.setVisible(true);


		           textdni.setText("");
		           contrasenya.setText("");
		        }
		        dispose();
		    }
	
		});
		btnAcceder.setEnabled(false);
		btnAcceder.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelInferior.add(btnAcceder);
		panelInferior.setOpaque(false);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			VentanaTrabajador.this.setVisible(false);
			VentanaTrabajador.this.parent.setVisible(true);
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelInferior.add(btnCancelar);
		
		JPanel panelCentral = new JPanel();
		contentPane.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new GridLayout(2, 2, 150, 150));
		panelCentral.setOpaque(false);
		
		JLabel lblUsuario = new JLabel("DNI: ");
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setBackground(new Color(240, 240, 240));
		lblUsuario.setFont(new Font("Verdana", Font.PLAIN, 16));
		lblUsuario.setOpaque(false);
		panelCentral.add(lblUsuario);
		
		textdni = new JTextField();
		textdni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			contrasenya.requestFocus();
			}
		});
		textdni.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String usuario = textdni.getText();
				String password = String.copyValueOf(contrasenya.getPassword());
				if(usuario.equals("")||password.equals("")) {
					btnAcceder.setEnabled(false);
				}else {
					btnAcceder.setEnabled(true);
				}
			}
		});
		textdni.setFont(new Font("Verdana", Font.PLAIN, 16));
		panelCentral.add(textdni);
		textdni.setColumns(10);
		
		JLabel lblContrasenya = new JLabel("Contraseña: ");
		lblContrasenya.setForeground(new Color(255, 255, 255));
		lblContrasenya.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasenya.setFont(new Font("Verdana", Font.PLAIN, 16));
		lblContrasenya.setOpaque(false);
		panelCentral.add(lblContrasenya);
		
		contrasenya = new JPasswordField();
		contrasenya.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent evt) {
		        btnAcceder.doClick();
		    }
		});


		contrasenya.addKeyListener(new KeyAdapter() {
		@Override
		public void keyTyped(KeyEvent e) {
			String usuario = textdni.getText();
			String password = String.copyValueOf(contrasenya.getPassword());
			if(usuario.equals("")||password.equals("")) {
				btnAcceder.setEnabled(false);
			}else {
				btnAcceder.setEnabled(true);
			}
		}
		});
		contrasenya.setFont(new Font("Verdana", Font.PLAIN, 16));
		panelCentral.add(contrasenya);
	}
	public JButton getBtnAcceder() {
		return btnAcceder;
	}
	public void setBtnAcceder(JButton btnAcceder) {
		this.btnAcceder = btnAcceder;
	}
	public JTextField getTextdni() {
		return textdni;
	}
	public void setTextdni(JTextField textdni) {
		this.textdni = textdni;
	}
	public JPasswordField getContrasenya() {
		return contrasenya;
	}
	public void setContrasenya(JPasswordField contrasenya) {
		this.contrasenya = contrasenya;
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





























