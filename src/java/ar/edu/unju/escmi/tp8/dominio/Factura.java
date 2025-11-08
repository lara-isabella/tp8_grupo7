package java.ar.edu.unju.escmi.tp8.dominio;

import java.time.LocalDate;

public class Factura {
    private long id;
    private LocalDate fecha;
    private String domicilio;
    private double total;
    private boolean estado;

    // CONSTRUCTORES

    public Factura() {
    }
    
    public Factura(long id, LocalDate fecha, String domicilio, double total, boolean estado) {
        this.id = id;
        this.fecha = fecha;
        this.domicilio = domicilio;
        this.total = total;
        this.estado = estado;
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

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
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

    // METODOS
    
}
