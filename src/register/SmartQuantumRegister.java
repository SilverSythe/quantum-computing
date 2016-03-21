package register;

import gates.SingleQubitGate;
import gates.TwoQubitGate;
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

    @Override
    public void apply(TwoQubitGate gate, int qubitIndex1, int qubitIndex2) throws MatrixException {
        Matrix m = new Matrix(gate.getGateMatrix());

        //int i = (qubitNum-1) - qubitIndex1;
        //int j = (qubitNum-1) - qubitIndex2;

        Matrix qRegisterCopy = new Matrix(quantumRegister);

        for(int n=0; n<quantumRegister.getRowSize(); n++){
            Complex value1, value2, value3, value4;
            //Find out which row the matrix we need by isolating the relevant bits of state n
            int row = ((n>>qubitIndex1)&1) + ((n>>qubitIndex2)&1) * 2;
            //Flip through the bits to find how states are interconnected
            //Case |00>
            if(row == 0){
                value1 = Complex.multComplex(m.getElement(row,0), qRegisterCopy.getElement(n, 0));
                value2 = Complex.multComplex(m.getElement(row,1), qRegisterCopy.getElement(n^(1<<qubitIndex1), 0));
                value3 = Complex.multComplex(m.getElement(row,2), qRegisterCopy.getElement(n^(1<<qubitIndex2), 0));
                value4 = Complex.multComplex(m.getElement(row,3), qRegisterCopy.getElement(n^(1<<qubitIndex2)^(1<<qubitIndex1), 0));
            }
            //Case |01>
            else if (row ==1){
                value1 = Complex.multComplex(m.getElement(row,0), qRegisterCopy.getElement(n^(1<<qubitIndex1), 0));
                value2 = Complex.multComplex(m.getElement(row,1), qRegisterCopy.getElement(n, 0));
                value3 = Complex.multComplex(m.getElement(row,2), qRegisterCopy.getElement(n^(1<<qubitIndex2)^(1<<qubitIndex1), 0));
                value4 = Complex.multComplex(m.getElement(row,3), qRegisterCopy.getElement(n^(1<<qubitIndex2), 0));
            }
            //Case |10>
            else if (row == 2){
                value1 = Complex.multComplex(m.getElement(row,0), qRegisterCopy.getElement(n^(1<<qubitIndex2), 0));
                value2 = Complex.multComplex(m.getElement(row,1), qRegisterCopy.getElement(n^(1<<qubitIndex2)^(1<<qubitIndex1), 0));
                value3 = Complex.multComplex(m.getElement(row,2), qRegisterCopy.getElement(n, 0));
                value4 = Complex.multComplex(m.getElement(row,3), qRegisterCopy.getElement(n^(1<<qubitIndex1), 0));
            }
            //Case |11>
            else {
                value1 = Complex.multComplex(m.getElement(row,0), qRegisterCopy.getElement(n^(1<<qubitIndex2)^(1<<qubitIndex1), 0));
                value2 = Complex.multComplex(m.getElement(row,1), qRegisterCopy.getElement(n^(1<<qubitIndex2), 0));
                value3 = Complex.multComplex(m.getElement(row,2), qRegisterCopy.getElement(n^(1<<qubitIndex1), 0));
                value4 = Complex.multComplex(m.getElement(row,3), qRegisterCopy.getElement(n, 0));
            }
            quantumRegister.setElement(n, 0, Complex.addComplex(Complex.addComplex(value1, value2), Complex.addComplex(value3, value4)));
        }
    }
}
