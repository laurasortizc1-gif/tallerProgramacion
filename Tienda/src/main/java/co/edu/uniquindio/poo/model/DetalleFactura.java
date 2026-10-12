package co.edu.uniquindio.poo.model;

public class DetalleFactura {

    private final int cantidadComprada;
    private final double subTotal;
    private final Producto producto;
    private final Factura ownedByFactura;

    /**
     * El subtotal NO se recibe: se calcula aqui para que nunca quede inconsistente.
     */
    public DetalleFactura(int cantidadComprada, Producto producto, Factura ownedByFactura) {
        this.cantidadComprada = cantidadComprada;
        this.producto = producto;
        this.ownedByFactura = ownedByFactura;
        this.subTotal = calcularSubTotal();
    }

    public double getSubTotal() {
        return subTotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidadComprada() {
        return cantidadComprada;
    }

    public Factura getOwnedByFactura() {
        return ownedByFactura;
    }

    public double calcularSubTotal(){
        return cantidadComprada * producto.getValor();
    }
}
