package mathStructs;

public class Complex {
    private double real;
    private double imag;

    /** Default constructor. Constructs a new Complex number with both components set to 0.
     */
    public Complex() {
        this.setComplex(0.0, 0.0);
    }

    /** Copy constructor. Creates a new Complex object using another.
     *
     * @param original the Complex object to be copied
     */
    public Complex(Complex original) {
        this.setComplex(original.getReal(), original.getImag());
    }

    /** Explicit constructor. Constructs a new Complex using a real and complex part.
     *
     * @param real a double representing the real part
     * @param imag a double representing the imaginary part
     */
    public Complex(double real, double imag) {
        this.setComplex(real, imag);
    }

    /** Set both the real and imaginary components
     *
     * @param real the real part
     * @param imag the imaginary part
     */
    public void setComplex(double real, double imag) {
        this.setReal(real);
        this.setImag(imag);
    }


    /** Sets the real part only.
     *
     * @param real a double to set the real part
     */
    public void setReal(double real) { this.real = real; }

    /** Sets the imaginary part only.
     *
     * @param imag a double to set the imaginary part
     */
    public void setImag(double imag) { this.imag = imag; }

    /** Gets the real part.
     *
     * @return a double representing the real part
     */
    public double getReal() { return this.real; }

    /** Gets the imaginary part.
     *
     * @return a double representing the imaginary part
     */
    public double getImag() { return this.imag; }


    /** Creates a String representation of the Complex object.
     *
     * @return the string representation
     */
    public String toString() {
        double real = this.getReal();
        double imag = this.getImag();
        if(imag >= 0.0){
            return String.format("%.3f + %.3fi", real, imag);
        } else {
            return String.format("%.3f +%.3fi", real, imag);
        }
    }

    /** Calculates the square modulus of the complex number.
     *
     * @return the modulus squared
     */
    public double normSquared() {
        return this.getReal() * this.getReal() + this.getImag() * this.getImag();
    }

    /** Calculates the modulus of the complex number.
     *
     * @return a double representing the norm
     */
    public double norm() {
        return Math.sqrt(this.normSquared());
    }

    /** Returns the complex conjugate of the complex number.
     *
     * @return the Complex object representing the complex conjugate
     */
    public Complex conj() {
        return new Complex(this.getReal(),-this.getImag());
    }

    /**
     * Finds the n-th power of the complex number.
     * @param n the exponent
     * @return the result
     */
    public void powerComplex(int n){
        double r = this.norm();
        double theta = Math.atan2(imag, real);
        real = Math.cos((double)n * theta) * Math.pow(r, (double)n);
        imag = Math.sin((double)n * theta) * Math.pow(r, (double)n);
    }

    /** Adds two Complex numbers.
     *
     * @param a the first Complex
     * @param b the second Complex
     * @return the sum
     */
    public static Complex addComplex(Complex a, Complex b) {
        return new Complex(a.getReal() + b.getReal(), a.getImag() + b.getImag());
    }

    /** Subtracts two Complex numbers.
     *
     * @param a the first complex number
     * @param b the second complex number
     * @return the second complex number subtracted from the first complex number
     */
    public static Complex subComplex(Complex a, Complex b) {
        return new Complex(a.getReal() - b.getReal(), a.getImag() - b.getImag());
    }

    /** Multiplies two Complex numbers.
     *
     * @param a the first complex number
     * @param b the second complex number
     * @return the complex product
     */
    public static Complex multComplex(Complex a, Complex b) {
        return new Complex(a.getReal()*b.getReal() - a.getImag()*b.getImag(),
                a.getReal()*b.getImag() + a.getImag()*b.getReal());
    }

    /** Multiplies a Complex number by a real number.
     *
     * @param a the Complex numer
     * @param b the real number
     * @return the result
     */
    public static Complex multComplex(Complex a, double b) {
        return new Complex(a.getReal()*b, a.getImag()*b);
    }

    /** Divides a Complex by a real number.
     *
     * @param a a Complex numerator
     * @param b the real denominator
     * @return the Complex result
     */
    public static Complex divideComplex(Complex a, double b) {
        return new Complex(a.getReal()/b, a.getImag()/b);
    }

    /** Divides a Complex by another Complex number.
     *
     * @param a the Complex numerator
     * @param b the Complex denominator
     * @return the Complex result
     */
    public static Complex divideComplex(Complex a, Complex b) {
        return divideComplex(multComplex(a,b.conj()), b.normSquared());
    }
}
