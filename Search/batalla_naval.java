package Search;
/*  Listo
    M: Cantida de filas
    N: Cantidad de columnas
    Barcos: [1]
    Vacíos: [0]

    Objetivo: Encontrar cual es el area que cubre el barco mas grande
    Si no hay barcos en el tablero, retornar 0

    Input:
    M N
    M filas con N columnas con 0 o 1

    Output: Area del barco mas grande

    Ejemplo 1:
    3 3
    0 0 0
    0 0 0
    0 0 0
    Salida: 0

    Ejemplo 2:
    8 5
    0 1 1 0 0
    0 0 0 1 0
    0 0 0 0 1
    0 0 0 0 0
    0 1 1 0 0
    1 1 0 0 0
    0 0 1 1 1
    0 0 0 0 0
    Salida: 4

    Ejemplo 3:
    4 12
    0 0 0 0 0 0 1 0 1 0 0 0
    0 0 0 1 0 0 0 1 0 0 0 1
    0 0 1 0 0 0 1 0 1 0 0 0
    1 0 0 1 0 0 0 1 0 0 1 0
    Salida: 1

    Ejemplo 4:
    4 18
    0 0 1 1 0 0 0 0 0 0 0 1 0 0 0 0 0 1
    0 0 0 1 0 0 0 0 0 0 0 1 0 1 0 0 0 0
    0 0 1 0 1 1 0 0 0 1 0 0 0 0 0 0 0 0
    0 0 1 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0
    Salida: 3

    Ejemplo 5:
    31 36
    0 0 1 0 0 0 0 0 1 1 0 0 0 0 1 1 0 0 0 0 0 0 0 0 1 0 1 1 0 0 0 0 1 1 0 0
    0 0 0 0 0 0 0 0 1 0 1 0 0 0 0 1 0 0 0 0 1 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0
    0 0 1 0 0 1 0 1 0 1 0 1 0 1 0 0 0 0 1 0 0 1 0 1 0 0 1 0 0 0 0 0 1 0 0 0
    0 1 0 1 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1 1 0 0 0 0 0 0 0
    1 0 1 0 0 0 0 0 0 0 1 0 1 0 0 0 0 1 1 0 0 0 1 0 0 0 0 0 1 1 0 0 0 0 0 0
    1 0 0 0 1 0 0 0 0 0 0 0 0 1 0 0 1 0 1 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0
    0 1 0 1 1 0 1 0 1 0 0 1 0 0 0 1 1 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0
    0 0 0 0 1 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0
    0 0 0 1 0 1 0 0 0 0 0 0 0 0 0 1 1 1 0 0 0 0 0 0 1 0 0 0 1 0 0 0 1 0 0 0
    0 0 0 1 1 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0 1 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0
    0 0 0 0 1 1 0 0 0 0 1 1 0 0 1 1 0 0 0 0 0 1 0 0 1 1 0 0 0 0 0 0 1 1 0 0
    0 0 0 0 0 0 1 0 0 0 0 0 1 0 0 1 0 0 0 1 0 0 0 0 0 0 1 0 1 0 0 1 1 0 0 0
    0 0 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 0 0 0 0 0 1 0 1 0 1 0 0 0 1 0 1 0 0 0
    1 1 1 0 0 0 1 0 0 0 0 1 0 1 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 1 0 0 0 1 0
    0 0 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 1 0 1 0 1 0 0 0 0 0 1 0 0 0
    0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 1 0 0 0 0 1 0 1 0
    0 0 1 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 1
    1 0 0 0 0 0 1 0 0 1 0 1 1 0 0 1 0 0 0 1 0 1 1 1 0 0 0 0 0 1 0 0 0 0 0 0
    1 1 0 0 1 1 0 0 0 0 0 0 0 1 0 1 1 0 0 0 1 0 0 0 1 1 0 1 1 0 1 1 0 0 0 1
    1 0 0 0 0 0 0 0 0 1 1 1 0 0 0 0 0 0 0 0 0 1 0 1 1 0 0 0 1 1 0 1 0 0 0 1
    1 1 0 0 0 0 0 1 0 0 1 0 0 0 0 1 0 1 1 1 0 0 0 0 0 0 0 0 1 0 1 0 0 0 0 0
    0 0 0 0 0 1 1 0 0 0 1 0 0 1 0 0 0 0 0 1 1 0 0 1 1 0 0 0 0 0 1 0 0 0 1 0
    0 0 0 1 0 0 1 0 1 0 0 0 0 0 0 0 0 0 1 0 1 1 1 0 0 0 0 0 1 0 0 1 0 0 0 0
    0 1 0 0 0 0 0 0 0 1 0 0 1 1 0 0 0 1 0 0 0 1 0 0 0 0 0 0 0 1 0 0 1 1 0 0
    1 1 1 1 0 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0
    0 0 1 0 0 1 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 1 0 1 1 0 0 0 1 1 1 0 0 0
    1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0
    0 0 0 0 0 1 0 1 0 1 0 0 0 0 0 0 0 1 0 0 0 0 1 0 0 1 1 0 0 0 1 1 0 0 1 0
    0 0 0 0 0 1 0 0 0 0 0 1 1 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0
    0 0 1 1 0 0 1 1 0 1 1 1 0 0 0 0 0 1 0 0 0 0 1 0 1 1 1 0 0 0 0 0 0 1 1 1
    0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 1 0 0 0 0 1 0 1 0 0 0 0 1 0 0
    Salida: 9
*/


import java.util.*;

public class batalla_naval {

    static int DFS(int[][] tablero, int i, int j) {
        if (i < 0 || i >= tablero.length || j < 0 || j >= tablero[0].length || tablero[i][j] == 0) {
            return 0;
        }
        tablero[i][j] = 0;
        int area = 1;
        area += DFS(tablero, i + 1, j);
        area += DFS(tablero, i - 1, j);
        area += DFS(tablero, i, j + 1);
        area += DFS(tablero, i, j - 1);
        return area;
    }

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt();
        int N = sc.nextInt();
        int[][] tablero = new int[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                tablero[i][j] = sc.nextInt();
            }
        }
        sc.close();
        int maxArea = 0;
        for (int i = 0; i < M; i++) { // Recorremos el tablero
            for (int j = 0; j < N; j++) { 
                if (tablero[i][j] == 1) { // Si encontramos un barco
                    maxArea = Math.max(maxArea, DFS(tablero, i, j)); 
                }
            }
        }
        System.out.println(maxArea);
    }
}
