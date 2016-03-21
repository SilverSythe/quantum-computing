package algorithms;

import mathStructs.Complex;

import java.util.Random;

public class ClassicalPrime {
    int N;

    public ClassicalPrime(int N){
        this.N = N;
    }

    public int[] apply(boolean printStatus){
        int q = 0;

        for(int q_=0;q_<25;q_++){
            if((1<<q_)>=N*N && (1<<q_)<2*N*N){
                q = q_;
            }
        }

        for(int k=2; k<=Math.log(N)/Math.log(2); k++){
            double val = Math.pow(N, 1.0 / k);
            if(Math.abs(val - (int)val) == 0){
                System.out.printf("Cannot continue, N is a power of a prime: N=%d^%d\n", (int)val, k);
                return new int[2];
            }
        }

        //If N is even, 2 is a factor
        if(N%2 == 0){
            return new int[] {2, N/2};
        }

        Random rand = new Random();

        int x = rand.nextInt(N-2) + 2;
        int r = 0;
        int iterations = 0;

        while(iterations<1E5){
            //If x is coprime to N, we can continue
            while(gcd(x, N) != 1){
                if(printStatus) System.out.printf("%d is not coprime with %d. Trying new x...\n\n", x, N);
                x = rand.nextInt(N-2) + 2;
                if(printStatus) System.out.printf("Generated value: x=%d.\n", x);
            }

            int[] values = new int[q];

            for(int n=0;n<values.length;n++){
                values[n] = expMod(x, n, N);
            }



            iterations++;
        }

        //TODO: TEMP
        return new int[2];
    }

    /**
     * Takes the discrete fourier transform of a real set of numbers. This part contains the normalisation factor
     * 1/N.
     * @param f the set of real space values
     * @return the coefficents of the fourier sum
     */
    public static Complex[] DFT(Complex[] f){
        //Fourier space coefficient output values
        Complex[] coeffs = new Complex[f.length];

        for(int n=0; n<f.length; n++) {
            double real = 0.0;
            double imag = 0.0;
            //Multiply and sum over exponentials to get n-th fourier space coefficient
            for (int b=0; b<f.length; b++) {
                coeffs[b] = Complex.multComplex(f[b], new Complex(Math.cos(2.0*Math.PI*(double)n*(double)b/f.length),
                                                                    Math.sin(2.0*Math.PI*(double)n*(double)b/f.length)));
            }
            //Normalise by multiplying by 1/N
            coeffs[n] = new Complex(real/(double)f.length, imag/(double)f.length);
        }

        return coeffs;
    }

    /**
     * Apply Euclid's algorithm to find the gcd of a and b.
     * @param a the first number
     * @param b the second number
     * @return the greatest common denominator
     */
    public int gcd(int a, int b){
        if(a<b){
            int temp = a;
            a=b;
            b=temp;
        }

        int r;

        while(true){
            r = a%b;
            if(r==0){
                return b;
            }
            a=b;
            b=r;
        }
    }

    /**
     * Finds the continued fraction of a number to find a rational approximation, and returns the denominator, as long
     * as it is smaller than qmax.
     * @param c the number to find the rational approximation of
     * @param qmax the maximum number to return
     * @return the denominator of the rational approximation
     */
    public int rationalApproxDenom(double c, int qmax) {
        double c_ = c;
        double z;
        int a0 = 0;
        int a1 = 1;
        int a2 = 0;
        while (true) {
            z = c_ - (int)c_;
            if (z < 0.5 / Math.pow(qmax,2)) {
                return(a1);
            }

            if (z != 0) {
                c_ = 1.0 / z;
            } else {
                return(a1);
            }

            a2 = (int)c_ * a1 + a0;
            if (a2 >= qmax) {
                return(a1);
            }

            a0 = a1;
            a1 = a2;
        }
    }

    /**
     * Finds f(x,a,N) = x^a mod N efficiently, without having to compute x^a explicitly.
     * @return the result of the function evaluation
     */
    public int expMod(int base, int exponent, int modulus){
        if (modulus == 1){
            return 0;
        }

        int c = 1;
        for (int e_ = 1; e_ <= exponent; e_++) {
            c = (c * base) % modulus;
        }

        return c;
    }
}
