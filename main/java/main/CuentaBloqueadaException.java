package main;

public class CuentaBloqueadaException extends BancoException {

    public CuentaBloqueadaException(String mensaje) {
        super(mensaje);
    }
}
