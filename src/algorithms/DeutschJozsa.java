package algorithms;

import gates.Hadamard;
import mathStructs.Complex;
import mathStructs.MatrixException;
import register.SmartQuantumRegister;

public class DeutschJozsa {
    private SmartQuantumRegister register;

    public DeutschJozsa(int qubitNum){
        register= new SmartQuantumRegister(qubitNum);
    }

    public void oracle(){
        double p=Math.random();
        System.out.println(p);

        if(p>0.5){  //balanced oracle
            for(int i=0;i<register.getNumberOfStates();i++){
                if(i%2==1) {
                    register.getRegister().setElement(i, 0, register.getRegister().getElement(i, 0));
                }else{
                    register.getRegister().setElement(i, 0, Complex.multComplex(register.getRegister().getElement(i, 0),-1));
                }
            }
        }
        else{    //constant oracle  ,here we assume the function will always gives f(x)=1

        }
    }

    public void measure() throws MatrixException {
        for(int i=0;i<register.getQubitNumber();i++){
            register.apply(new Hadamard(), i);
        }
        Complex result = new Complex(register.getProbabilityAmplitude(0));
        System.out.println(result);
        //the value will be 1 if constant, and will be 0 if balanced
        if(result.getReal()==0){
            System.out.println("the oracle function is balanced");
        }else{
            System.out.println("the oracle function is constant");
        }
    }

}
