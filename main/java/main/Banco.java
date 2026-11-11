package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Banco {

    private Map<String, Cliente> clientes = new HashMap<>();
    private Map<String, Cuenta> cuentas = new HashMap<>();
    private List<Transaccion> transacciones = new ArrayList<>();

    public Cuenta abrirCuenta(String cedula, String nombre, String telefono, String pin,
                              String confirmarPin, double depositoInicial) throws BancoException {
        if (cedula.isEmpty() || nombre.isEmpty() || telefono.isEmpty()) {
            throw new BancoException("Complete la cedula, el nombre y el telefono.");
        }
        if (!cedula.matches("\\d{9}")) {
            throw new BancoException("La cedula debe tener 9 digitos.");
        }
        if (!telefono.matches("\\d{8}")) {
            throw new BancoException("El telefono debe tener 8 digitos.");
        }
        if (!pin.matches("\\d{4}")) {
            throw new BancoException("El PIN debe tener 4 digitos.");
        }
        if (!pin.equals(confirmarPin)) {
            throw new BancoException("Los PIN no coinciden.");
        }
        if (Double.isNaN(depositoInicial) || Double.isInfinite(depositoInicial) || depositoInicial < 0) {
            throw new BancoException("El deposito inicial no puede ser negativo.");
        }

        if (clientes.containsKey(cedula)) {
            throw new BancoException("Ya existe una cuenta con esa cedula.");
        }
        Cliente cliente = new Cliente(cedula, nombre, telefono);
        clientes.put(cedula, cliente);
        String numero = String.valueOf(1000000001L + cuentas.size());
        Cuenta cuenta = new Cuenta(numero, pin, depositoInicial, cliente);
        cliente.agregarCuenta(cuenta);
        cuentas.put(numero, cuenta);
        return cuenta;
    }

    public Cuenta autenticar(String cedula, String pin) throws BancoException {
        Cliente cliente = clientes.get(cedula);
        if (cliente == null) {
            throw new BancoException("Cedula o PIN incorrecto.");
        }
        Cuenta cuenta = cliente.getCuentas().get(0);
        if (cuenta.isBloqueada()) {
            throw new CuentaBloqueadaException("La cuenta esta bloqueada. Comuniquese con el banco.");
        }
        if (!cuenta.validarPin(pin)) {
            cuenta.registrarFallo();
            if (cuenta.isBloqueada()) {
                throw new CuentaBloqueadaException("PIN incorrecto. La cuenta fue bloqueada tras "
                        + Cuenta.MAX_INTENTOS + " intentos fallidos.");
            }
            int restantes = Cuenta.MAX_INTENTOS - cuenta.getIntentosFallidos();
            throw new BancoException("Cedula o PIN incorrecto. Intentos restantes: " + restantes);
        }
        cuenta.reiniciarIntentos();
        return cuenta;
    }

    public Transaccion retirar(Cuenta cuenta, double monto) throws BancoException {
        return ejecutar(new Retiro(cuenta, monto));
    }

    public Transaccion depositar(Cuenta cuenta, double monto) throws BancoException {
        return ejecutar(new Deposito(cuenta, monto));
    }

    public Transaccion transferir(Cuenta origen, String numeroDestino, double monto) throws BancoException {
        Cuenta destino = cuentas.get(numeroDestino);
        if (destino == null) {
            throw new BancoException("La cuenta destino no existe.");
        }
        if (destino == origen) {
            throw new BancoException("No puede transferir a la misma cuenta.");
        }
        return ejecutar(new Transferencia(origen, destino, monto));
    }

    private Transaccion ejecutar(Transaccion transaccion) throws BancoException {
        transaccion.aplicar();
        transaccion.setId(transacciones.size() + 1);
        transacciones.add(transaccion);
        return transaccion;
    }

    public Transaccion ultimaTransaccion(Cuenta cuenta) {
        for (int i = transacciones.size() - 1; i >= 0; i--) {
            if (transacciones.get(i).getOrigen() == cuenta) {
                return transacciones.get(i);
            }
        }
        return null;
    }

    public Path generarComprobante(Transaccion t) throws IOException {
        Path carpeta = Paths.get("comprobantes");
        Files.createDirectories(carpeta);
        Path archivo = carpeta.resolve("COMP-" + t.getIdTexto() + ".txt");
        String texto = String.join(System.lineSeparator(),
                "FideBank ATM - Comprobante de la transaccion",
                "--------------------------------------------",
                "N. de transaccion: " + t.getIdTexto(),
                "Fecha y hora:      " + t.getFechaTexto(),
                "Tipo:              " + t.getTipo(),
                "Cuenta origen:     " + t.getOrigen().getNumero(),
                "Cuenta destino:    " + t.getCuentaDestino(),
                "Monto:             " + formatear(t.getMonto()),
                "");
        Files.writeString(archivo, texto);
        return archivo;
    }

    public static String formatear(double monto) {
        return String.format(Locale.US, "CRC %.2f", monto);
    }
}
