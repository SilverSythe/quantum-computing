package algorithms;

import mathStructs.Matrix;
import mathStructs.MatrixException;
import register.AbstractQuantumRegister;

public class Grover {
    private int answer;
    private int listSize;
    private Matrix oracle;
    private Matrix diffusion;

    public Grover(int answer, int listSize){
        this.answer = answer;
        this.listSize = listSize;
    }

    public void apply(AbstractQuantumRegister quantumRegister, int iterations) throws MatrixException {
        if(quantumRegister.getNumberOfStates() < listSize){
            System.out.printf("Quantum register is too small, needs at least %d states.", listSize);
            return;
        }

        //Grover's algorithm requires a uniform superposition to start with
        quantumRegister.setUniformSuperposition();

        oracle = new Matrix(quantumRegister.getNumberOfStates(), quantumRegister.getNumberOfStates());
        oracle.setIdentity();
        oracle.setElement(answer, answer, -1.0);

        Matrix registerTranspose = new Matrix(quantumRegister.getRegister());
        registerTranspose.transpose();
        diffusion = Matrix.mult(quantumRegister.getRegister(), registerTranspose);

        //diffusion = Matrix.selfOuterProduct(quantumRegister.getRegister());

        diffusion = Matrix.mult(2.0, diffusion);
        Matrix eye = new Matrix(quantumRegister.getNumberOfStates(), quantumRegister.getNumberOfStates());
        eye.setIdentity();
        diffusion = Matrix.subtract(diffusion, eye);

        //The main loop of the algorithm.
        for(int i=0;i<iterations;i++){
            quantumRegister.apply(oracle);
            quantumRegister.apply(diffusion);

            System.out.printf("n=%d: P=%f\n", i+1, quantumRegister.getProbabilityAmplitude(answer).normSquared());
        }
    }

    public void singleIteration(AbstractQuantumRegister quantumRegister) throws MatrixException{
        if(quantumRegister.getNumberOfStates() < listSize){
            System.out.printf("Quantum register is too small, needs at least %d states.\n", listSize);
            return;
        }

        oracle = new Matrix(quantumRegister.getNumberOfStates(), quantumRegister.getNumberOfStates());
        oracle.setIdentity();
        oracle.setElement(answer, answer, -1.0);

        Matrix registerTranspose = new Matrix(quantumRegister.getRegister());
        registerTranspose.transpose();
        diffusion = Matrix.mult(quantumRegister.getRegister(), registerTranspose);
        diffusion = Matrix.mult(2.0, diffusion);
        Matrix eye = new Matrix(quantumRegister.getNumberOfStates(), quantumRegister.getNumberOfStates());
        eye.setIdentity();
        diffusion = Matrix.subtract(diffusion, eye);

        quantumRegister.apply(oracle);
        quantumRegister.apply(diffusion);
    }

    public int getAnswer() {
        return answer;
    }

    public int getListSize() {
        return listSize;
    }
}
