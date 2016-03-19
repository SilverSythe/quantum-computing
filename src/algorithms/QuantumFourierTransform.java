package algorithms;

import gates.ControlledPhase;
import gates.Hadamard;
import mathStructs.Matrix;
import mathStructs.MatrixException;
import register.SmartQuantumRegister;

public class QuantumFourierTransform {
    private SmartQuantumRegister register;

    public QuantumFourierTransform(int qubitNum,int qubitIndex){
        this.register=new SmartQuantumRegister(qubitNum, qubitIndex);
    }

    public void apply() throws MatrixException {
        for(int i=0;i<register.getQubitNumber();i++){
            for(int j=0;j<i;j++){
                register.apply(new ControlledPhase(Math.PI/Math.pow(2,i-j)), i, j);
            }
            register.apply(new Hadamard(), i);
        }
    }

    public SmartQuantumRegister getRegister(){
        return this.register;
    }

    public Matrix getregister(){
        return this.register.getRegister();
    }
}