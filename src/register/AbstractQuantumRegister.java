package register;

import gates.SingleQubitGate;
import gates.TwoQubitGate;
import mathStructs.Complex;
import mathStructs.Matrix;
import mathStructs.MatrixException;

/**
 * An abstract implementation of a register of n qubits, leaving only the apply() methods unimplemented as to
 * allow multiple implementations of applying gates to a specific qubit.
 */
public abstract class AbstractQuantumRegister {
    protected Matrix quantumRegister;
    protected int qubitNum;

    /**
     * Default constructor. Creates a register of qubitNum qubits, making it a size 2^n x 1 size matrix. Qubits are
     * counted from 0, e.g. for 5 qubits all the indices are 0,1,2,3,4. All qubits are initially set to state |0>.
     * @param qubitNum the amount of qubits
     */
    public AbstractQuantumRegister(int qubitNum){
        quantumRegister = new Matrix(1<<qubitNum, 1);
        this.qubitNum = qubitNum;

        quantumRegister.setElement(0, 0, 1.0);
    }

    /**
     * Sets a register to a uniform superposition over all values. In theory this would be achieved by applying the
     * Hadamard gate to all qubits, but in the interest of efficiency this is implemented by assigning equal probability
     * amplitudes to all states.
     */
    public void setUniformSuperposition(){
        //Create a uniform distribution
        double uniformValue = 1.0 / (1 << (qubitNum>>1));
        for(int n=0;n<quantumRegister.getRowSize();n++){
            quantumRegister.setElement(n, 0, uniformValue);
        }
    }

    /**
     * Sets the quantum register the state |0>.
     */
    public void setZero(){
        quantumRegister.setElement(1, 0, 1.0);
        for(int i=1;i<quantumRegister.getRowSize();i++){
            quantumRegister.setElement(i, 0, 0.0);
        }
    }

    /**
     * Renormalises the quantum register to make sure all the squares of the probability amplitudes add up to 1.
     */
    public void renormalise(){
        double factor = 0.0;
        for(int n=0;n<quantumRegister.getRowSize();n++){
            factor += quantumRegister.getElement(n, 0).normSquared();
        }

        for(int n=0;n<quantumRegister.getRowSize();n++){
            quantumRegister.setElement(n, 0, Complex.divideComplex(quantumRegister.getElement(n, 0), Math.sqrt(factor)));
        }
    }

    /**
     * Convenience method to finding the value in the underlying matrix.
     * @param i the index of state whose probability amplitude to return
     * @return the probability amplitude
     */
    public Complex getProbabilityAmplitude(int i){
        return quantumRegister.getElement(i, 0);
    }

    /**
     * Convenience method to set a probability amplitude explicitly. This is not realistic, but can be useful.
     */
    public void setProbabilityAmplitude(Complex c, int i){ quantumRegister.setElement(i, 0, c); }

    /**
     * Returns the size of the register, equal to 2^n where n is the amount of qubits.
     * @return the register size
     */
    public int getNumberOfStates(){
        return quantumRegister.getRowSize();
    }

    /**
     * Returns the amount of qubits the register has.
     * @return the number of qubits
     */
    public int getQubitNumber(){ return qubitNum; }

    /**
     * Simulates the measurement of the state of the whole register. Each state has a probability of being measured
     * equal to the square of the probability amplitude. Once the measurement is complete, the state collapses onto
     * the result of the measurement wrt one of the 2^n basis states.
     * @return the result of the simulated measurement
     */
    public int measure(){
        double p = Math.random();
        double cumulativeProbability = 0.0;

        for(int i=0;i<quantumRegister.getRowSize();i++){
            cumulativeProbability += quantumRegister.getElement(i, 0).normSquared();

            if(p<=cumulativeProbability){
                collapse(i);
                return i;
            }
        }

        return -1;
    }

    /**
     * Measures the j-th qubit of the register, returning either 1 or 0 and partially collapsing the register, reducing
     * the non-zero valued states by a factor of 1/2.
     * @return the result of the simulated measurement
     */
    public int measureQubit(int j){
        //Probability that the qubit will be |0>; the probability of it being |1> is just 1-P(|0>)
        double probabilityZero = 0.0;

        //The bit to check
        int i = (qubitNum-1) - j;

        //Check all states that have the i-th bit equal to 0
        for(int n=0;n<quantumRegister.getRowSize();n++){
            if(((n>>i) & 1) == 0){
                probabilityZero += quantumRegister.getElement(n, 0).normSquared();
            }
        }

        System.out.printf("THE PROBABILITY IS %f\n", probabilityZero);

        //Collapse all states into the result of the measurement and return the result of the measurement
        if(Math.random() > probabilityZero){
            for(int n=0;n<quantumRegister.getRowSize();n++){
                if(((n>>i) & 1) == 0){
                    quantumRegister.setElement(n, 0, 0.0);
                }
            }
            renormalise();
            return 1;
        } else {
            for(int n=0;n<quantumRegister.getRowSize();n++){
                if(((n>>i) & 1) == 1){
                    quantumRegister.setElement(n, 0, 0.0);
                }
            }
            renormalise();
            return 0;
        }
    }

    /**
     * Measures all qubits from i to j (inclusive) in the quantum register. i must be smaller than j.
     * @param i the lower qubit index
     * @param j the upper qubit index
     */
    public void measureQubits(int i, int j){
        int i_ = (qubitNum-1) - i;
        int j_ = (qubitNum-1) - j;

        for(int n=j_;n<=i_;n++){
            measureQubit(n);
        }
    }

    /**
     * Collapses a quantum register onto value i, setting that probability amplitude to 1 and all others to 0, usually
     * as a result of a measurement.
     * @param i the value to collapse the register onto
     */
    public void collapse(int i){
        for(int k=0;k<quantumRegister.getRowSize();k++){
            if(k == i){
                quantumRegister.setElement(k, 0, 1.0);
            } else {
                quantumRegister.setElement(k, 0, 0.0);
            }
        }
    }

    /**
     * Helper method to check whether the register is still properly normalised, useful for debugging.
     * @return the total probability of the register
     */
    public double checkNormalisation(){
        double norm = 0.0;
        for(int n=0;n<getNumberOfStates();n++){
            norm += getProbabilityAmplitude(n).normSquared();
        }
        return norm;
    }

    /**
     * Returns the register in its entirety as a matrix.
     * @return the matrix representing the register
     */
    public Matrix getRegister(){
        return this.quantumRegister;
    }

    /**
     * Returns a String representation of the quantum register.
     * @return the String representation
     */
    public String toString(){
        String out = "";
        for(int i=0; i<quantumRegister.getRowSize(); i++){
            out += String.format("%s ", quantumRegister.getElement(i, 0).toString());
            out += String.format("|%d>     P=%.3f           ( |%s> )\n\n", i, quantumRegister.getElement(i, 0).normSquared(), Integer.toBinaryString(i));
        }

        return out;
    }

    /**
     * Applies a matrix to the whole register, which must be an nxn matrix where n is the amount of states in
     * the register.
     * @param matrix the matrix to multiply the register register with
     * @throws MatrixException when matrix is not a 2^n x 2^n matrix corresponding to the register's size
     */
    public void apply(Matrix matrix) throws MatrixException{
        quantumRegister = Matrix.mult(matrix, quantumRegister);
    }

    public abstract void apply(SingleQubitGate gate, int qubitIndex) throws MatrixException;

    public abstract void apply(TwoQubitGate gate, int qubitIndex1, int qubitIndex2) throws MatrixException;
}
