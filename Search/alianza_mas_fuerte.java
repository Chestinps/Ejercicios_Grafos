package Search;
/* 
    pN: Cantidad de nodos (planetas)
    pM: Cantidad de aristas (alianzas)
    pAristas: Corresponde al par de planetas que se aliaron
    pPuntos: capacidade industrial de cada planeta

    Otra cosa a tener en cuenta es que se van a realizar T casos de prueba, por lo que se debe retornar un arreglo de longs con los resultados de cada caso de prueba

    Objetivo: Calcular la alianza más fuerte, es decir, la alianza que tenga la mayor cantidad de puntos

    Ejemplo 1:
    2 -> Cantidad de casos de prueba
    5 2 -> Cantidad de nodos y aristas del primer caso de prueba 1
    4 2 -> Aristas del primer caso de prueba 1
    3 1 -> Aristas del primer caso de prueba 1
    5 1 4 0 3 -> Puntos de cada planeta del primer caso de prueba 1
    5 2 -> Cantidad de nodos y aristas del segundo caso de prueba 2
    2 3 -> Aristas del segundo caso de prueba 2
    3 1 -> Aristas del segundo caso de prueba 2
    1 5 4 0 0 -> Puntos de cada planeta del segundo caso de prueba 2
    

*/


import java.util.*;

public class alianza_mas_fuerte {

    public static long calcularPuntos(int pN, int pM, List<List<Integer>> pAristas, List<Integer> pPuntos) {
        // Write your code here
        long maxPuntos = 0;
        List<List<Integer>> grafo = new ArrayList<>();
        for (int i = 0; i < pN; i++) {
            grafo.add(new ArrayList<>());
        }
        for (int i = 0; i < pM; i++) {
            int u = pAristas.get(i).get(0);
            int v = pAristas.get(i).get(1);
            grafo.get(u).add(v);
            grafo.get(v).add(u);
        }
        for (int i = 0; i < pN; i++) {
            long puntos = 0;
            boolean[] visitados = new boolean[pN];
            Stack<Integer> stack = new Stack<>();
            stack.push(i);
            while (!stack.isEmpty()) {
                int current = stack.pop();
                if (!visitados[current]) {
                    visitados[current] = true;
                    puntos += pPuntos.get(current);
                    for (int vecino : grafo.get(current)) {
                        stack.push(vecino);
                    }
                }
            }
            maxPuntos = Math.max(maxPuntos, puntos);
        }
        return maxPuntos;
    }
}
