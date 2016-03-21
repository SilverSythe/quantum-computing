package gates;

import mathStructs.Matrix;

public abstract class SingleQubitGate {
    protected Matrix gate;

    public SingleQubitGate(){
        gate = new Matrix(2,2);
    }

    public Matrix getGateMatrix(){
        return this.gate;
    }
}
