package register;

import gates.SingleQubitGate;
import gates.TwoQubitGate;
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

    //TODO: implement application of sparse matrix to quantum register
    @Override
    public void apply(SingleQubitGate gate, int qubitIndex) throws MatrixException {

    }

    @Override
    public void apply(TwoQubitGate gate, int qubitIndex1, int qubitIndex2) throws MatrixException {

    }
}
