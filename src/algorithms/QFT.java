package algorithms;

import gates.CPhase;
import gates.Hadamard;
import mathStructs.Complex;
import mathStructs.Matrix;
import mathStructs.MatrixException;
import register.AbstractQuantumRegister;

/**
 * Static methods to apply the quantum Fourier transform to a quantum register.
 */
public class QFT {
    @Deprecated
    public static void apply(AbstractQuantumRegister register) throws MatrixException{
        int N = register.getNumberOfStates();

        Matrix QFTMatrix = new Matrix(N, N);

        Complex omega = new Complex(Math.cos(2.0*Math.PI/(double)N), Math.sin(2.0*Math.PI/(double)N));

        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                Complex value = new Complex(omega);
                value.powerComplex(i*j);
                value = Complex.divideComplex(value, Math.sqrt((double)N));
                QFTMatrix.setElement(i, j, value);
            }
        }

        register.apply(QFTMatrix);
    }

    /**
     * Applies the quantum Fourier transform on a register, by applying a network of gates.
     * @param register the register to act on
     * @throws MatrixException on illegal matrix operation
     */
    public static void applyQFT(AbstractQuantumRegister register) throws MatrixException {
        for(int i=0;i<register.getQubitNumber();i++){
            for(int j=0;j<i;j++){
                register.apply(new CPhase(Math.PI/Math.pow(2,i-j)), i, j);
            }
            register.apply(new Hadamard(), i);
        }
    }
}
