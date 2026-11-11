package main;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PanelMonto extends PanelBase {

    private boolean esRetiro;
    private JLabel lblSaldo;
    private JTextField txtMonto;

    public PanelMonto(VentanaATM ventana, boolean esRetiro) {
        super(ventana);
        this.esRetiro = esRetiro;

        titulo(esRetiro ? "Retirar dinero" : "Depositar dinero");
        textoGris("Saldo disponible:", 40, 70, 130);
        lblSaldo = caja(170, 68, 190);

        etiqueta(esRetiro ? "Monto a retirar" : "Monto a depositar", 40, 140);
        txtMonto = campo(40, 162, 320);

        JButton btnAccion = boton(esRetiro ? "Retirar" : "Depositar", 40, 350, 160, 44, true);
        JButton btnVolver = boton("Volver", 220, 350, 160, 44, false);

        btnAccion.addActionListener(e -> ejecutar());
        txtMonto.addActionListener(e -> ejecutar());
        btnVolver.addActionListener(e -> ventana.mostrar("MENU"));
    }

    @Override
    public void alMostrar() {
        lblSaldo.setText(Banco.formatear(ventana.getCuentaActual().getSaldo()));
        txtMonto.setText("");
    }

    private void ejecutar() {
        try {
            double monto = leerNumero(txtMonto.getText());
            Banco banco = ventana.getBanco();
            Cuenta cuenta = ventana.getCuentaActual();
            Transaccion transaccion = esRetiro ? banco.retirar(cuenta, monto) : banco.depositar(cuenta, monto);
            ventana.mostrarComprobante(transaccion);
        } catch (BancoException ex) {
            ventana.mostrarError(ex.getMessage());
        }
    }
}
