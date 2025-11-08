package java.ar.edu.unju.escmi.dao;

public class ClienteDaoImp implements IClienteDao {
    private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

    @Override
    public void guardarCliente(Cliente cliente) {
        try {
            manager.getTransaction().begin();
            manager.persist(cliente);
            manager.getTransaction().commit();
        } catch(Exception c) {
            if (managet.getTransaction()!=null) {
                manager.getTransaction().rollback();
            }
            System.out.println("No se pudo guardar el cliente.");
        }finally {
            manager.close();
        }
    }

    @Override
    public void borrarCliente(Cliente cliente){
        try{
            manager.getTransaction().begin();
            manager.remove(cliente);
            manager.getTransaction().commit();
        }catch (Exception c){
            if (manager.getTransaction()!=null){
                manager.getTransaction().rollback();
            }
            System.out.println("No se pudo eliminar el cliente.");
        }finally{
            manager.close();
        }
    }

    @Override
    public void modificarCliente(Cliente cliente){
        try{
            manager.getTransaction().begin();
            manager.merge(cliente);
            manager.getTransaction().commit();
        }catch (Exception c){
            if (manager.getTransaction()!=null){
                manager.getTransaction().rollback();
            }
            System.out.println("No se pudieron modificar los datos del cliente.");
        }finally{
            manager.close();
        }
    }

    @Override
    public List<Cliente> obtenerClientes(){
        TypedQuery<Cliente> query = manager.createQuery("SELECT c FROM Cliente c", Cliente.class);
        List<Cliente> clientes = query.getResultList();
        return clientes;
    }

    @Override
    public Cliente obtenerCliente(long id){
        return manager.find(Cliente.class, id);
    }
}
