package register;

import gates.Gate;
import mathStructs.Matrix;
import mathStructs.MatrixException;

/**
 * This implementation of a quantum register uses explicit tensor products to apply gates, making it simple but
 * very slow.
 */
public class ExplicitQuantumRegister extends AbstractQuantumRegister {

    public ExplicitQuantumRegister(int qubitNum) {
        super(qubitNum);
    }

    /**
     * Applies a single qubit gate to the register register using explicit tensor products. It applies the identity to
     * all other qubits.
     * @param gate the register gate to apply
     * @param qubitIndex the index of the qubit to apply it to
     * @throws MatrixException when matrix is not a 2^n x 2^n matrix corresponding to the register's size
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
        Matrix totalMatrix = new Matrix(1, 1);
        totalMatrix.setElement(0, 0, 1.0);

        //A 2x2 identity matrix
        Matrix eye = new Matrix(2,2);
        eye.setIdentity();

        //qubitIndex1 > qubitIndex2
        for(int k=0;k<qubitNum-qubitIndex1-1;k++){
            totalMatrix = Matrix.tensorProduct(totalMatrix, eye);
        }

        int tempdimension = (int) Math.pow(2,qubitIndex1-qubitIndex2+1);
        Matrix tempmatrix = new Matrix(tempdimension,tempdimension);
        System.out.println(tempdimension);
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                for(int k=0;k<Math.pow(2,qubitIndex1-qubitIndex2-1);k++){
                    int row= ((i>>1)<<(qubitIndex1-qubitIndex2)) + (k<<1) + i%2;
                    int col= ((j>>1)<<(qubitIndex1-qubitIndex2)) + (k<<1) + j%2;
                    //System.out.printf("%d, %d\n", row, col);
                    tempmatrix.setElement(row,col, gate.getGateMatrix().getElement(i,j));
                }
            }
        }

        totalMatrix = Matrix.tensorProduct(totalMatrix, tempmatrix);

        for(int k=0;k<qubitIndex2;k++){
            totalMatrix = Matrix.tensorProduct(totalMatrix, eye);
        }

        //Finally, multiply the matrix with the register
        quantumRegister = Matrix.mult(totalMatrix, quantumRegister);
    }
}
