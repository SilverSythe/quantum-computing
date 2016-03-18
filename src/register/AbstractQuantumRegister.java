package register;

import gates.Gate;
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
     * counted from 0, e.g. for 5 qubits all the indices are 0,1,2,3,4. All qubits are initially set to a superposition
     * of the |0> and |1> state, meaning the register has a uniform distribution over the 2^n states.
     *
     * @param qubitNum the amount of qubits
     */
    public AbstractQuantumRegister(int qubitNum){
        quantumRegister = new Matrix(1<<qubitNum, 1);
        this.qubitNum = qubitNum;

        //Create a uniform distribution
        double uniformValue = 1.0 / Math.pow(2.0, qubitNum/2.0);
        for(int n=0;n<quantumRegister.getRowSize();n++){
            quantumRegister.setElement(n, 0, uniformValue);
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
     * Returns the size of the register, equal to 2^n where n is the amount of qubits.
     * @return the register size
     */
    public int getNumberOfStates(){
        return quantumRegister.getRowSize();
    }

    /**
     * Simulates the measurement of the state of the register. Each state has a probability of being measured
     * equal to the square of the probability amplitude.
     * @return the result of the simulated measurement
     */
    public int measure(){
        double p = Math.random();
        double cumulativeProbability = 0.0;

        for(int i=0;i<quantumRegister.getRowSize();i++){
            cumulativeProbability += quantumRegister.getElement(i, 0).normSquared();

            if(p<=cumulativeProbability){
                return i;
            }
        }

        return -1;
    }

    /**
     * Returns the register in its entirety as a matrix.
     * @return the matrix representing the register
     */
    public Matrix getRegister(){
        return this.quantumRegister;
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

    public abstract void apply(Gate gate, int qubitIndex) throws MatrixException;

    public abstract void apply(Gate gate, int qubitIndex1, int qubitIndex2) throws MatrixException;
}
