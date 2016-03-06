package gates;

import mathStructs.Matrix;

public class Gate {
    private Matrix gate;

    public Gate(Matrix gateMatrix){
        gate = gateMatrix;
    }

    public Matrix getGate(){
        return this.gate;
    }
}
