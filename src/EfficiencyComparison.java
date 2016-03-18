import gates.Hadamard;
import mathStructs.MatrixException;
import ptolemy.plot.Plot;
import ptolemy.plot.PlotFrame;
import register.AbstractQuantumRegister;
import register.ExplicitQuantumRegister;
import register.SmartQuantumRegister;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class compares the efficiency of the different quantum register apply() implementations.
 */
public class EfficiencyComparison {
    public static void main(String[] args) throws MatrixException, IOException, InterruptedException {
        PrintWriter printWriterExplicit = new PrintWriter(new FileWriter("outExplicit.dat"));
        PrintWriter printWriterSmart = new PrintWriter(new FileWriter("outSmart.dat"));

        //Max of about 4E6 states
        int maxQubits = 22;

        //Array containing execution time values in nanoseconds
        double[] explicitTimes = new double[maxQubits];
        double[] smartTimes = new double[maxQubits];

        //Create arrays of quantum registers with increasing sizes
        AbstractQuantumRegister[] explicitQuantumRegisters = new ExplicitQuantumRegister[maxQubits];
        AbstractQuantumRegister[] smartQuantumRegisters = new SmartQuantumRegister[maxQubits];

        for(int i=0;i<maxQubits;i++){
            explicitQuantumRegisters[i] = new ExplicitQuantumRegister(i+1);
            smartQuantumRegisters[i] = new SmartQuantumRegister(i+1);
        }

        //Explicit evaluations
        for(int i=0;i<maxQubits;i++){
            explicitTimes[i] = System.nanoTime();

            explicitQuantumRegisters[i].apply(new Hadamard(), 0);

            explicitTimes[i] -= System.nanoTime();
            explicitTimes[i] *= -1E-9;

            if(explicitTimes[i] > 1.5){
                break;
            }

            Thread.sleep(100);
        }

        //Smart evaluations
        for(int i=0;i<maxQubits;i++){
            smartTimes[i] = System.nanoTime();

            smartQuantumRegisters[i].apply(new Hadamard(), 0);

            smartTimes[i] -= System.nanoTime();
            smartTimes[i] *= -1E-9;

            Thread.sleep(100);
        }

        //Plot the values
        Plot plot = new Plot();
        plot.setXLabel("N (number of qubits)");
        plot.setYLabel("time (seconds)");
        for(int i=0;i<maxQubits;i++){
            if(explicitTimes[i]!=0.0) {
                plot.addPoint(0, i + 1, explicitTimes[i], true);
                printWriterExplicit.write(String.format("%f\n", explicitTimes[i]));
            }
            plot.addPoint(1, i+1, smartTimes[i], true);
            printWriterSmart.write(String.format("%f\n", smartTimes[i]));
        }

        PlotFrame plotFrame = new PlotFrame("Efficiency comparison", plot);
        plotFrame.setSize(800, 600);
        plotFrame.setVisible(true);

        printWriterExplicit.close();
        printWriterSmart.close();
    }
}
