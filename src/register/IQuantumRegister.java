package register;

import gates.Gate;
import mathStructs.Matrix;

/**
 * An interface representing the functionality of a quantum register.
 */
public interface IQuantumRegister {

    void apply(Gate gate, int qubitIndex);

    void apply(Gate gate, int qubitIndex1, int qubitIndex2);

    int getSize();

    int measure();

    Matrix getRegister();

}
