public class Complex {
    private final int Re;
    private final int Im;

    // constructor
    Complex() {Re = 0; Im = 0;}
    Complex(int real, int imaginary) {Re = real; Im = imaginary;}

    // get
    public int getRe() {
        return this.Re;
    }
    public int getIm() {
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
        int divisor = other.Re * other.Re + other.Im * other.Im; // we do not need to work with fractions, I guess.
        if (divisor == 0) {
            throw new ArithmeticException("DivisionByZero.");
        } else {
            int new_Re = (this.Re * other.Re + this.Im * other.Im);
            int new_Im = (this.Im * other.Re - this.Re * other.Im);
            return new Complex(new_Re, new_Im);
        }
    }

    public boolean is_equalswith(Complex other) {
        return this.Re == other.Re && this.Im == other.Im;
    }

    public String toStr() {
        if (this.Im >= 0) {
            return this.Re + " + " + this.Im + "i";
        } else {
            return this.Re + " - " + (this.Im * -1) + "i";
        }
    }

}
