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
        if (System.console() != null) {
            System.out.print("\u001B[2J\u001B[H");
            System.out.flush();
        } else {
            for (int i = 0; i < 50; i++) System.out.println();
        }
        System.out.println("== Complex Matrix Calculator v0.1 ==");
    }

    public static void save(ArrayList<Matrix> Ms, String path) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            pw.println(Ms.size());
            for (Matrix m : Ms) m.save(pw);
        }
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

    public static void listing(ArrayList<Matrix> matrices, boolean show_det) {
        if (matrices.isEmpty()) {
            System.out.println("No matrices found.");
            return;
        }
        clear();
        int ils = 1;
        for (Matrix mx : matrices) {
            System.out.printf("%d).\n", ils);
            mx.show();
            System.out.printf("\tDim = (%d, %d),  Det: ", mx.getRows(), mx.getCols());
            try {
                if (show_det) System.out.print(mx.detGauss().toString() + "\n");
            } catch (IllegalArgumentException e) {
                System.out.print("NA\n");
            }
            System.out.println();
            ils++;
        }
    }

    public static Matrix sumMatrix(ArrayList<Matrix> Ms) {
        Matrix result = Ms.getFirst();
        int i = 1;
        for (int c = 1; c < Ms.size(); c++) {
            Matrix m = Ms.get(c);
            try {
                result = result.plusw(m);
                i++;
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                System.out.printf("%d matrices have been put together.", i);
                return result;
            }
        }
        return result;
    }

    public static Matrix difMatrix(ArrayList<Matrix> Ms) {
        Matrix result = Ms.getFirst();
        int i = 0;
        for (int c = 1; c < Ms.size(); c++) {
            Matrix m = Ms.get(c);
            try {
                result = result.minusw(m);
                i++;
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                System.out.printf("%d matrices have been differenced.", i);
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
            sc.nextLine();
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
                    System.err.println("EXCEPTION: " + e.getMessage());
                    // sc.nextLine();
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
                    System.out.print("\nEXCEPTION: You should type integers.");
                    sc.nextLine();
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
                    System.err.println("EXCEPTION: " + e.getMessage());
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
        fchsstr = fchsstr.toLowerCase().trim();
        if (fchsstr.equals("y") || fchsstr.equals("yes") || fchsstr.equals("ye")) {
            try {
                Ms = load(gpath);
            } catch (IOException e) {
                System.err.printf("IOException: Something went wrong while loading: %s\n", e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("EXCEPTION: There is no data.");
            }
        }
        return Ms;
    }

    public static void askSave(ArrayList<Matrix> Ms, Scanner sc, String gpath) {
        System.out.print("SAVE listing? [Y/N]: ");
        String fchsstr = sc.nextLine();
        fchsstr = fchsstr.toLowerCase().trim();
        if (fchsstr.equals("y") || fchsstr.equals("yes") || fchsstr.equals("ye")) {
            try {
                save(Ms, gpath);
            } catch (IOException e) {
                System.err.printf("IOException: Something went wrong while saving: %s\n", e.getMessage());
            }
        }
    }

    public static boolean askUser(Scanner sc, String text, String ... cond) {
        System.out.println(text);
        System.out.print("> ");
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
        String[] commands = {"help - help.", "stop - stop.", "mkm - Make Matrix.", "mls - Matrix listing.", "sum - summarise matrices.",
                "pd - product of two matrices.", "dif - matrices difference.", "t - transpose.", "det - find determinant.",
                "sd - change option: Show determinant in listing or not.", "div - divide two matrices.",
                "inv - inversed matrix."};
        ArrayList<Matrix> matrices;
        System.out.println("Hello!");
        Scanner sc = new Scanner(System.in);
        boolean showdet = true;

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
                    System.out.printf("Show_determinant is: %b\n", showdet);
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
                    listing(matrices, showdet);
                    break;
                case "sum":
                    if (matrices.size() < 2) { // edge cases and info
                        System.out.println("You should add at least two matrices first.");
                        break;
                    }
                    clear();
                    Matrix result;
                    ArrayList<Matrix> ms = new ArrayList<>();
                    listing(matrices, showdet);
                    System.out.print("Please, enter indecies of matricies to continue.\nYou have to chose id and press Enter.\nEnter blank when you're done:\n");
                    while (true) {
                        System.out.print(">");
                        String input = sc.nextLine().trim();
                        if (input.isEmpty()) break;
                        try {
                            int idx = Integer.parseInt(input) - 1;
                            if (idx < 0 || idx >= matrices.size()) {
                                System.out.println("No matrix with such id. Try again.");
                                continue;
                            }
                            ms.add(matrices.get(idx));
                        } catch (NumberFormatException e) {
                            System.out.println("EXCEPTION: Something wrong with the input: " + input);
                        }
                    }
                    if (ms.isEmpty()) {
                        System.out.println("No matrices selected.");
                        break;
                    }
                    try {
                        result = sumMatrix(ms);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("EXCEPTION: IndexOutOfBound. You specified wrong matrix index.");
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
                case "dif":
                    if (matrices.size() < 2) { // edge cases and info
                        System.out.println("You should add at least two matrices first.");
                        break;
                    }
                    clear();
                    Matrix result_dif;
                    ArrayList<Matrix> ms_dif = new ArrayList<>();
                    listing(matrices, showdet);
                    System.out.print("Please, enter indecies of matricies to continue.\nYou have to chose id and press Enter.\nEnter blank when you're done:\n");
                    while (true) {
                        System.out.print(">");
                        String input = sc.nextLine().trim();
                        if (input.isEmpty()) break;
                        try {
                            int idx = Integer.parseInt(input) - 1;
                            if (idx < 0 || idx >= matrices.size()) {
                                System.out.println("No matrix with such id. Try again.");
                                continue;
                            }
                            ms_dif.add(matrices.get(idx));
                        } catch (NumberFormatException e) {
                            System.out.println("EXCEPTION: Something wrong with the input: " + input);
                        }
                    }
                    if (ms_dif.isEmpty()) {
                        System.out.println("No matrices selected.");
                        break;
                    }
                    try {
                        result_dif = difMatrix(ms_dif);
                    } catch (IndexOutOfBoundsException e) {
                        System.err.println("EXCEPTION: IndexOutOfBound. You specified wrong matrix index.");
                        break;
                    }
                    System.out.println("The result is:");
                    result_dif.show();
                    // do like this for future everywhere
                    boolean choice_dif = askUser(sc, "Append the result to the listing? [Y/N]: ", "y", "ye", "yes");
                    if (choice_dif) {
                        matrices.add(result_dif);
                    }
                    break;
                case "pd":
                    if (matrices.size() < 2) { // edge cases and info
                        System.out.println("You should add at least two matrices first.");
                        break;
                    }
                    clear();
                    Matrix result_pd;
                    listing(matrices, showdet);
                    System.out.print("Pay attention: You have to choose two matrices A & B, where Cols of A equals Rows of B.\n");
                    System.out.print("Please, enter TWO indecies to continue.\nYou have to chose id and press Enter:\n");
                    int[] chosen = new int[2];
                    int got = 0;
                    while (got < 2) {
                        System.out.print(">");
                        String input = sc.nextLine().trim();
                        if (input.isEmpty()) break;
                        try {
                            int idx = Integer.parseInt(input) - 1;
                            if (idx < 0 || idx >= matrices.size()) {
                                System.out.println("No matrix with such id. Try again.");
                                continue;
                            }
                            chosen[got] = idx;
                            got++;
                        } catch (NumberFormatException e) {
                            System.out.println("EXCEPTION: Not a number input.");
                        }
                    }
                    if (got < 2) {
                        System.out.println("Need exactly two matrices. Aborting.");
                        break;
                    }
                    try {
                        result_pd = matrices.get(chosen[0]).prodw(matrices.get(chosen[1]));
                    } catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage() + " Aborting.");
                        break;
                    }
                    System.out.println("The result is:");
                    result_pd.show();
                    boolean choicepd = askUser(sc, "Append the result to the listing? [Y/N]: ", "y", "ye", "yes");
                    if (choicepd) {
                        matrices.add(result_pd);
                    }
                    break;
                case "t":
                    if (matrices.isEmpty()) { // edge cases and info
                        System.out.println("You should add at least one matrix first.");
                        break;
                    }
                    listing(matrices, showdet);
                    System.out.println("You should pick one matrix you want to transpose: ");
                    int id_t = -1;
                    while (id_t < 0 || id_t >= matrices.size()) {
                        System.out.print(">");
                        String input_t = sc.nextLine().trim();
                        if (input_t.isEmpty()) break;
                        try {
                            id_t = Integer.parseInt(input_t) - 1;
                            if (id_t < 0 || id_t >= matrices.size()) {
                                System.out.println("No matrix with such id. Try again.");
                                continue;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("EXCEPTION: Not a number input.");
                        }
                    }
                    Matrix result_t;
                    try {
                        result_t = matrices.get(id_t).transposed();
                    } catch (IndexOutOfBoundsException e) {
                        System.err.println("Operation aborted.");
                        break;
                    }
                    System.out.print("The result is: \n");
                    result_t.show();
                    boolean choice_t = askUser(sc, "Append the result to the listing? [Y/N]: ", "y", "ye", "yes");
                    if (choice_t) {
                        matrices.add(result_t);
                    }
                    System.out.println("Successfully");
                    break;
                case "det":
                    if (matrices.isEmpty()) { // edge cases and info
                        System.out.println("You should add at least one matrix first.");
                        break;
                    }
                    listing(matrices, false);
                    System.out.println("Pay attention: Determinant can be found only for square matrix.");
                    int id_det = -1;
                    while (id_det < 0 || id_det >= matrices.size()) {
                        System.out.print(">");
                        String input_t = sc.nextLine().trim();
                        if (input_t.isEmpty()) break;
                        try {
                            id_det = Integer.parseInt(input_t) - 1;
                            if (id_det < 0 || id_det >= matrices.size()) {
                                System.out.println("No matrix with such id. Try again.");
                                continue;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("EXCEPTION: Not a number input.");
                        }
                    }
                    Complex result_det;
                    try {
                        result_det = matrices.get(id_det).detGauss();
                    } catch (IllegalArgumentException e) {
                        System.err.println(e + " Aborting.");
                        break;
                    } catch (IndexOutOfBoundsException e) {
                        System.err.println("Operation Aborted.");
                        break;
                    }
                    System.out.println("The determinant is: " + result_det.toString());
                    System.out.println("Successfully");
                    break;
                case "sd":
                    showdet = !showdet;
                    break;
                case "div":
                    if (matrices.size() < 2) {
                        System.out.println("You should add at least two matrices first.");
                        break;
                    }
                    clear();
                    Matrix result_div;
                    listing(matrices, showdet);
                    System.out.println("Pay attention: A / B means A * B^(-1).");
                    System.out.println("B must be SQUARE and NON-SINGULAR (det(B) != 0).");
                    System.out.println("Enter TWO indices. After every index press Enter: ");
                    int[] chosen_div = new int[2];
                    int got_div = 0;
                    while (got_div < 2) {
                        System.out.print(">");
                        String input = sc.nextLine().trim();
                        if (input.isEmpty()) break;
                        try {
                            int idx = Integer.parseInt(input) - 1;
                            if (idx < 0 || idx >= matrices.size()) {
                                System.out.println("No matrix with such id. Try again.");
                                continue;
                            }
                            chosen_div[got_div] = idx;
                            got_div++;
                        } catch (NumberFormatException e) {
                            System.out.println("EXCEPTION: Not a number input.");
                        }
                    }
                    if (got_div < 2) {
                        System.out.println("Need exactly two matrices. Aborting.");
                        break;
                    }
                    try {
                        result_div = matrices.get(chosen_div[0]).divw(matrices.get(chosen_div[1]));
                    } catch (IllegalArgumentException e) {
                        System.err.println("EXCEPTION: Cannot divide -- " + e.getMessage());
                        break;
                    } catch (ArithmeticException e) {
                        System.err.println("EXCEPTION: " + e.getMessage());
                        break;
                    }
                    System.out.println("The result is:");
                    result_div.show();
                    boolean choice_div = askUser(sc, "Append the result to the listing? [Y/N]: ", "y", "ye", "yes");
                    if (choice_div) {
                        matrices.add(result_div);
                    }
                    break;
                case "inv":
                    if (matrices.isEmpty()) { // edge cases and info
                        System.out.println("You should add at least one matrix first.");
                        break;
                    }
                    listing(matrices, showdet);
                    System.out.println("Pay attention: Matrix can be inversed only if it is SQUARE.");
                    int id_inv = -1;
                    while (id_inv < 0 || id_inv >= matrices.size()) {
                        System.out.print(">");
                        String input_t = sc.nextLine().trim();
                        if (input_t.isEmpty()) break;
                        try {
                            id_inv = Integer.parseInt(input_t) - 1;
                            if (id_inv < 0 || id_inv >= matrices.size()) {
                                System.out.println("No matrix with such id. Try again.");
                                continue;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("EXCEPTION: Not a number input.");
                        }
                    }
                    Matrix result_inv;
                    try {
                        result_inv = matrices.get(id_inv).inversed();
                    } catch (IllegalArgumentException e) {
                        System.err.println(e + " Aborting.");
                        break;
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Operation Aborted.");
                        break;
                    } catch (ArithmeticException e) {
                        System.err.println(e + " Operation can not be finished.");
                        break;
                    }
                    System.out.println("The result is: ");
                    result_inv.show();
                    boolean choice_inv = askUser(sc, "Append the result to the listing? [Y/N]: ", "y", "ye", "yes");
                    if (choice_inv) {
                        matrices.add(result_inv);
                    }
                    System.out.println("Successfully");
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
// fin.