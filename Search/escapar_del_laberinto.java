package Search;
// Espacios vacíos representados como 0
// Murallas representadas como 1

// Movimientos posibles: arriba, abajo, izquierda, derecha
// Los bordes del laberinto son solo murallas


// Ejemplo 1
// Entradas:
// maze = [[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]]
// start = [0,4]
// end = [4,4]
// Salida: true
// Explicacion: Un posible camino es : left -> down -> left -> down -> right -> down -> right.

// Ejemplo 2
// Entradas:
// maze = [[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]]
// start = [0,4]
// end = [3,2]
// Salida: false
// Explicacion: No hay un camino que conecte el inicio y el fin.

// Complejidad: O(N * M) donde N es el número de filas y M es el número de columnas del laberinto.


import java.util.*;

public class escapar_del_laberinto {

    private static final int[][] DIRECCIONES = {
        {-1, 0},  // arriba
        {1, 0},   // abajo
        {0, -1},  // izquierda
        {0, 1}    // derecha
    };

    public static boolean MazeEscape(List<List<Integer>> maze, int start_i, int start_j, int end_i, int end_j) {
        int n = maze.size();
        int m = maze.get(0).size();

        if (maze.get(start_i).get(start_j) == 1 || maze.get(end_i).get(end_j) == 1) {
            return false;
        }

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visitado = new boolean[n][m];

        queue.offer(new int[]{start_i, start_j});
        visitado[start_i][start_j] = true;

        while (!queue.isEmpty()) {
            int[] actual = queue.poll();
            int actual_i = actual[0];
            int actual_j = actual[1];

            for (int[] dir : DIRECCIONES) {
                int nuevo_i = actual_i;
                int nuevo_j = actual_j;

                while (nuevo_i + dir[0] >= 0 && nuevo_i + dir[0] < n && 
                       nuevo_j + dir[1] >= 0 && nuevo_j + dir[1] < m && 
                       maze.get(nuevo_i + dir[0]).get(nuevo_j + dir[1]) == 0) {
                    nuevo_i += dir[0];
                    nuevo_j += dir[1];
                }

                if (nuevo_i == end_i && nuevo_j == end_j) {
                    return true;
                }

                if (!visitado[nuevo_i][nuevo_j]) {
                    queue.offer(new int[]{nuevo_i, nuevo_j});
                    visitado[nuevo_i][nuevo_j] = true;
                }
            }
        }
        return false;
    }

    // Método de prueba
    public static void main(String[] args) {
        // Ejemplo 1
        List<List<Integer>> maze1 = Arrays.asList(
            Arrays.asList(0, 0, 1, 0, 0),
            Arrays.asList(0, 0, 0, 0, 0),
            Arrays.asList(0, 0, 0, 1, 0),
            Arrays.asList(1, 1, 0, 1, 1),
            Arrays.asList(0, 0, 0, 0, 0)
        );
        int start_i1 = 0, start_j1 = 4, end_i1 = 4, end_j1 = 4;
        System.out.println(MazeEscape(maze1, start_i1, start_j1, end_i1, end_j1)); // true

        // Ejemplo 2
        List<List<Integer>> maze2 = Arrays.asList(
            Arrays.asList(0, 0, 1, 0, 0),
            Arrays.asList(0, 0, 0, 0, 0),
            Arrays.asList(0, 0, 0, 1, 0),
            Arrays.asList(1, 1, 0, 1, 1),
            Arrays.asList(0, 0, 0, 0, 0)
        );
        int start_i2 = 0, start_j2 = 4, end_i2 = 3, end_j2 = 2;
        System.out.println(MazeEscape(maze2, start_i2, start_j2, end_i2, end_j2)); // false
    }
}





