package register;

import gates.Gate;
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
    public void apply(Gate gate, int qubitIndex) throws MatrixException {
        Complex M00 = gate.getGateMatrix().getElement(0,0);
        Complex M01 = gate.getGateMatrix().getElement(0,1);
        Complex M10 = gate.getGateMatrix().getElement(1,0);
        Complex M11 = gate.getGateMatrix().getElement(1,1);

        Matrix qRegisterCopy = new Matrix(quantumRegister);

        int i = (qubitNum-1) - qubitIndex;

        for(int n=0; n<quantumRegister.getRowSize(); n++){
            Complex value1, value2;
            if(((n>>i) & 1) == 0){
                value1 = Complex.multComplex(M00, qRegisterCopy.getElement(n, 0));
                value2 = Complex.multComplex(M01, qRegisterCopy.getElement(n^(1<<i), 0));
            } else {
                value1 = Complex.multComplex(M10, qRegisterCopy.getElement(n^(1<<i), 0));
                value2 = Complex.multComplex(M11, qRegisterCopy.getElement(n, 0));
            }
            quantumRegister.setElement(n, 0, Complex.addComplex(value1, value2));
        }
    }

    @Override
    public void apply(Gate gate, int qubitIndex1, int qubitIndex2) throws MatrixException {
    //qubitIndex1<qubitIndex2
            Complex M00 = gate.getGateMatrix().getElement(0,0);
            Complex M01 = gate.getGateMatrix().getElement(0,1);
            Complex M02 = gate.getGateMatrix().getElement(0,2);
            Complex M03 = gate.getGateMatrix().getElement(0,3);
            Complex M10 = gate.getGateMatrix().getElement(1,0);
            Complex M11 = gate.getGateMatrix().getElement(1,1);
            Complex M12 = gate.getGateMatrix().getElement(1,2);
            Complex M13 = gate.getGateMatrix().getElement(1,3);
            Complex M20 = gate.getGateMatrix().getElement(2,0);
            Complex M21 = gate.getGateMatrix().getElement(2,1);
            Complex M22 = gate.getGateMatrix().getElement(2,2);
            Complex M23 = gate.getGateMatrix().getElement(2,3);
            Complex M30 = gate.getGateMatrix().getElement(3,0);
            Complex M31 = gate.getGateMatrix().getElement(3,1);
            Complex M32 = gate.getGateMatrix().getElement(3,2);
            Complex M33 = gate.getGateMatrix().getElement(3,3);

            Matrix qRegisterCopy = new Matrix(quantumRegister);

            int i = (qubitNum-1) - qubitIndex1;
            int j = (qubitNum-1) - qubitIndex2;

            for(int n=0; n<quantumRegister.getRowSize(); n++){
                Complex value1, value2,value3,value4;
                if((((n>>i) & 1) == 0) && (((n>>j) & 1) == 0)){
                    value1 = Complex.multComplex(M00, qRegisterCopy.getElement(n, 0));
                    value2 = Complex.multComplex(M01, qRegisterCopy.getElement(n^(1<<j), 0));
                    value3 = Complex.multComplex(M02, qRegisterCopy.getElement(n^(1<<i), 0));
                    value4 = Complex.multComplex(M03, qRegisterCopy.getElement((n^(1<<i))^(1<<j), 0));
                } else if(((n>>i) & 1) == 0) {
                    value1 = Complex.multComplex(M10, qRegisterCopy.getElement(n^(1<<j), 0));
                    value2 = Complex.multComplex(M11, qRegisterCopy.getElement(n, 0));
                    value3 = Complex.multComplex(M12, qRegisterCopy.getElement((n^(1<<i))^(1<<j), 0));
                    value4 = Complex.multComplex(M13, qRegisterCopy.getElement(n^(1<<i), 0));
                }else if((((n>>i) & 1) == 1) && (((n>>j) & 1) == 0)) {
                    value1 = Complex.multComplex(M20, qRegisterCopy.getElement(n^(1<<i), 0));
                    value2 = Complex.multComplex(M21, qRegisterCopy.getElement((n^(1<<i))^(1<<j), 0));
                    value3 = Complex.multComplex(M22, qRegisterCopy.getElement(n, 0));
                    value4 = Complex.multComplex(M23, qRegisterCopy.getElement(n^(1<<j), 0));
                }else{
                    value1 = Complex.multComplex(M30, qRegisterCopy.getElement((n^(1<<i))^(1<<j), 0));
                    value2 = Complex.multComplex(M31, qRegisterCopy.getElement(n^(1<<i), 0));
                    value3 = Complex.multComplex(M32, qRegisterCopy.getElement(n^(1<<j), 0));
                    value4 = Complex.multComplex(M33, qRegisterCopy.getElement(n, 0));
                }
                quantumRegister.setElement(n, 0, Complex.addComplex(Complex.addComplex(value1, value2),Complex.addComplex(value3, value4)));
            }
    }
}
