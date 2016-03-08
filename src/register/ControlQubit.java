package register;

import gates.Gate;
import mathStructs.Matrix;
import mathStructs.MatrixException;

/**
 * Represents a single qubit subsystem meant to be used as a control, which does not interact with anything else. It is
 * therefore just a superposition of |0> and |1>. It is implemented as a quantum register of size 1.
 */
public class ControlQubit extends AbstractQuantumRegister {

    /**
     * Default constructor. As a single qubit, it only has 2 possible states, making it a 2x1 matrix.
     */
    public ControlQubit() {
        super(2);
    }

    @Override
    public void apply(Matrix matrix) throws MatrixException {
        quantumRegister = Matrix.mult(matrix, quantumRegister);
    }

    @Override
    public void apply(Gate gate, int qubitIndex) throws MatrixException {
        quantumRegister = Matrix.mult(gate.getGateMatrix(), quantumRegister);
    }

    @Override
    public void apply(Gate gate, int qubitIndex1, int qubitIndex2) throws MatrixException {

    }
}
