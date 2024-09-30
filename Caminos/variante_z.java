package Caminos;
// pN: número de ciudades
// pM: número de rutas entre ciudades
// pC: número de ciudades con stock de la vacuna
// pV: tiempo que una vacuna puede pasar sin refrigeración

// pCiudadesC: lista de ciudades con stock de la vacuna
// pRutas: lista de rutas entre ciudades (ciudad origen, ciudad destino, tiempo de viaje)
// pS: ciudad de origen
// pT: ciudad de destino

// Return: tiempo mínimo para llegar de la ciudad de origen a la ciudad de destino
// Si no es posible llegar a la ciudad de destino, retornar -1

import java.util.*;

public class variante_z {
    static class Ruta {
        int destino;
        int tiempo;
        
        Ruta(int destino, int tiempo) {
            this.destino = destino;
            this.tiempo = tiempo;
        }
    }

    public static int[] dijkstra(int start, int n, List<List<Ruta>> graph) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        PriorityQueue<Ruta> pq = new PriorityQueue<>(Comparator.comparingInt(r -> r.tiempo));
        pq.add(new Ruta(start, 0));

        while (!pq.isEmpty()) {
            Ruta actual = pq.poll();
            int ciudad = actual.destino;
            int tiempoActual = actual.tiempo;

            if (tiempoActual > dist[ciudad]) {
                continue;
            }

            for (Ruta ruta : graph.get(ciudad)) {
                int nuevoTiempo = tiempoActual + ruta.tiempo;
                if (nuevoTiempo < dist[ruta.destino]) {
                    dist[ruta.destino] = nuevoTiempo;
                    pq.add(new Ruta(ruta.destino, nuevoTiempo));
                }
            }
        }

        return dist;
    }

    public static int calcularTiempo(int pN, int pM, int pC, int pV, List<Integer> pCiudadesC, List<List<Integer>> pRutas, int pS, int pT) {
        // Mapa para almacenar las rutas entre las ciudades
        List<List<Ruta>> grafo = new ArrayList<>();
        for (int i = 0; i < pN; i++) {
            grafo.add(new ArrayList<>());
        }
        
        // Rellenamos el grafo con las rutas proporcionadas
        for (List<Integer> ruta : pRutas) {
            int origen = ruta.get(0) - 1;
            int destino = ruta.get(1) - 1;
            int tiempo = ruta.get(2);
            grafo.get(origen).add(new Ruta(destino, tiempo));
            grafo.get(destino).add(new Ruta(origen, tiempo)); // Es un grafo bidireccional
        }
        
        
        // 1. Calcular distancias desde la ciudad de origen (pS)
        int[] distDesdeOrigen = dijkstra(pS - 1, pN, grafo);
        
        // 2. Encontrar la ruta más corta desde una ciudad con stock hacia el destino (pT)
        int tiempoMinimo = Integer.MAX_VALUE;
        for (int ciudadConStock : pCiudadesC) {
            int[] distDesdeStock = dijkstra(ciudadConStock - 1, pN, grafo);
            if (distDesdeStock[pT - 1] <= pV) { // Verificamos si el tiempo desde la ciudad con stock hasta el destino cumple con el tiempo de refrigeración
                int tiempoTotal = distDesdeOrigen[ciudadConStock - 1] + distDesdeStock[pT - 1];
                tiempoMinimo = Math.min(tiempoMinimo, tiempoTotal);
            }
        }
        
        // Si no hay ninguna ciudad con stock válida o no es posible llegar al destino, retornar -1
        return (tiempoMinimo == Integer.MAX_VALUE) ? -1 : tiempoMinimo;
    }

    public static void main(String[] args) {
        // Ejemplo de prueba
        int pN = 5;
        int pM = 9;
        int pC = 1;
        int pV = 4;
        List<Integer> pCiudadesC = Arrays.asList(5);
        List<List<Integer>> pRutas = Arrays.asList(
                Arrays.asList(3, 5, 3),
                Arrays.asList(2, 4, 3),
                Arrays.asList(3, 2, 4),
                Arrays.asList(1, 4, 4),
                Arrays.asList(1, 5, 2),
                Arrays.asList(4, 3, 3),
                Arrays.asList(2, 5, 5),
                Arrays.asList(1, 2, 4),
                Arrays.asList(4, 5, 1)
        );
        int pS = 1;
        int pT = 4;

        int resultado = calcularTiempo(pN, pM, pC, pV, pCiudadesC, pRutas, pS, pT);
        System.out.println("Tiempo mínimo: " + resultado); // Debería imprimir el tiempo mínimo
    }
}

