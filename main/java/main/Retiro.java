package main;

public class Retiro extends Transaccion {

    public Retiro(Cuenta origen, double monto) {
        super(origen, monto);
    }

    @Override
    public String getTipo() {
        return "RETIRO";
    }

    @Override
    public void aplicar() throws BancoException {
        getOrigen().retirar(getMonto());
    }
}
