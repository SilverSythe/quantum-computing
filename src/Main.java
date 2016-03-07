import algorithms.Grover;
import mathStructs.MatrixException;
import register.ExplicitQuantumRegister;

public class Main {

    public static void main(String[] args) throws MatrixException {
        //32 state quantum register
        ExplicitQuantumRegister register = new ExplicitQuantumRegister(5);

        //Search for the number 26 in a list of 32
        Grover grover = new Grover(26, 32);
        grover.apply(register, 500);
        //System.out.println(register.getRegister());
        System.out.println(register.measure());
    }
}
