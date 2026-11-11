package main;

public class Transferencia extends Transaccion {

    private Cuenta destino;

    public Transferencia(Cuenta origen, Cuenta destino, double monto) {
        super(origen, monto);
        this.destino = destino;
    }

    @Override
    public String getTipo() {
        return "TRANSFERENCIA";
    }

    @Override
    public String getCuentaDestino() {
        return destino.getNumero();
    }

    @Override
    public void aplicar() throws BancoException {
        getOrigen().retirar(getMonto());
        destino.depositar(getMonto());
    }
}
