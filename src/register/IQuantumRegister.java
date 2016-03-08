package register;

import gates.Gate;
import mathStructs.Complex;
import mathStructs.Matrix;
import mathStructs.MatrixException;

/**
 * An interface representing the functionality of a register register.
 */
public interface IQuantumRegister {

    void apply(Matrix matrix) throws MatrixException;

    void apply(Gate gate, int qubitIndex) throws MatrixException;

    void apply(Gate gate, int qubitIndex1, int qubitIndex2) throws MatrixException;

    Complex getProbabilityAmplitude(int i);

    int getNumberOfStates();

    int measure();

    Matrix getRegister();

}
