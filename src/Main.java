/*
* написать класс для работы с матрицами комплексных чисел.
* В классе должны быть методы, позволяющие сложить, перемножить, разделить матрицы (при возможности),
* транспонировать и вычислить определитель. К классу должен прилагаться консольный интерфейс,
* позволяющий создать матрицу и задать операцию
 */

public class Main {
    public static void main(String[] args) {
        Matrix M = new Matrix(2, 3);
        M.set(new Complex(3, 4), 0, 0);
        M.set(new Complex(1, 2), 0, 1);
        M.set(new Complex(4, 2), 0, 2);
        M.show();
        System.out.println();
        Matrix M_tr = M.transposed();
        M_tr.show();
    }
}
