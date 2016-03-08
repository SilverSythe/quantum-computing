import algorithms.Grover;
import mathStructs.MatrixException;
import register.ExplicitQuantumRegister;

public class Main {

    public static void main(String[] args) throws MatrixException {
        //32 state register register
        ExplicitQuantumRegister register = new ExplicitQuantumRegister(9);


        //Search for the number 26 in a list of 32
        Grover grover = new Grover(123, 512);
        grover.apply(register, 1000);
        System.out.println(register.measure());
    }
}
