package gates;

public class PauliX extends SingleQubitGate {

    public PauliX(){
        super();
        gate.setElement(0, 0, 0.0);
        gate.setElement(1, 0, 1.0);
        gate.setElement(0, 1, 1.0);
        gate.setElement(1, 1, 0.0);
    }

}
