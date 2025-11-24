package proyectoDeustoCine;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentanaTrabajador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static VentanaPrincipal parent; 
	private JTextField textdni;
	private JPasswordField contrasenya;
	private JButton btnAcceder;

	public static void main(String[] args) {
		EventQueue eventQueue = new EventQueue();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaTrabajador frame = new VentanaTrabajador(parent);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public VentanaTrabajador(VentanaPrincipal parent) {
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(100, 200, 100, 200));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(150, 150));
		
		JPanel panelSuperior = new JPanel();
		contentPane.add(panelSuperior, BorderLayout.NORTH);
		
		JLabel lblIntroducir = new JLabel("INTRODUZCA SUS DATOS");
		lblIntroducir.setFont(new Font("Verdana", Font.PLAIN, 16));
		panelSuperior.add(lblIntroducir);
		
		JPanel panelInferior = new JPanel();
		contentPane.add(panelInferior, BorderLayout.SOUTH);
		
		btnAcceder = new JButton("Acceder");
		btnAcceder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 	String dni = textdni.getText();
			        String contrasena = new String(contrasenya.getPassword());
				   try {
			           
			            Conexion conexion = new Conexion();
			            conexion.connect();
			            Connection conn = conexion.getConnection();
			            if (conn == null) {
			                JOptionPane.showMessageDialog(null, "No se pudo establecer conexión con la base de datos.");
			                return;
			            }
			            String sql = "SELECT TIPO, NOMBRE FROM TRABAJADORES WHERE COD_DNI = ? AND CONTRASENA = ?";
			            PreparedStatement stmt = conn.prepareStatement(sql);
			            stmt.setString(1, dni);
			            stmt.setString(2, contrasena);
			            ResultSet rs = stmt.executeQuery();
			            if (rs.next()) {
			                String tipo = rs.getString("TIPO");
			                String nombre = rs.getString("NOMBRE");
			                if (tipo.equalsIgnoreCase("Supervisor")) {
			                    JOptionPane.showMessageDialog(null, "Bienvenido, supervisor " + nombre);
			                    VentanaSupervisor vSup = new VentanaSupervisor(VentanaTrabajador.this, nombre);
			                    vSup.setVisible(true);
			                    dispose();
			                } else if (tipo.equalsIgnoreCase("Empleado")) {
			                    JOptionPane.showMessageDialog(null, "Bienvenido, empleado " + nombre);
			                    VentanaEmpleado vEmp = new VentanaEmpleado(VentanaTrabajador.this);
			                    vEmp.setVisible(true);
			                    dispose();
			                }
			            } else {
			                JOptionPane.showMessageDialog(null, "DNI o contraseña incorrectos.");
			            }
			            rs.close();
			            stmt.close();
			            conexion.close();
			        } catch (SQLException ex) {
			            ex.printStackTrace();
			            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
			        }
			    }
			});
		btnAcceder.setEnabled(false);
		btnAcceder.setFont(new Font("Verdana", Font.PLAIN, 14));
		panelInferior.add(btnAcceder);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaTrabajador.this.setVisible(false);
				VentanaTrabajador.this.parent.setVisible(true);
			}
		});
		btnCancelar.setFont(new Font("Verdana", Font.PLAIN, 14));
		panelInferior.add(btnCancelar);
		
		JPanel panelCentral = new JPanel();
		contentPane.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new GridLayout(2, 2, 150, 150));
		
		JLabel lblUsuario = new JLabel("DNI: ");
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setBackground(new Color(240, 240, 240));
		lblUsuario.setFont(new Font("Verdana", Font.PLAIN, 16));
		panelCentral.add(lblUsuario);
		
		textdni = new JTextField();
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
		lblContrasenya.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasenya.setFont(new Font("Verdana", Font.PLAIN, 16));
		panelCentral.add(lblContrasenya);
		
		contrasenya = new JPasswordField();
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
}
