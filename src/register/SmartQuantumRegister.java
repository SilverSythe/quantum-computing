package register;

import gates.SingleQubitGate;

import mathStructs.Complex;
import mathStructs.Matrix;
import mathStructs.MatrixException;

public class SmartQuantumRegister extends AbstractQuantumRegister {
    /**
     * Default constructor. Creates a register of qubitNum qubits, making it a size 2^n x 1 size matrix.
     *
     * @param qubitNum the amount of qubits
     */
    public SmartQuantumRegister(int qubitNum) {
        super(qubitNum);
    }

    @Override
    public void apply(SingleQubitGate gate, int qubitIndex) throws MatrixException {
        Complex M00 = gate.getGateMatrix().getElement(0,0);
        Complex M01 = gate.getGateMatrix().getElement(0,1);
        Complex M10 = gate.getGateMatrix().getElement(1,0);
        Complex M11 = gate.getGateMatrix().getElement(1,1);

        Matrix qRegisterCopy = new Matrix(quantumRegister);

        //int i = (qubitNum-1) - qubitIndex;

        for(int n=0; n<quantumRegister.getRowSize(); n++){
            Complex value1, value2;
            if(((n>>qubitIndex) & 1) == 0){
                value1 = Complex.multComplex(M00, qRegisterCopy.getElement(n, 0));
                value2 = Complex.multComplex(M01, qRegisterCopy.getElement(n^(1<<qubitIndex), 0));
            } else {
                value1 = Complex.multComplex(M10, qRegisterCopy.getElement(n^(1<<qubitIndex), 0));
                value2 = Complex.multComplex(M11, qRegisterCopy.getElement(n, 0));
            }
            quantumRegister.setElement(n, 0, Complex.addComplex(value1, value2));
        }
    }

    
}
