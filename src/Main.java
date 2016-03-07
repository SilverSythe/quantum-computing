import gates.Hadamard;
import mathStructs.MatrixException;
import register.ExplicitQuantumRegister;

public class Main {

    public static void main(String[] args) throws MatrixException {
        ExplicitQuantumRegister register = new ExplicitQuantumRegister(3);

        System.out.println(register.getRegister());

        register.apply(new Hadamard(), 1);

        System.out.println(register.getRegister());

        register.apply(new Hadamard(), 1);

        System.out.println(register.getRegister());
    }
}
