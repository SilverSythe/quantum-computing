package register;

import gates.Gate;
import mathStructs.Complex;
import mathStructs.Matrix;
import mathStructs.MatrixException;

/**
 * An abstract implementation of a register register of n qubits, leaving only the apply() methods unimplemented as to
 * allow multiple implementations of applying register gates to a specific qubit.
 */
public abstract class AbstractQuantumRegister {
    protected Matrix quantumRegister;
    protected int qubitNum;

    /**
     * Default constructor. Creates a register of qubitNum qubits, making it a size 2^n x 1 size matrix.
     * @param qubitNum the amount of qubits
     */
    public AbstractQuantumRegister(int qubitNum){
        quantumRegister = new Matrix(1<<qubitNum, 1);
        this.qubitNum = qubitNum;
    }

    /**
     * Convenience method to avoid explicitly finding the value in the matrix.
     * @param i the index of state whose probability amplitude to return
     * @return the probability amplitude
     */
    public Complex getProbabilityAmplitude(int i){
        return quantumRegister.getElement(i, 0);
    }

    /**
     * Returns the size of the register register, equal to 2^n where n is the amount of qubits
     * @return the register size
     */
    public int getNumberOfStates(){
        return quantumRegister.getRowSize();
    }

    /**
     * Simulates the measurement of the state of the register register. Each state has a probability of being measured
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
     * Returns the register register in its entirety as a matrix
     * @return the matrix representing the register register
     */
    public Matrix getRegister(){
        return this.quantumRegister;
    }

    public abstract void apply(Matrix matrix) throws MatrixException;

    public abstract void apply(Gate gate, int qubitIndex) throws MatrixException;

    public abstract void apply(Gate gate, int qubitIndex1, int qubitIndex2) throws MatrixException;
}
