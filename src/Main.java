/*
* написать класс для работы с матрицами комплексных чисел.
* В классе должны быть методы, позволяющие сложить, перемножить, разделить матрицы (при возможности),
* транспонировать и вычислить определитель. К классу должен прилагаться консольный интерфейс,
* позволяющий создать матрицу и задать операцию
 */

public class Main {
    public static void main(String[] args) {
        Matrix m1 = new Matrix(3, 3);
        Matrix m2 = new Matrix(3, 3);
        System.out.print("m1:\n");
        m1.fillRand(-2, 32, true);
        m2.fillRand(-7, 14, true);
        m1.show();
        System.out.print("\nm2:\n");
        m2.show();
        System.out.print("\n\nsum:\n");
        m1.plusw(m2).show();
        System.out.print("det(m1): " + m1.detGauss().toString() + "\ndet(m2): " + m2.detGauss().toString() + "\n");
        System.out.print("\nm1 Inversed:\n");
        m1.inversed().show();
        System.out.print("\nm1 transposed:\n");
        m1.transposed().show();
    }
}
