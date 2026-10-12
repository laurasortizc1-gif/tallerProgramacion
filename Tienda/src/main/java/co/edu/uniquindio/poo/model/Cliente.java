package co.edu.uniquindio.poo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cliente {

    private final String documentoIdentidad;
    private final String nombreCompleto;
    private final Tienda ownedByTienda;
    private final String telefono;
    private final String correo;
    private final String ciudadResidencia;
    private final List<Factura> listaFacturas;

    public Cliente(String documentoIdentidad, String nombreCompleto,
                   Tienda ownedByTienda, String telefono,
                   String correo, String ciudadResidencia) {

        this.documentoIdentidad = documentoIdentidad;
        this.nombreCompleto = nombreCompleto;
        this.ownedByTienda = ownedByTienda;
        this.telefono = telefono;
        this.correo = correo;
        this.ciudadResidencia = ciudadResidencia;
        this.listaFacturas = new ArrayList<>();
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public List<Factura> getListaFacturas() {
        return Collections.unmodifiableList(listaFacturas);
    }

    public Tienda getOwnedByTienda() {
        return ownedByTienda;
    }

    /**
     * Agrega una factura a la lista del cliente.
     * Lo llama la Tienda cuando registra una factura nueva.
     * @param factura factura del cliente
     */
    public void agregarFactura(Factura factura) {
        listaFacturas.add(factura);
    }

    public boolean isCompraEnFecha(LocalDate fechaConsulta) {
        boolean comproFecha = false;
        for (Factura factura : listaFacturas){
            if(factura.fecha().isEqual(fechaConsulta)){
                return true;
            }
        }
        return comproFecha;
    }

    public boolean verificarNombreConR() {
        return nombreCompleto.startsWith("R");
    }

    /**
     * Verifica si el nombre del cliente empieza por un texto, sin importar mayusculas.
     * @param prefijo texto con el que debe empezar el nombre (ej: "Juan")
     * @return true si el nombre empieza por ese texto
     */
    public boolean nombreComienzaCon(String prefijo) {
        return nombreCompleto.toLowerCase().startsWith(prefijo.toLowerCase());
    }

    /**
     * Verifica si el cliente vive en una ciudad, sin importar mayusculas.
     * @param ciudad ciudad a comparar
     * @return true si es la misma ciudad
     */
    public boolean viveEnCiudad(String ciudad) {
        return ciudadResidencia.equalsIgnoreCase(ciudad);
    }
}
