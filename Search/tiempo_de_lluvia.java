package Search;
/*
    Matriz de N filas y M columnas
    Los edificios se representan con 0
    Las calles en condiciones normales se representan con 1
    Las calles inundadas se representan con 2

    Cada 1 segundo todas las calles normales directamente adyacentes en las cuatro direcciones (arriva, abajo, izquierda, derecha) a una calle inundada se inundarán también.

    Objetivo: Se necesita saber después de cuántos segundos todas las calles se encontrarán bajo el agua.

    Input:
    pN: Número de filas
    pM: Número de columnas
    pMatriz: Matriz de N filas y M columnas
*/

import java.util.*;

public class tiempo_de_lluvia {
    // Algoritmo: BFS
    public static int calcularRespuesta(int pN, int pM, List<List<Integer>> pMatriz) {
        // Write your code here
        int segundos = 0;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        boolean[][] visited = new boolean[pN][pM]; // Para saber si ya visitamos una celda
        boolean[][] inundadas = new boolean[pN][pM]; // Para saber si ya se inundó una celda
        Queue<int[]> queue = new LinkedList<>(); // Para almacenar las celdas que se inundarán
        for (int i = 0; i < pN; i++) { 
            for (int j = 0; j < pM; j++) {
                if (pMatriz.get(i).get(j) == 2) { // Si la celda está inundada
                    queue.add(new int[]{i, j}); // Agregamos la celda a la cola
                    visited[i][j] = true; // Marcamos la celda como visitada
                    inundadas[i][j] = true; // Marcamos la celda como inundada
                }
            }
        }
        while (!queue.isEmpty()) { // Mientras haya celdas que se inundarán
            int size = queue.size(); // Obtenemos la cantidad de celdas que se inundarán en este segundo
            for (int i = 0; i < size; i++) { // Iteramos sobre las celdas que se inundarán
                int[] current = queue.poll(); // Obtenemos la celda actual
                for (int[] dir : directions) { // Iteramos sobre las cuatro direcciones
                    int x = current[0] + dir[0]; // Obtenemos la nueva fila
                    int y = current[1] + dir[1]; // Obtenemos la nueva columna
                    if (x >= 0 && x < pN && y >= 0 && y < pM && !visited[x][y]) { // Si la celda es válida y no ha sido visitada
                        visited[x][y] = true; // Marcamos la celda como visitada
                        if (pMatriz.get(x).get(y) == 1) { // Si la celda es una calle normal
                            pMatriz.get(x).set(y, 2); // La marcamos como inundada
                            inundadas[x][y] = true; // Marcamos la celda como inundada
                            queue.add(new int[]{x, y}); // Agregamos la celda a la cola
                        }
                    }
                }
            }
            segundos++; // Aumentamos el contador de segundos
        }
        for (int i = 0; i < pN; i++) { // Verificamos si quedaron calles normales
            for (int j = 0; j < pM; j++) { 
                if (pMatriz.get(i).get(j) == 1) { // Si la celda es una calle normal
                    return -1; // Retornamos -1
                }
            }
        }
        return segundos + 1; // Retornamos la cantidad de segundos que tardaron en inundarse todas las calles
    }
}
