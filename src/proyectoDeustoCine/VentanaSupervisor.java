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


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
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


	public VentanaSupervisor(VentanaTrabajador parent, String nombreSupervisor, DataBase bases) {
		this.parent = parent;
		this.nombreSupervisor = nombreSupervisor;
		this.bases= bases;
		lista = bases.getTrabajadores();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(100, 200, 100, 200));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(50, 100));
		
		JPanel panelNorte = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelNorte.getLayout();
		flowLayout.setVgap(10);
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		lblTitulo = new JLabel("¡Bienvenid@ "+ nombreSupervisor +"!");
		lblTitulo.setFont(new Font("Verdana", Font.PLAIN, 16));
		lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
		panelNorte.add(lblTitulo);
		
		panelNotificaciones = new JPanel();
		panelNotificaciones.setLayout(null);
		panelNotificaciones.setPreferredSize(new java.awt.Dimension(40, 40));


		ImageIcon iconoCampana = new ImageIcon("imagenes/notification.png");
		lblCampana = new JLabel(iconoCampana);
		lblCampana.setBounds(5, 5, 30, 30);
		panelNotificaciones.add(lblCampana);


//		//el numero de arriba de la campana
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


		JPanel panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		panelSur.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
		panelSur.add(panel);
		JPanel panel_1 = new JPanel();
		
		JButton btnCerrarSesion = new JButton("Cerrar Sesión");
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaSupervisor.this.setVisible(false);
				VentanaSupervisor.this.parent.getTextdni().setText("");
				VentanaSupervisor.this.parent.getContrasenya().setText("");
				VentanaSupervisor.this.parent.getBtnAcceder().setEnabled(false);
				VentanaSupervisor.this.parent.setVisible(true);	
			}
		});
		panelSur.add(panel_1);
		panel_1.add(btnCerrarSesion);
		
		lblCont = new JLabel("Solicitudes de cambio: " + cont);
		panel.add(lblCont);
		panel.add(panelNotificaciones);


		JPanel panelDerecho = new JPanel();
		contentPane.add(panelDerecho, BorderLayout.EAST);
		panelDerecho.setLayout(new GridLayout(2, 0, 0, 100));
	
		btnVisualizar = new JButton("Visualizar");
		btnVisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = table.getSelectedRow();
				int columna = table.getSelectedColumn();
				String textoCelda = table.getValueAt(fila, columna).toString();
				String dni = extraerDni(textoCelda);
				for (Trabajador t : lista) {
					String dniTr = t.getCodico_dni();
					if(dni.equals(dniTr)) {
						JOptionPane.showMessageDialog(
					            VentanaSupervisor.this,
					            "Nombre: " + t.getNombre() + "\n" +
					            "Apellido: " + t.getApellido() + "\n" +
					            "Salario: " + t.getSalario() + "\n" +
					            "Telefono: " + t.getTelefono()
					        );
					    return; 
					}
				}
					
			}
		});
		btnVisualizar.setEnabled(false);
		panelDerecho.add(btnVisualizar);
	 
		btnEditar = new JButton("Editar");
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {


		        int fila = table.getSelectedRow();
		        int col = table.getSelectedColumn();


		        if (fila == -1 || col == -1) {
		            JOptionPane.showMessageDialog(VentanaSupervisor.this,
		                    "Selecciona una casilla válida.");
		            return;
		        }
		        if (filaPrimera == -1 && colPrimera == -1) {
		            filaPrimera = fila;
		            colPrimera = col;
		            table.repaint();


		            JOptionPane.showMessageDialog(VentanaSupervisor.this,
		                    "Primera casilla guardada.\nAhora selecciona otra y pulsa EDITAR de nuevo.");
		            return;
		        }
		        int filaSegunda = fila;
		        int colSegunda = col;


		        Object valor1 = table.getValueAt(filaPrimera, colPrimera);
		        Object valor2 = table.getValueAt(filaSegunda, colSegunda);


		        String texto1 = valor1.toString();
		        String texto2 = valor2.toString();


		        String horario1 = extraerHorario(texto1);
		        String dni1 = extraerDni(texto1);


		        String horario2 = extraerHorario(texto2);
		        String dni2 = extraerDni(texto2);


		        String nuevoTexto1 = "<html>" + horario1 + "<br>" + dni2 + "</html>";
		        String nuevoTexto2 = "<html>" + horario2 + "<br>" + dni1 + "</html>";


		        table.setValueAt(nuevoTexto1, filaPrimera, colPrimera);
		        table.setValueAt(nuevoTexto2, filaSegunda, colSegunda);


		        filaPrimera = -1;
		        colPrimera = -1;


		        table.repaint();


		        JOptionPane.showMessageDialog(VentanaSupervisor.this,
		                "¡Trabajadores intercambiados!");
		    }
		});	
		panelDerecho.add(btnEditar);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent event) {
		        if (!event.getValueIsAdjusting()) {
		            int fila = table.getSelectedRow();
		            int columna = table.getSelectedColumn();


		            if (fila != -1 && columna != -1) {
		                Object valor = table.getValueAt(fila, columna);
		                System.out.println("Has seleccionado la casilla → " + valor);
		                btnVisualizar.setEnabled(true);
		                btnEditar.setEnabled(true);
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
		if (modelo == null) {
		    modelo = new DefaultTableModel(columnas,0) {
		        @Override
		        public boolean isCellEditable(int row, int col) {
		            return col != 0 ;
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
	    for (String s : solicitudes) {
	        sb.append("• ").append(s).append("\n");
	        
	    }


	    JOptionPane.showMessageDialog(this,
	            sb.toString(),
	            "Solicitudes Pendientes",
	            JOptionPane.PLAIN_MESSAGE);
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
	            // coger dni del trabajador correspondiente
	            String dni = copia.get(index).getCodico_dni();
	            index++;
	            
	        modelo.setValueAt("<html>" + horario + "<br>" + dni + "</html>", fila, col);
	        }
	    }
	}
public String extraerDni(String texto) {//para porder coger solo dni
	    // quitamos etiquetas HTML
	    texto = texto.replace("<html>", "")
	                 .replace("</html>", "")
	                 .replace("<br>", "\n");
	    String[] lineas = texto.split("\n");
	    if (lineas.length < 2) return null;
	    
	    return lineas[1].trim();
	}
public String extraerHorario(String texto) {
	    // quitamos etiquetas HTML
	    texto = texto.replace("<html>", "")
	                 .replace("</html>", "")
	                 .replace("<br>", "\n");


	    String[] lineas = texto.split("\n");
	    if (lineas.length == 0) return "";
	    return lineas[0].trim();  
	}
public Color colorPorDni(String dni) {
	    switch (dni) {
	        case "12345678A": return Color.YELLOW;
	        case "23456789B": return Color.CYAN;
	        case "34567890C": return Color.PINK;
	        case "45678901D": return Color.ORANGE;
	        case "56789012E": return Color.GREEN;
	        case "67890123F": return Color.LIGHT_GRAY;
	    }
	    return Color.WHITE; // por si acaso
	}
public class RendererPorDni extends DefaultTableCellRenderer {
	@Override
	    public Component getTableCellRendererComponent(
	         JTable table, Object value, boolean isSelected, boolean hasFocus,
	          int row, int column) {
	      Component c = super.getTableCellRendererComponent(
	             table, value, isSelected, hasFocus, row, column);
	        if (value == null) return c;


	        String texto = value.toString();
	        String dni = extraerDni(texto);
	    
	        if (row == filaPrimera && column == colPrimera) {
	            c.setBackground(Color.BLUE);
	            return c;
	        }
	        if (!isSelected && dni != null) {
	            c.setBackground(colorPorDni(dni));
	        } else if (!isSelected) {
	            c.setBackground(Color.WHITE);
	        }
	        return c;
	    }
	}
	public void actualizarBadge(int numSolicitudes) {
    if (numSolicitudes <= 0) {
        lblBadge.setVisible(false);
    } else {
        lblBadge.setText(String.valueOf(numSolicitudes));
        lblBadge.setVisible(true);
    }
}


	public DataBase getDb() {
		// TODO Auto-generated method stub
		return bases;
	}
	public List<Trabajador> getLista() {
		return lista;
	}
	public void setLista(List<Trabajador> lista) {
		this.lista = lista;
	}
	public JTable getTable() {
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
	




		   
	
  
	
}
	





