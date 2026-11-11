package main;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PanelTransferencia extends PanelBase {

    private JLabel lblSaldo;
    private JTextField txtDestino;
    private JTextField txtMonto;

    public PanelTransferencia(VentanaATM ventana) {
        super(ventana);

        titulo("Transferir dinero");
        textoGris("Saldo disponible:", 40, 70, 130);
        lblSaldo = caja(170, 68, 190);

        etiqueta("Cuenta destino", 40, 130);
        txtDestino = campo(40, 152, 360);
        etiqueta("Monto a transferir", 40, 210);
        txtMonto = campo(40, 232, 360);

        JButton btnTransferir = boton("Transferir", 40, 350, 160, 44, true);
        JButton btnVolver = boton("Volver", 220, 350, 160, 44, false);

        btnTransferir.addActionListener(e -> transferir());
        btnVolver.addActionListener(e -> ventana.mostrar("MENU"));
    }

    @Override
    public void alMostrar() {
        lblSaldo.setText(Banco.formatear(ventana.getCuentaActual().getSaldo()));
        txtDestino.setText("");
        txtMonto.setText("");
    }

    private void transferir() {
        try {
            double monto = leerNumero(txtMonto.getText());
            Transaccion transaccion = ventana.getBanco().transferir(
                    ventana.getCuentaActual(), txtDestino.getText().trim(), monto);
            ventana.mostrarComprobante(transaccion);
        } catch (BancoException ex) {
            ventana.mostrarError(ex.getMessage());
        }
    }
}
