package Search;
/*  Listo

    Existe un fruto podrido en algun arbol del jardín, 
    Cada dia que pasa va extendiendo su enfermedad a sus arboles vecinos 
    La expansión de la enfermedad puede ser hacia arriba, abajo, izquierda o derecha
    En caso de no poder llegar a todos los arboles del jardín debe retornar -1

    N: número de filas
    M: número de columnas
    Las siguientes N líneas contienen M caracteres que representan el jardín
    0: espacio vacío
    1: árbol sano
    2: árbol infectado
    Debe existir 1 solo árbol infectado al comienzo

    El output debe indicar la cantidad de días que tarda en infectar a todos los árboles

    Ejemplo 1:
    Entrada:
    1 9
    1 2 1 1 1 1 0 1 1
    Salida: -1

    Ejemplo 2:
    Entrada:
    18 10
    1 1 1 0 1 1 1 1 1 1
    1 1 1 0 1 1 1 1 1 1
    1 1 1 1 0 0 1 0 1 1
    1 1 0 1 1 1 1 1 1 1
    1 1 1 1 1 1 1 1 1 1
    1 2 1 1 1 1 1 1 1 1
    0 0 1 0 0 0 1 1 1 1
    1 1 0 1 1 1 1 1 0 1
    1 1 1 1 0 1 1 1 1 1
    1 1 1 0 1 1 1 1 1 1
    1 1 1 1 1 0 1 1 1 1
    1 0 1 0 1 1 1 1 0 1
    1 1 1 1 1 1 1 1 1 1
    0 1 0 0 1 1 0 1 1 1
    0 1 0 1 1 1 1 1 1 1
    1 1 1 1 1 1 1 1 1 1
    1 0 1 0 1 1 1 0 1 1
    1 1 1 0 1 1 1 1 1 1
    Salida: 24

    Ejemplo 3:
    4 37
    1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1
    1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 0 1
    1 1 1 0 1 0 1 1 1 1 0 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1 1 0 1 1 1 0 1
    1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 0 2 0 1 1 1 1 1 1 1 1 1 0
    Salida: 30

    Ejemplo 4:
    18 68
    1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 0 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1 1 1 1 1 0 1 0 1 1 1 1 1
    1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0
    1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 0 0 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1
    1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 0 1 0 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 0 1 1 0 1
    1 1 1 1 0 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 0 1 1 1 1 1 0 1 1 1 1 1 1 1 0 1 1 1 0 1 1 1 1 1 0 1 0 0 1
    1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 0 1 1 1 1 1 1 0 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1
    1 1 1 0 1 1 1 1 1 1 1 0 1 0 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 0 0 1 1 1 0 1 1 1 1 1 1 0 1 1 0 0 1 1 1 1 1 1 1 1
    1 1 1 0 1 1 1 1 1 1 1 1 1 0 1 0 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
    1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 0 1 1 1 1 1 1 1 1 1 0 1 1 0 1 1 1 1 0 0 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 0 1 1 1 0 0 1 1 1 1
    1 1 0 1 1 1 1 1 0 1 1 0 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1
    1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 0 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 0 0 1
    1 0 1 1 1 1 1 1 1 1 1 1 0 1 1 1 0 1 1 0 1 1 1 0 1 1 1 1 1 0 1 1 1 1 1 1 1 1 0 1 0 1 1 1 1 1 0 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1
    1 1 1 1 0 0 1 1 1 1 1 1 1 1 0 1 1 1 0 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 0 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 0 1 1 1 0 1 0 1 1 1
    1 0 1 1 1 1 0 1 1 1 0 1 0 0 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 0 1 0 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 0 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1
    1 1 1 1 1 1 1 1 1 1 0 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1
    1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 0 1 1 1
    1 1 1 0 1 1 1 0 1 1 1 1 1 0 0 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
    1 1 1 1 1 1 1 1 0 0 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 2 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1
    Salida: 60
*/

import java.util.*;

public class el_fruto_podrido {

    // Se utiliza BFS para recorrer los árboles infectados y propagar la enfermedad
    public static int BFS(int N, int M, int[][] jardin, int[] inicial) {
        int dias = 0;
        int[][] direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        Queue<int[]> queue = new LinkedList<>();
        queue.add(inicial);
        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i = 0; i < size; i++){
                int[] actual = queue.poll();

                for(int[] dir : direcciones){
                    int x = actual[0] + dir[0];
                    int y = actual[1] + dir[1];
                    if(x >= 0 && x < N && y >= 0 && y < M && jardin[x][y] == 1){
                        jardin[x][y] = 2;
                        queue.add(new int[]{x, y});
                    }
                }
            }
            dias++;
        }

        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                if(jardin[i][j] == 1){
                    return -1;
                }
            }
        }
        return dias;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[][] jardin = new int[N][M];
        int[] inicial = new int[2];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                jardin[i][j] = sc.nextInt();
                if(jardin[i][j] == 2){
                    inicial[0] = i;
                    inicial[1] = j;
                }
            }
        }
        int dias = BFS(N, M, jardin, inicial);
        System.out.println(dias);
        sc.close();
    }
}
