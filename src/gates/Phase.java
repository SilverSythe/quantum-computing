package gates;

import mathStructs.Complex;

public class Phase extends SingleQubitGate {

    public Phase(double angle){
        super();
        gate.setElement(0, 0, 1.0);
        gate.setElement(1, 0, 0.0);
        gate.setElement(0, 1, 0.0);
        gate.setElement(1, 1, new Complex(Math.cos(angle), Math.sin(angle)));
    }

}
