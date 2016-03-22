package algorithms;

import mathStructs.Complex;
import mathStructs.MatrixException;
import register.AbstractQuantumRegister;

import java.util.Random;

/**
 * Implements Shor's algorithm through a combination of classical and quantum algorithms.
 */
public class Shor {
    int N;
    int iterations_;

    public Shor(int N){
        this.N = N;
        iterations_ = 0;
    }

    /**
     * Applies Shor's algorithm, using an input and output register.
     * @param inputRegister the input register
     * @param outputRegister the output register
     * @param printStatus whether to print progress and inner calculations
     * @return
     * @throws MatrixException
     */
    public int[] apply(AbstractQuantumRegister inputRegister, AbstractQuantumRegister outputRegister, boolean printStatus) throws MatrixException{
        int q = 0;

        for(int q_=0;q_<25;q_++){
            if((1<<q_)>=N*N && (1<<q_)<2*N*N){
                q = q_;
            }
        }

        for(int k=2; k<=Math.log(N)/Math.log(2); k++){
            double val = Math.pow(N, 1.0 / k);
            if(Math.abs(val - (int)val) == 0){
                System.out.printf("Cannot use Shor's algorithm, N is a power of a prime: N=%d^%d\n", (int)val, k);
                return new int[2];
            }
        }

        if(inputRegister.getNumberOfStates() < (1<<q)){
            if(printStatus) System.out.printf("Input register does not have enough states, needs at least %d qubits to run.\n", q);
            return new int[2];
        }
        if(outputRegister.getNumberOfStates() < (1<<q)){
            if(printStatus) System.out.printf("Output register does not have enough states, needs at least %d qubits to run.\n", q);
            return new int[2];
        }

        //If N is even, 2 is a factor
        if(N%2 == 0){
            return new int[] {2, N/2};
        }

        Random rand = new Random();

        int x = rand.nextInt(N-2) + 2;
        int r = 0;
        int iterations = 0;

        while(iterations < 1E5) {
            if(printStatus) System.out.printf("Generated value: x=%d.\n", x);
            //If x is coprime to N, we can continue
            while(gcd(x, N) != 1){
                if(printStatus) System.out.printf("%d is not coprime with %d. Trying new x...\n\n", x, N);
                x = rand.nextInt(N-2) + 2;
                if(printStatus) System.out.printf("Generated value: x=%d.\n", x);
            }

            inputRegister.setUniformSuperposition();
            outputRegister.setZero();

            //Apply the function x^a expMod N for a=0 to 2^q - 1 to the second register
            for(int a=0; a<outputRegister.getNumberOfStates(); a++){
                int value = expMod(x, a, N);
                outputRegister.setProbabilityAmplitude(new Complex(1.0, 0.0), value);
            }

            System.out.println();

            outputRegister.renormalise();

            //Measure the output register to obtain a value k, which collapses the first input register. This is done
            //explicitly here in code, but in a real quantum computer this would happen immediately upon measurement
            //as a direct consequence of quantum mechanics
            int k = outputRegister.measure();
            if(printStatus) System.out.printf("Measured second register, with result: k=%d\n", k);
            for(int a=0; a<inputRegister.getNumberOfStates(); a++){
                if(expMod(x, a, N) != k){
                    inputRegister.setProbabilityAmplitude(new Complex(0.0, 0.0), a);
                }
            }

            inputRegister.renormalise();

            //Calculate the smallest period r of f(x) = a^x expMod N
            //Perform QFT on input register
            if(printStatus) System.out.printf("Applying QFT to first register...\n");
            QFT.applyQFT(inputRegister);

            for(int j=0;j<inputRegister.getNumberOfStates()/128;j++){
                System.out.println(inputRegister.getProbabilityAmplitude(j).normSquared());
            }

            //Measuring the input register will collapse the state onto a specific value
            int m = inputRegister.measure();

            if(printStatus) System.out.printf("Measuring first register, with result: m=%d\n", m);

            //Find a rational approximation of m/2^q, whose denominator could be the period (or a multiple thereof)
            r = rationalApproxDenom((double)m/(double)(1<<q), (1<<q));
            if(printStatus) System.out.printf("Period estimation: r=%d\n", r);

            if((r % 2 != 0) && (2*r<(1<<q))){
                if(printStatus) System.out.printf("Period was found to be odd, multiplying by 2.\n");
                r*=2;
            }

            //r must be positive and nonzero
            if(r>0) {
                int val;
                //Try multiples of r
                for (int c = 1; c < q; c++) {
                    val = expMod(x, c * r / 2, N);

                    if (printStatus) System.out.printf("Trying multiple: r' = %d * %d = %d.\n", c, r, c * r);
                    if (val != 1) {
                        if (gcd(val + 1, N) * gcd(val - 1, N) == N && gcd(val + 1, N) != 1 && gcd(val - 1, N) != 1) {
                            if (printStatus) System.out.printf("Period r=%d works! Returning factors...\n", c * r);

                            if (printStatus)
                                System.out.printf("\nNumber of iterations taken: n=%d\n\n", iterations + 1);
                            this.iterations_ = iterations + 1;
                            return new int[]{gcd(val + 1, N), gcd(val - 1, N)};
                        }
                    }
                }
            }

            //If this doesn't work, try a new value of x
            if(printStatus) System.out.printf("Starting over...\n\n");
            x = rand.nextInt(N-2) + 2;

            iterations++;
        }

        return new int[2];
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
     * Returns the number of (quantum) iterations of the last run of the algorithm.
     * @return the number of iterations
     */
    public int getLastRunIterations(){
        return iterations_;
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
