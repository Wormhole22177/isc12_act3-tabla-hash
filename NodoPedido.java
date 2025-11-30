/**
 * Clase de Soporte: Nodo para la lista enlazada.
 * Se usa para manejar las colisiones dentro de cada índice de la tabla hash.
 */
public class NodoPedido {
    String clave;       // Guardamos la clave por separado para búsquedas rápidas
    Pedido valor;       // El objeto Pedido
    NodoPedido siguiente; // Puntero al siguiente nodo (Manejo de colisiones)

    public NodoPedido(String clave, Pedido valor) {
        this.clave = clave;
        this.valor = valor;
        this.siguiente = null;
    }
}