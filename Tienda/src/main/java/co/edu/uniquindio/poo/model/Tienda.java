package co.edu.uniquindio.poo.model;

import java.time.LocalDate;
import java.util.*;

/**
 *
 */
public class Tienda {

    private final String nombre;
    private final String nit;// documentar
    private String telefono;

    private final ArrayList<Cliente> listaClientes = new ArrayList<>();
    private final List<Factura> listaFacturas = new LinkedList<>();
    private final Map<String,Producto> hashMaplistaProductos = new HashMap<>();


    public Tienda(String nombre, String nit,String telefono){
        this.nombre = nombre;
        this.nit = nit;
        this.telefono = telefono;
    }

    // setters y getters

    public String getNombre() {
        return nombre;
    }

    public String getNit() {
        return nit;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    //-------------------CRUD CLIENTE---------------------------------------------------------------------------


    /**
     * Este metodo permite registrar un cliente
     * @param cliente
     * @return
     */
    public String registrarCliente(Cliente cliente){
        Cliente clienteEncontrado = buscarCliente1(cliente.getDocumentoIdentidad());
        if(clienteEncontrado == null){
            listaClientes.add(cliente);
            return "El cliente fue registrado exitosamente";
        }else return "No se puede registrar, ya existe un cliente con esa informacion registrado anteriormente.";
    }

    public String registrarCliente2(Cliente cliente){
        Optional<Cliente> clienteEncontrado = buscarCliente2(cliente.getDocumentoIdentidad());
        if(clienteEncontrado.isEmpty()){
            listaClientes.add(cliente);
            return "El cliente fue registrado exitosamente";
        }else return "No se puede registrar, ya existe un cliente con esa informacion registrado anteriormente.";
    }


    public Cliente buscarCliente1(String documentoIdentidad) {
        for (Cliente cliente: listaClientes){//foreach
            if (documentoIdentidad.equals(cliente.getDocumentoIdentidad())){
                return cliente;
            }
        }
        return null;
    }

    /**
     * Esta forma de hacer una busqueda se llama imperativo- significa que usted ve el còmo? hace las cosas
     * @param documentoIdentidad
     * @return
     */
    public Optional<Cliente> buscarCliente2(String documentoIdentidad) {
        for (Cliente cliente :listaClientes){
            if (documentoIdentidad.equals(cliente.getDocumentoIdentidad())){
                return Optional.of(cliente);//guardo en la caja
            }
        }
        return Optional.empty();
    }

    // imperativa y declarativa(estudiarlo)


    /**
     * mama.hacerArrozConPollo();
     * Declarativo: significa que usted declara lo que quiere hacer, osea el Què?
     * @param documentoIdentidad
     * @return
     */
    public Optional<Cliente> buscarCliente3(String documentoIdentidad) {
        return listaClientes.stream().filter(cliente ->
                documentoIdentidad.equals(cliente.getDocumentoIdentidad())).findFirst();
    }



    // Cambiar if(clienteEncontrado == null){ por un Optional
    // hacer el metodo buscar cliente usando un optional

    //-------------------REGISTRO DE PRODUCTOS Y FACTURAS--------------------------------------------------------

    /**
     * Registra un producto en la tienda. El codigo es la clave del mapa.
     * @param producto producto a registrar
     * @return mensaje con el resultado
     */
    public String registrarProducto(Producto producto){
        if(hashMaplistaProductos.containsKey(producto.getCodigo())){
            return "No se puede registrar, ya existe un producto con ese codigo.";
        }
        hashMaplistaProductos.put(producto.getCodigo(), producto);
        return "El producto fue registrado exitosamente";
    }

    /**
     * Registra una factura en la tienda y tambien la agrega a la lista del cliente.
     * @param factura factura a registrar
     * @return mensaje con el resultado
     */
    public String registrarFactura(Factura factura){
        Optional<Factura> facturaEncontrada = obtenerFactura(factura.codigo());
        if(facturaEncontrada.isPresent()){
            return "No se puede registrar, ya existe una factura con ese codigo.";
        }
        listaFacturas.add(factura);
        factura.cliente().agregarFactura(factura);
        return "La factura fue registrada exitosamente";
    }

    public Factura buscarFactura(String codigo){

        for (Factura factura : listaFacturas){
            if(factura.codigo().equals(codigo)){
                return factura;
            }
        }
        return null;
    }

    public Optional<Factura> obtenerFactura(String codigo){
        return listaFacturas.stream().filter(f -> f.codigo().equals(codigo)).findFirst();
    }

    // cacular el valor de la factura (sumar todos los detalles factura)

    //CRUD Cliente, Factura , Producto

    //1. Obtener la lista de los productos con una cantidad disponible mayor igual a 10

    public List<Producto> obtenerMayoresDiez() {
        List<Producto> productosAdecuado = new ArrayList<>();

        for (Producto productosBuenos : hashMaplistaProductos.values()) {
            if (productosBuenos.getCantidadDisponible() >= 10) {
                productosAdecuado.add(productosBuenos);
            }
        }
        return productosAdecuado;
    }
     //2. Obtener la lista de codigos de los productos con una cantidad disponible mayor igual a 10 y menor que 50

    public ArrayList<String> obtenerCodigosProductosAgotados(int limiteInferior, int limiteSuperior){
        ArrayList<String> resultado = new ArrayList<>();
        for(String codigo : hashMaplistaProductos.keySet()){
            Producto producto = hashMaplistaProductos.get(codigo);
            if(producto.getCantidadDisponible() >= limiteInferior && producto.getCantidadDisponible() < limiteSuperior ){
                resultado.add(codigo);
            }
        }
        return resultado;
    }
    public ArrayList<String> obtenerCodigosProductosAgotados2(int limiteInferior, int limiteSuperior){
        ArrayList<String> resultado = new ArrayList<>();
        for (Producto productoAux : hashMaplistaProductos.values()) {
            if (productoAux.getCantidadDisponible() >= limiteInferior && productoAux.getCantidadDisponible() < limiteSuperior) {
                resultado.add(productoAux.getCodigo());
            }
        }
        return resultado;
    }

//2. Obtener la lista de clientes que hayan comprado el 07 de octubre de 2026

    public ArrayList<Cliente> obtenerClientesCompras(){
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        LocalDate fechaConsulta = LocalDate.of(2026,10,7);
        for (Factura factura : listaFacturas){
            if(factura.fecha().isEqual(fechaConsulta)){
                listaClientes.add(factura.cliente());
            }
        }
        return listaClientes;
    }

    public ArrayList<Cliente> obtenerClientesCompras2(LocalDate fechaConsulta){
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        for (Factura factura : listaFacturas){
            if(factura.fecha().isEqual(fechaConsulta)){
                listaClientes.add(factura.cliente());
            }
        }
        return listaClientes;
    }

    public ArrayList<Cliente> obtenerClientesCompras3(LocalDate fechaConsulta){
        ArrayList<Cliente> resultado = new ArrayList<>();
        for(Cliente clienteAux : listaClientes){
            if(clienteAux.isCompraEnFecha(fechaConsulta)){
                resultado.add(clienteAux);
            }
        }
        return resultado;
    }

    //punto 4:   Obtener las facturas que tenga un cliente donde su nombre empiece por R

    public ArrayList<Factura> obtenerFacturasClienteConR(){
        ArrayList<Factura> resultado = new ArrayList<>();

        for (Factura factura : listaFacturas){
            if(factura.tieneClienteConR()){
                resultado.add(factura);
            }
        }
        return resultado;

    }

    //punto 5:   Obtener las facturas donde se haya comprado un celular de marca Iphone 16 pro max

    public ArrayList<Factura> obtenerFacturasConProducto(String nombreProducto){
        ArrayList<Factura> resultado = new ArrayList<>();

        for (Factura factura : listaFacturas){
            if(factura.contieneProducto(nombreProducto)){
                resultado.add(factura);
            }
        }
        return resultado;
    }

     //punto 6:   Obtener las facturas que tenga un cliente
    // donde su nombre sea juan y haya comprado un celular de marca Iphone 16 pro max

    public ArrayList<Factura> obtenerFacturasClienteYProducto(String nombreCliente, String nombreProducto){
        ArrayList<Factura> resultado = new ArrayList<>();

        for (Factura factura : listaFacturas){
            if(factura.cliente().nombreComienzaCon(nombreCliente) && factura.contieneProducto(nombreProducto)){
                resultado.add(factura);
            }
        }
        return resultado;
    }


    // Punto 7: Implementar un método que reciba una categoría
    //  y retorne todos los productos registrados que pertenezcan a ella.

    public ArrayList<Producto> obtenerProductosPorCategoria(Categoria categoria){
        ArrayList<Producto> resultado = new ArrayList<>();

        for (Producto producto : hashMaplistaProductos.values()){
            if(producto.getCategoria() == categoria){
                resultado.add(producto);
            }
        }
        return resultado;
    }

    //punto 8 : Implementar un método que reciba un precio mínimo y un precio máximo, y retorne
    // los productos cuyo precio se encuentre dentro de ese rango, incluyendo ambos límites.

    public ArrayList<Producto> obtenerProductosPorRangoPrecio(double precioMinimo, double precioMaximo){
        ArrayList<Producto> resultado = new ArrayList<>();

        for (Producto producto : hashMaplistaProductos.values()){
            if(producto.getValor() >= precioMinimo && producto.getValor() <= precioMaximo){
                resultado.add(producto);
            }
        }
        return resultado;
    }

    //punto 9: Implementar un método que retorne todos los productos
    // registrados en la tienda, ordenados de menor a mayor según su precio.

    public List<Producto> obtenerProductosOrdenadosPorPrecio(){
        List<Producto> resultado = new ArrayList<>(hashMaplistaProductos.values());//copia, no se toca el mapa
        resultado.sort(Comparator.comparingDouble(Producto::getValor));
        return resultado;
    }

    //punto 10: Implementar un método que identifique el producto con el precio más alto de la tienda.
    // Si no existen productos registrados, el método debe retornar un Optional vacío.

    /**
     * Forma imperativa: se recorre y se guarda el mayor encontrado hasta el momento.
     */
    public Optional<Producto> obtenerProductoMasCaro(){
        Producto masCaro = null;

        for (Producto producto : hashMaplistaProductos.values()){
            if(masCaro == null || producto.getValor() > masCaro.getValor()){
                masCaro = producto;
            }
        }
        return Optional.ofNullable(masCaro);//si no hubo productos, queda vacio
    }

    /**
     * Forma declarativa: se le dice al stream que quiero el maximo segun el precio.
     */
    public Optional<Producto> obtenerProductoMasCaro2(){
        return hashMaplistaProductos.values().stream()
                .max(Comparator.comparingDouble(Producto::getValor));
    }

    //Punto 11: Implementar un método que reciba el nombre de una ciudad y retorne
    // todos los clientes que residan en ella.
    // La búsqueda debe realizarse sin diferenciar entre mayúsculas y minúsculas.

    public ArrayList<Cliente> obtenerClientesPorCiudad(String ciudad){
        ArrayList<Cliente> resultado = new ArrayList<>();

        for (Cliente cliente : listaClientes){
            if(cliente.viveEnCiudad(ciudad)){
                resultado.add(cliente);
            }
        }
        return resultado;
    }


















}
