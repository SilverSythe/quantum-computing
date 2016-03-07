package register;

import gates.Gate;
import mathStructs.Complex;
import mathStructs.Matrix;
import mathStructs.MatrixException;

/**
 * An interface representing the functionality of a quantum register.
 */
public interface IQuantumRegister {

    void apply(Gate gate, int qubitIndex) throws MatrixException;

    void apply(Gate gate, int qubitIndex1, int qubitIndex2) throws MatrixException;

    Complex getProbabilityAmplitude(int i);

    int getSize();

    int measure();

    Matrix getRegister();

}
