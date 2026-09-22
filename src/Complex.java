import java.lang.Math.*;

public class Complex {
    private final double Re;
    private final double Im;

    // constructor
    Complex() {Re = 0.0; Im = 0.0;}
    Complex(double real, double imaginary) {Re = real; Im = imaginary;}

    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);

    // get
    public double getRe() {
        return this.Re;
    }
    public double getIm() {
        return this.Im;
    }
    // methods
    // Immutable: every operation returns a new Complex;
    // the receiver is never modified!

    public boolean isZero() {
        return Double.compare(Re, 0.0) == 0 && Double.compare(Im, 0.0) == 0;
    }

    public Complex conjugate() {
        return new Complex(this.Re, -this.Im);
    }

    public Complex plus(Complex other) {
        return new Complex(this.Re + other.Re, this.Im + other.Im);
    }

    public Complex minus(Complex other) {
        return new Complex(this.Re - other.Re, this.Im - other.Im);
    }

    public Complex prod(Complex other) {
        return new Complex(this.Re * other.Re - this.Im * other.Im, this.Im * other.Re + this.Re * other.Im);
    }

    public Complex div(Complex other) {
        double divisor = other.Re * other.Re + other.Im * other.Im;
        if (other.Re == 0.0 && other.Im == 0.0) {
            throw new ArithmeticException("DivisionByZero.");
        } else {
            double new_Re = (this.Re * other.Re + this.Im * other.Im) / divisor;
            double new_Im = (this.Im * other.Re - this.Re * other.Im) / divisor;
            return new Complex(new_Re, new_Im);
        }
    }

    public double abs_C() { // |z|
        return (double)Math.sqrt(Math.pow(this.Re, 2) + Math.pow(this.Im, 2));
        // long cause
    }

    public boolean equals(Complex other) {
        return Double.compare(Re, other.Re) == 0 && Double.compare(Im, other.Im) == 0;
    }

    @Override
    public String toString() {
        if (isZero()) return String.format("%d", 0);
        if (Im > 0) return String.format("%.1f + %.1fi", Re, Im);
        if (Im < 0) return String.format("%.1f - %.1fi", Re, -Im);
        return String.format("%.1f", Re);
    }
    // because it is got annoying.
    public Complex plus(double real) {
        return new Complex(this.Re + real, this.Im);
    }

    public Complex minus(double real) {
        return new Complex(this.Re - real, this.Im);
    }

    public Complex prod(double real) {
        return new Complex(this.Re * real, this.Im * real);
    }

    public Complex div(double real) {
        if (real == 0.0) throw new ArithmeticException("Division by zero.");
        return new Complex(this.Re / real, this.Im / real);
    }
    public Complex copy() {
        return new Complex(Re, Im);
    }
}
