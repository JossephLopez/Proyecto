package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Transaccion {

    private int id;
    private LocalDateTime fechaHora;
    private Cuenta origen;
    private double monto;

    public Transaccion(Cuenta origen, double monto) {
        this.origen = origen;
        this.monto = monto;
        this.fechaHora = LocalDateTime.now();
    }

    public abstract String getTipo();

    public abstract void aplicar() throws BancoException;

    public String getCuentaDestino() {
        return "N/A";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdTexto() {
        return String.format("%06d", id);
    }

    public String getFechaTexto() {
        return fechaHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public Cuenta getOrigen() {
        return origen;
    }

    public double getMonto() {
        return monto;
    }
}
