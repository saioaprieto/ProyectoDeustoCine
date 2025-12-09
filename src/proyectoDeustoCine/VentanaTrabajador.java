package proyectoDeustoCine;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import dataBase.DataBase;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JCheckBox;
public class VentanaTrabajador extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static VentanaPrincipal parent;
	private JTextField textdni;
	private JPasswordField contrasenya;
	private JButton btnAcceder;
	private DataBase bases;
	private JCheckBox chkMostrar;
	private MouseListener oscurecerBtnAcceder;
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
		contentPane = new JPanel();
		JPanel contentPaneFondo = crearPanelConFondo("imagenes/fondoPeliculas.jpg");
		contentPaneFondo.setBorder(new EmptyBorder(100, 200, 100, 200));
		setContentPane(contentPaneFondo);
		contentPaneFondo.setLayout(new BorderLayout(150, 150));
		JPanel panelSuperior = new JPanel();
		contentPaneFondo.add(panelSuperior, BorderLayout.NORTH);
		JLabel lblIntroducir = new JLabel("INTRODUZCA SUS DATOS");
		lblIntroducir.setForeground(new Color(255, 255, 255));
		lblIntroducir.setFont(new Font("Verdana", Font.BOLD, 28));
		panelSuperior.add(lblIntroducir);
		panelSuperior.setOpaque(false);
		JPanel panelInferior = new JPanel();
		contentPaneFondo.add(panelInferior, BorderLayout.SOUTH);
		panelInferior.setOpaque(false);
		btnAcceder = new JButton("Acceder");
		btnAcceder.setFont(new Font("Verdana", Font.PLAIN, 24));
		btnAcceder.setEnabled(false);
		btnAcceder.removeMouseListener(oscurecerBtnAcceder);
		btnAcceder.setBackground(new Color(240,240,240));
		oscurecerBtnAcceder = new MouseAdapter() {
	   	        @Override
	   	        public void mouseEntered(MouseEvent e) {
		   	            btnAcceder.setBackground(new Color(200, 200, 190));
	   	        }
	   	        @Override
	   	        public void mouseExited(MouseEvent e) {
		   	            btnAcceder.setBackground(new Color(240,240,240));	   	        	
	   	        }
	   	    };
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
					btnAcceder.setEnabled(false);
					btnAcceder.removeMouseListener(oscurecerBtnAcceder);
					return;
				}
				String nombre = datos[0];
				String tipo = datos[1];
				
				if (tipo.equalsIgnoreCase("Supervisor")) {
					JOptionPane.showMessageDialog(null, "Bienvenido, supervisor " + nombre);
					new VentanaSupervisor(VentanaTrabajador.this, nombre, VentanaTrabajador.this.bases).setVisible(true);
					textdni.setText("");
					contrasenya.setText("");
					btnAcceder.setEnabled(false);
					btnAcceder.removeMouseListener(oscurecerBtnAcceder);
				} else {
					JOptionPane.showMessageDialog(null, "Bienvenido, empleado " + nombre);
					VentanaSupervisor supBase = new VentanaSupervisor(VentanaTrabajador.this, nombre,
							VentanaTrabajador.this.bases);
					new VentanaEmpleado(VentanaTrabajador.this, nombre,dni, supBase).setVisible(true);
					textdni.setText("");
					contrasenya.setText("");
					btnAcceder.setEnabled(false);
					btnAcceder.removeMouseListener(oscurecerBtnAcceder);
				}
				dispose();
			}
		});
		panelInferior.add(btnAcceder);
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Verdana", Font.PLAIN, 24));
		btnCancelar.setBackground(new Color(240,240,240));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaTrabajador.this.setVisible(false);
				VentanaTrabajador.this.parent.setVisible(true);
			}
		});
		btnCancelar.addMouseListener(new MouseAdapter() {
  	        @Override
  	        public void mouseEntered(MouseEvent e) {
  	            btnCancelar.setBackground(new Color(200, 200, 190));
  	        }
  	        @Override
  	        public void mouseExited(MouseEvent e) {
  	            btnCancelar.setBackground(new Color(240,240,240));
  	        }
  	    });
		panelInferior.add(btnCancelar);
		JPanel panelCentral = new JPanel();
		panelCentral.setOpaque(false);
		panelCentral.setLayout(new GridLayout(3, 2, 50, 50));
		contentPaneFondo.add(panelCentral, BorderLayout.CENTER);
		JLabel lblUsuario = new JLabel("DNI: ");
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setFont(new Font("Verdana", Font.BOLD, 28));
		panelCentral.add(lblUsuario);
		textdni = new JTextField();
		textdni.setFont(new Font("Verdana", Font.PLAIN, 28));
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
				btnAcceder.setEnabled(!usuario.equals("") && !password.equals(""));
				btnAcceder.addMouseListener(oscurecerBtnAcceder);
			}
		});
		panelCentral.add(textdni);
		JLabel lblContrasenya = new JLabel("Contraseña: ");
		lblContrasenya.setForeground(Color.WHITE);
		lblContrasenya.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasenya.setFont(new Font("Verdana", Font.BOLD, 28));
		panelCentral.add(lblContrasenya);
		contrasenya = new JPasswordField();
		contrasenya.setFont(new Font("Verdana", Font.PLAIN, 28));
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
				btnAcceder.setEnabled(!usuario.equals("") && !password.equals(""));
				btnAcceder.addMouseListener(oscurecerBtnAcceder);
			}
		});
		panelCentral.add(contrasenya);
		chkMostrar = new JCheckBox("Mostrar contraseña");
		chkMostrar.setForeground(Color.WHITE);
		chkMostrar.setFont(new Font("Verdana", Font.BOLD, 24));
		chkMostrar.setOpaque(false);
		chkMostrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chkMostrar.isSelected()) {
					contrasenya.setEchoChar((char) 0);
				} else {
					contrasenya.setEchoChar('*');
				}
			}
		});
		panelCentral.add(new JLabel("Opciones:") {{
			setForeground(Color.WHITE);
			setFont(new Font("Verdana", Font.BOLD, 28));
			setHorizontalAlignment(SwingConstants.CENTER);
		}});
		panelCentral.add(chkMostrar);
		
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


