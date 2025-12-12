package proyectoDeustoCine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import dominio.Trabajador;

public class VentanaCambiarTrabajador extends JFrame {

    public VentanaCambiarTrabajador(JTable table, int filaSeleccionada, int colSeleccionada, List<Trabajador> lista) {
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    	setTitle("Seleccionar trabajador");
        setSize(900, 600);
        setLocationRelativeTo(null);
//gj
        JPanel contentPane = crearPanelConFondo("imagenes/fondoPeliculas.jpg");
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JPanel panel = new JPanel(new GridLayout(0, 4, 10, 10));
        panel.setOpaque(false);

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        contentPane.add(scroll, BorderLayout.CENTER);

        for (Trabajador t : lista) {
            int filaActual = -1;
            for (int f = 0; f < table.getRowCount(); f++) {
                String valor = table.getValueAt(f, colSeleccionada).toString();
                if (valor.contains(t.getCodico_dni())) {
                    filaActual = f;
                    break;
                }
            }
            if (filaActual == -1) continue;

            String valorActual = table.getValueAt(filaActual, colSeleccionada).toString();
            String horarioActual = extraerHorario(valorActual);
            String puestoActual = table.getValueAt(filaActual, 0).toString();

            JButton btn = new JButton("<html>" + t.getNombre() + "<br>" + horarioActual + "<br>" + puestoActual + "</html>");
            ImageIcon icono = new ImageIcon("imagenes/" + t.getNombre() + ".png");
            Image imgEscalada = icono.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(imgEscalada));
            btn.setHorizontalTextPosition(SwingConstants.CENTER);
            btn.setVerticalTextPosition(SwingConstants.BOTTOM);

            btn.setOpaque(true);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setForeground(Color.WHITE);

            final int filaTrabajador = filaActual;
            btn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					btn.addActionListener(new ActionListener() {
					    @Override
					    public void actionPerformed(ActionEvent e) {
					        String valorSeleccionado = table.getValueAt(filaSeleccionada, colSeleccionada).toString();
					        String dniSeleccionado = extraerDni(valorSeleccionado);
					        String horarioSeleccionado = extraerHorario(valorSeleccionado);

					        String valorBoton = table.getValueAt(filaTrabajador, colSeleccionada).toString();
					        String dniBoton = extraerDni(valorBoton);
					        String horarioBoton = extraerHorario(valorBoton);

					        table.setValueAt("<html>" + horarioBoton + "<br>" + dniBoton + "</html>", filaSeleccionada, colSeleccionada);
					        table.setValueAt("<html>" + horarioSeleccionado + "<br>" + dniSeleccionado + "</html>", filaTrabajador, colSeleccionada);

					        table.repaint();
					        dispose();
					    }
					});

				}
            });


            panel.add(btn);
        }
    }

    private String extraerHorario(String texto) {
        texto = texto.replace("<html>", "").replace("</html>", "").replace("<br>", "\n");
        String[] lineas = texto.split("\n");
        if (lineas.length > 0) {
            return lineas[0].trim();
        } else {
            return "";
        }
    }

    private String extraerDni(String texto) {
        texto = texto.replace("<html>", "").replace("</html>", "").replace("<br>", "\n");
        String[] lineas = texto.split("\n");
        if (lineas.length > 1) {
            return lineas[1].trim();
        } else {
            return null;
        }
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
