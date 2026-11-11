package main;

public class Deposito extends Transaccion {

    public Deposito(Cuenta origen, double monto) {
        super(origen, monto);
    }

    @Override
    public String getTipo() {
        return "DEPOSITO";
    }

    @Override
    public void aplicar() throws BancoException {
        getOrigen().depositar(getMonto());
    }
}
