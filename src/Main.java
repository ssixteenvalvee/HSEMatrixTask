/*
* написать класс для работы с матрицами комплексных чисел.
* В классе должны быть методы, позволяющие сложить, перемножить, разделить матрицы (при возможности),
* транспонировать и вычислить определитель. К классу должен прилагаться консольный интерфейс,
* позволяющий создать матрицу и задать операцию
 */

public class Main {
    public static void main(String[] args) {
        Complex num = new Complex(10, 2);
        Complex num2 = new Complex(20, 10);
        Complex sumnum = num.sumwith(num2);
        System.out.println(sumnum.toStr());
    }
}
