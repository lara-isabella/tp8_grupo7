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
            System.out.println("6- Eliminar cliente");
            System.out.println("7- Modificar cliente");
            System.out.println("8- Modificar precio producto");
            System.out.println("9- Eliminar producto");
            System.out.println("10- Mostrar facturas");
            System.out.println("11- Mostrar clientes");
            System.out.println("12- Facturas > $500.000");
            System.out.println("13- Salir");
            System.out.print("Opción: ");

            op = scanner.nextInt();
            scanner.nextLine();

            switch (op) {

                case 1 -> altaCliente(scanner);
                case 2 -> altaProducto(scanner);
                case 3 -> realizarVenta(scanner);
                case 4 -> buscarFactura(scanner);
                case 5 -> eliminarFactura(scanner);
                case 6 -> eliminarCliente(scanner);
                case 7 -> modificarCliente(scanner);
                case 8 -> modificarPrecio(scanner);
                case 9 -> eliminarProducto(scanner);
                case 10 -> mostrarFacturas();
                case 11 -> mostrarClientes();
                case 12 -> mostrarFacturasAltas();
                case 13 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
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
                System.out.println("Debe ingresar números.");
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
        cliente.setEstado(true);

        clienteDao.agregarCliente(cliente);

        System.out.println("Cliente agregado. ID asignado: " + cliente.getId());
    }

    // ========================== ALTA PRODUCTO ==========================

    private static void altaProducto(Scanner scanner) {

        System.out.println("\n=== Alta Producto ===");

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        System.out.print("Precio: ");
        double precio = scanner.nextDouble();
        scanner.nextLine();

        Producto producto = new Producto();
        producto.setDescripcion(descripcion);
        producto.setPrecioUnitario(precio);
        producto.setEstado(true);

        productoDao.agregarProducto(producto);

        System.out.println("Producto agregado. ID: " + producto.getId());
    }

    // ========================== REALIZAR VENTA ==========================

    private static void realizarVenta(Scanner scanner) {

        System.out.println("\n=== Nueva Venta ===");

        List<Cliente> clientes = clienteDao.obtenerClientes();
        List<Producto> productos = productoDao.obtenerProductos();

        if (clientes == null || clientes.isEmpty()) {
            System.out.println("No hay clientes cargados.");
            return;
        }
        if (productos == null || productos.isEmpty()) {
            System.out.println("No hay productos cargados.");
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

        Factura factura = new Factura();
        factura.setCliente(cliente);
        factura.setFecha(LocalDate.now());
        factura.setEstado(true);
        factura.setDomicilio(cliente.getDomicilio());

        List<DetalleFactura> detalles = new ArrayList<>();

        boolean continuar = true;

        while (continuar) {

            System.out.print("ID Producto (0 para finalizar): ");
            long idProd = scanner.nextLong();

            if (idProd == 0) break;

            Producto p = productoDao.obtenerProductoPorId(idProd);

            if (p == null) {
                System.out.println("No existe ese producto.");
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

        factura.setDetalles(detalles);
        factura.calcularTotal();

        facturaDao.agregarFactura(factura);

        System.out.println("Factura creada. Total = $" + factura.getTotal());
    }

    // ========================== BUSCAR FACTURA ==========================

    private static void buscarFactura(Scanner scanner) {

        System.out.print("ID Factura: ");
        long id = scanner.nextLong();

        Factura f = facturaDao.buscarFacturaPorId(id);

        if (f != null) {
            System.out.println("\n=== FACTURA ===");
            System.out.println("ID: " + f.getId());
            System.out.println("Cliente: " + f.getCliente().getNombre());
            System.out.println("Total: " + f.getTotal());
        } else {
            System.out.println("No encontrada.");
        }
    }

    // ========================== ELIMINAR FACTURA ==========================

    private static void eliminarFactura(Scanner scanner) {

        System.out.print("ID Factura a eliminar: ");
        long id = scanner.nextLong();

        Factura f = facturaDao.buscarFacturaPorId(id);

        if (f != null) {
            f.setEstado(false);
            facturaDao.modificarFactura(f);
            System.out.println("Factura eliminada.");
        } else {
            System.out.println("No existe esa factura.");
        }
    }

    // ========================== MODIFICAR CLIENTE ==========================

    private static void modificarCliente(Scanner scanner) {

        System.out.print("ID Cliente: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        Cliente c = clienteDao.obtenerClientePorId(id);

        if (c == null) {
            System.out.println("No existe ese cliente.");
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

        if (p == null) {
            System.out.println("No existe el producto.");
            return;
        }

        System.out.print("Nuevo precio: ");
        p.setPrecioUnitario(scanner.nextDouble());

        productoDao.modificarProducto(p);

        System.out.println("Precio actualizado.");
    }

    // ========================== ELIMINAR CLIENTE ==========================

    private static void eliminarCliente(Scanner scanner) {

        System.out.print("ID Cliente: ");
        long id = scanner.nextLong();

        Cliente c = clienteDao.obtenerClientePorId(id);

        if (c != null) {
            c.setEstado(false);
            clienteDao.modificarCliente(c);
            System.out.println("Cliente eliminado.");
        } else {
            System.out.println("No existe ese cliente.");
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
        } else {
            System.out.println("No existe ese producto.");
        }
    }

    // ========================== MOSTRAR FACTURAS ==========================

    public static void mostrarFacturas() {
        System.out.println("\n=== TODAS LAS FACTURAS ===");
        List<Factura> lista = facturaDao.obtenerFacturas();

        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay facturas.");
            return;
        }

        lista.forEach(f -> {
            System.out.println("Factura #" + f.getId());
            System.out.println("Cliente: " + f.getCliente().getNombre());
            System.out.println("Total: $" + f.getTotal());
            System.out.println("----------------------");
        });
    }

    // ========================== MOSTRAR CLIENTES ==========================

    public static void mostrarClientes() {
        System.out.println("\n=== CLIENTES ===");
        List<Cliente> lista = clienteDao.obtenerClientes();

        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay clientes.");
            return;
        }

        lista.forEach(c -> {
            System.out.println(c.getId() + " - " + c.getNombre() + " " + c.getApellido());
        });
    }

    // ==========================/ FACTURAS > 500000 /==========================

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
