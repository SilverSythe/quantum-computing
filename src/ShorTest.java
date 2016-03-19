import algorithms.Shor;
import mathStructs.MatrixException;
import register.AbstractQuantumRegister;
import register.SmartQuantumRegister;

public class ShorTest {
    public static void main(String[] args) throws MatrixException{
        Shor shor = new Shor(471);

        AbstractQuantumRegister input = new SmartQuantumRegister(9);
        AbstractQuantumRegister output = new SmartQuantumRegister(9);

        int[] factors = shor.apply(input, output);

        System.out.printf("Prime factors: %d, %d\n", factors[0], factors[1]);
    }
}