/*
* написать класс для работы с матрицами комплексных чисел.
* В классе должны быть методы, позволяющие сложить, перемножить, разделить матрицы (при возможности),
* транспонировать и вычислить определитель. К классу должен прилагаться консольный интерфейс,
* позволяющий создать матрицу и задать операцию
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void clear() {
    System.out.print("\u001B[2J\u001B[H");
    System.out.println("== Complex Matrix Calculator v0.1 ==");
    System.out.flush();
    }

    public static void save(ArrayList<Matrix> Ms, String path) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(path));
        pw.println(Ms.size());      // for 'for' cycle in loading  *
        for (Matrix m : Ms)  {
            m.save(pw);
        }
        pw.close();
    }

    public static ArrayList<Matrix> load(String path) throws IOException {
        ArrayList<Matrix> matrices = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) { // to not forget to close the file
            int n = Integer.parseInt(br.readLine().trim()); // (*)
            for (int k = 0; k < n; k++) {
                matrices.add(Matrix.load(br));
            }
        }
        System.out.println("Successful.");
        return matrices;
    }

    public static void listing(ArrayList<Matrix> matrices) {
        if (matrices.isEmpty()) {
            System.out.println("No matrices found.");
            return;
        }
        clear();
        int ils = 1;
        for (Matrix mx : matrices) {
            System.out.printf("%d).\n", ils);
            mx.show();
            System.out.println();
            ils++;
        }
    }

    public static Matrix sumMatrix(ArrayList<Matrix> Ms) {
        Matrix result = new Matrix(Ms.get(0).getRows(), Ms.get(0).getCols());
        int i = 1;
        for (Matrix m : Ms) {
            try {
                result = result.plusw(m);
                i++;
            } catch (IllegalArgumentException e) {
                System.err.println(e);
                System.out.printf("%d matrices have been put together.", i-1);
                return result;
            }
        }
        return result;
    }

    public static void MakeMatrix(List<Matrix> matrices, Scanner sc) {
        Matrix m;
        int row;
        int col;
        System.out.print("\nType the number of Rows and Cols (R C):\n> ");
        try {
            row = sc.nextInt();
            col = sc.nextInt();
            sc.nextLine();
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
                    System.err.printf("Error: %s", e);
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
                    System.err.printf("ExceptionCaught: %s", e);
                    sc.nextLine();
                    return;
                }
                matrices.add(m);
                System.out.println("Successfull");
            }
            else {
                System.out.println("\nSomething went wrong. Try use command again.");
            }
        }

    public static ArrayList<Matrix> loadListing(String gpath, Scanner sc) {
        ArrayList<Matrix> Ms = new ArrayList<>();
        System.out.print("Load listing? [Y/N]: ");
        String fchsstr = sc.nextLine();
        fchsstr = fchsstr.toLowerCase();
        if (fchsstr.equals("y") || fchsstr.equals("yes") || fchsstr.equals("ye")) {
            try {
                Ms = load(gpath);
            } catch (IOException e) {
                System.out.printf("Something went wrong while loading: %s\n", e);
            } catch (NullPointerException e) {
                System.out.println("There is no data.");
            }
        }
        return Ms;
    }

    public static void askSave(ArrayList<Matrix> Ms, Scanner sc, String gpath) {
        System.out.print("SAVE listing? [Y/N]: ");
        String fchsstr = sc.nextLine();
        fchsstr = fchsstr.toLowerCase();
        if (fchsstr.equals("y") || fchsstr.equals("yes") || fchsstr.equals("ye")) {
            try {
                save(Ms, gpath);
            } catch (IOException e) {
                System.out.printf("Something went wrong while saving: %s\n", e);
            }
        }
        return;
    }

    public static boolean askUser(Scanner sc, String text, String ... cond) {
        System.out.println(text);
        String answerString = sc.nextLine();
        answerString = answerString.toLowerCase().trim();
        for (String c : cond) {
            if (answerString.equals(c)) {
                return true;
            }
        }
        return false;
    }

    public static final String path = "data.txt";                   // !!!

    public static void main(String[] args) {
        String Instruction = "TIP:\tTo perform some arithmetic operations with matrices\n\tyou should add them with 'mkm' command firstly.\n";
        String[] commands = {"help - help", "stop - stop", "mkm - Make Matrix.", "mls - Matrix listing.", "sum - summarise matrices"};
        ArrayList<Matrix> matrices;
        System.out.println("Hello!");
        Scanner sc = new Scanner(System.in);

        matrices = loadListing(path, sc);

        String line = "";
        System.out.println("Type 'help' for help and 'stop' to stop.");
        while (!line.equals("stop")) {
            System.out.print(">");
            line = sc.nextLine();
            switch(line) {
                case "stop":
                    askSave(matrices, sc, path);
                    System.out.print("OK. Bye then.\n"); 
                    System.exit(0);
                case "help":
                    clear();
                    System.out.println(Instruction);
                    for (String cmd : commands) {
                        System.out.printf("%s\n", cmd);
                    }
                    break;
                case "mkm":
                    clear();
                    try {
                        MakeMatrix(matrices, sc);
                    } catch (NegativeArraySizeException e) {
                        System.out.println("EXCEPTION: Rows and Cols have to be Natural.");
                    }
                    break;
                case "mls":
                    listing(matrices);
                    break;
                case "sum":
                    if (matrices.size() < 2) { // edge cases and info
                        System.out.println("You should add at least two matrices first.");
                        break;
                    }
                    clear();
                    Matrix result;
                    ArrayList<Matrix> ms = new ArrayList<>();
                    listing(matrices);
                    System.out.print("Please, enter indecies of matricies to continue.\nYou have to chose id and press Enter.\nEnter blank when you're done:\n");
                    while (true) { //
                        String input = sc.nextLine().trim();
                        if (input.isEmpty()) break;
                        try {
                            int idx = Integer.parseInt(input) - 1;
                            if (idx < 0 || idx >= matrices.size()) {
                                System.out.println("No matrix with id" + (idx + 1) + ". Try again.");
                                continue;
                            }
                            ms.add(matrices.get(idx));
                        } catch (NumberFormatException e) {
                            System.out.println("EXCEPTION: Something wrong with the input: " + input);
                        }
                    }
                    //
                    if (ms.isEmpty()) {
                        System.out.println("No matrices selected.");
                        break;
                    }
                    try {
                        result = sumMatrix(ms);
                    } catch (IndexOutOfBoundsException e) {
                        System.err.println("Exception: IndexOutOfBound. You specified wrong matrix index.");
                        break;
                    }
                    System.out.println("The result is:");
                    result.show();
                    // do like this for future everywhere
                    boolean choice = askUser(sc, "Append the result to the listing? [Y/N]: ", "y", "ye", "yes");
                    if (choice) {
                        matrices.add(result);
                    }
                    //
                    break;
                default:
                    System.out.println("\n" + Instruction);
                    for (String cmd : commands) {
                        System.out.printf("%s\n", cmd);
                    }
            }
        }
    }
}
