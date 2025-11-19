package ar.edu.unju.escmi.tp8.dominio;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private long id;

    @Column(name = "nombre_producto", nullable = false, length = 100)
    private String descripcion;

    @Column
    private double precioUnitario;

    @Column(name = "visibilidad")
    private boolean estado;

    // CONSTRUCTORES

    public Producto() {
    }

    public Producto(long id, String descripcion, double precioUnitario, boolean estado) {
        this.id = id;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.estado = estado;
    }

    // GETTERS Y SETTERS

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public double getPrecioUnitario() {
        return precioUnitario;
    }
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    public boolean isEstado() {
        return estado;
    }
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
}

