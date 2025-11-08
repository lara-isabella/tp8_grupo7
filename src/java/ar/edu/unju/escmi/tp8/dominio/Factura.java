package java.ar.edu.unju.escmi.tp8.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="facturas")
public class Factura {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY, unique = true)
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
    private List<DetalleFactura> detalles;


    // CONSTRUCTORES

    public Factura() {
    }
    
    public Factura(long id, LocalDate fecha, double total, boolean estado, String domicilio, Cliente cliente) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.domicilio = domicilio;
        this.cliente = cliente;
        this.detalles = new ArrayList<>();
    }

    // GETTERS Y SETTERS

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    // METODOS


}
