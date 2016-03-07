package gates;

public class Hadamard extends Gate {

    public Hadamard() {
        gate.setElement(0, 0, 1.0);
        gate.setElement(1, 0, 1.0);
        gate.setElement(0, 1, 1.0);
        gate.setElement(1, 1, -1.0);
    }

}
