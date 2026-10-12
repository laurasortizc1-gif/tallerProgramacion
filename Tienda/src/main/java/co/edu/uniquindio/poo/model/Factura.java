package co.edu.uniquindio.poo.model;

import java.time.LocalDate;
import java.util.List;

public record Factura(String codigo, LocalDate fecha, double total,
                      EstadoFactura estadoFactura, MetodoPago metodoPago, Cliente cliente,
                      List<DetalleFactura> listaDetallesFactura, Tienda ownedByTienda) {


    public boolean tieneClienteConR() {

        boolean resultado = false;
        resultado = cliente.verificarNombreConR();
        return resultado ;


    }

    /**
     * Verifica si en la factura se compro un producto con ese nombre.
     * Recorre los detalles y revisa el producto de cada uno.
     * @param nombreProducto nombre del producto (ej: "Iphone 16 pro max")
     * @return true si algun detalle tiene ese producto
     */
    public boolean contieneProducto(String nombreProducto) {
        for (DetalleFactura detalle : listaDetallesFactura) {
            if (detalle.getProducto().getNombre().equalsIgnoreCase(nombreProducto)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Suma el subtotal de todos los detalles de la factura.
     * @return valor total calculado
     */
    public double calcularTotal() {
        double suma = 0;
        for (DetalleFactura detalle : listaDetallesFactura) {
            suma += detalle.getSubTotal();
        }
        return suma;
    }
}
