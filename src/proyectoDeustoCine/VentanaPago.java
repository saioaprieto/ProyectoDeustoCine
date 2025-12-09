package proyectoDeustoCine;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
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
  private MouseListener oscurecerBtnPagar;
 
  public VentanaPago(VentanaSala parent3, VentanaPrincipal ventanaPrincipal, String datosQR) {
      this.ventanaPrincipal = ventanaPrincipal;
      this.parent3 = parent3;
      this.datosQR = datosQR;
     
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 732, 527);
      setExtendedState(JFrame.MAXIMIZED_BOTH);
      contentPane = crearPanelConFondo("imagenes/pagojpg.jpg");
      contentPane.setBorder(new EmptyBorder(100, 100, 100, 100));
      contentPane.setLayout(new BorderLayout(100, 100));
      setContentPane(contentPane);
     
      JLabel lblNewLabel = new JLabel("REALIZAR EL PAGO");
      lblNewLabel.setForeground(Color.WHITE);
      lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
      lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
      contentPane.add(lblNewLabel, BorderLayout.NORTH);
     
      JPanel panelSur = new JPanel();
      panelSur.setOpaque(false);
      contentPane.add(panelSur, BorderLayout.SOUTH);
     
      btnPagar = new JButton("Pagar");
      btnPagar.setFont(new Font("Tahoma", Font.PLAIN, 16));
      btnPagar.setEnabled(false);
	   btnPagar.removeMouseListener(oscurecerBtnPagar);
      btnPagar.setBackground(new Color(240,240,240));
      btnPagar.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              if (!comprobarTextos()) return;
              JOptionPane.showMessageDialog(
                      parent3,
                      "PAGO REALIZADO CON ÉXITO"
              );
              VentanaPago.this.setVisible(false);
              VentanaTicket miNuevaVent = new VentanaTicket(VentanaPago.this, ventanaPrincipal, datosQR);
              miNuevaVent.setVisible(true);
          }
      });
      oscurecerBtnPagar = new MouseAdapter() {
 	        @Override
 	        public void mouseEntered(MouseEvent e) {
 	            btnPagar.setBackground(new Color(200, 200, 190));
 	        }
 	        @Override
 	        public void mouseExited(MouseEvent e) {
 	            btnPagar.setBackground(new Color(240,240,240));
 	        }
 	    };
      panelSur.add(btnPagar);
     
      JButton btnCerrar = new JButton("Cerrar");
      btnCerrar.setFont(new Font("Tahoma", Font.PLAIN, 16));
      btnCerrar.setBackground(new Color(240,240,240));
      btnCerrar.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              VentanaPago.this.dispose();
              ventanaPrincipal.setVisible(true);
          }
      });
      btnCerrar.addMouseListener(new MouseAdapter() {
 	        @Override
 	        public void mouseEntered(MouseEvent e) {
 	            btnCerrar.setBackground(new Color(200, 200, 190));
 	        }
 	        @Override
 	        public void mouseExited(MouseEvent e) {
 	            btnCerrar.setBackground(new Color(240,240,240));
 	        }
 	    });
      panelSur.add(btnCerrar);
     
      JPanel panelCentral = new JPanel();
      panelCentral.setLayout(new GridLayout(7, 2, 50, 20));
      panelCentral.setOpaque(false);
      contentPane.add(panelCentral, BorderLayout.CENTER);
      //hau jarriet para que funcione el requestFocus, ze bestela etzian iten
      txtNombre = new JTextField();
      txtApellidos = new JTextField();
      txtTelefono = new JTextField();
      txtCorreo = new JTextField();
      txtNumTarjeta = new JTextField();
      txtCvv = new JTextField();
      txtFecCad = new JTextField();
     
      agregarCampo(panelCentral, "NOMBRE", txtNombre, txtApellidos);
      agregarCampo(panelCentral, "APELLIDOS", txtApellidos, txtTelefono);
      agregarCampo(panelCentral, "TELEFONO", txtTelefono, txtCorreo);
      agregarCampo(panelCentral, "CORREO ELECTRONICO", txtCorreo, txtNumTarjeta);
      agregarCampo(panelCentral, "Nº TARJETA DE CREDITO", txtNumTarjeta, txtCvv);
      agregarCampo(panelCentral, "CVV", txtCvv, txtFecCad);
      agregarCampo(panelCentral, "FECHA DE CADUCIDAD", txtFecCad, null);
  }
 
  public void agregarCampo(JPanel panel, String labelTexto, JTextField campo, JTextField siguiente) {
      JLabel label = new JLabel(labelTexto);
      label.setForeground(Color.WHITE);
      label.setHorizontalAlignment(SwingConstants.RIGHT);
      panel.add(label);
      campo.setColumns(20);    
      campo.setPreferredSize(new Dimension(600, 30));
      JPanel contenedor = new JPanel(new FlowLayout(FlowLayout.LEFT));
      contenedor.setOpaque(false);
      contenedor.add(campo);      
      campo.addActionListener(evt -> {
          if (siguiente != null) {
              siguiente.requestFocus();
          } else {
              btnPagar.doClick();
          }
      });
      campo.addKeyListener(new KeyAdapter() {
          @Override
          public void keyReleased(KeyEvent e) {
              btnPagar.setEnabled(camposLlenos());
              btnPagar.addMouseListener(oscurecerBtnPagar);
          }
      });
      panel.add(contenedor);
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
 
  public boolean comprobarTextos() {
      String nombre = txtNombre.getText();
      String apellidos = txtApellidos.getText();
      String correo = txtCorreo.getText();
      String telefono = txtTelefono.getText();
      String tarjeta = txtNumTarjeta.getText();
      String cvv = txtCvv.getText();
      String fecha = txtFecCad.getText();
      if (nombre.isEmpty()) {
          JOptionPane.showMessageDialog(parent3, "Escribe un nombre.");
          return false;
      }
      if (apellidos.isEmpty()) {
          JOptionPane.showMessageDialog(parent3, "Escribe los apellidos.");
          return false;
      }
      try {
          Integer.parseInt(telefono);
          if (telefono.length() != 9) {
              JOptionPane.showMessageDialog(parent3, "El teléfono debe tener 9 números.");
              return false;
          }
      } catch (Exception e) {
          JOptionPane.showMessageDialog(parent3, "El teléfono debe ser numérico.");
          return false;
      }
      if (!correo.contains("@") || !correo.contains(".")) {
          JOptionPane.showMessageDialog(parent3, "Correo incorrecto.");
          return false;
      }
      try {
          Long.parseLong(tarjeta);
          if (tarjeta.length() != 16) {
              JOptionPane.showMessageDialog(parent3, "La tarjeta debe tener 16 números.");
              return false;
          }
      } catch (Exception e) {
          JOptionPane.showMessageDialog(parent3, "La tarjeta debe ser numérica.");
          return false;
      }
      try {
          Integer.parseInt(cvv);
          if (cvv.length() != 3) {
              JOptionPane.showMessageDialog(parent3, "El CVV debe tener 3 números.");
              return false;
          }
      } catch (Exception e) {
          JOptionPane.showMessageDialog(parent3, "El CVV debe ser numérico.");
          return false;
      }
      if (fecha.length() != 5 || fecha.charAt(2) != '/') {
          JOptionPane.showMessageDialog(parent3, "La fecha debe tener formato MM/AA.");
          return false;
      }
      return true;
  }
 
  public boolean camposLlenos() {
      return !txtNombre.getText().isEmpty()
              && !txtApellidos.getText().isEmpty()
              && !txtTelefono.getText().isEmpty()
              && !txtCorreo.getText().isEmpty()
              && !txtNumTarjeta.getText().isEmpty()
              && !txtCvv.getText().isEmpty()
              && !txtFecCad.getText().isEmpty();
  }
}



