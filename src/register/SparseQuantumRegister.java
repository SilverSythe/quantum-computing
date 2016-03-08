package register;

import gates.Gate;
import mathStructs.Matrix;
import mathStructs.MatrixException;

public class SparseQuantumRegister extends AbstractQuantumRegister {
    /**
     * Default constructor. Creates a register of qubitNum qubits, making it a size 2^n x 1 size matrix.
     *
     * @param qubitNum the amount of qubits
     */
    public SparseQuantumRegister(int qubitNum) {
        super(qubitNum);
    }

    @Override
    public void apply(Matrix matrix) throws MatrixException {
        quantumRegister = Matrix.mult(matrix, quantumRegister);
    }

    //TODO: implement application of sparse matrix to quantum register
    @Override
    public void apply(Gate gate, int qubitIndex) throws MatrixException {

    }

    @Override
    public void apply(Gate gate, int qubitIndex1, int qubitIndex2) throws MatrixException {

    }
}
