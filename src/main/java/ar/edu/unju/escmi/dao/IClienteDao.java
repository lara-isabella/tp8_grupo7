package ar.edu.unju.escmi.dao;

import java.util.List;
import ar.edu.unju.escmi.tp8.dominio.Cliente;

public interface IClienteDao {
	public void agregarCliente(Cliente cliente);
	public void modificarCliente(Cliente cliente);
	public List<Cliente> obtenerClientes();
	public Cliente obtenerClientePorId(Long id);

}