package register;

import mathStructs.Complex;
import mathStructs.Matrix;

/**
 * An abstract implementation of a quantum register of n qubits, leaving only the apply() method unimplemented as to
 * allow multiple implementations of applying quantum gates to a specific qubit.
 */
public abstract class AbstractQuantumRegister implements IQuantumRegister {
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
    @Override
    public Complex getProbabilityAmplitude(int i){
        return quantumRegister.getElement(i, 0);
    }

    /**
     * Returns the size of the quantum register, equal to 2^n where n is the amount of qubits
     * @return the register size
     */
    @Override
    public int getNumberOfStates(){
        return quantumRegister.getRowSize();
    }

    /**
     * Simulates the measurement of the state of the quantum register. Each state has a probability of being measured
     * equal to the square of the probability amplitude.
     * @return the result of the simulated measurement
     */
    @Override
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
     * Returns the quantum register in its entirety as a matrix
     * @return the matrix representing the quantum register
     */
    @Override
    public Matrix getRegister(){
        return this.quantumRegister;
    }
}
