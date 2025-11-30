/**
 * Implementación de la Tabla Hash.
 * Gestiona la inserción, búsqueda, eliminación y redimensionamiento dinámico.
 */
public class TablaHashCocina {

    //Definiendo los colores que utilizamos en la visulización de programa
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";

    // Atributos de configuraciónb
    private NodoPedido[] tabla; // El arreglo principal
    private int tamanioTabla; // Capacidad actual (M)
    private int numElementos; // Cantidad real de pedidos (n)

    // Factores de carga para activar la redispersión
    // Esto definira el comportamiento de la dinamica de la expansión y reducción
    private final double FACTOR_CARGA_MAX = 0.90; // Al 90% expandemos
    private final double FACTOR_CARGA_MIN = 0.50; // Al 50% reduccimos
    private final int TAMANIO_INICIAL = 5; // Empezamos pequeño para forzar colisiones y se expanda en horizontal

    public TablaHashCocina() {
        this.tamanioTabla = TAMANIO_INICIAL;
        this.tabla = new NodoPedido[TAMANIO_INICIAL];
        this.numElementos = 0;
    }

    //  FUNCIÓN HASH (Algoritmo FNV-1a)

    /**
     * Genera un índice usando el algoritmo Fowler–Noll–Vo (FNV-1a).
     * Este algoritmo logra una dispersión "caótica" y muy aleatoria
     */
    
    private int generarIndice(String clave) {
        // Constantes del algoritmo FNV para 32 bits
        final int FNV_PRIME = 16777619;
        final int FNV_OFFSET_BASIS = -2128831035;

        int hash = FNV_OFFSET_BASIS;

        for (int i = 0; i < clave.length(); i++) {
            // Paso 1: XOR (Mezcla los bits de la letra actual con el hash)
            hash = hash ^ clave.charAt(i);

            // Paso 2: Multiplicación por Primo (Dispersa el resultado)
            hash = hash * FNV_PRIME;
        }
        // Paso 3: Ajustar al tamaño de la tabla (Siempre positivo)
        return Math.abs(hash) % tamanioTabla;
    }

    // Insercion(PUT)
    public void recibirPedido(String clave, Pedido pedido) {
        // A. Verificar si necesitamos crecer (Redispersión Directa)
        if ((double) numElementos / tamanioTabla >= FACTOR_CARGA_MAX) {
            System.out.println(
                    ANSI_RED +
                            ANSI_BOLD +
                            "\n🟥[AVISO] Factor de Carga supera " +
                            FACTOR_CARGA_MAX +
                            ". Redimensionando tabla (Expandiendo)..." +
                            ANSI_RESET
            );
            redimensionar(tamanioTabla * 2);
        }

        int indice = generarIndice(clave);
        NodoPedido nuevoNodo = new NodoPedido(clave, pedido);

        // B. Inserción en la lista enlazada (Manejo de colisiones)
        if (tabla[indice] == null) {
            tabla[indice] = nuevoNodo;
            numElementos++;
        } else {
            // En caso de colisión -> Recorrer la lista
            NodoPedido actual = tabla[indice];
            while (actual != null) {
                if (actual.clave.equals(clave)) {
                    actual.valor = pedido; // Actualizar pedido existente
                    return;
                }
                if (actual.siguiente == null) break; // Llegamos al final
                actual = actual.siguiente;
            }
            // Agregamos al final de la lista
            actual.siguiente = nuevoNodo;
            numElementos++;
        }
    }

    //Operación (Eliminar)
    public boolean entregarPedido(String clave) {
        int indice = generarIndice(clave);
        NodoPedido actual = tabla[indice];
        NodoPedido anterior = null;

        while (actual != null) {
            if (actual.clave.equals(clave)) {
                // Encontrado: Eliminar nodo
                if (anterior == null) {
                    tabla[indice] = actual.siguiente; // Era el primero de la lista
                } else {
                    anterior.siguiente = actual.siguiente; // Saltamos el nodo
                }
                numElementos--;

                // Verificar si necesitamos reducir6
                //  (Redispersión Inversa)
                if (
                        numElementos > 0 &&
                                (double) numElementos / tamanioTabla <= FACTOR_CARGA_MIN &&
                                tamanioTabla > TAMANIO_INICIAL
                ) {
                    System.out.println(
                            ANSI_RED +
                                    ANSI_BOLD +
                                    "\n🟥[AVISO] Factor de Carga bajo " +
                                    FACTOR_CARGA_MIN +
                                    ". Compactando memoria (Reducir)..." +
                                    ANSI_RESET
                    );
                    redimensionar(tamanioTabla / 2);
                }
                return true; // Éxito
            }
            anterior = actual;
            actual = actual.siguiente;
        }
        return false; // No encontrado
    }

    // Operación GET
    public Pedido buscarPedido(String clave) {
        int indice = generarIndice(clave);
        NodoPedido actual = tabla[indice];
        int pasos = 0; // Para fines didácticos

        while (actual != null) {
            pasos++;
            if (actual.clave.equals(clave)) {
                System.out.println(
                        "(Busqueda tomó " + pasos + " pasos en la lista)"
                );
                return actual.valor;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    // Redispersión (REHASHING)

    /**
     * Crea una nueva tabla y reubica TODOS los elementos existentes.
     * Es costoso, pero necesario para mantener la eficiencia.
     */
    private void redimensionar(int nuevoTamano) {
        NodoPedido[] tablaAntigua = tabla;
        tabla = new NodoPedido[nuevoTamano];
        tamanioTabla = nuevoTamano;
        numElementos = 0; // Se recalculará al reinsertar

        // Recorrer la tabla vieja completa
        for (NodoPedido nodo : tablaAntigua) {
            while (nodo != null) {
                // Insertar en la nueva tabla (esto recalcula el hash con el nuevo tamaño)
                recibirPedido(nodo.clave, nodo.valor);
                nodo = nodo.siguiente;
            }
        }
        System.out.println(
                BLUE +
                        ANSI_BOLD +
                        "🔷[SISTEMA] Tabla redimensionada. Nueva capacidad: " +
                        tamanioTabla +
                        ANSI_RESET
        );
    }

    //  Visuzlización
    public void mostrarEstadoCocina() {
        double fc = (double) numElementos / tamanioTabla;
        System.out.println(
                BLUE +
                        "\n⬥⬥⬥ ESTADO VISUAL DE LA COCINA (TABLA HASH ABIERTA) ⬥⬥⬥" +
                        ANSI_RESET
        );
        System.out.printf(
                "Capacidad: %d ▍ Pedidos Activos: %d ▍ Factor de Carga: %.2f\n",
                tamanioTabla,
                numElementos,
                fc
        );
        System.out.println(
                "▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄"
        );

        for (int i = 0; i < tamanioTabla; i++) {
            System.out.printf("[Indice %2d] ", i);
            NodoPedido actual = tabla[i];
            if (actual == null) {
                System.out.print("(Vacio)");
            } else {
                while (actual != null) {
                    System.out.print(
                            BLUE +
                                    " → [" +
                                    actual.valor.getIdPedido() +
                                    "]" +
                                    ANSI_RESET
                    );
                    actual = actual.siguiente;
                }
                System.out.print(
                        YELLOW + " ◾ (Fin de la lista)" + ANSI_RESET
                );
            }
            System.out.println();
        }
        System.out.println(
                "▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄"
        );
    }
}
