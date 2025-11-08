package java.ar.edu.unju.escmi.tp8.dominio;

public class Producto {
    private long id;
    private String descripcion;
    private double precioUnitario;
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
    
    // METODOS
}
