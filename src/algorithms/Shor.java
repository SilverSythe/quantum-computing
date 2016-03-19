package algorithms;

import mathStructs.Complex;
import mathStructs.MatrixException;
import register.AbstractQuantumRegister;

public class Shor {
    int N;

    public Shor(int N){
        this.N = N;
    }

    public int[] apply(AbstractQuantumRegister inputRegister, AbstractQuantumRegister outputRegister) throws MatrixException{
        int q = (int)(Math.log(N)/Math.log(2)) + 1;

        if(inputRegister.getNumberOfStates() < (1<<q)){
            System.out.printf("Input register does not have enough states, needs at least %d to run.\n", 1<<q);
            return new int[2];
        }
        if(outputRegister.getNumberOfStates() < N){
            System.out.printf("Output register does not have enough states, needs at least %d to run.\n", N);
            return new int[2];
        }

        //If N is even, 2 is a factor
        if(N%2 == 0){
            return new int[] {2, N/2};
        }

        int x = 1;
        int r = 0;

        while(x<N) {
            //If a is a non-trivial factor, no further calculation is needed
            if (gcd(x, N) != 1) {
                return new int[]{x, N / x};
            }

            inputRegister.setUniformSuperposition();
            outputRegister.setZero();

            //TODO: USE PHASE ESTIMATION INSTEAD
            //Apply the function x^a mod N for a=0 to 2^q - 1 to the second register
            for(int a=0; a<inputRegister.getNumberOfStates(); a++){
                int value = (int)Math.pow(x, a) % N;
                outputRegister.setProbabilityAmplitude(new Complex(1.0, 0.0), value);
            }

            outputRegister.renormalise();

            //System.out.println(outputRegister.getRegister().toString());

            //Measure the output register to obtain a value k, which collapses the first input register. This is done
            //explicitly here in code, but in a real quantum computer this would happen immediately upon measurement
            //as a direct consequence of quantum mechanics
            int k = outputRegister.measure();

            for(int a=0; a<inputRegister.getNumberOfStates(); a++){
                if((int)Math.pow(x, a) % N != k){
                    inputRegister.setProbabilityAmplitude(new Complex(0.0, 0.0), a);
                }
            }

            inputRegister.renormalise();

            //Calculate the smallest period r of f(x) = a^x mod N

            //Perform QFT on input register
            QuantumFourierTransform.applyQFT(inputRegister);

            int m = inputRegister.measure();

            r = denominator((double)m/(double)q, q);

            if((r % 2 == 0)){
                if(2*r<q){
                    r*=2;
                } else {
                    break;
                }
            }

            if (((int) Math.pow(x, r << 1) == -1 % N)) {
                break;
            }

            //Try a new value of a
            x++;
        }
        int val = (int)Math.pow(x, r<<1);
        return new int[] {gcd(val+1, N), gcd(val-1, N)};
    }

    /**
     * Helper method to apply f(x) to the output register (qubits Q -> 2Q-1) where f(x) = a^x mod N and x is the
     * corresponding input value, i.e. it turns the state sum(|x>|0>) for x=0 to Q-1 to the state sum(|x,f(x)>) for
     * x=0 to Q-1.
     * @param register
     * @param a
     * @param N
     */
    private void createModuloCircuit(AbstractQuantumRegister register, int a, int N){

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
     public int denominator(double c, int qmax) {
        double y = c;
        double z;
        int q0 = 0;
        int q1 = 1;
        int q2 = 0;
        while (true) {
            z = y - (int)y;
            if (z < 0.5 / Math.pow(qmax,2)) {
                return(q1);
            }
            if (z != 0) {
                y = 1.0 / z;
            } else {
                return(q1);
            }
            q2 = (int)y * q1 + q0;
            if (q2 >= qmax) {
                return(q1);
            }
            q0 = q1;
            q1 = q2;
        }
    }
}
