package gates;

import mathStructs.Matrix;

public class CNOT extends Gate {
    public CNOT(){
        gate = new Matrix(4, 4);
        gate.setElement(0, 0, 1.0);
        gate.setElement(1, 1, 1.0);
        gate.setElement(2, 3, 1.0);
        gate.setElement(2, 3, 1.0);
    }
}
