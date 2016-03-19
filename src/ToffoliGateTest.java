import gates.*;
import mathStructs.MatrixException;
import register.LazyQuantumRegister;

/**
 * Applies Toffoli gate to the quantum register,Toffoli gate apply on three qubits, two control bit and one
 * target bit, it only reverse the state of the target bit when the two control bits are both 1
 */

public class ToffoliGateTest {

    public static void main(String[] args) throws MatrixException {
        LazyQuantumRegister register = new LazyQuantumRegister(3);

        /** creat a | 1 1 1 > state */
        register.apply(new Hadamard(), 0);
        register.apply(new Hadamard(), 1);
        register.apply(new Hadamard(), 2);
        register.apply(new PauliX(), 2);
        register.apply(new PauliX(), 1);
        register.apply(new PauliX(), 0);
        System.out.println(register.getRegister().toString());

        register.ToffoliGate(0, 1, 2);
        System.out.println(register.getRegister().toString());
    }
}
