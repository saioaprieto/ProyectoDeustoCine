package proyectoDeustoCine;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
<<<<<<< HEAD
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
=======
>>>>>>> branch 'main' of ssh://git@github.com/saioaprieto/ProyectoDeustoCine.git

public class VentanaSupervisor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblTitulo;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaSupervisor frame = new VentanaSupervisor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaSupervisor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
<<<<<<< HEAD
		
		JPanel panelNorte = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelNorte.getLayout();
		flowLayout.setVgap(10);
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		lblTitulo = new JLabel("");
		panelNorte.add(lblTitulo);
		
		JPanel panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		panelSur.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel = new JPanel();
		panelSur.add(panel);
		
		JPanel panel_1 = new JPanel();
		panelSur.add(panel_1);
		
		JButton btnNewButton = new JButton("New button");
		panel_1.add(btnNewButton);
		
		JPanel panelDerecho = new JPanel();
		contentPane.add(panelDerecho, BorderLayout.EAST);
		panelDerecho.setLayout(new GridLayout(2, 0, 0, 0));
		
		JButton btnNewButton_1 = new JButton("New button");
		panelDerecho.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		panelDerecho.add(btnNewButton_2);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
=======
>>>>>>> branch 'main' of ssh://git@github.com/saioaprieto/ProyectoDeustoCine.git
	}

}