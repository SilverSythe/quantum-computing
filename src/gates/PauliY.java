package gates;

import mathStructs.Complex;

public class PauliY extends SingleQubitGate {

    public PauliY(){
        super();
        gate.setElement(0, 0, 0.0);
        gate.setElement(1, 0, new Complex(0.0, 1.0));
        gate.setElement(0, 1, new Complex(0.0, -1.0));
        gate.setElement(1, 1, 0.0);
    }

}
