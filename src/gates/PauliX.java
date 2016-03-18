package gates;

import mathStructs.Matrix;

public class PauliX extends Gate {
    public PauliX(){
        gate = new Matrix(2, 2);
        gate.setElement(0, 0, 0.0);
        gate.setElement(1, 0, 1.0);
        gate.setElement(0, 1, 1.0);
        gate.setElement(1, 1, 0.0);
    }
}
