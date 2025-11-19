package ar.edu.unju.escmi.tp8.dominio;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private LocalDate fecha;

    @Column
    private double total;

    @Column
    private boolean estado;

    @Column
    private String domicilio;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleFactura> detalles = new ArrayList<>();

    
    // CONSTRUCTORES  
    public Factura() {
        this.fecha = LocalDate.now();
    }

    public Factura(long id, LocalDate fecha, double total, boolean estado, String domicilio, Cliente cliente) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.domicilio = domicilio;
        this.cliente = cliente;
    }


    // GETTERS Y SETTERS

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<DetalleFactura> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleFactura> detalles) { this.detalles = detalles; }

    // MÉTODOS 

    public void calcularTotal() {
        double suma = 0;

        for (DetalleFactura det : detalles) {
            suma += det.getSubtotal();
        }

        this.total = suma;

        System.out.println("Total de la factura: " + this.total);
    }

}
