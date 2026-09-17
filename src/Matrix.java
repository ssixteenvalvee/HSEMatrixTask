import java.util.Random;
import java.util.Scanner;
import java.util.random.*;

public class Matrix {
    private final int Rows;
    private final int Cols;
    private final Complex[][] A;

    Matrix() {
        Rows = 0;
        Cols = 0;
        A = new Complex[Rows][Cols];
    }
    Matrix(int r, int c) {
        Rows = r;
        Cols = c;
        A = new Complex[Rows][Cols];
    }

    // get
    public int getRows() {return Rows;}
    public int getCols() {return Cols;}
    public Complex[][] getA() {return A;}

    // methods
    public void fill() {
        Scanner scanner = new Scanner(System.in);
        for (int row = 0; row < Rows; row++) {
            for (int col = 0; col < Cols; col++) {
                System.out.println("Enter Re and Im: ");
                double re = scanner.nextDouble();
                double im = scanner.nextDouble();
                Complex c = new Complex(re, im);
                A[row][col] = c;
            }
        }
    }

    private final Random random = new Random();

    public void fillRand(double min, double max) {
        for (int row = 0; row < Rows; row++) {
            for (int col = 0; col < Cols; col++) {
                double re = min + (max - min) * random.nextDouble();
                double im = min + (max - min) * random.nextDouble();
                Complex c = new Complex(re, im);
                A[row][col] = c;
            }
        }
    }

    public Complex detGauss() {
        Complex det = new Complex(1.0, 0.0);
        int n = A.length;
        if (Rows != Cols) {
            throw new IllegalArgumentException("This operation may be done only with square matrix.");
        }
        for (int i = 0; i < n; i++) {
            int row = i;
            for (int k = i + 1; k < n; k++) {
                if ((A[k][i]).abs_C() > (A[row][i]).abs_C()) {
                    row = k;
                }
            }
            if ((A[row][i]).isZero()) {
                return new Complex(0.0, 0.0);
            }
            if (row != i) {
                for (int j=0; j < n; j++) {
                    Complex temp = A[row][j];
                    A[row][j] = A[i][j];
                    A[i][j] = temp;
                }
                det = new Complex(-det.getRe(), -det.getIm());
            }
            for (int k = i + 1; k < n; k++) {
                Complex dived = (A[k][i]).div(A[i][i]);
                for (int j = i; j < n; j++) {
                    A[k][j] = A[k][j].minus(dived.prod(A[i][j]));
                }
            }
            det = det.prod(A[i][i]);
        }
        return det;
    }
}
