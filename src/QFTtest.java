import algorithms.QuantumFourierTransform;
import mathStructs.MatrixException;
import register.AbstractQuantumRegister;
import register.SmartQuantumRegister;

/**quantum fourier transform satisfy the equation:
 * |y> = 1/sqrt(N) * SUM( exp(2 * PI * i * x * y / N) |x>)
 * notice that the input state goes like |x0, x1, x2>,
 * but the output goes like|y2, y1, y0>
 */
public class QFTtest {
    public static void main(String[] args) throws MatrixException {
        AbstractQuantumRegister register = new SmartQuantumRegister(5);
        register.setUniformSuperposition();
        System.out.println(register.toString());

        QuantumFourierTransform.applyQFT(register);

        System.out.println(register.toString());
    }
}