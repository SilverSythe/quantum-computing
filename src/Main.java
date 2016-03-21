import gates.PauliX;
import mathStructs.MatrixException;
import register.AbstractQuantumRegister;
import register.SmartQuantumRegister;

public class Main {

    public static void main(String[] args) throws MatrixException {

        /*
        AbstractQuantumRegister explicitQuantumRegister = new ExplicitQuantumRegister(3);

        explicitQuantumRegister.apply(new PauliX(), 2);

        explicitQuantumRegister.apply(new CNOT(), 2, 0);

        //System.out.println(explicitQuantumRegister.measureQubit(2));
        System.out.println(explicitQuantumRegister.getRegister().toString());

        System.out.println("\n\n\n");*/


        AbstractQuantumRegister smartQuantumRegister = new SmartQuantumRegister(3);

        smartQuantumRegister.apply(new PauliX(), 2);

        //smartQuantumRegister.apply(new CNOT(), 0, 2);

        //System.out.println(explicitQuantumRegister.measureQubit(2));
        System.out.println(smartQuantumRegister.toString());
    }
}
