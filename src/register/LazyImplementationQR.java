package register;

import gates.*;
import mathStructs.Complex;
import mathStructs.Matrix;
import mathStructs.MatrixException;

public class LazyImplementationQR extends AbstractQuantumRegister{
    /**
     * Default constructor. Creates a register of qubitNum qubits, making it a size 2^n x 1 size matrix. Qubits are
     * counted from 0, e.g. for 5 qubits all the indices are 0,1,2,3,4. All qubits are initially set to a superposition
     * of the |0> and |1> state, meaning the register has a uniform distribution.
     *
     * @param qubitNum the amount of qubits
     */
    public LazyImplementationQR(int qubitNum) {
        super(qubitNum);
        double uniformValue = 1.0 / Math.pow(2.0, qubitNum/2.0);
        for(int n=0;n<quantumRegister.getRowSize();n++){
            quantumRegister.setElement(n, 0, uniformValue);
        }
    }

    /**
     * Default constructor. Creates a register of qubitNum qubits, making it a size 2^n x 1 size matrix. Qubits are
     * counted from 0, e.g. for 5 qubits all the indices are 0,1,2,3,4.
     * Register is initially set to be in a certain state
     * @param qubitNum the amount of qubits
     * @param qubitIndex the initial state of the Register
     */
    public LazyImplementationQR(int qubitNum,int qubitIndex) {
        super(qubitNum);
        quantumRegister.setElement(qubitIndex, 0, 1.0);
    }

    @Override
    public void apply(Matrix matrix) throws MatrixException {
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
            for(int l=0;l<Math.pow(2,qubitIndex);l++){
                for(int m=0;m<Math.pow(2,qubitNum-qubitIndex-1);m++){
                    int a,b;
                    a=l<<(qubitNum-qubitIndex)+m;
                    b=a+1<<(qubitNum-qubitIndex-1);
                    Complex[] temp=new Complex[2];
                    temp[0]=Complex.addComplex(Complex.multComplex(quantumRegister.getElement(a,0),gate.getElement(0,0)),Complex.multComplex(quantumRegister.getElement(b,0),gate.getElement(0,1)));
                    temp[1]=Complex.addComplex(Complex.multComplex(quantumRegister.getElement(a,0),gate.getElement(1,0)),Complex.multComplex(quantumRegister.getElement(b,0),gate.getElement(1,1)));
                    quantumRegister.setElement(a,0,temp[0]);
                    quantumRegister.setElement(b,0,temp[1]);
                }
            }
    }

    /**
     * Applies a two qubit gate to the quantum register using explicit tensor products. It applies the identity to
     * all other qubits.
     * @param gate the quantum gate to apply
     * @param qubitIndex1 the first index of the qubit to apply it to
     * @param qubitIndex2 the second index of the qubit to apply it to
     *                    qubitIndex1 < qubitIndex2
     */
    @Override
    public void apply(Gate gate, int qubitIndex1, int qubitIndex2) throws MatrixException {
        for(int l=0;l<Math.pow(2,qubitIndex1);l++){
            for(int m=0;m<Math.pow(2,qubitIndex2-qubitIndex1-1);m++) {
                for(int n=0;n<Math.pow(2,qubitNum-qubitIndex2-1);n++){
                    int a,b,c,d;
                    a=l<<(qubitNum-qubitIndex1)+ m<<(qubitNum-qubitIndex2)+n;
                    b=a+1<<(qubitNum-qubitIndex2-1);
                    c=a+1<<(qubitNum-qubitIndex1-1);
                    d=c+1<<(qubitNum-qubitIndex2-1);
                    Complex[] temp=new Complex[4];
                    temp[0]=Complex.addComplex(Complex.addComplex(Complex.multComplex(quantumRegister.getElement(a,0),gate.getElement(0,0)),Complex.multComplex(quantumRegister.getElement(b,0),gate.getElement(0,1))),Complex.addComplex(Complex.multComplex(quantumRegister.getElement(c,0),gate.getElement(0,2)),Complex.multComplex(quantumRegister.getElement(d,0),gate.getElement(0,3))));
                    temp[1]=Complex.addComplex(Complex.addComplex(Complex.multComplex(quantumRegister.getElement(a,0),gate.getElement(1,0)),Complex.multComplex(quantumRegister.getElement(b,0),gate.getElement(1,1))),Complex.addComplex(Complex.multComplex(quantumRegister.getElement(c,0),gate.getElement(1,2)),Complex.multComplex(quantumRegister.getElement(d,0),gate.getElement(1,3))));
                    temp[2]=Complex.addComplex(Complex.addComplex(Complex.multComplex(quantumRegister.getElement(a,0),gate.getElement(2,0)),Complex.multComplex(quantumRegister.getElement(b,0),gate.getElement(2,1))),Complex.addComplex(Complex.multComplex(quantumRegister.getElement(c,0),gate.getElement(2,2)),Complex.multComplex(quantumRegister.getElement(d,0),gate.getElement(2,3))));
                    temp[3]=Complex.addComplex(Complex.addComplex(Complex.multComplex(quantumRegister.getElement(a,0),gate.getElement(3,0)),Complex.multComplex(quantumRegister.getElement(b,0),gate.getElement(3,1))),Complex.addComplex(Complex.multComplex(quantumRegister.getElement(c,0),gate.getElement(3,2)),Complex.multComplex(quantumRegister.getElement(d,0),gate.getElement(3,3))));
                    quantumRegister.setElement(a,0,temp[0]);
                    quantumRegister.setElement(b,0,temp[1]);
                    quantumRegister.setElement(c,0,temp[2]);
                    quantumRegister.setElement(d,0,temp[3]);
                }
            }
        }
    }

    /**
     * Applies Toffoli gate to the quantum register,Toffoli gate apply on three qubits, two control bit and one
     * target bit, it only reverse the state of the target bit when the two control bits are both 1
     * @param controlbit1 the first control qubit
     * @param controlbit2 the second control qubit
     * @param targetbit the working qubit
     */
    public void ToffoliGate(int controlbit1,int controlbit2,int targetbit){
        apply(Hadamard,targetbit);
        apply(ControlledV, controlbit2,targetbit);
        apply(CNOT,controlbit1,controlbit2);
        apply(ControlledV, controlbit2,targetbit);
        apply(ControlledV, controlbit2,targetbit);
        apply(ControlledV, controlbit2,targetbit);
        apply(CNOT,controlbit1,controlbit2);
        apply(ControlledV, controlbit2,targetbit);
        apply(Hadamard,targetbit);
    }

    public int getQubitNumber(){
        return qubitNum;
    }

}
