package src.proyectoDeustoCine;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class VentanaEmpleado extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VentanaTrabajador parent;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public VentanaEmpleado(VentanaTrabajador parent) {
		this.parent = parent;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(10, 10));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblTitulo = new JLabel("¡Bienvenid@ ");
		panel.add(lblTitulo);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);
		
		JButton btnCerrarSesion = new JButton("Cerrar Sesión");
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				VentanaEmpleado.this.setVisible(false);
				VentanaEmpleado.this.parent.getTextdni().setText("");
				VentanaEmpleado.this.parent.getContrasenya().setText("");
				VentanaEmpleado.this.parent.getBtnAcceder().setEnabled(false);
				VentanaEmpleado.this.parent.setVisible(true);
				
			}
		});
		panel_4.add(btnCerrarSesion);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.EAST);
		panel_2.setLayout(new GridLayout(2, 0, 100, 100));
		
		JButton btnSolicitar = new JButton("Solicitar cambio");
		panel_2.add(btnSolicitar);
		
		JButton btnVisualizar = new JButton("Visualizar");
		panel_2.add(btnVisualizar);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}
}