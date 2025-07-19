package xdesign;

import java.io.Serializable;

public class Pedido implements Serializable {

    private static int counter = 0;// Contador estático para asignar un ID único a cada pedido

    private int id;
    private String tipoCamiseta;
    private String talla;
    private int cantidad;
    private String codigoDiseno;
    private String tipoPedido;
    private String direccionEntrega;
    private String formaPago;
    private String numeroTelefonico;
    private String nombreCliente;
    // Atributos de la clase Pedido

    public Pedido(String tipoCamiseta, String talla, int cantidad, String codigoDiseno, String tipoPedido,
            String direccionEntrega, String formaPago, String numeroTelefonico, String nombreCliente) {
        this.id = ++counter;
        this.tipoCamiseta = tipoCamiseta;
        this.talla = talla;
        this.cantidad = cantidad;
        this.codigoDiseno = codigoDiseno;
        this.tipoPedido = tipoPedido;
        this.direccionEntrega = direccionEntrega;
        this.formaPago = formaPago;
        this.numeroTelefonico = numeroTelefonico;
        this.nombreCliente = nombreCliente;
    }

    public Pedido() {
    }

    public static void setCounter(int counter) {
        Pedido.counter = counter;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipoCamiseta(String tipoCamiseta) {
        this.tipoCamiseta = tipoCamiseta;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setCodigoDiseno(String codigoDiseno) {
        this.codigoDiseno = codigoDiseno;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public int getId() {
        return id;
    }

    public String getTipoCamiseta() {
        return tipoCamiseta;
    }

    public String getTalla() {
        return talla;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getCodigoDiseno() {
        return codigoDiseno;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public static int getCounter() {
        return counter;
    }

}
