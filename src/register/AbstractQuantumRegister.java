package register;

import mathStructs.Matrix;

/**
 * An abstract implementation of a quantum register of n qubits, leaving only the apply() method unimplemented as to
 * allow multiple implementations of applying quantum gates to a specific qubit.
 */
public abstract class AbstractQuantumRegister implements IQuantumRegister {
    Matrix quantumRegister;

    /**
     * Default constructor. Creates a register of qubitNum qubits, making it a size 2^n x 1 size matrix.
     * @param qubitNum the amount of qubits
     */
    public AbstractQuantumRegister(int qubitNum){
        quantumRegister = new Matrix(1<<qubitNum, 1);
    }

    /**
     * Returns the size of the quantum register, equal to 2^n where n is the amount of qubits
     * @return the register size
     */
    @Override
    public int getSize(){
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
            cumulativeProbability += quantumRegister.getElement(i, 1).normSquared();

            if(p<=cumulativeProbability){
                return i;
            }
        }
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
