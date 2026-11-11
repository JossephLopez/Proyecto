package main;

import javax.swing.JButton;
import javax.swing.JLabel;

public class PanelMenu extends PanelBase {

    private JLabel lblCuenta;

    public PanelMenu(VentanaATM ventana) {
        super(ventana);

        titulo("Menu principal");
        textoGris("Cuenta:", 40, 70, 60);
        lblCuenta = caja(100, 68, 200);

        JButton btnSaldo = boton("Consultar saldo", 40, 120, 225, 90, true);
        JButton btnRetirar = boton("Retirar", 287, 120, 225, 90, true);
        JButton btnDepositar = boton("Depositar", 534, 120, 225, 90, true);
        JButton btnTransferir = boton("Transferir", 40, 240, 225, 90, true);
        JButton btnComprobante = boton("Comprobante", 287, 240, 225, 90, true);
        JButton btnSalir = boton("Salir", 534, 240, 225, 90, false);

        btnSaldo.addActionListener(e -> ventana.mostrarInfo("Saldo disponible: "
                + Banco.formatear(ventana.getCuentaActual().getSaldo())));
        btnRetirar.addActionListener(e -> ventana.mostrar("RETIRO"));
        btnDepositar.addActionListener(e -> ventana.mostrar("DEPOSITO"));
        btnTransferir.addActionListener(e -> ventana.mostrar("TRANSFERENCIA"));
        btnComprobante.addActionListener(e -> verUltimoComprobante());
        btnSalir.addActionListener(e -> ventana.cerrarSesion());
    }

    @Override
    public void alMostrar() {
        lblCuenta.setText(ventana.getCuentaActual().getNumero());
    }

    private void verUltimoComprobante() {
        Transaccion ultima = ventana.getBanco().ultimaTransaccion(ventana.getCuentaActual());
        if (ultima == null) {
            ventana.mostrarInfo("Aun no ha realizado transacciones.");
        } else {
            ventana.mostrarComprobante(ultima);
        }
    }
}
