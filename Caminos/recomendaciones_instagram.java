package Caminos;
/*  Listo
    Conexiones unidireccionales entre ususarios

    Ejemplo:
    Usuario 1 sigue a Usuario 2
    Usuario 2 sigue a Usuario 3
    Usuario 3 no sigue a nadie

    Al Usuario 1 le recomendamos el contenido del Usuario 2 (por que lo sigue) y del Usuario 3 (por que el Usuario 2 lo sigue)
    Al Usuario 2 le recomendamos el contenido del Usuario 3 (por que lo sigue)
    Al Usuario 3 no le recomendamos nada (por que no sigue a nadie)

    Input:
    N: Número de conexiones
    N líneas que determinan las conexiones entre usuarios

    Si la celda es 1, significa que el usuario de la fila sigue al usuario de la columna
    Si la celda es 0, significa que el usuario de la fila no sigue al usuario de la columna

    Output: Matriz en el mismo orden de entrada
    Si la celda es 1, significa que si y solo si el usuario de la fila debe recibir recomendaciones del usuario de la columna
    Si la celda es 0, significa que el usuario de la fila no debe recibir recomendaciones del usuario de la columna

    Ejemplo 1:
    3
    1 1 0
    0 1 1
    0 0 1
    Respuesta:
    1 1 1
    0 1 1
    0 0 1

    Ejemplo 2:
    4
    1 1 0 1 
    0 1 1 0 
    0 0 1 1 
    0 0 0 1
    Respuesta:
    1 1 1 1
    0 1 1 1
    0 0 1 1
    0 0 0 1
*/

import java.util.*;

public class recomendaciones_instagram {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt();
        
        int[][] conexiones = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                conexiones[i][j] = sc.nextInt();
            }
        }

        int[][] recomendaciones = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                recomendaciones[i][j] = conexiones[i][j];
            }
        }

        // Floyd-Warshall para determinar las recomendaciones
        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (recomendaciones[i][k] == 1 && recomendaciones[k][j] == 1) {
                        recomendaciones[i][j] = 1;
                    }
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(recomendaciones[i][j] + " ");
            }
            System.out.println();
        }
        
        sc.close();
    }
}

