package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public abstract class PanelBase extends JPanel {

    protected static final Color TEXTO = new Color(40, 40, 40);
    protected static final Color GRIS = new Color(120, 120, 120);
    protected static final Color BORDE = new Color(150, 150, 150);
    protected static final Color FONDO_BOTON = new Color(210, 214, 224);

    protected VentanaATM ventana;

    public PanelBase(VentanaATM ventana) {
        this.ventana = ventana;
        setLayout(null);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 450));
    }

    public void alMostrar() {
    }

    protected JLabel titulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.BOLD, 22));
        label.setForeground(TEXTO);
        label.setBounds(40, 30, 700, 34);
        add(label);
        return label;
    }

    protected JLabel etiqueta(String texto, int x, int y) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setForeground(TEXTO);
        label.setBounds(x, y, 340, 20);
        add(label);
        return label;
    }

    protected JLabel textoGris(String texto, int x, int y, int ancho) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));
        label.setForeground(GRIS);
        label.setBounds(x, y, ancho, 20);
        add(label);
        return label;
    }

    protected JLabel caja(int x, int y, int ancho) {
        JLabel label = new JLabel();
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));
        label.setForeground(TEXTO);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE), BorderFactory.createEmptyBorder(0, 6, 0, 6)));
        label.setBounds(x, y, ancho, 24);
        add(label);
        return label;
    }

    protected JTextField campo(int x, int y, int ancho) {
        JTextField campo = new JTextField();
        darEstilo(campo, x, y, ancho);
        return campo;
    }

    protected JPasswordField campoClave(int x, int y, int ancho) {
        JPasswordField campo = new JPasswordField();
        darEstilo(campo, x, y, ancho);
        return campo;
    }

    private void darEstilo(JTextField campo, int x, int y, int ancho) {
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE), BorderFactory.createEmptyBorder(0, 8, 0, 8)));
        campo.setBounds(x, y, ancho, 34);
        add(campo);
    }

    protected JButton boton(String texto, int x, int y, int ancho, int alto, boolean principal) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setForeground(TEXTO);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (principal) {
            boton.setBackground(FONDO_BOTON);
            boton.setFont(new Font("SansSerif", Font.BOLD, 14));
            boton.setBorder(BorderFactory.createLineBorder(TEXTO, 2));
        } else {
            boton.setBackground(Color.WHITE);
            boton.setFont(new Font("SansSerif", Font.PLAIN, 14));
            boton.setBorder(BorderFactory.createLineBorder(TEXTO, 1));
        }
        boton.setBounds(x, y, ancho, alto);
        add(boton);
        return boton;
    }

    protected double leerNumero(String texto) throws BancoException {
        try {
            return Double.parseDouble(texto.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new BancoException("Ingrese un monto valido (solo numeros).");
        }
    }
}
