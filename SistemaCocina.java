import java.util.Scanner;

/**
 * Clase Principal: Interfaz por consola.
 */
public class SistemaCocina {

    // Colores
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String ANSI_ITALIC = "\u001B[3m";
    public static final String YELLOW = "\u001B[33m";



    public static void main(String[] args) {
        TablaHashCocina cocina = new TablaHashCocina();
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        System.out.println(
            CYAN +
                ANSI_BOLD +
                "█ Implementación de una Tabla Hash --- Gestión de pedidos y entrega de Cocina " +
                ANSI_RESET
        );
        System.out.println(
            ANSI_GREEN +
                ANSI_BOLD +
                "█ Desarrollado por el equipo --- B61" +
                ANSI_RESET
        );
        System.out.println(
                YELLOW +
                        ANSI_ITALIC +
                        "✱ Miguel Angel Jasso Robledo \n✱ Diego ALberto Rojas Sánchez  \n✱ Giovanni Ruiz Benitez" +
                        ANSI_RESET
        );

        while (!salir) {
            System.out.println(BLUE + "\n◆◆◆ MENÚ PRINCIPAL ◆◆◆" + ANSI_RESET);
            System.out.println("1. Recibir nuevo pedido → (Insertar)");
            System.out.println("2. Entregar pedido al repartidor → (Eliminar)");
            System.out.println("3. Buscar detalles de un pedido");
            System.out.println(
                "4. Ver estado de la cocina → (Estructura Hash)"
            );
            System.out.println("5. Simulación Masiva → (Carga rápida)");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print(
                        "Ingrese ID del Pedido (ej. Tacos-01,Enchiladas-02,Sushi-03,Tlacoyos-542,Quesadilla-41212,Elotes-542341,etc...): "
                    );
                    String id = scanner.nextLine();
                    System.out.print("Nombre del Cliente: ");
                    String cliente = scanner.nextLine();
                    System.out.print("Platillos: ");
                    String platos = scanner.nextLine();
                    // Simulamos precio aleatorio
                    double precio = Math.random() * 50 + 10;

                    Pedido nuevo = new Pedido(id, cliente, precio, platos);
                    cocina.recibirPedido(id, nuevo);
                    System.out.println(
                        ANSI_GREEN +
                            ANSI_BOLD +
                            "▶ Pedido ingresado correctamente." +
                            ANSI_RESET
                    );
                    break;
                case "2":
                    System.out.print("Ingrese ID del Pedido a entregar: ");
                    String idBorrar = scanner.nextLine();
                    boolean eliminado = cocina.entregarPedido(idBorrar);
                    if (eliminado) {
                        System.out.println(
                            ANSI_GREEN +
                                ANSI_BOLD +
                                "▶ Pedido " +
                                idBorrar +
                                " entregado y eliminado del sistema." +
                                ANSI_RESET
                        );
                    } else {
                        System.out.println(
                            ANSI_RED +
                                "❌ Error: El pedido no existe." +
                                ANSI_RESET
                        );
                    }
                    break;
                case "3":
                    System.out.print("Ingrese ID a buscar: ");
                    String idBuscar = scanner.nextLine();
                    Pedido encontrado = cocina.buscarPedido(idBuscar);
                    if (encontrado != null) {
                        System.out.println("🔍 DATOS ➔ " + encontrado);
                    } else {
                        System.out.println(
                            ANSI_RED + "❌ Pedido no encontrado." + ANSI_RESET
                        );
                    }
                    break;
                case "4":
                    cocina.mostrarEstadoCocina();
                    break;
                case "5":
                    System.out.println(
                        "Generando 10 pedidos automáticos para probar redispersión..."
                    );
                    for (int z = 1; z <= 10; z++) {
                        String autoId = "AUTO-" + z;
                        cocina.recibirPedido(
                            autoId,
                            new Pedido(autoId, "ClienteAuto", 100, "ComboAuto")
                        );
                    }
                    System.out.println(
                        ANSI_GREEN +
                            "✅ Carga masiva completada. Revise el estado de la cocina." +
                            ANSI_RESET
                    );
                    break;
                case "6":
                    salir = true;
                    System.out.println(
                        BLUE + "Cerrando sistema..." + ANSI_RESET
                    );
                    break;
                default:
                    System.out.println(
                        ANSI_RED + "Opción no válida." + ANSI_RESET
                    );
            }
        }
        scanner.close();
    }
}
