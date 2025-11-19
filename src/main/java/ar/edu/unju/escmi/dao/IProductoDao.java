package ar.edu.unju.escmi.dao;

import java.util.List;
import ar.edu.unju.escmi.tp8.dominio.Producto;

public interface IProductoDao {
    public void agregarProducto (Producto producto);
    public void modificarProducto (Producto producto);
    public List<Producto> obtenerProductos();
    public Producto obtenerProductoPorId (long id);
}