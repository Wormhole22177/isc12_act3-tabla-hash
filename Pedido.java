
public class Pedido {
    public static final String ANSI_GREEN = "\u001B[32m"; //color
    public static final String ANSI_RESET = "\u001B[0m";
    private final String idPedido; // Esta es la Clave (Key)
    private final String nombreCliente;
    private final double montoTotal;
    private final String platillos; // Descripción breve (ej. "2 Tacos, 1 Soda, oh cualquier cosa")

    public Pedido(
        String idPedido,
        String nombreCliente,
        double montoTotal,
        String platillos
    ) {
        this.idPedido = idPedido;
        this.nombreCliente = nombreCliente;
        this.montoTotal = montoTotal;
        this.platillos = platillos;
    }

    // Getters necesarios
    public String getIdPedido() {
        return idPedido;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    @Override
    public String toString() {
        return String.format( ANSI_GREEN +
            "[ID: %s  ▍ Cliente: %s  ▍ $%.2f  ▍ %s]",
            idPedido,
            nombreCliente,
            montoTotal,
            platillos
        + ANSI_RESET);
    }
}
