package autopartescr;

public class Repuesto {

    //atributos de la clase
    private String nombre;
    private String marca;
    private double precioSinImpuestos;
    private double impuesto;
    private double precioConImpuestos;
    private String categoria;

    //Constructor para crear un nuevo objeto Repuesto
    public Repuesto(String nombre, String marca, double precioSinImpuestos, String categoria) {

        // Inicializar los atributos de la clase
        this.nombre = nombre;
        this.marca = marca;
        this.precioSinImpuestos = precioSinImpuestos;
        this.categoria = categoria;
        this.impuesto = 0;
        this.precioConImpuestos = 0;

    }

    // Métodos getter y setter para los atributos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecioSinImpuestos() {
        return precioSinImpuestos;
    }

    public void setPrecioSinImpuestos(double precioSinImpuestos) {
        this.precioSinImpuestos = precioSinImpuestos;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }

    public double getPrecioConImpuestos() {
        return precioConImpuestos;
    }

    public void setPrecioConImpuestos(double precioConImpuestos) {
        this.precioConImpuestos = precioConImpuestos;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    //Método toString para mostrar los detalles del objeto
    @Override

    public String toString() {
        return String.format("  Nombre:%s  Marca:%s  Precio Sin Impuestos:%.2f  Categoría:%s  Impuesto:%.2f  Precio Con Impuestos:%.2f",
                nombre, marca, precioSinImpuestos, categoria, impuesto, precioConImpuestos);
    }

}
