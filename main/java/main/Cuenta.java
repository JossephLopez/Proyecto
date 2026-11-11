package main;

public class Cuenta {

    public static final int MAX_INTENTOS = 3;

    private String numero;
    private String pin;
    private double saldo;
    private boolean bloqueada;
    private int intentosFallidos;
    private Cliente titular;

    public Cuenta(String numero, String pin, double saldoInicial, Cliente titular) {
        this.numero = numero;
        this.pin = pin;
        this.saldo = saldoInicial;
        this.titular = titular;
    }

    public boolean validarPin(String pinIngresado) {
        return pin.equals(pinIngresado);
    }

    public void registrarFallo() {
        intentosFallidos++;
        if (intentosFallidos >= MAX_INTENTOS) {
            bloqueada = true;
        }
    }

    public void reiniciarIntentos() {
        intentosFallidos = 0;
    }

    public void depositar(double monto) throws BancoException {
        validarMonto(monto);
        saldo += monto;
    }

    public void retirar(double monto) throws BancoException {
        validarMonto(monto);
        if (monto > saldo) {
            throw new SaldoInsuficienteException();
        }
        saldo -= monto;
    }

    private void validarMonto(double monto) throws BancoException {
        if (Double.isNaN(monto) || Double.isInfinite(monto) || monto <= 0) {
            throw new BancoException("El monto debe ser un numero mayor a 0.");
        }
    }

    public String getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean isBloqueada() {
        return bloqueada;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public Cliente getTitular() {
        return titular;
    }
}
