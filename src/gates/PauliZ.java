package gates;

public class PauliZ extends SingleQubitGate {

    public PauliZ(){
        super();
        gate.setElement(0, 0, 1.0);
        gate.setElement(1, 0, 0.0);
        gate.setElement(0, 1, 0.0);
        gate.setElement(1, 1, -1.0);
    }

}
