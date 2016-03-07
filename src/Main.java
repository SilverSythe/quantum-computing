import mathStructs.Matrix;

public class Main {

    public static void main(String[] args) {
        Matrix A = new Matrix(2,2);
        Matrix B = new Matrix(2,2);

        A.setElement(0, 0, 1.0);
        A.setElement(1, 1, 1.0);
        A.setElement(0, 1, 2.0);
        A.setElement(1, 0, 2.0);

        B.setElement(0, 0, 2.0);
        B.setElement(0, 1, 1.0);
        B.setElement(1, 0, 3.0);
        B.setElement(1, 1, 4.0);

        Matrix C = Matrix.tensorProduct(A, B);

        System.out.println(C.toString());
    }
}
