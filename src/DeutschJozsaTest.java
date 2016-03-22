import algorithms.DeutschJozsa;
import mathStructs.MatrixException;

public class DeutschJozsaTest {
    public static void main(String[] args) throws MatrixException{
        DeutschJozsa test=new DeutschJozsa(2);
        test.oracle();
        test.measure();
    }
}
