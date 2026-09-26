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
    public static void main(String[] args) {
        String[] commands = {"help - help", "stop - stop", "mkmx - Make Matrix."};
        List<Matrix> matrices = new ArrayList<>();
        System.out.println("> Hello!");
        String line = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("Type 'help' for help and 'stop' to stop.");
        while (!line.equals("stop")) {
            System.out.print("\n> ");
            line = sc.nextLine();
            switch(line) {
                case "stop":
                    System.out.print("OK. Bye now.\n"); 
                    System.exit(0);
                case "help":
                    for (String cmd : commands) {
                        System.out.printf("\n> %s", cmd);
                    }
                    break;
                case "mkmx":
                    Matrix m;
                    int row;
                    int col;
                    System.out.print("\nType the number of Rows and Cols (R C):\n> ");
                    try {
                        System.out.printf("(");
                        row = sc.nextInt();
                        col = sc.nextInt();
                        System.out.printf(")");
                    } catch (InputMismatchException e) {
                        System.out.print("\nExceptionCaught: Rows and Cols should be Integers.");
                        break;
                    }
                    String choice;
                    sc = new Scanner(System.in);
                    System.out.printf("\nFill it randomly, with string or seperately?: [R/S/SEP]\n> ");
                    choice = sc.nextLine();
                    choice = choice.toLowerCase();
                        if (choice.equals("s")) {
                            m = new Matrix(row, col);
                            try {
                                m.fillString();
                            } catch(IllegalArgumentException e) {
                                System.err.printf("Error: %e", e);
                            }
                            matrices.add(m);
                            System.out.print("Successfull");
                        }
                        else if(choice.equals("r")) {
                            System.out.printf("\nSelect the diapasone [X, Y]: ");
                            int l;
                            int r;
                            try {
                                l = sc.nextInt();
                                r = sc.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.print("\nExceptionCaught: You should type integers.");
                                break;
                            }
                            m = new Matrix(row, col);
                            m.fillRand(l, r, false);
                            matrices.add(m);
                            System.out.print("Successfull");
                        }
                        else if (choice.equals("sep")) {
                            m = new Matrix(row, col);
                            try {
                                m.fill();
                            } catch (InputMismatchException e) {
                                System.err.printf("ExceptionCaught: %e", e);
                            }
                            matrices.add(m);
                            System.out.print("Successfull");
                        }
                        else {
                            System.out.println("\nSomething went wrong. Try use command again.");
                        }
                    break;
                case "ls":
                    int ils = 1;
                    for (Matrix mx : matrices) {
                        System.out.printf("%d).\n", ils);
                        mx.show();
                        System.out.println();
                    }
            }
        }
    }
}
