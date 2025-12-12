package proyectoDeustoCine;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import dataBase.DataBase;
import dominio.Trabajador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.event.ActionEvent;
public class VentanaSupervisor extends JFrame {
  private static final long serialVersionUID = 1L;
  private JPanel contentPane;
  private JLabel lblTitulo;
  private JTable table;
  private VentanaTrabajador parent;
  private String nombreSupervisor;
  private DataBase bases;
  private List<Trabajador> lista;
  private JButton btnVisualizar;
  private int filaPrimera = -1;
  private int colPrimera = -1;
  private JButton btnEditar;
  private int cont;
  private JLabel lblCont;  
  private JPanel panelNotificaciones;
  private JLabel lblCampana;
  private JLabel lblBadge;
  private MouseListener oscurecerBtnVisualizar;
  private MouseListener oscurecerBtnEditar;
 
  public VentanaSupervisor(VentanaTrabajador parent, String nombreSupervisor, DataBase bases) {
      this.parent = parent;
      this.nombreSupervisor = nombreSupervisor;
      this.bases= bases;
      lista = bases.getTrabajadores();
    
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setExtendedState(JFrame.MAXIMIZED_BOTH);
      setBounds(100, 100, 450, 300);
      JPanel contentPane = crearPanelConFondo("imagenes/fondoPeliculas.jpg");
    
      contentPane.setBorder(new EmptyBorder(100, 200, 100, 200));
      setContentPane(contentPane);
      contentPane.setLayout(new BorderLayout(50, 100));
    
      JPanel panelNorte = new JPanel();
      panelNorte.setOpaque(false);
      FlowLayout flowLayout = (FlowLayout) panelNorte.getLayout();
      flowLayout.setVgap(10);
    
      JPanel panelBienvenida = new JPanel();
      panelBienvenida.setBackground(new Color(255, 255, 255, 180));
      panelBienvenida.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
      panelBienvenida.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
    
      lblTitulo = new JLabel("¡Bienvenid@ " + nombreSupervisor + "!");
      lblTitulo.setFont(new Font("Verdana", Font.BOLD, 16));
      panelBienvenida.add(lblTitulo);
       panelNorte.add(panelBienvenida);
      contentPane.add(panelNorte, BorderLayout.NORTH);
  
      panelNotificaciones = new JPanel() {
          @Override
          protected void paintComponent(Graphics g) {
              super.paintComponent(g);
           
              g.setColor(new Color(255, 255, 255, 180));
              g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
          }
      };
      panelNotificaciones.setLayout(null);
      panelNotificaciones.setOpaque(false);
      panelNotificaciones.setPreferredSize(new Dimension(40, 40));
     
      ImageIcon iconoCampana = new ImageIcon("imagenes/notification.png");
      lblCampana = new JLabel(iconoCampana);
      lblCampana.setBounds(5, 5, 30, 30);
      panelNotificaciones.add(lblCampana);
     
      lblBadge = new JLabel("0", SwingConstants.CENTER);
      lblBadge.setOpaque(true);
      lblBadge.setBackground(Color.RED);
      lblBadge.setForeground(Color.WHITE);
      lblBadge.setFont(new Font("Arial", Font.BOLD, 12));
      lblBadge.setBounds(22, 0, 18, 18);
      lblBadge.setVisible(false);
      panelNotificaciones.add(lblBadge);
    
      List<String> pendientes = bases.getSolicitudesPendientes();
      cont = pendientes.size();
      actualizarBadge(cont);
    
      panelNotificaciones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
      panelNotificaciones.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseClicked(java.awt.event.MouseEvent e) {
              mostrarSolicitudes();
          }
      });
     
    
      JPanel panelSur = new JPanel(new GridLayout(0, 2, 0, 0));
      panelSur.setOpaque(false);
      contentPane.add(panelSur, BorderLayout.SOUTH);
     
      JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
      panelIzquierdo.setOpaque(false);
      panelSur.add(panelIzquierdo);
     
      JPanel panelSolicitudes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
      panelSolicitudes.setBackground(new Color(255, 255, 255, 180));
      panelSolicitudes.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    
      lblCont = new JLabel("Solicitudes de cambio: " + cont);
      lblCont.setFont(new Font("Arial", Font.PLAIN, 12));
      lblCont.setPreferredSize(new Dimension(lblCont.getPreferredSize().width, 18));
      panelSolicitudes.add(lblCont);
      panelSolicitudes.setPreferredSize(new Dimension(panelSolicitudes.getPreferredSize().width, 22));
    
      panelIzquierdo.add(panelSolicitudes);
      panelIzquierdo.add(panelNotificaciones);
     
      JPanel panelDerechoSur = new JPanel();
      panelDerechoSur.setOpaque(false);
      panelSur.add(panelDerechoSur);
     
      JButton btnCerrarSesion = new JButton("Cerrar Sesión");
      btnCerrarSesion.setBackground(new Color(240, 240, 240));
      btnCerrarSesion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
      btnCerrarSesion.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
      btnCerrarSesion.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              VentanaSupervisor.this.setVisible(false);
              VentanaSupervisor.this.parent.getTextdni().setText("");
              VentanaSupervisor.this.parent.getContrasenya().setText("");
              VentanaSupervisor.this.parent.getBtnAcceder().setEnabled(false);
              VentanaSupervisor.this.parent.setVisible(true);  
          }
      });
      btnCerrarSesion.addMouseListener(new MouseAdapter() {
 	        @Override
 	        public void mouseEntered(MouseEvent e) {
 	            btnCerrarSesion.setBackground(new Color(200, 200, 190));
 	        }
 	        @Override
 	        public void mouseExited(MouseEvent e) {
 	            btnCerrarSesion.setBackground(new Color(240,240,240));
 	        }
 	    });
      panelDerechoSur.add(btnCerrarSesion);
    
      JPanel panelDerecho = new JPanel();
      contentPane.add(panelDerecho, BorderLayout.EAST);
      panelDerecho.setLayout(new GridLayout(2, 0, 0, 100));
      panelDerecho.setOpaque(false);
     
      btnVisualizar = new JButton("Visualizar");
      btnVisualizar.setEnabled(false);
      btnVisualizar.removeMouseListener(oscurecerBtnVisualizar);
      btnVisualizar.setBackground(new Color(240,240,240));
      oscurecerBtnVisualizar = new MouseAdapter() {
 	        @Override
 	        public void mouseEntered(MouseEvent e) {
	   	            btnVisualizar.setBackground(new Color(200, 200, 190));
 	        }
 	        @Override
 	        public void mouseExited(MouseEvent e) {
	   	            btnVisualizar.setBackground(new Color(240,240,240));	   	        	
 	        }
 	    };
      btnVisualizar.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              int fila = table.getSelectedRow();
              int columna = table.getSelectedColumn();
              String textoCelda = table.getValueAt(fila, columna).toString();
              String dni = extraerDni(textoCelda);
              for (Trabajador t : lista) {
                  if(dni.equals(t.getCodico_dni())) {
                	  ImageIcon icono = new ImageIcon("imagenes/" + t.getNombre() + ".png");
                	  Image img = icono.getImage();
                	  int max = 100; 
                	  double ratio = Math.min((double) max / icono.getIconWidth(), (double) max / icono.getIconHeight());
                	  icono = new ImageIcon(img.getScaledInstance((int)(icono.getIconWidth()*ratio), (int)(icono.getIconHeight()*ratio), Image.SCALE_SMOOTH));
                      JOptionPane.showMessageDialog(VentanaSupervisor.this,
                              "Nombre: " + t.getNombre() + "\n" +
                              "Apellido: " + t.getApellido() + "\n" +
                              "Salario: " + t.getSalario() + "€"+"\n" +
                              "Telefono: " + t.getTelefono(),
                      "Información del trabajador",
                      JOptionPane.INFORMATION_MESSAGE,
                      icono);
                      btnVisualizar.setEnabled(false);
                      btnEditar.setEnabled(false);
                      btnVisualizar.removeMouseListener(oscurecerBtnVisualizar);
                      btnEditar.removeMouseListener(oscurecerBtnEditar);
                      return;
                  }
              }
          }
      });
      panelDerecho.add(btnVisualizar);
     
      btnEditar = new JButton("Editar");
      btnEditar.setEnabled(false);
      btnEditar.removeMouseListener(oscurecerBtnEditar);
      btnEditar.setBackground(new Color(240,240,240));
      oscurecerBtnEditar = new MouseAdapter() {
	        @Override
	        public void mouseEntered(MouseEvent e) {
	   	            btnEditar.setBackground(new Color(200, 200, 190));
	        }
	        @Override
	        public void mouseExited(MouseEvent e) {
	   	            btnEditar.setBackground(new Color(240,240,240));	   	        	
	        }
	    };
	    btnEditar.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {

	            int fila = table.getSelectedRow();
	            int col = table.getSelectedColumn();

	            if (fila == -1 || col == -1) {
	                JOptionPane.showMessageDialog(VentanaSupervisor.this,
	                        "Selecciona una casilla antes de editar.");
	                return;
	            }

	            String textoCelda = table.getValueAt(fila, col).toString();
	            String dniActual = extraerDni(textoCelda);

	            List<Trabajador> listaFiltrada = new ArrayList<>();
	            for (Trabajador t : lista) {
	                if (!t.getCodico_dni().equals(dniActual)) {
	                    listaFiltrada.add(t);
	                }
	            }

	            VentanaCambiarTrabajador ventana =
	                    new VentanaCambiarTrabajador(table, fila, col, listaFiltrada);
	            ventana.setVisible(true);

	            table.repaint();
	        }
	    });






      panelDerecho.add(btnEditar);
     
      JScrollPane scrollPane = new JScrollPane();
      contentPane.add(scrollPane, BorderLayout.CENTER);
      scrollPane.setOpaque(false);
      scrollPane.getViewport().setOpaque(false);
     
      table = new JTable();
      table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
          public void valueChanged(ListSelectionEvent event) {
              if (!event.getValueIsAdjusting()) {
                  int fila = table.getSelectedRow();
                  int columna = table.getSelectedColumn();
                  if (fila != -1 && columna != -1) {
                      btnVisualizar.setEnabled(true);
                      btnEditar.setEnabled(true);
                      btnVisualizar.addMouseListener(oscurecerBtnVisualizar);
                      btnEditar.addMouseListener(oscurecerBtnEditar);
                  }
              }
          }
      });
      scrollPane.setViewportView(table);
     
      String[] columnas = {"TAREA", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO", "DOMINGO"};
     
      table.setCellSelectionEnabled(true);
      table.setRowSelectionAllowed(false);
      table.setColumnSelectionAllowed(false);
      table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
     
      DefaultTableModel modelo = bases.getModeloHorario();
     
      if(modelo == null) {
          modelo = new DefaultTableModel(columnas,0) {
              public boolean isCellEditable(int row, int col) {
                  return col != 0;
              }
          };
          modelo.addRow(new Object[]{"BAR", "16:00 - 20:00", "16:00 - 20:00", "16:00 -23:00", "16:00 -23:00", "16:00 -23:00", "16:00 -23:00"});
          modelo.addRow(new Object[]{"TAQUILLA", "16:00 - 19:00", "16:00 - 19:00", "16:00 - 21:00", "16:00 - 21:00", "16:00 - 21:00", "16:00 - 21:00"});
          modelo.addRow(new Object[]{"LECTOR", "16:00 - 20:00", "16:00 - 20:00", "16:00 - 23:00", "16:00 - 23:00", "16:00 - 23:00", "16:00 - 23:00"});
          modelo.addRow(new Object[]{"SALA1", "16:00 - 22:00", "16:00 - 22:00", "16:00 - 01:00", "16:00 - 01:00", "16:00 - 01:00", "16:00 - 01:00"});
          modelo.addRow(new Object[]{"SALA2", "16:00 - 22:00", "16:00 - 22:00", "16:00 - 01:00", "16:00 - 01:00", "16:00 - 01:00", "16:00 - 01:00"});
          modelo.addRow(new Object[]{"SALA3", "16:00 - 22:00", "16:00 - 22:00", "16:00 - 01:00", "16:00 - 01:00", "16:00 - 01:00", "16:00 - 01:00"});
          asignarDnis(modelo, lista);
          bases.setModeloHorario(modelo);
      }
      table.setModel(modelo);
      table.setRowHeight(80);
      table.setDefaultRenderer(Object.class, new RendererPorDni());
  }
  public void mostrarSolicitudes() {
      List<String> solicitudes = bases.getSolicitudesPendientes();
      if (solicitudes.isEmpty()) {
          JOptionPane.showMessageDialog(this,
                  "No hay solicitudes pendientes.",
                  "Solicitudes",
                  JOptionPane.INFORMATION_MESSAGE);
          return;
      }
      StringBuilder sb = new StringBuilder("Solicitudes pendientes:\n\n");
      for (String s : solicitudes) sb.append("• ").append(s).append("\n");
      JOptionPane.showMessageDialog(this, sb.toString(), "Solicitudes Pendientes", JOptionPane.PLAIN_MESSAGE);
  }
  public void asignarDnis(DefaultTableModel modelo, List<Trabajador> lista) {
      int numFilas = modelo.getRowCount();    
      int numColumnas = modelo.getColumnCount();
      for (int col = 1; col < numColumnas; col++) {
          List<Trabajador> copia = new ArrayList<>(lista);
          Collections.shuffle(copia);
          int index = 0;
          for (int fila = 0; fila < numFilas; fila++) {
              String horario = modelo.getValueAt(fila, col).toString();
              String dni = copia.get(index).getCodico_dni();
              index++;
              modelo.setValueAt("<html>" + horario + "<br>" + dni + "</html>", fila, col);
          }
      }
  }
  public String extraerDni(String texto) {
      texto = texto.replace("<html>", "").replace("</html>", "").replace("<br>", "\n");
      String[] lineas = texto.split("\n");
      if (lineas.length < 2) return null;
      return lineas[1].trim();
  }
  public String extraerHorario(String texto) {
      texto = texto.replace("<html>", "").replace("</html>", "").replace("<br>", "\n");
      String[] lineas = texto.split("\n");
      if (lineas.length == 0) return "";
      return lineas[0].trim();
  }
  public Color colorPorDni(String dni) {
      switch(dni) {
          case "12345678A": return Color.YELLOW;
          case "23456789B": return Color.CYAN;
          case "34567890C": return Color.PINK;
          case "45678901D": return Color.ORANGE;
          case "56789012E": return Color.GREEN;
          case "67890123F": return Color.LIGHT_GRAY;
      }
      return Color.WHITE;
  }
  public class RendererPorDni extends DefaultTableCellRenderer {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                     int row, int column) {
          Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
          if(value == null) return c;
          String dni = extraerDni(value.toString());
          if(row == filaPrimera && column == colPrimera) {
              c.setBackground(Color.BLUE);
          } else if(!isSelected && dni != null) {
              c.setBackground(colorPorDni(dni));
          } else if(!isSelected) {
              c.setBackground(Color.WHITE);
          }
          return c;
      }
  }
  public void actualizarBadge(int numSolicitudes) {
      if(numSolicitudes <= 0) lblBadge.setVisible(false);
      else {
          lblBadge.setText(String.valueOf(numSolicitudes));
          lblBadge.setVisible(true);
      }
  }
  public DataBase getDb() {
	   return bases;
	   }
  public List<Trabajador> getLista() {
	   return lista;
	   }
  public void setLista(List<Trabajador> lista) {
	   this.lista = lista; }
  public JTable getTable(){
	   return table;
	   }
  public void setTable(JTable table) {
	   this.table = table;
	   }
  public JLabel getLblCont() {
	   return lblCont;
	   }
  public void setLblCont(JLabel lblCont) {
	   this.lblCont = lblCont;
	   }
  public int getCont() {
	   return cont;
  }
  public void setCont(int cont) {
	   this.cont = cont;
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



