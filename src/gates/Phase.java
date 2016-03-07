package gates;

import mathStructs.Complex;
import mathStructs.Matrix;

public class Phase extends Gate {

    public Phase(double angle){
        gate = new Matrix(2, 2);
        gate.setElement(0, 0, 1.0);
        gate.setElement(1, 0, 0.0);
        gate.setElement(0, 1, 0.0);
        gate.setElement(1, 1, new Complex(Math.cos(angle), Math.sin(angle)));
    }

}
