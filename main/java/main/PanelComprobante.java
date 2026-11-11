package main;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.nio.file.Path;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelComprobante extends PanelBase {

    private static final String[] NOMBRES = {
        "N. de transaccion:", "Fecha y hora:", "Tipo:", "Cuenta origen:", "Cuenta destino:", "Monto:"
    };

    private JLabel[] valores = new JLabel[NOMBRES.length];

    public PanelComprobante(VentanaATM ventana) {
        super(ventana);

        titulo("Comprobante de la transaccion");

        JPanel marco = new JPanel(null);
        marco.setBackground(Color.WHITE);
        marco.setBorder(BorderFactory.createLineBorder(BORDE));
        marco.setBounds(40, 75, 420, 315);
        add(marco);

        for (int i = 0; i < NOMBRES.length; i++) {
            JLabel nombre = new JLabel(NOMBRES[i]);
            nombre.setFont(new Font("SansSerif", Font.BOLD, 13));
            nombre.setForeground(TEXTO);
            nombre.setBounds(20, 14 + i * 48, 380, 18);
            marco.add(nombre);

            valores[i] = new JLabel();
            valores[i].setFont(new Font("SansSerif", Font.PLAIN, 14));
            valores[i].setForeground(TEXTO);
            valores[i].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDE));
            valores[i].setBounds(20, 32 + i * 48, 380, 24);
            marco.add(valores[i]);
        }

        JButton btnImprimir = boton("Imprimir comprobante", 500, 100, 260, 50, true);
        JButton btnVolver = boton("Volver al menu", 500, 170, 260, 50, false);

        btnImprimir.addActionListener(e -> imprimir());
        btnVolver.addActionListener(e -> ventana.mostrar("MENU"));
    }

    @Override
    public void alMostrar() {
        Transaccion t = ventana.getTransaccionActual();
        valores[0].setText(t.getIdTexto());
        valores[1].setText(t.getFechaTexto());
        valores[2].setText(t.getTipo());
        valores[3].setText(t.getOrigen().getNumero());
        valores[4].setText(t.getCuentaDestino());
        valores[5].setText(Banco.formatear(t.getMonto()));
    }

    private void imprimir() {
        try {
            Path archivo = ventana.getBanco().generarComprobante(ventana.getTransaccionActual());
            ventana.mostrarInfo("Comprobante guardado en:\n" + archivo.toAbsolutePath());
        } catch (IOException ex) {
            ventana.mostrarError("No se pudo guardar el comprobante.");
        }
    }
}
