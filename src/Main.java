import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        char[][] matriz = new char[10][10];
        int vidas = 10;
        boolean rescatado = false;

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                matriz[i][j] = '*';
            }
        }

        // Colocar robot y meta
        matriz[0][0] = 'R';
        matriz[9][9] = 'F';

        // Colocar obstáculos y baterías
        for (int i = 0; i < 5; i++) {
            colocar(matriz, 'X');
        }
        colocar(matriz, 'B');

        imprimir(matriz);
        System.out.println("Vidas: " + vidas);

        while (vidas > 0 && !rescatado) {
            int robF = robF(matriz);
            int robC = robC(matriz);

            System.out.print("Introduce una letra W A S D: ");
            char letra = sc.next().charAt(0);

            int dx = 0;
            int dy = 0;
            boolean valida = true;

            switch (letra) {
                case 'W' -> dy = -1;
                case 'S' -> dy = 1;
                case 'A' -> dx = -1;
                case 'D' -> dx = 1;
                default -> valida = false;
            }

            if (valida) {
                int newF = robF + dy;
                int newC = robC + dx;

                if (newF < 0 || newF >= matriz.length
                        || newC < 0 || newC >= matriz[0].length
                        || matriz[newF][newC] == 'X') {
                    System.out.println();
                    System.out.println("Movimiento bloqueado");
                } else {
                    char destino = matriz[newF][newC];

                    matriz[robF][robC] = '*';
                    matriz[newF][newC] = 'R';

                    if (destino == 'B') {
                        vidas += 2;
                        System.out.println("¡Has ganado 2 vidas!");
                    } else if (destino == 'F') {
                        System.out.println("Rescate completado");
                        rescatado = true;
                    }
                }
            } else {
                System.out.println("No puedes ejecutar esa tecla! Usa W, A, S o D.");
            }

            System.out.println();
            System.out.println("Vidas: " + vidas);
            imprimir(matriz);
        }

        if (!rescatado) {
            System.out.println("Te has quedado sin vidas");
        }
    }

    public static void imprimir(char[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void colocar(char[][] matriz, char caracter) {
        boolean colocado = false;
        Random ran = new Random();

        while (!colocado) {
            int ranFil = ran.nextInt(matriz.length);
            int ranCol = ran.nextInt(matriz[0].length);
            if (matriz[ranFil][ranCol] == '*') {
                matriz[ranFil][ranCol] = caracter;
                colocado = true;
            }
        }
    }

    public static int robF(char[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                if (matriz[i][j] == 'R') {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int robC(char[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                if (matriz[i][j] == 'R') {
                    return j;
                }
            }
        }
        return -1;
    }
}