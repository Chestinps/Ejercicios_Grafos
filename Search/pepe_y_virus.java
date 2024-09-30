package Search;
/*  Listo
    Pepe es el paciente cero
    Pepe es considerado nivel 0
    Si alguien ha estado en contacto con el paciente cero, su nivel es 1
    Si alguien ha estado en contacto con alguien de nivel 1, su nivel es 2
    Si alguien no ha estado en contacto con nadie, su nivel es -1
    Si alguien ha estado en contacto con alguien de nivel X, y no ha estado en contacto con nadie con un nivel menor que X, se considera que esta persona tiene el nivel de X + 1

    Input:
    N: Número de personas
    C: Número de conexiones
    C líneas que determinan las conexiones entre dos personas (0 a N-1)

    Output: N-1 lineas que contengan el nivel de cada persona
    Si el nivel no puede ser determinado, retornar -1
*/

import java.util.*;

public class pepe_y_virus {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt();
        int C = sc.nextInt();
        
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int i = 0; i < C; i++) {
            int person1 = sc.nextInt();
            int person2 = sc.nextInt();
            adjList.get(person1).add(person2);
            adjList.get(person2).add(person1);
        }

        int[] levels = new int[N];
        Arrays.fill(levels, -1);
        levels[0] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            int currentLevel = levels[current];

            for (int neighbor : adjList.get(current)) {
                if (levels[neighbor] == -1) {
                    levels[neighbor] = currentLevel + 1;
                    queue.add(neighbor);
                }
            }
        }

        // Imprimir los niveles
        for (int i = 1; i < N; i++) {
            System.out.println(levels[i]);
        }

        sc.close();
    }
}

