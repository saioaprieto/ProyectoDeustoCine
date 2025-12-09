package proyectoDeustoCine;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
public class VentanaEmpleado extends JFrame {
   private static final long serialVersionUID = 1L;
   private JPanel contentPane;
   private VentanaTrabajador parent;
   private VentanaSupervisor parentSupervisor;
   private String nombreEmpleado;
   private JButton btnCerrarSesion;
   private JLabel lblTitulo;
   private JButton btnNewButton;
   private JButton btnSolicitar;
   private JScrollPane scrollPane;
   private JTable table;
   private String dniEmpleado;
  
   public VentanaEmpleado(VentanaTrabajador parent, String nombreEmpleado, String dniEmpleado, VentanaSupervisor parentSupervisor) {
       this.parent = parent;
       this.nombreEmpleado = nombreEmpleado;
       this.parentSupervisor = parentSupervisor;
       this.dniEmpleado=dniEmpleado;
     
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		JPanel contentPane = crearPanelConFondo("imagenes/fondoPeliculas.jpg");
		contentPane.setBorder(new EmptyBorder(100, 200, 100, 200));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(50, 100));
		
		JPanel panelNorte = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelNorte.getLayout();
		flowLayout.setVgap(10);
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		lblTitulo = new JLabel("¡Bienvenid@ "+ nombreEmpleado +"!");
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
		
		btnCerrarSesion = new JButton("Cerrar Sesión");
		btnCerrarSesion.setBackground(new Color(240,240,240));
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaEmpleado.this.setVisible(false);
				parent.setVisible(true);	
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
		panel_1.add(btnCerrarSesion);
		
		JPanel panelDerecho = new JPanel();
		JPanel panel_2 = new JPanel();
		panelDerecho.add(panel_2);
		contentPane.add(panelDerecho, BorderLayout.EAST);
		panelDerecho.setLayout(new GridLayout(3, 0, 0, 100));
		btnSolicitar = new JButton("Solicitar Cambio");
		btnSolicitar.setBackground(new Color(240,240,240));
		btnSolicitar.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			        String solicitud = JOptionPane.showInputDialog(
			                VentanaEmpleado.this,
			                "Indica el cambio que deseas solicitar:",
			                "Solicitud de cambio",
			                JOptionPane.PLAIN_MESSAGE
			        );
			        if (solicitud == null || solicitud.isEmpty()) {
			            return;
			        }
			        parentSupervisor.getDb().guardarSolicitud(
			                dniEmpleado,
			                solicitud,
			                nombreEmpleado
			        );
			        int nuevoCont = parentSupervisor.getCont() + 1;
			        parentSupervisor.setCont(nuevoCont);
			        parentSupervisor.getLblCont().setText("Solicitudes de cambio: " + nuevoCont);
			        parentSupervisor.actualizarBadge(nuevoCont);
			        JOptionPane.showMessageDialog(VentanaEmpleado.this,
			                "Solicitud enviada:\n" + solicitud);
			    }
			});
		btnSolicitar.addMouseListener(new MouseAdapter() {
  	        @Override
  	        public void mouseEntered(MouseEvent e) {
  	            btnSolicitar.setBackground(new Color(200, 200, 190));
  	        }
  	        @Override
  	        public void mouseExited(MouseEvent e) {
  	            btnSolicitar.setBackground(new Color(240,240,240));
  	        }
  	    });
		btnSolicitar.setEnabled(true);
		panelDerecho.add(btnSolicitar);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = parentSupervisor.getTable();
		table.setEnabled(false);
		scrollPane.setViewportView(table);
		
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


