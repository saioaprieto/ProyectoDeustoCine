package proyectoDeustoCine;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class VentanaTicket extends JFrame {
   private static final long serialVersionUID = 1L;
   private JPanel contentPane;
   private VentanaPago parent;
   private VentanaPrincipal ventPrinc;
   private String datosQR;
   public VentanaTicket(VentanaPago parent, VentanaPrincipal ventPrin, String datosQR) {
       this.parent = parent;
       this.ventPrinc = ventPrin;
       this.datosQR = datosQR;
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setBounds(100, 100, 613, 434);
       setExtendedState(JFrame.MAXIMIZED_BOTH);
       // FONDO
       JPanel contentPane = crearPanelConFondo("src/imagenes/dibujoTicket.jpg");
       contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
       setContentPane(contentPane);
       contentPane.setLayout(new BorderLayout(0, 0));
       // CABECERA
       JPanel panel_1 = new JPanel();
       panel_1.setOpaque(false);
       contentPane.add(panel_1, BorderLayout.NORTH);
       panel_1.setLayout(new GridLayout(0, 1, 10, 10));
       JLabel labelTitulo = new JLabel("¡ GRACIAS POR TU COMPRA!");
       labelTitulo.setFont(new Font("Tahoma", Font.PLAIN, 18));
       labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
       panel_1.add(labelTitulo);
       JLabel labelQR = new JLabel("Enseña este código QR en taquilla para acceder al cine y disfrutar de su película");
       labelQR.setFont(new Font("Tahoma", Font.PLAIN, 13));
       labelQR.setForeground(new Color(0, 0, 0));
       labelQR.setHorizontalAlignment(SwingConstants.CENTER);
       panel_1.add(labelQR);
       // BOTÓN CERRAR
       JPanel panelSur = new JPanel();
       panelSur.setOpaque(false);
       contentPane.add(panelSur, BorderLayout.SOUTH);
       JButton btnCerrar = new JButton("Cerrar");
       btnCerrar.setFont(new Font("Tahoma", Font.PLAIN, 16));
       btnCerrar.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               VentanaTicket.this.dispose();
               if (VentanaTicket.this.ventPrinc != null) {
                   VentanaTicket.this.ventPrinc.setVisible(true);
               }
           }
       });
       panelSur.add(btnCerrar);
       // CENTRO: TARJETA CON QR + TEXTO
       JPanel panelCentral = new JPanel(new GridBagLayout());
       panelCentral.setOpaque(false);
       contentPane.add(panelCentral, BorderLayout.CENTER);
       JPanel panelTicket = new JPanel(new BorderLayout());
       panelTicket.setBackground(new Color(255, 255, 255, 230)); // blanco casi opaco
       panelTicket.setBorder(new EmptyBorder(20, 20, 20, 20));
       panelTicket.setPreferredSize(new Dimension(450, 550));
       JLabel qrLabel = crearLabelQRDesdeDatos();
       qrLabel.setHorizontalAlignment(SwingConstants.CENTER);
       panelTicket.add(qrLabel, BorderLayout.CENTER);
       JTextArea areaTicket = new JTextArea(datosQR);
       areaTicket.setEditable(false);
       areaTicket.setOpaque(false);
       areaTicket.setFont(new Font("Monospaced", Font.PLAIN, 14));
       areaTicket.setForeground(Color.BLACK);
       areaTicket.setLineWrap(true);
       areaTicket.setWrapStyleWord(true);
       areaTicket.setBorder(new EmptyBorder(10, 0, 0, 0));
       panelTicket.add(areaTicket, BorderLayout.SOUTH);
       GridBagConstraints gbc = new GridBagConstraints();
       gbc.gridx = 0;
       gbc.gridy = 0;
       panelCentral.add(panelTicket, gbc);
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
   private JLabel crearLabelQRDesdeDatos() {
       int size = 300; 
       ImageIcon icon;
       try {
           java.awt.image.BufferedImage img = generarQR(datosQR, size);
           icon = new ImageIcon(img);
       } catch (Exception e) {
           e.printStackTrace();
           JLabel fallback = new JLabel("Error generando QR");
           fallback.setHorizontalAlignment(SwingConstants.CENTER);
           return fallback;
       }
       JLabel lblQR = new JLabel(icon);
       lblQR.setHorizontalAlignment(SwingConstants.CENTER);
       return lblQR;
   }
   private java.awt.image.BufferedImage generarQR(String texto, int size) throws WriterException {
       QRCodeWriter qrCodeWriter = new QRCodeWriter();
       BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, size, size);
       java.awt.image.BufferedImage image =
               new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_RGB);
       for (int x = 0; x < size; x++) {
           for (int y = 0; y < size; y++) {
               int color = bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF; // negro/blanco
               image.setRGB(x, y, color);
           }
       }
       return image;
   }
}



