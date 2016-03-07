package register;

import gates.Gate;

public class ExplicitQuantumRegister extends AbstractQuantumRegister {
    /**
     * Default constructor. Creates a register of qubitNum qubits, making it a size 2^n x 1 size matrix.
     *
     * @param qubitNum the amount of qubits
     */
    public ExplicitQuantumRegister(int qubitNum) {
        super(qubitNum);
    }

    @Override
    public void apply(Gate gate, int qubitIndex) {

    }

    @Override
    public void apply(Gate gate, int qubitIndex1, int qubitIndex2) {

    }
}
