package main;

import javax.swing.SwingUtilities;

public class Proyecto {

   public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaATM().setVisible(true));
    }
}
