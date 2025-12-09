package proyectoDeustoCine;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class VentanaSala extends JFrame {
	
  private static final long serialVersionUID = 1L;
  private VentanaPrincipal parent;
  private String titulo;
  private LocalTime inicio;
  private int sala;
  private String claveSesion;
  private Set<String> butacasOcupadas = new HashSet<>();
  private JButton[][] botonesButacas;
  private int filas = 6;
  private int columnas = 8;
  private JSpinner spinnerEntradas;
  private Thread hiloCuentaAtras;
  private int segundosRestantes;
 
  public VentanaSala(VentanaPrincipal parent, String titulo, LocalTime inicio, int sala) {
      this.parent =parent;
      this.titulo = titulo;
      this.inicio = inicio;
      this.sala = sala;
      this.claveSesion = titulo + "_" + inicio + "_Sala" + sala;
      this.butacasOcupadas = parent.getDb().getButacasOcupadas(claveSesion);
     
     
      setTitle("Sala " + sala + " - " + titulo + " (" + inicio + ")");
      setSize(900, 700);
      setLocationRelativeTo(null);
      JPanel contentPane = crearPanelConFondo("imagenes/fondoPeliculas.jpg");
      setContentPane(contentPane);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setExtendedState(JFrame.MAXIMIZED_BOTH);
      BorderLayout bl_contentPane = new BorderLayout();
      bl_contentPane.setVgap(20);
      getContentPane().setLayout(bl_contentPane);
      contentPane.setBorder(new EmptyBorder(30, 5, 5, 5));
      JLabel lbl = new JLabel("Sala "+ sala +" | "+titulo+" | Inicio: "+ inicio,SwingConstants.CENTER);
      lbl.setFont(new Font("Arial", Font.BOLD, 22));
      lbl.setForeground(Color.WHITE);
      getContentPane().add(lbl, BorderLayout.NORTH);
     
      JPanel contenedor = new JPanel(new BorderLayout());
      contenedor.setOpaque(false);
      JPanel panelPantallaButacas = new JPanel(new BorderLayout());
      panelPantallaButacas.setBorder(new EmptyBorder(0,50,0,50));
      panelPantallaButacas.setOpaque(false);
     
      JLabel lblPantalla = new JLabel("PANTALLA", SwingConstants.CENTER);
      lblPantalla.setFont(new Font("Arial", Font.BOLD, 24));
      lblPantalla.setOpaque(true);
      lblPantalla.setBackground(new Color(0, 0, 0, 110));
      lblPantalla.setForeground(Color.WHITE);
      lblPantalla.setPreferredSize(new Dimension(0, 50));
      panelPantallaButacas.add(lblPantalla, BorderLayout.NORTH);
     
      botonesButacas = new JButton[filas][columnas];
      JPanel panelButacas = new JPanel(new GridLayout(filas, columnas, 5, 5));
      panelButacas.setBorder(new EmptyBorder(10, 0, 50, 0));
      panelButacas.setOpaque(false);
     
      ImageIcon imgButaca = new ImageIcon("imagenes/butaca.png");
      char filaLetra = 'A';
      for (int i = 0; i < filas; i++) {
          for (int j = 0; j < columnas; j++) {
              String codigo = "" + (char) (filaLetra + i) + (j + 1);
              JButton butaca = new JButton(codigo);
              Image imgEscalada = imgButaca.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
              butaca.setIcon(new ImageIcon(imgEscalada));
              butaca.setFocusPainted(false);
              butaca.setOpaque(true);
              butaca.setBackground(Color.GREEN);
              butaca.setForeground(Color.BLACK);
              butaca.addActionListener(new ActionListener() {
           	    public void actionPerformed(ActionEvent e) {
           	        seleccionarButaca(butaca);
           	    }
           	});
              botonesButacas[i][j] = butaca;
              panelButacas.add(butaca);
          }
      }
      for (int i = 0; i < filas; i++) {
          for (int j = 0; j < columnas; j++) {
              JButton butaca = botonesButacas[i][j];
              if (butacasOcupadas.contains(butaca.getText())) {
                  butaca.setBackground(Color.RED);
                  butaca.setEnabled(false);
              }
          }
      }
      panelPantallaButacas.add(panelButacas, BorderLayout.CENTER);
      contenedor.add(panelPantallaButacas, BorderLayout.CENTER);
      JPanel panelDerecha = new JPanel();
      panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));
      panelDerecha.setBorder(new javax.swing.border.EmptyBorder(10, 20, 10, 20));
      panelDerecha.setOpaque(false);
      JLabel lblTiempo = new JLabel("Tiempo restante: 02:00");
      lblTiempo.setFont(new Font("Arial", Font.BOLD, 16));
      lblTiempo.setForeground(Color.WHITE);
      panelDerecha.add(lblTiempo);
      int tiempoTotalSegundos = 120; // 2 mins
     
      hiloCuentaAtras = new Thread(new Runnable() {
    	    @Override
    	    public void run() {
    	        segundosRestantes = tiempoTotalSegundos;

    	        while (segundosRestantes >= 0) {
    	            String texto = String.format("Tiempo restante: %02d:%02d",segundosRestantes / 60,segundosRestantes % 60);
    	            
    	            SwingUtilities.invokeLater(new Runnable() {
    	                @Override
    	                public void run() {
    	                    lblTiempo.setText(texto);
    	                }
    	            });
    	            if (segundosRestantes == 0) {
    	                SwingUtilities.invokeLater(new Runnable() {
    	                    @Override
    	                    public void run() {
    	                        parent.setVisible(true);
    	                        setVisible(false); // cerrar ventana actual
    	                    }
    	                });
    	                break;
    	            }

    	            segundosRestantes--;

    	            try {
    	                Thread.sleep(1000);
    	            } catch (InterruptedException e) {
    	                break;
    	            }
    	        }
    	    }
    	});
    	hiloCuentaAtras.setDaemon(true);
    	hiloCuentaAtras.start();
    
      JLabel label = new JLabel("Cantidad de entradas:");
      label.setFont(new Font("Tahoma", Font.PLAIN, 16));
      label.setForeground(Color.WHITE);
      panelDerecha.add(label);
     
      spinnerEntradas = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
      spinnerEntradas.setAlignmentX(Component.LEFT_ALIGNMENT);
      spinnerEntradas.setPreferredSize(new Dimension(100, 50)); 
      spinnerEntradas.setMaximumSize(new Dimension(100, 50));
      spinnerEntradas.setMinimumSize(new Dimension(100, 50));
      spinnerEntradas.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseEntered(MouseEvent e) {
	            spinnerEntradas.setBackground(new Color(200, 200, 190));
	        }
	        @Override
	        public void mouseExited(MouseEvent e) {
	            spinnerEntradas.setBackground(new Color(245, 245, 240));
	        }
	    });
      panelDerecha.add(spinnerEntradas);
      panelDerecha.setOpaque(false);
      JLabel lblTotal = new JLabel("Total: ");
      lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
      lblTotal.setForeground(Color.WHITE);
      panelDerecha.add(lblTotal);
      int precio = 8;
      Thread hiloActualizarTotal = new Thread(new Runnable() {
    	    @Override
    	    public void run() {
    	        int ultimoTotal = -1;

    	        while (true) {
    	            try {
    	                Thread.sleep(100); 
    	            } catch (InterruptedException e) {
    	                break; 
    	            }

    	            int seleccionadas = 0;
    	            for (int i = 0; i < botonesButacas.length; i++) {
    	                for (int j = 0; j < botonesButacas[i].length; j++) {
    	                    JButton b = botonesButacas[i][j];
    	                    if (b.getBackground() == Color.YELLOW) {
    	                        seleccionadas++;
    	                    }
    	                }
    	            }

    	            int total = seleccionadas * precio;
    	            if (total != ultimoTotal) {
    	                ultimoTotal = total;
    	                int totalFinal = total; 
    	                SwingUtilities.invokeLater(new Runnable() {
    	                    @Override
    	                    public void run() {
    	                        lblTotal.setText("Total: " + totalFinal + " €");
    	                    }
    	                });
    	            }
    	        }
    	    }
    	});
    	hiloActualizarTotal.setDaemon(true);
    	hiloActualizarTotal.start();
      
      JButton btnConfirmar = new JButton("Confirmar selección");
      btnConfirmar.setBackground(new Color(240,240,240));
      btnConfirmar.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		confirmarButacas();
      	}
      });
      btnConfirmar.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseEntered(MouseEvent e) {
	            btnConfirmar.setBackground(new Color(200, 200, 190));
	        }
	        @Override
	        public void mouseExited(MouseEvent e) {
	            btnConfirmar.setBackground(new Color(240, 240, 240));
	        }
	    });
     
      btnConfirmar.setFont(new Font("Arial", Font.BOLD, 16));
      panelDerecha.add(btnConfirmar);
     
      JButton btnCancelar = new JButton("Cancelar");
      btnCancelar.setBackground(new Color(240,240,240));
      btnCancelar.addMouseListener(new MouseAdapter() {
      	@Override
      	public void mouseEntered(MouseEvent e) {
      		btnCancelar.setBackground(new Color(200, 200, 190));
      	}
      	
      	@Override
      	public void mouseExited(MouseEvent e) {
           btnCancelar.setBackground(new Color(240, 240, 240));
      	}
      });
      btnCancelar.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		VentanaSala.this.dispose();
   		VentanaSala.this.parent.setVisible(true);
      	}
      });
      btnCancelar.setFont(new Font("Arial", Font.BOLD, 16));
      panelDerecha.add(btnCancelar);
     
      contenedor.add(panelDerecha, BorderLayout.EAST);
      getContentPane().add(contenedor, BorderLayout.CENTER);
  }
 
  public Set<JButton> butacasSeleccionadas = new HashSet<>();
  public void seleccionarButaca(JButton b) {
      String codigo = b.getText();
      if (butacasOcupadas.contains(codigo)) {
          JOptionPane.showMessageDialog(this, "Esta butaca ya está ocupada.");
          return;
      }
      int cantidad = (int) spinnerEntradas.getValue();
      if (butacasSeleccionadas.contains(b)) {
          butacasSeleccionadas.remove(b);
          b.setBackground(Color.GREEN);
      } else {
          if (butacasSeleccionadas.size() >= cantidad) {
              JOptionPane.showMessageDialog(this, "Ya has seleccionado el número máximo de butacas.");
              return;
          }
          butacasSeleccionadas.add(b);
          b.setBackground(Color.YELLOW);
      }
  }
  public void confirmarButacas() {
	    int cantidad = (int) spinnerEntradas.getValue();
	    if (butacasSeleccionadas.size() != cantidad) {
	        JOptionPane.showMessageDialog(this, "Debes seleccionar exactamente " + cantidad + " butacas.");
	        return;
	    }
	    int precio = 8;
	    java.util.List<String> codigos = new java.util.ArrayList<>();
	    for (JButton b : butacasSeleccionadas) {
	        codigos.add(b.getText());
	    }
	    int total = codigos.size() * precio;
	    parent.getDb().ocuparButacas(claveSesion, butacasSeleccionadas);
	    for (JButton b : butacasSeleccionadas) {
	        String codigo = b.getText();
	        butacasOcupadas.add(codigo);
	        b.setBackground(Color.RED);
	        b.setEnabled(false);
	    }
	    butacasSeleccionadas.clear();	   
	        String codLocalizador = java.util.UUID.randomUUID().toString().substring(0, 8); // opcional
	        String datosQR = String.format(
	            "CINE DEUSTO\n" +
	            "Película: %s\n" +
	            "Sala: %d\n" +
	            "Inicio: %s\n" +
	            "Butacas: %s\n" +
	            "Total: %d euros\n" +
	            "Localizador: %s",
	            titulo,
	            sala,
	            inicio.toString(),
	            String.join(",", codigos),
	            total,
	            codLocalizador
	        );
	   
      parent.getDb().ocuparButacas(claveSesion, butacasSeleccionadas);
      for (JButton b : butacasSeleccionadas) {
          String codigo = b.getText();
          butacasOcupadas.add(codigo);
          b.setBackground(Color.RED);
          b.setEnabled(false);
      }
      butacasSeleccionadas.clear();
      JOptionPane.showMessageDialog(this, "Butacas reservadas correctamente.");
      hiloCuentaAtras.interrupt();
      VentanaPago pago = new VentanaPago(VentanaSala.this, VentanaSala.this.parent, datosQR);
      pago.setVisible(true);
      this.dispose();
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



