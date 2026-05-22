import java.util.ArrayList;
import java.util.Scanner;

public class App {

    // Almacenamiento de datos (ArrayLists paralelos)
    static ArrayList<String> nombres = new ArrayList<>();
    static ArrayList<Integer> precios = new ArrayList<>();
    static ArrayList<Integer> stock = new ArrayList<>();

    // Variables de negocio
    static int saldoCaja = 50000; 
    static int saldoUsuario = 0;
    static int ventasTotales = 0;
    static final String CLAVE_ADMIN = "1234";

    static Scanner leer = new Scanner(System.in);

    public static void main(String[] args) {
        
        nombres.add("Papas Margarita (Natural)");
        precios.add(2500);
        stock.add(10);

        nombres.add("Chocoramo");
        precios.add(2200);
        stock.add(8);

        nombres.add("Gaseosa Coca-Cola 400ml");
        precios.add(3000);
        stock.add(15);

        nombres.add("Galletas Festival (Chocolate)");
        precios.add(1200);
        stock.add(20);

        nombres.add("Jugo del Valle (Hit)");
        precios.add(2800);
        stock.add(12);

        nombres.add("Manimoto");
        precios.add(1500);
        stock.add(10);

        boolean salir = false;
        while (!salir) {
            System.out.println("\n========================================");
            System.out.println("   MÁQUINA EXPENDEDORA COLOMBIA (App)");
            System.out.println("========================================");
            System.out.println("Saldo actual: $" + saldoUsuario);
            System.out.println("1. Ver Productos / Comprar");
            System.out.println("2. Ingresar Dinero");
            System.out.println("3. Retirar Cambio / Salir");
            System.out.println("4. Modo Administrador");
            System.out.println("0. Apagar Máquina");
            System.out.print("Seleccione una opción: ");

            String opcion = leer.nextLine();

            switch (opcion) {
                case "1":
                    comprarProducto();
                    break;
                case "2":
                    ingresarDinero();
                    break;
                case "3":
                    devolverCambio();
                    break;
                case "4":
                    autenticarAdmin();
                    break;
                case "0":
                    System.out.println("Apagando... ¡Hasta luego!");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }



    static void comprarProducto() {
        System.out.println("\n--- PRODUCTOS DISPONIBLES ---");
        System.out.printf("%-3s | %-30s | %-10s | %-6s\n", "ID", "Nombre", "Precio", "Stock");
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < nombres.size(); i++) {
            System.out.printf("%-3d | %-30s | $%-9d | %-6d\n", 
                (i + 1), nombres.get(i), precios.get(i), stock.get(i));
        }

        System.out.print("\nIngrese el ID del producto (o 0 para volver): ");
        try {
            int id = Integer.parseInt(leer.nextLine());
            if (id == 0) return;

            int index = id - 1;
            if (index >= 0 && index < nombres.size()) {
                int precio = precios.get(index);
                if (stock.get(index) <= 0) {
                    System.out.println("LO SENTIMOS: Producto agotado.");
                } else if (saldoUsuario < precio) {
                    System.out.println("SALDO INSUFICIENTE. Faltan $" + (precio - saldoUsuario));
                } else {
                    stock.set(index, stock.get(index) - 1);
                    saldoUsuario -= precio;
                    saldoCaja += precio;
                    ventasTotales += precio;
                    System.out.println("\n¡COMPRA EXITOSA! Disfrute su " + nombres.get(index));
                }
            } else {
                System.out.println("ID de producto inexistente.");
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida.");
        }
    }

    static void ingresarDinero() {
        System.out.println("\n--- INGRESAR DINERO ---");
        System.out.println("Aceptamos: $50, $100, $200, $500, $1.000, $2.000, $5.000, $10.000");
        System.out.print("Monto a ingresar: ");
        try {
            int monto = Integer.parseInt(leer.nextLine());
            if (monto == 50 || monto == 100 || monto == 200 || monto == 500 || 
                monto == 1000 || monto == 2000 || monto == 5000 || monto == 10000) {
                saldoUsuario += monto;
                System.out.println("Saldo actualizado: $" + saldoUsuario);
            } else {
                System.out.println("Denominación no aceptada.");
            }
        } catch (Exception e) {
            System.out.println("Monto inválido.");
        }
    }

    static void devolverCambio() {
        if (saldoUsuario > 0) {
            System.out.println("\n=== ENTREGANDO CAMBIO: $" + saldoUsuario + " ===");
            
            // Denominaciones colombianas comunes para vueltas en una máquina
            int[] denominaciones = {10000, 5000, 2000, 1000, 500, 200, 100, 50};
            int restante = saldoUsuario;

            for (int valor : denominaciones) {
                int cantidad = restante / valor;
                if (cantidad > 0) {
                    if (valor >= 1000) {
                        System.out.println("- " + cantidad + " billete(s) de $" + valor);
                    } else {
                        System.out.println("- " + cantidad + " moneda(s) de $" + valor);
                    }
                    restante %= valor;
                }
            }

            if (restante > 0) {
                System.out.println("- Residuo no entregable: $" + restante + " (solo monedas de $50+)");
            }
            
            System.out.println("¡Gracias por su compra!");
            saldoUsuario = 0;
        } else {
            System.out.println("\nNo hay saldo pendiente por retirar.");
        }
    }

    static void autenticarAdmin() {
        System.out.print("\nIngrese clave de administrador: ");
        if (leer.nextLine().equals(CLAVE_ADMIN)) {
            ejecutarMenuAdmin();
        } else {
            System.out.println("Clave incorrecta.");
        }
    }

    static void ejecutarMenuAdmin() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- PANEL DE ADMINISTRACIÓN ---");
            System.out.println("1. Ver Reporte");
            System.out.println("2. Surtir Productos");
            System.out.println("3. Cambiar Precios");
            System.out.println("4. Retirar Dinero de Caja");
            System.out.println("5. Salir del Modo Admin");
            System.out.print("Seleccione una opción: ");

            String opcion = leer.nextLine();
            switch (opcion) {
                case "1":
                    System.out.println("\nVentas: $" + ventasTotales + " | Caja: $" + saldoCaja);
                    for (int i = 0; i < nombres.size(); i++) {
                        System.out.println((i + 1) + ". " + nombres.get(i) + " | Stock: " + stock.get(i));
                    }
                    break;
                case "2":
                    System.out.print("ID producto: ");
                    int idS = Integer.parseInt(leer.nextLine()) - 1;
                    System.out.print("Cantidad: ");
                    stock.set(idS, stock.get(idS) + Integer.parseInt(leer.nextLine()));
                    System.out.println("Stock actualizado.");
                    break;
                case "3":
                    System.out.print("ID producto: ");
                    int idP = Integer.parseInt(leer.nextLine()) - 1;
                    System.out.print("Nuevo Precio: ");
                    precios.set(idP, Integer.parseInt(leer.nextLine()));
                    System.out.println("Precio actualizado.");
                    break;
                case "4":
                    System.out.println("Caja: $" + saldoCaja);
                    System.out.print("Monto a retirar: ");
                    int m = Integer.parseInt(leer.nextLine());
                    if (m <= saldoCaja) { saldoCaja -= m; System.out.println("Retiro exitoso."); }
                    break;
                case "5":
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
