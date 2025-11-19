package ar.edu.unju.escmi.tp8.main;

import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.dao.IProductoDao;
import ar.edu.unju.escmi.dao.IFacturaDao;
import ar.edu.unju.escmi.dao.imp.ClienteDaoImp;
import ar.edu.unju.escmi.dao.imp.ProductoDaoImp;
import ar.edu.unju.escmi.dao.imp.FacturaDaoImp;

import ar.edu.unju.escmi.tp8.dominio.Cliente;
import ar.edu.unju.escmi.tp8.dominio.Producto;
import ar.edu.unju.escmi.tp8.dominio.Factura;
import ar.edu.unju.escmi.tp8.dominio.DetalleFactura;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.time.LocalDate;

public class Main {

    private static final IClienteDao clienteDao = new ClienteDaoImp();
    private static final IProductoDao productoDao = new ProductoDaoImp();
    private static final IFacturaDao facturaDao = new FacturaDaoImp();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int op = 0;
        final String ROSA = "\u001B[38;5;205m"; 

        do {
            System.out.println(ROSA + "\n====== MENU PRINCIPAL =====");
            System.out.println("1- Alta cliente");
            System.out.println("2- Alta producto");
            System.out.println("3- Realizar venta");
            System.out.println("4- Buscar factura");
            System.out.println("5- Eliminar factura");
            System.out.println("6- Eliminar cliente ");
            System.out.println("7- Modificar cliente");
            System.out.println("8- Modificar precio producto");
            System.out.println("9- Eliminar producto");
            System.out.println("10- Mostrar facturas");
            System.out.println("11- Mostrar clientes ");
            System.out.println("12- Facturas > $500.000");
            System.out.println("13- Salir");
            System.out.print("Opcion: ");

            try {
                op = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingrese un numero valido.");
                scanner.nextLine();
                continue;
            }

            // USAMOS SWITCH TRADICIONAL (Compatible con todas las versiones de Java)
            switch (op) {
                case 1: altaCliente(scanner); break;
                case 2: altaProducto(scanner); break;
                case 3: realizarVenta(scanner); break;
                case 4: buscarFactura(scanner); break;
                case 5: eliminarFactura(scanner); break;
                case 6: eliminarCliente(scanner); break;
                case 7: modificarCliente(scanner); break;
                case 8: modificarPrecio(scanner); break;
                case 9: eliminarProducto(scanner); break;
                case 10: mostrarFacturas(); break;
                case 11: mostrarClientes(); break;
                case 12: mostrarFacturasAltas(); break;
                case 13: System.out.println("Saliendo..."); break;
                default: System.out.println("Opcion invalida.");
            }

        } while (op != 13);

