package main;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PanelLogin extends PanelBase {

    private JTextField txtCedula;
    private JPasswordField txtPin;

    public PanelLogin(VentanaATM ventana) {
        super(ventana);

        JLabel titulo = titulo("Ingrese a su cuenta");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBounds(0, 33, 800, 34);

        etiqueta("Cedula", 200, 100);
        txtCedula = campo(200, 122, 400);
        etiqueta("PIN", 200, 190);
        txtPin = campoClave(200, 212, 400);

        JButton btnIngresar = boton("Ingresar", 200, 300, 190, 44, true);
        JButton btnCancelar = boton("Cancelar", 410, 300, 190, 44, false);
        JButton btnAbrir = boton("<html><u>No tiene cuenta? Abrir cuenta</u></html>", 250, 372, 300, 30, false);
        btnAbrir.setBorder(null);
        btnAbrir.setContentAreaFilled(false);
        btnAbrir.setForeground(new Color(30, 80, 160));

        btnIngresar.addActionListener(e -> ingresar());
        txtPin.addActionListener(e -> ingresar());
        btnCancelar.addActionListener(e -> alMostrar());
        btnAbrir.addActionListener(e -> ventana.mostrar("APERTURA"));
    }

    @Override
    public void alMostrar() {
        txtCedula.setText("");
        txtPin.setText("");
    }

    private void ingresar() {
        try {
            Cuenta cuenta = ventana.getBanco().autenticar(
                    txtCedula.getText().trim(), new String(txtPin.getPassword()));
            ventana.setCuentaActual(cuenta);
            ventana.mostrar("MENU");
        } catch (BancoException ex) {
            ventana.mostrarError(ex.getMessage());
            txtPin.setText("");
        }
    }
}
