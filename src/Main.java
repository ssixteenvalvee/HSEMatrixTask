/*
* написать класс для работы с матрицами комплексных чисел.
* В классе должны быть методы, позволяющие сложить, перемножить, разделить матрицы (при возможности),
* транспонировать и вычислить определитель. К классу должен прилагаться консольный интерфейс,
* позволяющий создать матрицу и задать операцию
 */
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void clear() {
    System.out.print("\u001B[2J\u001B[H");
    System.out.println("== Complex Matrix Calculator v0.1==");
    System.out.flush();
    }

    public static void MakeMatrix(List<Matrix> matrices, Scanner sc) {
        Matrix m;
        int row;
        int col;
        System.out.print("\nType the number of Rows and Cols (R C):\n> ");
        try {
            System.out.printf("(");
            row = sc.nextInt();
            col = sc.nextInt();
            sc.nextLine();
            System.out.printf(")");
        } catch (InputMismatchException e) {
            System.out.print("\nExceptionCaught: Rows and Cols should be Integers.");
            return;
        }
        String choice;
        System.out.printf("\nFill it randomly, with string or seperately?: [R/S/SEP]\n> ");
        choice = sc.nextLine();
        choice = choice.toLowerCase();
            if (choice.equals("s")) {
                m = new Matrix(row, col);
                try {
                    m.fillString(sc);
                } catch(IllegalArgumentException e) {
                    System.err.printf("Error: %e", e);
                    return;
                }
                matrices.add(m);
                System.out.println("Successfull");
            }
            else if(choice.equals("r")) {
                System.out.printf("\nSelect the diapasone [X, Y]: ");
                int l;
                int r;
                try {
                    l = sc.nextInt();
                    r = sc.nextInt();
                    sc.nextLine();
                } catch (InputMismatchException e) {
                    System.out.print("\nExceptionCaught: You should type integers.");
                    return;
                }
                m = new Matrix(row, col);
                m.fillRand(l, r, false);
                matrices.add(m);
                System.out.println("Successfull");
            }
            else if (choice.equals("sep") || choice.equals("SEP")) {
                m = new Matrix(row, col);
                try {
                    m.fill(sc);
                } catch (InputMismatchException e) {
                    System.err.printf("ExceptionCaught: %e", e);
                    return;
                }
                matrices.add(m);
                System.out.println("Successfull");
            }
            else {
                System.out.println("\nSomething went wrong. Try use command again.");
            }
        }


    public static void main(String[] args) {
        String[] commands = {"help - help", "stop - stop", "mkm - Make Matrix.", "mls - Matrix listing."};
        List<Matrix> matrices = new ArrayList<>();
        System.out.println("Hello!");
        String line = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("Type 'help' for help and 'stop' to stop.");
        while (!line.equals("stop")) {
            System.out.print("~");
            line = sc.nextLine();
            switch(line) {
                case "stop":
                    System.out.print("OK. Bye then.\n"); 
                    System.exit(0);
                case "help":
                    clear();
                    for (String cmd : commands) {
                        System.out.printf("> %s\n", cmd);
                    }
                    break;
                case "mkm":
                    clear();
                    MakeMatrix(matrices, sc);
                    break;
                case "mls":
                    clear();
                    int ils = 1;
                    for (Matrix mx : matrices) {
                        System.out.printf("%d).\n", ils);
                        mx.show();
                        System.out.println();
                    }
                    break;
                default:
                    clear();
            }
        }
    }
}
