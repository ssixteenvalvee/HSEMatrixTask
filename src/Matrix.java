import java.util.Random;
import java.util.Scanner;

// perhaps make it mutable.
// add set method for Complex[][] A?

public class Matrix {
    private final int Rows;
    private final int Cols;
    private final Complex[][] A;

    Matrix(int r, int c) {
        Rows = r;
        Cols = c;
        A = new Complex[Rows][Cols];
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++) {
                A[i][j] = new Complex(0, 0);
            }
        }
    }
    Matrix(int r, int c, Complex[][] M) {
        Rows = r;
        Cols = c;
        A = new Complex[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                A[i][j] = M[i][j];
            }
        }
    }

    // get
    public int getRows() {return Rows;}
    public int getCols() {return Cols;}
    public Complex[][] getA() {
        Complex[][] a = new Complex[Rows][Cols];
        for (int i = 0; i < Rows; i++) {
            a[i] = A[i].clone();
        }
        return a;
    }
    public Complex get(int r, int c) {return A[r][c];}
    //set
    public void set(Complex val, int r, int c) {A[r][c] = val;}
    // methods
    public void fill() {
        Scanner scanner = new Scanner(System.in);
        for (int row = 0; row < Rows; row++) {
            for (int col = 0; col < Cols; col++) {
                System.out.printf("Enter Re and Im at (%d, %d): ", row, col);
                double re = scanner.nextDouble();
                double im = scanner.nextDouble();
                Complex c = new Complex(re, im);
                A[row][col] = c;
            }
        }
    }

    private final Random random = new Random();

    public static Complex parser(String s) {
        if (s == null) throw new IllegalArgumentException("Input is null.");
        if (s.length() > 64) throw new IllegalArgumentException("Too long input: (<= 64).");

        s = s.trim().replace(" ", "");
        if (s.isEmpty()) throw new IllegalArgumentException("Input has no data to parse.");

        boolean hasI = s.endsWith("i");
        String body = (hasI) ? s.substring(0, s.length() - 1) : s;

        if (hasI && (body.isEmpty() || body.equals("+"))) {return new Complex(0, 1);}
        if (hasI && body.equals("-")) {return new Complex(0, -1);}

        int split = -1;
        for (int i = 1; i < body.length(); i++) {
            char c = body.charAt(i);
            if ((c == '+' || c == '-')) {
                split = i;
            }
        }

        if (!hasI) {
            return new Complex(Double.parseDouble(body), 0);
        }
        if (split == -1) {
            return new Complex(0, Double.parseDouble(body));
        }
        double re = Double.parseDouble(body.substring(0, split));
        double im = Double.parseDouble(body.substring(split));
        return new Complex(re, im);
}

    public void fillRand(double min, double max, boolean iflong) {
        for (int row = 0; row < Rows; row++) {
            for (int col = 0; col < Cols; col++) {
                double re = min + (max - min) * random.nextDouble();
                double im = min + (max - min) * random.nextDouble();
                A[row][col] = (iflong) ? new Complex((long)re, (long)im) : new Complex(re, im);
            }
        }
    }

    public void show() {
        for (int i = 0; i < this.Rows; i++) {
            for (int j = 0; j < this.Cols; j++) {
                // System.out.print(this.A[i][j].toString() + "\t");
                String body = String.format("[%s]\t", A[i][j].toString());
                System.out.printf("%-20s", body);
            }
            System.out.println();
        }
    }

    public Complex detGauss() { // Gauss method
        Complex det = new Complex(1.0, 0.0);
        int n = A.length;
        Complex[][] a = new Complex[n][n];
        for (int i = 0; i < n; i++) {
            a[i] = A[i].clone(); // maybe faster than [i][j] (not sure).
        }
        if (Rows != Cols) {
            throw new IllegalArgumentException("This operation may be done only with square matrix.");
        }
        for (int i = 0; i < n; i++) {
            int row = i;
            for (int k = i + 1; k < n; k++) {
                if ((a[k][i]).abs_C() > (a[row][i]).abs_C()) {
                    row = k;
                }
            }
            if ((a[row][i]).isZero()) {
                return new Complex(0.0, 0.0);
            }
            if (row != i) {
                for (int j=0; j < n; j++) {
                    Complex temp = a[row][j];
                    a[row][j] = a[i][j];
                    a[i][j] = temp;
                }
                det = new Complex(-det.getRe(), -det.getIm());
            }
            for (int k = i + 1; k < n; k++) {
                Complex dived = (a[k][i]).div(a[i][i]);
                for (int j = i; j < n; j++) {
                    a[k][j] = a[k][j].minus(dived.prod(a[i][j]));
                }
            }
            det = det.prod(a[i][i]);
        }
        return det;
    }

    public Matrix plusw(Matrix B) {
        if (this.Rows != B.Rows || this.Cols != B.Cols) {
            throw new IllegalArgumentException("Only with single-dimension matrix.");
        }
        Matrix C = new Matrix(this.Rows, this.Cols);
        for (int i = 0; i < this.Rows; i++) {
            for (int j = 0; j < this.Cols; j++) {
                Complex c_elem = A[i][j].plus(B.get(i, j));
                // C.getA()[i][j] = c_elem;
                C.set(c_elem, i, j);
            }
        }
        return C;
    }

    public Matrix minusw(Matrix B) {
        if (this.Rows != B.Rows || this.Cols != B.Cols) {
            throw new IllegalArgumentException("Only for matrixs with same dimensions.");
        }
        Matrix C = new Matrix(this.Rows, this.Cols);
        for (int i = 0; i < this.Rows; i++) {
            for (int j = 0; j < this.Cols; j++) {
                Complex c_elem = A[i][j].minus(B.get(i, j));
                // C.getA()[i][j] = c_elem;
                C.set(c_elem, i, j);
            }
        }
        return C;
    }

    public Matrix prodw(Matrix B) {
        if (this.Cols != B.Rows) {
            throw new IllegalArgumentException( //
                    "Cannot multiply: The number of A_Columns must be eqaul the number of B_Rows.");
        }
        Matrix C = new Matrix(this.Rows, B.getCols());
        //Complex[][] a = this.A; // massive - matrix
        //Complex[][] b = B.A; // massive - matrix
        for (int i = 0; i < this.Rows; i++) {
            for (int j = 0; j < B.getCols(); j++) {
                Complex cij = new Complex(0, 0);
                for (int k = 0; k < this.Cols; k++) {
                    cij = cij.plus(A[i][k].prod(B.A[k][j]));
                }
                C.set(cij, i, j); // value, row, col;
            }
        }
        return C;
    }

    public Matrix scalarProd(Complex scalar) {
        Matrix scA = new Matrix(this.Rows, this.Cols); // clone
        for (int i = 0; i < this.Rows; i++) {
            for (int j = 0; j < this.Cols; j++) {
                scA.set(this.A[i][j].prod(scalar), i, j);
            }
        }
        return scA;
    }

    public Matrix transposed() {
        Matrix A_transposed = new Matrix(this.Cols, this.Rows); // A dim(2 x 3), A_tr dim(3 x 2)
        Complex[][] a = this.A;
        for (int i = 0; i < this.Rows; i++) {
            for (int j = 0; j < this.Cols; j++) {
                A_transposed.set(a[i][j], j, i);
            }
        }
        return A_transposed;
    }

    public Matrix inversed() { // Gauss method
        if (this.Rows != this.Cols) {
            throw new IllegalArgumentException("Only for square matrix.");
        }
        /*
        Complex det = this.detGauss();
        if (det.isZero()) {
            throw new ArithmeticException("The determinant is zero. A^-1 do not exist.");
        }
        */
        if (this.Rows == 2 && this.Cols == 2) {
            Matrix C = new Matrix(this.Rows, this.Cols);
            C.set(this.A[1][1], 0, 0);
            C.set(this.A[0][1].prod(new Complex(-1, 0)), 0, 1);
            C.set(this.A[1][0].prod(new Complex(-1, 0)), 1, 0);
            C.set(this.A[0][0], 1, 1);
            Complex divisor = A[0][0].prod(A[1][1]).minus(A[0][1].prod(A[1][0]));
            Complex scalar = new Complex(1, 0).div(divisor);
            return C.scalarProd(scalar);
        }
        // ...
        int n = this.Rows; // or Cols doesn't matter
        Matrix AE = new Matrix(n, 2 * this.Cols);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                AE.set(this.A[i][j], i, j);
            }
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    AE.set(Complex.ONE, i, j + n);
                } else {
                    AE.set(Complex.ZERO, i, j + n);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            int row = i;
            for (int k = i + 1; k < n; k++) {
                if ((AE.get(k, i)).abs_C() > (AE.get(row, i)).abs_C()) {
                    row = k;
                }
            }
            if (AE.get(row, i).isZero()) {
                throw new ArithmeticException("This is linearly dependent matrix.");
            }
            if (row != i) {
                for (int j = 0; j < 2*n; j++) {
                    Complex temp = AE.get(row, j);
                    AE.set(AE.get(i, j), row, j);
                    AE.set(temp, i, j);
                    // a[row][j] = a[i][j];
                    //a[i][j] = temp;
                }
            }

            Complex o = AE.get(i, i);
            for (int z = 0; z < 2*n; z++) {
                AE.set(AE.get(i, z).div(o), i, z);
            }

            for (int k = 0; k < n; k++) {
                if (k == i) {
                    continue;
                }
                Complex divisor = AE.get(k, i);
                if (divisor.isZero()) {
                    continue;
                }
                for (int j = 0; j < 2 * n; j++) {
                    Complex p = divisor.prod(AE.get(i, j));
                    Complex d = AE.get(k, j).minus(p);
                    AE.set(d, k, j);
                }
            }
            // ...
        }
        Matrix A_Inversed = new Matrix(n, n);
        for (int i=0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A_Inversed.set(AE.get(i, n+j), i, j);
            }
        }
        return A_Inversed;
    }

    public Matrix divw(Matrix B) {
        // very, very costy!
        return this.prodw(B.inversed());
    }
}
