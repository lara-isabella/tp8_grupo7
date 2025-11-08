package java.ar.edu.unju.escmi.tp8.dominio;

public class DetalleFactura {
    private long id;
    private int cantidad;
    private double subtotal;

    // CONSTRUCTORES

    public DetalleFactura() {
    }

    public DetalleFactura(long id, int cantidad, double subtotal) {
        this.id = id;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    // GETTERS Y SETTERS

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public double getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    // METODOS
}
