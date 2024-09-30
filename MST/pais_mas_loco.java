package MST;
/*
    N: Cantidad de nodos (ciudades)
    M: Cantidad de caminos (bidireccionales)
    Los caminos pueden ser de 3 tipos:
    - Tipo 1: Sólo usable por hombres
    - Tipo 2: Sólo usable por mujeres
    - Tipo 3: Usable por mujeres y hombres

    Objetivo: Destruir todos los caminos posibles, de forma que aun sea posible llegar a cualquier lado del país 
    (desde cualquier lado) tanto para hombres como para mujeres. -> Kruskal

    Input:
    N M
    M líneas con tres enteros que representan el camino entre dos ciudades, el tipo de camino y el tipo de camino

    Output: Cantidad de caminos que se pueden destruir
*/

import java.util.*;

public class pais_mas_loco {

    static int[] padre, rango;

    // Función para inicializar el array de padres y rangos
    static void init(int n) {
        padre = new int[n+1];
        rango = new int[n+1];
        for (int i = 1; i <= n; i++) {
            padre[i] = i;
            rango[i] = 0;
        }
    }

    // Función para encontrar la raíz del nodo (con compresión de caminos)
    static int find(int x) {
        if (x != padre[x]) {
            padre[x] = find(padre[x]);
        }
        return padre[x];
    }

    // Función para unir dos componentes
    static boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        if (rootX == rootY) {
            return false;
        }
        
        if (rango[rootX] > rango[rootY]) {
            padre[rootY] = rootX;
        } else if (rango[rootX] < rango[rootY]) {
            padre[rootX] = rootY;
        } else {
            padre[rootY] = rootX;
            rango[rootX]++;
        }
        
        return true;
    }

    // Función principal para resolver el problema
    public static int kruskal(int n, int m, int[][] caminos) {
        // Listas de caminos para hombres, mujeres y compartidos
        List<int[]> hombres = new ArrayList<>();
        List<int[]> mujeres = new ArrayList<>();
        List<int[]> compartidos = new ArrayList<>();
        
        // Separar los caminos según el tipo
        for (int[] camino : caminos) {
            int a = camino[0], b = camino[1], tipo = camino[2];
            if (tipo == 1) {
                hombres.add(new int[]{a, b});
            } else if (tipo == 2) {
                mujeres.add(new int[]{a, b});
            } else {
                compartidos.add(new int[]{a, b});
            }
        }
        
        // Inicializar para hombres y mujeres
        init(n);
        int aristasHombres = 0;
        
        // Aplicar Kruskal para los hombres (solo tipo 1 y 3)
        for (int[] camino : hombres) {
            if (union(camino[0], camino[1])) {
                aristasHombres++;
            }
        }
        
        // Guardamos el estado del grafo de hombres para reutilizar luego
        int[] padreHombres = padre.clone();
        
        // Inicializamos para las mujeres
        init(n);
        int aristasMujeres = 0;
        
        // Aplicar Kruskal para las mujeres (solo tipo 2 y 3)
        for (int[] camino : mujeres) {
            if (union(camino[0], camino[1])) {
                aristasMujeres++;
            }
        }
        
        // Verificamos si tanto hombres como mujeres pueden conectarse completamente
        if (aristasHombres + compartidos.size() < n - 1 || aristasMujeres + compartidos.size() < n - 1) {
            return -1;
        }
        
        // Ahora unimos los caminos compartidos para ambos géneros
        init(n);
        padre = padreHombres;
        int aristasTotales = aristasHombres;
        
        for (int[] camino : compartidos) {
            if (union(camino[0], camino[1])) {
                aristasTotales++;
            }
        }
        
        // Repetimos el mismo proceso para las mujeres, usando los caminos compartidos
        init(n);
        for (int[] camino : compartidos) {
            if (union(camino[0], camino[1])) {
                aristasMujeres++;
            }
        }
        
        // Si hemos usado exactamente N-1 caminos para conectar todas las ciudades, no podemos destruir más
        return m - aristasTotales;
    }


    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt();
        int M = sc.nextInt();
        
        // Array para almacenar la información de los caminos
        int[][] caminos = new int[M][3];
        
        // Leer todos los caminos
        for (int i = 0; i < M; i++) {
            caminos[i][0] = sc.nextInt(); // ciudad de origen
            caminos[i][1] = sc.nextInt(); // ciudad de destino
            caminos[i][2] = sc.nextInt(); // tipo de camino (1, 2, o 3)
        }
        sc.close();
        
        // Resolver el problema y mostrar el resultado
        int resultado = kruskal(N, M, caminos);
        System.out.println(resultado);
    }
}




