import algorithms.Shor;
import mathStructs.MatrixException;
import register.AbstractQuantumRegister;
import register.SmartQuantumRegister;

public class ShorTest {
    public static void main(String[] args) throws MatrixException{
        Shor shor = new Shor(55);

        AbstractQuantumRegister input = new SmartQuantumRegister(14);
        AbstractQuantumRegister output = new SmartQuantumRegister(14);

        int[] factors = shor.apply(input, output, true);

        System.out.printf("Prime factors: %d --> %d, %d\n", factors[0] * factors[1], factors[0], factors[1]);

        //Try brute force method
        //ClassicalPrime primeBruteForce = new ClassicalPrime(55);


    }
}