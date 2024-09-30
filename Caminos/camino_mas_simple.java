package Caminos;
// cantidad mínima de veces que el robot tiene que girar para llegar desde el punto de origen hasta el punto de destino
// si no puede llegar, retorna -1 

// pN: número de filas
// pM: número de columnas

// pMapa: matriz de pN x pM que representa el mapa
// pMapa[i][j] = '.' si la celda es transitable
// pMapa[i][j] = '*' si la celda es un obstáculo
// pMapa[i][j] = 'V' si la celda es el punto de origen
// pMapa[i][j] = 'H' si la celda es el punto de destino

// Ejemplo de entrada 1
//5 5
//****V
//***..
//*.*.*
//....*
//H*..*
// Salida: 4

// Ejemplo de entrada 2
//10 10
//...*.*...H
//...**.....
//..**...*..
//*.......*.
//.*........
//*....*....
//.....*.*..
//.*.*.*....
//....V.....
//....*....*
// Salida: 1

// Ejemplo de entrada 3
//5 4
//V...
//***.
//....
//.***
//.H.*
// Salida: 4


import java.util.*;

public class camino_mas_simple {
    public static int calcularMinimo(int pN, int pM, List<String> pMapa) {
        // Write your code here
        
        return 0;
    }
}
