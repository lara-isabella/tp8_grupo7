package ar.edu.unju.escmi.dao;

import java.util.List;
import ar.edu.unju.escmi.tp8.dominio.DetalleFactura;

public interface IDetalleFacturaDao {
    //recibe el id de factura
    public List<DetalleFactura> obtenerDetallesFactura (long id);
}