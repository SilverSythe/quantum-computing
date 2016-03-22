package gates;

import mathStructs.Complex;
import mathStructs.Matrix;

public class ControlledV extends TwoQubitGate {
    public ControlledV(){
        gate=new Matrix(4,4);
        gate.setElement(0,0,1.0);
        gate.setElement(1,1,1.0);
        gate.setElement(2,2,1.0);
        gate.setElement(3,3,new Complex(0, 1));
    }
}
