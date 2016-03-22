import algorithms.Grover;
import mathStructs.MatrixException;
import register.SmartQuantumRegister;

/**
 * This class tests Grover's algorithm.
 */
public class GroverTest {

    public static void main(String[] args) throws MatrixException{
        int N = 10;
        SmartQuantumRegister register = new SmartQuantumRegister(N);

        Grover grover = new Grover(1, 1<<N);

        int iterations = 100;

        grover.apply(register, iterations);

    }
}