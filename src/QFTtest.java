import algorithms.QuantumFourierTransform;
import gates.ControlledPhase;
import gates.Hadamard;
import mathStructs.MatrixException;

/**quantum fourier transform satisfy the equation:
 * |y> = 1/sqrt(N) * SUM( exp(2 * PI * i * x * y / N) |x>)
 * notice that the input state goes like |x0, x1, x2>,
 * but the output goes like|y2, y1, y0>
 */
public class QFTtest {
    public static void main(String[] args) throws MatrixException {
        QuantumFourierTransform QFT= new QuantumFourierTransform(3,0);
        System.out.println(QFT.getregister().toString());

        QFT.apply();
        System.out.println(QFT.getregister().toString());
    }
}