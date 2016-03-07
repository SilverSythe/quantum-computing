package register;

import gates.Gate;
import mathStructs.Matrix;
import mathStructs.MatrixException;

public class ExplicitQuantumRegister extends AbstractQuantumRegister {
    /**
     * Default constructor. Creates a register of qubitNum qubits, making it a size 2^n x 1 size matrix. Qubits are
     * counted from 0, e.g. for 5 qubits all the indices are 0,1,2,3,4. All qubits are initially set to a superposition
     * of the |0> and |1> state, meaning the register has a uniform distribution.
     *
     * @param qubitNum the amount of qubits
     */
    public ExplicitQuantumRegister(int qubitNum) {
        super(qubitNum);
        double uniformValue = 1.0 / Math.pow(2.0, qubitNum/2.0);
        for(int n=0;n<quantumRegister.getRowSize();n++){
            quantumRegister.setElement(n, 0, uniformValue);
        }
    }

    /**
     * Applies a matrix to the whole quantum register, which must be an nxn matrix where n is the amount of states in
     * the quantum register.
     * @param matrix the matrix to multiply the quantum register with
     */
    @Override
    public void apply(Matrix matrix) throws MatrixException{
        quantumRegister = Matrix.mult(matrix, quantumRegister);
    }

    /**
     * Applies a single qubit gate to the quantum register using explicit tensor products. It applies the identity to
     * all other qubits.
     * @param gate the quantum gate to apply
     * @param qubitIndex the index of the qubit to apply it to
     */
    @Override
    public void apply(Gate gate, int qubitIndex) throws MatrixException {
        Matrix totalMatrix = new Matrix(1, 1);
        totalMatrix.setElement(0, 0, 1.0);

        //A 2x2 identity matrix
        Matrix eye = new Matrix(2,2);
        eye.setIdentity();

        for(int k=0;k<qubitIndex;k++){
            totalMatrix = Matrix.tensorProduct(totalMatrix, eye);
        }

        totalMatrix = Matrix.tensorProduct(totalMatrix, gate.getGateMatrix());

        for(int k=0;k<qubitNum-qubitIndex-1;k++){
            totalMatrix = Matrix.tensorProduct(totalMatrix, eye);
        }

        //Finally, multiply the matrix with the register
        quantumRegister = Matrix.mult(totalMatrix, quantumRegister);
    }

    @Override
    public void apply(Gate gate, int qubitIndex1, int qubitIndex2) throws MatrixException {

    }
}
