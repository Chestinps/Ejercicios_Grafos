package MST;
/*
La misión es pasar por todos los planetas avanzados

Para acotar la distancia por recorrer (y poder cumplir con su misión en menos tiempo), 
la tripulación de puede instalar en la órbita de cada planeta avanzado 
(los planetas no avanzados no tienen los recursos necesarios para este tipo de instalaciones), 
una vez que ya están allí, un portal a la Otra Dimensión. Al entrar a un portal, La tripulación puede salir 
(de forma instantanea) en cualquier otro portal instalado previamente. 
No hay límite sobre la cantidad de portales que Iniciativa puede instalar, 
más allá de poder instalarlos sólo en planetas avanzados.

Se le da una lista de n planetas. Los primeros k son planetas avanzados (los n-k restantes no lo son). 
La misión de Iniciativa parte en el planeta 1. -> Prim


Se van a tener T casos de prueba, cada uno con la siguiente información:
pN: cantidad de planetas
pM: cantidad de carreteras
pK: cantidad de planetas avanzados
pAristas: lista de carreras entre planetas (planeta origen, planeta destino, costo de viaje)

Return: para cada caso de prueba, retornar la distancia mínima que se necesita recorrer

Ejemplo de entrada 1
1 -> T
5 10 3 -> pN, pM, pK
1 2 10 -> Arista 1
1 3 5 -> Arista 2
1 4 10 -> Arista 3
1 5 80 -> Arista 4
2 3 9 -> Arista 5
2 4 1 -> Arista 6
2 5 20 -> Arista 7
3 4 100 -> Arista 8
3 5 2 -> Arista 9
4 5 20 -> Arista 10

Salida: 14
*/


import java.util.*;

public class agujeros_de_gusano {

    // Función para calcular la distancia mínima usando Prim considerando solo planetas avanzados y el planeta 1
    public static int calcularDistancia(int pN, int pM, int pK, List<List<Integer>> pAristas) {
        // Crear el grafo como lista de adyacencia
        List<List<int[]>> grafo = new ArrayList<>();
        for (int i = 0; i < pN; i++) {
            grafo.add(new ArrayList<>());
        }

        // Llenar el grafo con las aristas dadas
        for (List<Integer> arista : pAristas) {
            int u = arista.get(0) - 1; // Planeta origen (ajustado a índice 0)
            int v = arista.get(1) - 1; // Planeta destino (ajustado a índice 0)
            int costo = arista.get(2);
            grafo.get(u).add(new int[]{v, costo});
            grafo.get(v).add(new int[]{u, costo});
        }

        // Usamos un array booleano para saber cuáles son planetas avanzados
        boolean[] esAvanzado = new boolean[pN];
        for (int i = 0; i < pK; i++) {
            esAvanzado[i] = true; // Los primeros pK planetas son avanzados
        }

        // Algoritmo de Prim
        boolean[] visitado = new boolean[pN]; // Para marcar los planetas visitados
        PriorityQueue<int[]> cola = new PriorityQueue<>(Comparator.comparingInt(a -> a[1])); // Min-heap para elegir la arista más corta
        cola.add(new int[]{0, 0}); // Comenzamos desde el planeta 1 (índice 0)
        int distanciaTotal = 0; // La distancia total del MST
        int planetasVisitados = 0; // Contador de planetas avanzados visitados (sin contar no avanzados)

        // Mientras haya planetas avanzados que no hayamos visitado
        while (!cola.isEmpty() && planetasVisitados < pK) {
            int[] actual = cola.poll();
            int planeta = actual[0];
            int costo = actual[1];

            // Si ya hemos visitado este planeta, lo ignoramos
            if (visitado[planeta]) {
                continue;
            }

            // Marcar como visitado
            visitado[planeta] = true;

            // Solo sumamos el costo si el planeta es avanzado o es el planeta 1
            if (esAvanzado[planeta] || planeta == 0) {
                distanciaTotal += costo;
                planetasVisitados++;
            }

            // Añadir todas las aristas del planeta actual a la cola si llevan a un planeta no visitado
            for (int[] vecino : grafo.get(planeta)) {
                int planetaVecino = vecino[0];
                int costoVecino = vecino[1];
                // Solo añadir si no ha sido visitado aún
                if (!visitado[planetaVecino]) {
                    cola.add(new int[]{planetaVecino, costoVecino});
                }
            }
        }

        return distanciaTotal;
    }

    public static void main(String[] args) {
        // Ejemplo de prueba
        int pN = 5; // Número de planetas
        int pM = 10; // Número de carreteras
        int pK = 3; // Número de planetas avanzados

        // Lista de aristas: (planeta origen, planeta destino, costo)
        List<List<Integer>> pAristas = Arrays.asList(
            Arrays.asList(1, 2, 10),
            Arrays.asList(1, 3, 5),
            Arrays.asList(1, 4, 10),
            Arrays.asList(1, 5, 80),
            Arrays.asList(2, 3, 9),
            Arrays.asList(2, 4, 1),
            Arrays.asList(2, 5, 20),
            Arrays.asList(3, 4, 100),
            Arrays.asList(3, 5, 2),
            Arrays.asList(4, 5, 20)
        );

        // Llamar a la función
        int resultado = calcularDistancia(pN, pM, pK, pAristas);
        System.out.println("La distancia mínima que se necesita recorrer es: " + resultado);
    }
}






