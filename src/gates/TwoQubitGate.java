package gates;

import mathStructs.Matrix;

public abstract class TwoQubitGate {
    protected Matrix gate;

    public TwoQubitGate(){
        gate = new Matrix(4,4);
    }

    public Matrix getGateMatrix(){
        return this.gate;
    }
}
