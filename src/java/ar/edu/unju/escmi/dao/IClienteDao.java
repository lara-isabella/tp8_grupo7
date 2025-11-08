package java.ar.edu.unju.escmi.dao;

import java.ar.edu.unju.escmi.tp8.dominio.Cliente;
import java.util.List;

public interface IClienteDao {
    public void guardarCliente(Cliente cliente);
    public void borrarCliente(Cliente cliente);
    public void modificarCliente(Cliente cliente);
    public List<Cliente> obtenerClientes();
    public Cliente obtenerCliente(long id);

}
