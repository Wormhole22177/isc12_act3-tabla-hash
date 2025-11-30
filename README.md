# Proyecto: Sistema de Gestión Dinámica de pedidos y entrega de Cocina  (Tabla Hash Abierta)

## Descripción General
Este proyecto consiste en la implementación manual de una **Tabla Hash Abierta** en Java , diseñada para resolver un **problema real** de alta concurrencia: la gestión de pedidos en curso de una Cocina hipotetica.

El sistema simula el flujo de entrada (recepción) y salida (entrega) de pedidos, gestionando la memoria dinámicamente para mantener la eficiencia operativa.

## Características Técnicas

### 1. Estructura de Datos
* **Tabla Hash Abierta:** Se utiliza el manejo de colisiones mediante **Encadenamiento Separado** (Separate Chaining).
* **Soporte:** Se implementan **listas enlazadas** (`NodoPedido`) como estructura de soporte adecuada para gestionar múltiples elementos en el mismo índice .
* **Sin librerías externas:** No se utilizan clases como `HashMap` o `HashTable` de Java para la lógica principal, cumpliendo con la restricción de implementación propia .

### 2. Tipos de Datos
* **Clave (Key):** Se utiliza un `String` único representando el ID del pedido .
* **Valor (Value):** Se implementa una **clase propia** llamada `Pedido` , la cual encapsula atributos complejos (nombre del cliente, monto, lista de platillos) para enriquecer el modelo de datos.

### 3. Dinámica de Memoria (Redispersión)
El sistema implementa algoritmos de gestión de capacidad basados en el **Factor de Carga** :
* **Redispersión Directa (Crecer):** Cuando el factor de carga supera el 0.90, la tabla duplica su tamaño y reubica todos los nodos para mantener la eficiencia de búsqueda .
* **Redispersión Inversa (Encoger):** Cuando el factor de carga desciende del 0.50 (tras múltiples entregas/borrados), la tabla reduce su tamaño a la mitad para liberar memoria .
* Podemos modificar los valores para ver como se comporta de diferente manera.

### 4. Visualización 
Se incluye una interfaz por consola (CLI) que muestra gráficamente:
* El estado interno de los indices (índices del array).
* Las cadenas de colisión formadas por la lista enlazada.
* El cálculo en tiempo real del factor de carga.

---

