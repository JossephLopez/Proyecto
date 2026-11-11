package main;

public class SaldoInsuficienteException extends BancoException {

    public SaldoInsuficienteException() {
        super("Saldo insuficiente para realizar la operacion.");
    }
}
