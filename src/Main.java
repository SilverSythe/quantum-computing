import gates.CNOT;
import gates.Hadamard;
import gates.PauliX;
import mathStructs.MatrixException;
import register.AbstractQuantumRegister;
import register.SmartQuantumRegister;

public class Main {

    public static void main(String[] args) throws MatrixException {
        AbstractQuantumRegister register = new SmartQuantumRegister(3);

        register.apply(new Hadamard(), 0);
        register.apply(new Hadamard(), 2);
        register.apply(new PauliX(), 2);
        register.apply(new PauliX(), 0);

        System.out.println(register.getRegister().toString());

        register.apply(new CNOT(), 2, 0);

        System.out.println(register.getRegister().toString());
    }
}
