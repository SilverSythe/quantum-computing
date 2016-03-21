package gates;

public class CNOT extends TwoQubitGate {

    public CNOT(){
        super();
        gate.setElement(0, 0, 1.0);
        gate.setElement(1, 1, 1.0);
        gate.setElement(2, 3, 1.0);
        gate.setElement(3, 2, 1.0);
    }

}
