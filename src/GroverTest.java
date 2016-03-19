import algorithms.Grover;
import mathStructs.MatrixException;
import register.SmartQuantumRegister;

public class GroverTest {
    public static void main(String[] args) throws MatrixException{
        int N = 10;
        SmartQuantumRegister register = new SmartQuantumRegister(N);

        Grover grover = new Grover(1, 1<<N);

        int iterations = 100;


        grover.apply(register, iterations);

        double[] probabilites = new double[iterations];

        /*
        for(int n=0;n<iterations;n++) {
            grover.singleIteration(register);
            probabilites[n] = register.getProbabilityAmplitude(1).normSquared();
            System.out.println(probabilites[n]);
        }*/
    }
}