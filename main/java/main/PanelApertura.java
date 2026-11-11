package main;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class PanelApertura extends PanelBase {

    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JPasswordField txtPin;
    private JPasswordField txtConfirmar;
    private JTextField txtDeposito;

    public PanelApertura(VentanaATM ventana) {
        super(ventana);

        titulo("Abrir cuenta nueva");

        etiqueta("Cedula (9 digitos)", 40, 80);
        txtCedula = campo(40, 102, 340);
        etiqueta("Nombre completo", 40, 160);
        txtNombre = campo(40, 182, 340);
        etiqueta("Telefono (8 digitos)", 40, 240);
        txtTelefono = campo(40, 262, 340);

        etiqueta("PIN (4 digitos)", 420, 80);
        txtPin = campoClave(420, 102, 340);
        etiqueta("Confirmar PIN", 420, 160);
        txtConfirmar = campoClave(420, 182, 340);
        etiqueta("Deposito inicial", 420, 240);
        txtDeposito = campo(420, 262, 340);

        JButton btnCrear = boton("Crear cuenta", 420, 350, 160, 44, true);
        JButton btnCancelar = boton("Cancelar", 600, 350, 160, 44, false);

        btnCrear.addActionListener(e -> crearCuenta());
        btnCancelar.addActionListener(e -> ventana.mostrar("LOGIN"));
    }

    @Override
    public void alMostrar() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtPin.setText("");
        txtConfirmar.setText("");
        txtDeposito.setText("");
    }

    private void crearCuenta() {
        try {
            double deposito = 0;
            if (!txtDeposito.getText().trim().isEmpty()) {
                deposito = leerNumero(txtDeposito.getText());
            }
            Cuenta cuenta = ventana.getBanco().abrirCuenta(
                    txtCedula.getText().trim(),
                    txtNombre.getText().trim(),
                    txtTelefono.getText().trim(),
                    new String(txtPin.getPassword()),
                    new String(txtConfirmar.getPassword()),
                    deposito);
            ventana.mostrarInfo("Cuenta creada con exito.\nNumero de cuenta: " + cuenta.getNumero()
                    + "\nIngrese con su cedula y su PIN.");
            ventana.mostrar("LOGIN");
        } catch (BancoException ex) {
            ventana.mostrarError(ex.getMessage());
        }
    }
}
