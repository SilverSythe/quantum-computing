package algorithms;

import mathStructs.Complex;
import mathStructs.Matrix;
import mathStructs.MatrixException;
import register.AbstractQuantumRegister;

/**
 * Static methods to apply the quantum fourier transform (and inverse) to a quantum register.
 */
public class QuantumFourierTransform {
    public static void applyQFT(AbstractQuantumRegister register) throws MatrixException{
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

    public static void applyInverseQFT(AbstractQuantumRegister register) throws MatrixException{
        int N = register.getNumberOfStates();

        Matrix QFTMatrix = new Matrix(N, N);

        Complex omega = new Complex(Math.cos(2.0*Math.PI/(double)N), Math.sin(2.0*Math.PI/(double)N));

        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                Complex value = new Complex(omega);
                value.powerComplex(i*j);
                value.conj();
                value = Complex.divideComplex(value, Math.sqrt((double)N));
                QFTMatrix.setElement(i, j, value);
            }
        }

        register.apply(QFTMatrix);
    }
}
