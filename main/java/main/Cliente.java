package main;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private String cedula;
    private String nombre;
    private String telefono;
    private List<Cuenta> cuentas = new ArrayList<>();

    public Cliente(String cedula, String nombre, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void agregarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }
}
