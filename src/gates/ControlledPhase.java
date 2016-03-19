package gates;

import mathStructs.Complex;
import mathStructs.Matrix;

public class ControlledPhase extends Gate {

    public ControlledPhase(double angle){
        gate=new Matrix(4,4);
        gate.setElement(0,0,1.0);
        gate.setElement(1,1,1.0);
        gate.setElement(2,2,1.0);
        gate.setElement(3,3,new Complex(Math.cos(angle),Math.sin(angle)));
    }
}
