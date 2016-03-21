package gates;

import mathStructs.Matrix;

public class Hadamard extends SingleQubitGate {

    public Hadamard() {
        super();
        gate.setElement(0, 0, 1.0);
        gate.setElement(1, 0, 1.0);
        gate.setElement(0, 1, 1.0);
        gate.setElement(1, 1, -1.0);
        gate = Matrix.mult(Math.pow(2.0,-0.5), gate);
    }

}
