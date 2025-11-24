package proyectoDeustoCine;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;

public class VentanaSupervisor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblTitulo;
	private JTable table;
	private VentanaTrabajador parent;
	private String nombreSupervisor;
	private Conexion conn;
	
	public VentanaSupervisor(VentanaTrabajador parent, String nombreSupervisor) {
		this.parent = parent;
		this.nombreSupervisor = nombreSupervisor;
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
		
		
		JPanel panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		panelSur.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel = new JPanel();
		panelSur.add(panel);
		
		JPanel panel_1 = new JPanel();
		panelSur.add(panel_1);
		
		JButton btnNewButton = new JButton("Cerrar Sesión");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				VentanaSupervisor.this.setVisible(false);
				VentanaSupervisor.this.parent.getTextdni().setText("");
				VentanaSupervisor.this.parent.getContrasenya().setText("");
				VentanaSupervisor.this.parent.getBtnAcceder().setEnabled(false);
				VentanaSupervisor.this.parent.setVisible(true);
				
			}
		});
		panel_1.add(btnNewButton);
		
		JPanel panelDerecho = new JPanel();
		contentPane.add(panelDerecho, BorderLayout.EAST);
		panelDerecho.setLayout(new GridLayout(2, 0, 0, 100));
		
		JButton btnVisualizar = new JButton("Visualizar");
		panelDerecho.add(btnVisualizar);
		
		JButton btnEditar = new JButton("Editar");
		panelDerecho.add(btnEditar);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		String[] columnas = {"TAREA", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO", "DOMINGO"};
		
		
		
		DefaultTableModel modelo = new DefaultTableModel(columnas,0) {
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
		
		table.setModel(modelo);
		table.setRowHeight(80);
		table.setDefaultRenderer(Object.class, new RendererHorario());
		
		 Conexion db = new Conexion();
	        db.connect();

	        ArrayList<Trabajador> lista = db.obtenerPersonas();

//	        asignarTrabajador(lista);

	        db.close();
	       
	}
//	private void activarColoresDNI() {
//
//	    table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
//
//	        @Override
//	        public Component getTableCellRendererComponent(
//	                JTable table, Object value, boolean isSelected, boolean hasFocus,
//	                int row, int column) {
//
//	            Component c = super.getTableCellRendererComponent(
//	                    table, value, isSelected, hasFocus, row, column);
//
//	            if (value == null) {
//	                c.setBackground(Color.WHITE);
//	                return c;
//	            }
//
//	            String texto = value.toString();
//
//	            // Si hay dos DNIs (caso BAR)
//	            if (texto.contains("\n")) {
//	                String[] partes = texto.split("\n");
//	                c.setBackground(colorParaDNI(partes[0])); // color del primero
//	            } else {
//	                c.setBackground(colorParaDNI(texto));
//	            }
//
//	            return c;
//	        }
//	    });
//	}
//	private Color colorParaDNI(String dni) {
//	    return new Color(
//	        (dni.hashCode() & 0xFF0000) >> 16,
//	        (dni.hashCode() & 0x00FF00) >> 8,
//	        (dni.hashCode() & 0x0000FF)
//	    );
//	}

	private class RendererHorario extends javax.swing.table.DefaultTableCellRenderer {

	    @Override
	    public Component getTableCellRendererComponent(
	            JTable table, Object value, boolean isSelected, boolean hasFocus,
	            int row, int column) {

	        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

	        if (!isSelected) c.setBackground(Color.WHITE);
	        if (value == null) return c;

	        String texto = value.toString();

	        // Extraer un DNI si existe (BAR tiene 2, nos vale el primero)
	        String dni = extraerPrimerDNI(texto);

	        if (dni != null) {
	            // Crear color en base al DNI (sin mapas)
	            Color color = colorDesdeDNI(dni);
	            if (!isSelected) c.setBackground(color);
	        }

	        return c;
	    }
	}
	private String extraerPrimerDNI(String html) {

	    // quitar etiquetas HTML
	    html = html.replaceAll("<[^>]*>", " ").trim();

	    // separar palabras
	    String[] partes = html.split("\\s+");

	    // buscar el primer DNI (8 dígitos + letra)
	    for (String p : partes) {
	        if (p.matches("\\d{8}[A-Za-z]")) {
	            return p;
	        }
	    }
	    return null;
	}
	private Color colorDesdeDNI(String dni) {

	    int hash = Math.abs(dni.hashCode());

	    int r = 100 + (hash % 155);
	    int g = 100 + ((hash / 10) % 155);
	    int b = 100 + ((hash / 100) % 155);

	    return new Color(r, g, b);
	}
	public void AsignarTrabajador2(ArrayList<Trabajador>lista ) {
		 DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		
		 for(int columna = 0; columna < modelo.getColumnCount(); columna ++) {
			  Collections.shuffle(lista);
			  for(int fila = 0; fila < modelo.getRowCount(); fila ++) {
				  
			  }
			 
		 }
	}

//	public void asignarTrabajador(ArrayList<Trabajador> trabajadores) {
//		
//		    for (int fila = 0; fila < modelo.getRowCount(); fila++) {
//
//		        String tarea = modelo.getValueAt(fila, 0).toString();
//
//		        for (int col = 1; col < modelo.getColumnCount(); col++) {
//
//		            Object raw = modelo.getValueAt(fila, col);
//
//		            if (raw == null) continue;
//
//		            String texto = raw.toString();
//
////		             Sacar solo el horario (si antes ya había HTML)
//		            String horario = texto.contains("<html>")
//		                    ? texto.replaceAll("<[^>]*>", "")     // quitar etiquetas HTML
//		                           .trim().split("\\s+")[0]        // recortar todo salvo el horario
//		                    : texto;
//
//		            // -------- BAR (2 personas) --------
//		            if (tarea.equalsIgnoreCase("BAR")) {
//
//		                Trabajador t1 = trabajadores.get((int)(Math.random() * trabajadores.size()));
//		                Trabajador t2 = trabajadores.get((int)(Math.random() * trabajadores.size()));
//
//		                while (t1.getCodico_dni().equals(t2.getCodico_dni())) {
//		                    t2 = trabajadores.get((int)(Math.random() * trabajadores.size()));
//		                }
//
//		                String celda =
//		                    "<html>"
//		                  + "<p style='font-size:14px; font-weight:bold; margin:0;'>" + horario + "</p>"
//		                  + "<p style='font-size:13px; margin:0;'>" 
//		                  + t1.getCodico_dni() + "<br>" 
//		                  + t2.getCodico_dni() + "</p>"
//		                  + "</html>";
//
//		                modelo.setValueAt(celda, fila, col);
//		            }
//
//		            // -------- RESTO (1 persona) --------
//		            else {
//
//		                Trabajador t = trabajadores.get((int)(Math.random() * trabajadores.size()));
//
//		                String celda =
//		                    "<html>"
//		                  + "<p style='font-size:14px; font-weight:bold; margin:0;'>" + horario + "</p>"
//		                  + "<p style='font-size:13px; margin:0;'>" 
//		                  + t.getCodico_dni() + "</p>"
//		                  + "</html>";
//
//		                modelo.setValueAt(celda, fila, col);
//		            }
//	}
//		        
//
//}
		    
	
    
	
}

	