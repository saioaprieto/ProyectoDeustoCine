package proyectoDeustoCine;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
public class VentanaEmpleado extends JFrame {
    private JPanel contentPane;
    private VentanaTrabajador parent;
    private JTable table;
	private String nombreEmpleado;
	private JScrollPane scrollPane;
	private JButton btnSolicitar;
	private JButton btnCerrarSesion;
	private String dniEmpleado;
public VentanaEmpleado(VentanaTrabajador parent, String nombreEmpleado,String dniEmpleado, VentanaSupervisor vistaSupervisor) {
    this.parent = parent;
    this.nombreEmpleado = nombreEmpleado;
    this.dniEmpleado=dniEmpleado;
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setExtendedState(JFrame.MAXIMIZED_BOTH);


    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(100, 200, 100, 200));
    setContentPane(contentPane);
    contentPane.setLayout(new BorderLayout(50, 100));
    
    JPanel panelNorte = new JPanel();
    JLabel lblTitulo = new JLabel("¡Bienvenid@ " + nombreEmpleado + "!");
    lblTitulo.setFont(new Font("Verdana", Font.PLAIN, 16));
    panelNorte.add(lblTitulo);
    contentPane.add(panelNorte, BorderLayout.NORTH);


    scrollPane = new JScrollPane();
    contentPane.add(scrollPane, BorderLayout.CENTER);


    table = vistaSupervisor.getTable();  
    table.setEnabled(false);
    table.setRowHeight(80);
    scrollPane.setViewportView(table);


    JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panelSur.setPreferredSize(new Dimension(100, 80)); 
    contentPane.add(panelSur, BorderLayout.SOUTH);
   
    btnCerrarSesion = new JButton("Cerrar Sesion");
    btnCerrarSesion.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		VentanaEmpleado.this.setVisible(false);
    		parent.setVisible(true);
    		parent.getBtnAcceder().setEnabled(false);
    		
    	}
    });
   	
    panelSur.add(btnCerrarSesion);
    
    JPanel panelDerecho = new JPanel();
    contentPane.add(panelDerecho, BorderLayout.EAST);
    panelDerecho.setLayout(new GridLayout(3, 0, 0, 0));
    
    JPanel panel_1 = new JPanel();
    panelDerecho.add(panel_1);
    
    btnSolicitar = new JButton("Solicitar Cambio");
    btnSolicitar.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		String solicitud = JOptionPane.showInputDialog( VentanaEmpleado.this, 
    				"Indica qué cambio deseas solicitar:", 
    				"Solicitud de Cambio",
    				JOptionPane.PLAIN_MESSAGE );
    	
    	if (solicitud != null ) {
    		String nombre = nombreEmpleado;
    		vistaSupervisor.getDb().guardarSolicitud(nombreEmpleado, " (" + dniEmpleado + ")", solicitud);


            JOptionPane.showMessageDialog(
                    VentanaEmpleado.this,
                    "Solicitud enviada correctamente."
            );
            int nuevas = vistaSupervisor.getDb().getSolicitudesPendientes().size();
            vistaSupervisor.setCont(nuevas);
            vistaSupervisor.actualizarBadge(nuevas);
            vistaSupervisor.getLblCont().setText("Solicitudes de cambio: " + nuevas);
    	}
    	}
    });
    panelDerecho.add(btnSolicitar);
}
}
