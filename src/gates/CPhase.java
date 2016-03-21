package gates;

import mathStructs.Complex;

public class CPhase extends TwoQubitGate {

    public CPhase(double angle){
        super();
        gate.setElement(0,0,1.0);
        gate.setElement(1,1,1.0);
        gate.setElement(2,2,1.0);
        gate.setElement(3,3,new Complex(Math.cos(angle),Math.sin(angle)));
    }

}
