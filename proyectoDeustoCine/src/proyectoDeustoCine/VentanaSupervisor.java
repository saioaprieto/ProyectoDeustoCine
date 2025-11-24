package src.proyectoDeustoCine;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaSupervisor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblTitulo;
	private JTable table;
	private VentanaTrabajador parent;
	private String nombreSupervisor;
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
	}

}