package proyectoDeustoCine;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class VentanaPago extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtTelefono;
	private VentanaSala parent3;
	private JTextField txtCorreo;
	private JTextField txtNumTarjeta;
	private JTextField txtCvv;
	private JTextField txtFecCad;
	private VentanaPrincipal ventanaPrincipal;
	private JButton btnPagar;

	private String datosQR;
	/**
	 * Create the frame.
	 */
	public VentanaPago(VentanaSala parent3, VentanaPrincipal ventanaPrincipal, String datosQR) {
		 this.ventanaPrincipal = ventanaPrincipal;
		 this.parent3 = parent3;
		 this.datosQR=datosQR;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 732, 527);
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		JPanel contentPane = crearPanelConFondo("imagenes/pagojpg.jpg");
		contentPane.setBorder(new EmptyBorder(100, 200, 100, 200));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(100, 100));
		
		JLabel lblNewLabel = new JLabel("REALIZAR EL PAGO\r\n");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		panelSur.setOpaque(false);
		
		btnPagar = new JButton("Pagar");
		btnPagar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnPagar.setEnabled(false);
		btnPagar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int respuesta = JOptionPane.showConfirmDialog(
		                parent3,
		                "PAGO REALIZADO CON EXITO",
		                "Confirmación",
		                JOptionPane.OK_CANCEL_OPTION
		        );
		        if (respuesta == JOptionPane.OK_OPTION) {
		            VentanaPago.this.setVisible(false);
		            VentanaTicket miNuevaVent = new VentanaTicket(VentanaPago.this, ventanaPrincipal, datosQR);
		            miNuevaVent.setVisible(true);
		        }
		    }
		});
		panelSur.add(btnPagar);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        VentanaPago.this.dispose();
		        ventanaPrincipal.setVisible(true);
			}
		});
		panelSur.add(btnCerrar);
		
		JPanel panelCentral = new JPanel();
		contentPane.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new GridLayout(7, 2, 20, 20));
		panelCentral.setOpaque(false);
		
		JLabel labelNombre = new JLabel("NOMBRE");
		labelNombre.setForeground(new Color(255, 255, 255));
		labelNombre.setHorizontalAlignment(SwingConstants.CENTER);
		panelCentral.add(labelNombre);
		
		txtNombre = new JTextField();
		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnPagar.setEnabled(comprobarTextos());
			}
		});
		panelCentral.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel labelApellidos = new JLabel("APELLIDOS");
		labelApellidos.setForeground(new Color(255, 255, 255));
		labelApellidos.setHorizontalAlignment(SwingConstants.CENTER);
		panelCentral.add(labelApellidos);
		
		txtApellidos = new JTextField();
		txtApellidos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnPagar.setEnabled(comprobarTextos());
			}
		});
		panelCentral.add(txtApellidos);
		txtApellidos.setColumns(10);
		
		JLabel labelTelefono = new JLabel("TELEFONO");
		labelTelefono.setForeground(new Color(255, 255, 255));
		labelTelefono.setHorizontalAlignment(SwingConstants.CENTER);
		panelCentral.add(labelTelefono);
		
		txtTelefono = new JTextField();
		txtTelefono.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnPagar.setEnabled(comprobarTextos());
			}
		});
		panelCentral.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		JLabel labelCorreo = new JLabel("CORREO ELECTRONICO");
		labelCorreo.setForeground(new Color(255, 255, 255));
		labelCorreo.setHorizontalAlignment(SwingConstants.CENTER);
		panelCentral.add(labelCorreo);
		
		txtCorreo = new JTextField();
		txtCorreo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnPagar.setEnabled(comprobarTextos());
			}
		});
		panelCentral.add(txtCorreo);
		txtCorreo.setColumns(10);
		
		JLabel labelTarjeta = new JLabel("Nº TARJETA DE CREDITO ");
		labelTarjeta.setForeground(new Color(255, 255, 255));
		labelTarjeta.setHorizontalAlignment(SwingConstants.CENTER);
		panelCentral.add(labelTarjeta);
		
		txtNumTarjeta = new JTextField();
		txtNumTarjeta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnPagar.setEnabled(comprobarTextos());
			}
		});
		panelCentral.add(txtNumTarjeta);
		txtNumTarjeta.setColumns(10);
		
		JLabel labelCvv = new JLabel("CVV");
		labelCvv.setForeground(new Color(255, 255, 255));
		labelCvv.setHorizontalAlignment(SwingConstants.CENTER);
		panelCentral.add(labelCvv);
		
		txtCvv = new JTextField();
		txtCvv.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnPagar.setEnabled(comprobarTextos());
			}
		});
		panelCentral.add(txtCvv);
		txtCvv.setColumns(10);
		
		JLabel labelFechaCaducidad = new JLabel("FECHA DE CADUCIDAD");
		labelFechaCaducidad.setForeground(new Color(255, 255, 255));
		labelFechaCaducidad.setHorizontalAlignment(SwingConstants.CENTER);
		panelCentral.add(labelFechaCaducidad);
		
		txtFecCad = new JTextField();
		txtFecCad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnPagar.setEnabled(comprobarTextos());
			}
		});
		panelCentral.add(txtFecCad);
		txtFecCad.setColumns(10);
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
	


	private boolean comprobarTextos() {
		
		String nombre = txtNombre.getText();
		String apellidos = txtApellidos.getText();
		String correo = txtCorreo.getText();
		String telefono = txtTelefono.getText();
		String numTarjeta = txtNumTarjeta.getText();
		String cvv = txtCvv.getText();
		String fecCad = txtFecCad.getText();
		
		if(nombre.isEmpty()||apellidos.isEmpty()||correo.isEmpty()||telefono.isEmpty()||numTarjeta.isEmpty()||cvv.isEmpty()||fecCad.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
}