        scanner.close();
    }

    // ========================== ALTA CLIENTE ==========================
    private static void altaCliente(Scanner scanner) {
        System.out.println("\n=== Alta Cliente ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        Integer dni = null;
        while (dni == null) {
            try {
                System.out.print("DNI: ");
                dni = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Solo numeros para el DNI.");
                scanner.nextLine();
            }
        }
        System.out.print("Domicilio: ");
        String domicilio = scanner.nextLine();

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDni(dni);
        cliente.setDomicilio(domicilio);
        cliente.setEstado(true); // Activo por defecto

        clienteDao.agregarCliente(cliente);
        System.out.println("Cliente agregado con exito.");
    }

    // ========================== ALTA PRODUCTO ==========================
    private static void altaProducto(Scanner scanner) {
        System.out.println("\n=== Alta Producto ===");
        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine();
        double precio = 0;
        try {
            System.out.print("Precio: ");
            precio = scanner.nextDouble();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return;
        }
        Producto producto = new Producto();
        producto.setDescripcion(descripcion);
        producto.setPrecioUnitario(precio);
        producto.setEstado(true);
        productoDao.agregarProducto(producto);
        System.out.println("Producto agregado.");
    }

    // ========================== REALIZAR VENTA ==========================
    private static void realizarVenta(Scanner scanner) {
        System.out.println("\n=== Nueva Venta ===");

        // Validar que existan datos
        List<Cliente> listaC = clienteDao.obtenerClientes();
        if(listaC == null || listaC.isEmpty()) {
            System.out.println("No hay clientes cargados.");
            return;
        }

        System.out.print("ID Cliente: ");
        long idCliente = scanner.nextLong();
        scanner.nextLine();

        Cliente cliente = clienteDao.obtenerClientePorId(idCliente);

        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        // VALIDACIÓN CORREGIDA: Usamos isEstado()
        if (!cliente.isEstado()) {
            System.out.println("ERROR: El cliente esta eliminado y no puede comprar.");
            return;
        }

        Factura factura = new Factura();
        factura.setCliente(cliente);
        factura.setFecha(LocalDate.now());
        factura.setEstado(true);
        factura.setDomicilio(cliente.getDomicilio());

        List<DetalleFactura> detalles = new ArrayList<>();
        boolean continuar = true;

        while (continuar) {
            System.out.print("ID Producto (0 finalizar): ");
            long idProd = scanner.nextLong();
            if (idProd == 0) break;

            Producto p = productoDao.obtenerProductoPorId(idProd);
            
            // ASUMIENDO QUE PRODUCTO TAMBIÉN USA isEstado(), SI NO, CAMBIAR A getEstado()
            if (p == null || !p.isEstado()) { 
                System.out.println("Producto no encontrado o eliminado.");
                continue;
            }

            System.out.print("Cantidad: ");
            int cant = scanner.nextInt();

            DetalleFactura d = new DetalleFactura();
            d.setProducto(p);
            d.setCantidad(cant);
            d.setFactura(factura);
            d.calcularSubtotal();
            detalles.add(d);
        }

        if (!detalles.isEmpty()) {
            factura.setDetalles(detalles);
            factura.calcularTotal();
            facturaDao.agregarFactura(factura);
            System.out.println("Venta completada. Total: $" + factura.getTotal());
        } else {
            System.out.println("Venta cancelada (sin productos).");
        }
    }

    // ========================== BUSCAR FACTURA ==========================
    private static void buscarFactura(Scanner scanner) {
        System.out.print("ID Factura: ");
        long id = scanner.nextLong();
        Factura f = facturaDao.buscarFacturaPorId(id);
        // Asumiendo isEstado() para Factura también
        if (f != null && f.isEstado()) { 
            System.out.println("Factura #" + f.getId());
            System.out.println("Cliente: " + f.getCliente().getNombre());
            System.out.println("Total: " + f.getTotal());
        } else {
            System.out.println("No encontrada o eliminada.");
        }
    }

    // ========================== ELIMINAR FACTURA ==========================
    private static void eliminarFactura(Scanner scanner) {
        System.out.print("ID Factura: ");
        long id = scanner.nextLong();
        Factura f = facturaDao.buscarFacturaPorId(id);
        if (f != null) {
            f.setEstado(false);
            facturaDao.modificarFactura(f);
            System.out.println("Factura eliminada.");
        } else {
            System.out.println("No existe.");
        }
    }

    // ========================== ELIMINAR CLIENTE (LOGICO) ==========================
    private static void eliminarCliente(Scanner scanner) {
        System.out.print("ID Cliente: ");
        long id = scanner.nextLong();
        Cliente c = clienteDao.obtenerClientePorId(id);

        if (c != null) {
            // CORREGIDO: Usamos isEstado()
            if (!c.isEstado()) {
                System.out.println("El cliente ya estaba eliminado.");
            } else {
                c.setEstado(false); // Borrado lógico
                clienteDao.modificarCliente(c);
                System.out.println("Cliente eliminado correctamente.");
            }
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    // ========================== MODIFICAR CLIENTE ==========================
    private static void modificarCliente(Scanner scanner) {
        System.out.print("ID Cliente: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        Cliente c = clienteDao.obtenerClientePorId(id);

        if (c == null) return;

        // CORREGIDO: Usamos isEstado()
        if (!c.isEstado()) {
            System.out.println("No se puede modificar un cliente eliminado.");
            return;
        }

        System.out.print("Nuevo nombre: ");
        c.setNombre(scanner.nextLine());
        System.out.print("Nuevo apellido: ");
        c.setApellido(scanner.nextLine());
        System.out.print("Nuevo domicilio: ");
        c.setDomicilio(scanner.nextLine());
        System.out.print("Nuevo DNI: ");
        c.setDni(scanner.nextInt());
        scanner.nextLine();

        clienteDao.modificarCliente(c);
        System.out.println("Cliente modificado.");
    }

    // ========================== MODIFICAR PRECIO ==========================
    private static void modificarPrecio(Scanner scanner) {
        System.out.print("ID Producto: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        Producto p = productoDao.obtenerProductoPorId(id);
        
        if (p != null && p.isEstado()) { // Asumiendo isEstado para Producto
            System.out.print("Nuevo precio: ");
            p.setPrecioUnitario(scanner.nextDouble());
            productoDao.modificarProducto(p);
            System.out.println("Precio actualizado.");
        } else {
            System.out.println("Producto no encontrado o eliminado.");
        }
    }

    // ========================== ELIMINAR PRODUCTO ==========================
    private static void eliminarProducto(Scanner scanner) {
        System.out.print("ID Producto: ");
        long id = scanner.nextLong();
        Producto p = productoDao.obtenerProductoPorId(id);
        if (p != null) {
            p.setEstado(false);
            productoDao.modificarProducto(p);
            System.out.println("Producto eliminado.");
        }
    }

    // ========================== MOSTRAR FACTURAS ==========================
    public static void mostrarFacturas() {
        List<Factura> lista = facturaDao.obtenerFacturas();
        System.out.println("\n=== FACTURAS ===");
        if (lista != null) {
            for (Factura f : lista) {
                if(f.isEstado()) { // Asumiendo isEstado
                    System.out.println("ID: " + f.getId() + " - Total: $" + f.getTotal());
                }
            }
        }
    }

    // ========================== MOSTRAR CLIENTES ==========================
    public static void mostrarClientes() {
        System.out.println("\n=== CLIENTES ACTIVOS ===");
        List<Cliente> lista = clienteDao.obtenerClientes();

        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay clientes.");
            return;
        }

        for (Cliente c : lista) {
            // CORREGIDO: Usamos isEstado() en lugar de getEstado()
            if (c.isEstado()) { 
                System.out.println("ID: " + c.getId() + " - " + c.getNombre() + " " + c.getApellido());
            }
        }
    }

    // ========================== FACTURAS ALTAS ==========================
    public static void mostrarFacturasAltas() {
        List<Factura> altas = facturaDao.obtenerFacturasMayoresAMedioMillon();

        System.out.println("\n=== FACTURAS MAYORES A $500.000 ===");

        if (altas == null || altas.isEmpty()) {
            System.out.println("Ninguna factura supera $500.000.");
            return;
        }

        altas.forEach(f -> {
            System.out.println("Factura " + f.getId() + " - $" + f.getTotal());
        });
    }
}