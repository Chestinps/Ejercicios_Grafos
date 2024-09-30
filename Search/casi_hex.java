package Search;
/*  Listo
    Tablero de N x N
    Cada casilla (cuadrada) puede ser vecina de hasta 8 casillas
    Las casillas vacias se representan con un 0
    Las casillas ocupadas por el primer jugador se representan con un 1
    Las casillas ocupadas por el segundo jugador se representan con un 2

    Objetivo del jugador 1: Ocupar un camino que conecte cualquier celda de la primera fila con cualquiera de la última fila.
    Objetivo del jugador 2: Ocupar un camino que conecte cualquier celda de la primera columna con cualquiera de la última columna.

    Es posible que ambos jugadores cumplan su objetivo
    En este caso el resultado de la partida es "AMBIGUOUS"
    Si solo el jugador uno logra su objetivo, el resultado es "1"
    Si solo el jugador dos logra su objetivo, el resultado es "2"
    Si ninguno de los jugadores cumple su objetivo, el resultado es "0"

    Input
    N: Tamaño del tablero
    Siguiente N lineas: Cada una con N caracteres que representan el tablero entre 0, 1 y 2 cada una

    Ejemplo:
    3
    0 0 1
    0 1 2
    2 2 1
    Resultado: AMBIGUOUS (Ambos jugadores cumplen su objetivo)
*/


import java.util.*;

public class casi_hex {
    // Direcciónes de las 8 casillas vecinas en un tablero
    private static final int[] DX = {-1, -1, 0, 1, 1, 1, 0, -1};
    private static final int[] DY = {0, 1, 1, 1, 0, -1, -1, -1};
    private static int N;
    private static char[][] tablero;

    // Método DFS para explorar los posibles caminos
    private static boolean DFS(int x, int y, char jugador, boolean[][] visitado) {
        if ((jugador == '1' && x == N - 1) || (jugador == '2' && y == N - 1)) {
            return true;
        }

        visitado[x][y] = true;

        for (int d = 0; d < 8; d++) {
            int nx = x + DX[d];
            int ny = y + DY[d];

            if (esValido(nx, ny) && !visitado[nx][ny] && tablero[nx][ny] == jugador) {
                if (DFS(nx, ny, jugador, visitado)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean esValido(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N;
    }

    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        tablero = new char[N][N];
        sc.nextLine(); // Consumir la nueva línea

        for (int i = 0; i < N; i++) {
            String linea = sc.nextLine().replaceAll("\\s+", "");
            for (int j = 0; j < N; j++) {
                tablero[i][j] = linea.charAt(j);
            }
        }
        sc.close();

        boolean jugador1Gana = false;
        boolean jugador2Gana = false;

        // Verificar el objetivo del Jugador 1 (de la primera fila a la última fila)
        for (int i = 0; i < N; i++) {
            if (tablero[0][i] == '1') {
                if (DFS(0, i, '1', new boolean[N][N])) {
                    jugador1Gana = true;
                    break;
                }
            }
        }

        // Verificar el objetivo del Jugador 2 (de la primera columna a la última columna)
        for (int i = 0; i < N; i++) {
            if (tablero[i][0] == '2') {
                if (DFS(i, 0, '2', new boolean[N][N])) {
                    jugador2Gana = true;
                    break;
                }
            }
        }

        // Determinar el resultado del juego
        if (jugador1Gana && jugador2Gana) {
            System.out.println("AMBIGUOUS");
        } else if (jugador1Gana) {
            System.out.println("1");
        } else if (jugador2Gana) {
            System.out.println("2");
        } else {
            System.out.println("0");
        }
    }

}
