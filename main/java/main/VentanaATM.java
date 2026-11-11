package main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VentanaATM extends JFrame {

    private Banco banco = new Banco();
    private Cuenta cuentaActual;
    private Transaccion transaccionActual;

    private CardLayout tarjetas = new CardLayout();
    private JPanel contenedor = new JPanel(tarjetas);
    private JLabel lblPantalla = new JLabel();
    private Map<String, PanelBase> paneles = new HashMap<>();
    private Map<String, String> titulos = new HashMap<>();

    public VentanaATM() {
        super("FideBank ATM");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        add(crearEncabezado(), BorderLayout.NORTH);
        add(contenedor, BorderLayout.CENTER);

        agregarPanel("LOGIN", "Ingreso", new PanelLogin(this));
        agregarPanel("APERTURA", "Abrir cuenta", new PanelApertura(this));
        agregarPanel("MENU", "Menu", new PanelMenu(this));
        agregarPanel("RETIRO", "Retiro", new PanelMonto(this, true));
        agregarPanel("DEPOSITO", "Deposito", new PanelMonto(this, false));
        agregarPanel("TRANSFERENCIA", "Transferencia", new PanelTransferencia(this));
        agregarPanel("COMPROBANTE", "Comprobante", new PanelComprobante(this));

        pack();
        setLocationRelativeTo(null);
        mostrar("LOGIN");
    }

    private JPanel crearEncabezado() {
        JPanel encabezado = new JPanel(null);
        encabezado.setPreferredSize(new Dimension(800, 50));
        encabezado.setBackground(new Color(225, 228, 235));
        encabezado.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(40, 40, 40)));

        JLabel logo = new JLabel("FideBank ATM");
        logo.setFont(new Font("SansSerif", Font.BOLD, 16));
        logo.setForeground(new Color(40, 40, 40));
        logo.setBounds(20, 0, 300, 49);
        encabezado.add(logo);

        lblPantalla.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblPantalla.setForeground(new Color(120, 120, 120));
        lblPantalla.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPantalla.setBounds(440, 0, 340, 49);
        encabezado.add(lblPantalla);
        return encabezado;
    }

    private void agregarPanel(String nombre, String titulo, PanelBase panel) {
        paneles.put(nombre, panel);
        titulos.put(nombre, titulo);
        contenedor.add(panel, nombre);
    }

    public void mostrar(String nombre) {
        lblPantalla.setText(titulos.get(nombre));
        paneles.get(nombre).alMostrar();
        tarjetas.show(contenedor, nombre);
    }

    public void mostrarComprobante(Transaccion transaccion) {
        transaccionActual = transaccion;
        mostrar("COMPROBANTE");
    }

    public void cerrarSesion() {
        cuentaActual = null;
        transaccionActual = null;
        mostrar("LOGIN");
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "FideBank ATM", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarInfo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "FideBank ATM", JOptionPane.INFORMATION_MESSAGE);
    }

    public Banco getBanco() {
        return banco;
    }

    public Cuenta getCuentaActual() {
        return cuentaActual;
    }

    public void setCuentaActual(Cuenta cuentaActual) {
        this.cuentaActual = cuentaActual;
    }

    public Transaccion getTransaccionActual() {
        return transaccionActual;
    }
}
