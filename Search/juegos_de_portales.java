package Search;
/*  Listo
    
    Se tiene un tablero de 100 celdas
    El jugador comienza en la celda 1 y debe llegar a la celda 100
    En cada turno se lanza un dado de 6 caras y el jugador avanza esa cantidad de celdas
    Al llegar a una celda con un portal, el jugador es transportado de forma instantánea 
    (dentro del mismo turno) al otro extremo del portal.
    Los portales son uni-direccionales.
    Si el jugador está en una de las últimas celdas i, lanza el dado y obtiene un número x que lo llevaría más allá de la celda 100 (i+x>100)
    entonces el jugador no se mueve.

    Para una nueva edición del juego se quiere crear varios tableros
    dependiendo de la duración mínima del juego que escojan los jugadores. 
    Se le pide a usted, que desarrolle un programa que, dado un tablero del Juego de portales, 
    encuentre el número mínimo de lanzamientos del dado necesarios 
    para llegar al destino o la última celda desde el origen o la 1ra celda.

    Input:
    t: número de tableros
    Para cada tablero:
    pN: número de portales de avance
    pPortalesAvance: lista de portales de avance (origen, destino)
    pM: número de portales de retroceso
    pPortalesRetroceso: lista de portales de retroceso (origen, destino)

    Output:
    Para cada tablero, retornar el número mínimo de lanzamientos del dado necesarios para llegar al destino o la última celda desde el origen o la 1ra celda.

    Ejemplo 1:
    2 -> t
    3 -> pN del tablero 1
    32 62 -> Avance 1
    42 68 -> Avance 2
    12 98 -> Avance 3
    7 -> pM del tablero 1
    95 13 -> Retroceso 1
    97 25 -> Retroceso 2
    93 37 -> Retroceso 3
    79 27 -> Retroceso 4
    75 19 -> Retroceso 5
    49 47 -> Retroceso 6
    67 17 -> Retroceso 7
    4 -> pN del tablero 2
    8 52 -> Avance 1
    6 80 -> Avance 2
    26 42 -> Avance 3
    2 72 -> Avance 4
    9 -> pM del tablero 2
    51 19 -> Retroceso 1
    39 11 -> Retroceso 2
    37 29 -> Retroceso 3
    81 3 -> Retroceso 4
    59 5 -> Retroceso 5
    79 23 -> Retroceso 6
    53 7 -> Retroceso 7
    43 33 -> Retroceso 8
    77 21 -> Retroceso 9

*/


    import java.util.*;

    public class juegos_de_portales {
    
        public static int BFS(int pN, List<List<Integer>> pPortalesAvance, int pM, List<List<Integer>> pPortalesRetroceso) {
            // Crear un array de tamaño 101 (del 1 al 100) para representar el tablero
            int[] tablero = new int[101];
            for (int i = 0; i <= 100; i++) {
                tablero[i] = i;
            }
    
            // Aplicar los portales de avance
            for (List<Integer> portal : pPortalesAvance) {
                int origen = portal.get(0);
                int destino = portal.get(1);
                tablero[origen] = destino;
            }
    
            // Aplicar los portales de retroceso
            for (List<Integer> portal : pPortalesRetroceso) {
                int origen = portal.get(0);
                int destino = portal.get(1);
                tablero[origen] = destino;
            }
    
            // Cola para BFS
            Queue<int[]> cola = new LinkedList<>();
            boolean[] visitado = new boolean[101]; // Para evitar ciclos
            cola.offer(new int[]{1, 0});  // [posición actual, número de lanzamientos]
            visitado[1] = true;
    
            while (!cola.isEmpty()) { // Mientras haya celdas por visitar
                int[] actual = cola.poll(); // Sacamos la celda actual
                int posicion = actual[0]; // Posición actual
                int lanzamientos = actual[1]; // Número de lanzamientos
    
                // Si llegamos a la celda 100, devolvemos el número de lanzamientos
                if (posicion == 100) {
                    return lanzamientos;
                }
    
                // Intentamos movernos a las siguientes 6 celdas posibles
                for (int i = 1; i <= 6; i++) {
                    int nuevaPosicion = posicion + i; // Nueva posición
                    if (nuevaPosicion <= 100 && !visitado[nuevaPosicion]) { // Si la celda es válida y no ha sido visitada
                        visitado[nuevaPosicion] = true; // Marcamos la celda como visitada
                        cola.offer(new int[]{tablero[nuevaPosicion], lanzamientos + 1}); // Agregamos la celda a la cola
                    }
                }
            }
    
            // Si no hay forma de llegar a la celda 100 (lo cual no debería pasar en un tablero válido)
            return -1;
        }
    
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            
            // Leer el número de tableros
            int t = scanner.nextInt();
    
            for (int i = 0; i < t; i++) {
                // Leer el número de portales de avance
                int pN = scanner.nextInt();
                List<List<Integer>> portalesAvance = new ArrayList<>();
                for (int j = 0; j < pN; j++) {
                    int origen = scanner.nextInt();
                    int destino = scanner.nextInt();
                    portalesAvance.add(Arrays.asList(origen, destino));
                }
    
                // Leer el número de portales de retroceso
                int pM = scanner.nextInt();
                List<List<Integer>> portalesRetroceso = new ArrayList<>();
                for (int j = 0; j < pM; j++) {
                    int origen = scanner.nextInt();
                    int destino = scanner.nextInt();
                    portalesRetroceso.add(Arrays.asList(origen, destino));
                }
    
                // Calcular y mostrar el resultado para el tablero actual
                int resultado = BFS(pN, portalesAvance, pM, portalesRetroceso);
                System.out.println("Tablero " + (i + 1) + ": El número mínimo de lanzamientos es: " + resultado);
            }
    
            scanner.close();
        }
    }
    
