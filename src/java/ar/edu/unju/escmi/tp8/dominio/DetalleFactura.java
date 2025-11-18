package java.ar.edu.unju.escmi.tp8.dominio;

import jakarta.persistence.*;

@Entity
@Table(name="detalles")
public class DetalleFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int cantidad;

    @Column
    private double subtotal;

    @ManyToOne
    @JoinColumn(name = "factura_id")
    private Factura factura;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    // CONSTRUCTORES

    public DetalleFactura() {}

    public DetalleFactura(long id, int cantidad, double subtotal) {
        this.id = id;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    // GETTERS Y SETTERS

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    // MÉTODOS

    public void calcularSubtotal() {
        if (producto != null) {
            this.subtotal = this.cantidad * producto.getPrecioUnitario();
        }
    }

}

