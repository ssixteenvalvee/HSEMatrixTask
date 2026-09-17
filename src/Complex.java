import java.lang.Math.*;

public class Complex {
    private final double Re;
    private final double Im;

    // constructor
    Complex() {Re = 0; Im = 0;}
    Complex(double real, double imaginary) {Re = real; Im = imaginary;}

    // get
    public double getRe() {
        return this.Re;
    }
    public double getIm() {
        return this.Im;
    }
    // methods
    // every Complex obj is unique:
    // Complex num = new Complex(r, i);
    // Complex abs_num = num.absolute();
    // -> thus, we had a num and now we jave another obj, but it is absolute num
    // (maybe it is a wrong && bad approach? IDK)
    public Complex conjugate() {
        return new Complex(this.Re, -this.Im);
    }

    public Complex sumwith(Complex other) {
        return new Complex(this.Re + other.Re, this.Im + other.Im);
    }

    public Complex diffwith(Complex other) {
        return new Complex(this.Re - other.Re, this.Im - other.Im);
    }

    public Complex productwith(Complex other) {
        return new Complex(this.Re * other.Re - this.Im * other.Im, this.Im * other.Re + this.Re * other.Im);
    }

    public Complex divwith(Complex other) {
        double divisor = other.Re * other.Re + other.Im * other.Im; // we do not need to work with fractions, I guess.
        if (divisor == 0) {
            throw new ArithmeticException("DivisionByZero.");
        } else {
            double new_Re = (this.Re * other.Re + this.Im * other.Im);
            double new_Im = (this.Im * other.Re - this.Re * other.Im);
            return new Complex(new_Re, new_Im);
        }
    }

    public long abs() { // |z|
        return (long)Math.sqrt(Math.pow(this.Re, 2) + Math.pow(this.Im, 2));
        // long cause
    }

    public boolean is_equalswith(Complex other) {
        return this.Re == other.Re && this.Im == other.Im;
    }

    public String toStr() {
        if (this.Im > 0) {
            return this.Re + " + " + this.Im + "i";
        } else if (this.Im < 0) {
            return this.Re + " - " + (this.Im * -1) + "i";
        }
        else {
            return this.Re + "";
        }
    }
}
