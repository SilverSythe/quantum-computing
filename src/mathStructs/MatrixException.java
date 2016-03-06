package mathStructs;

public class MatrixException extends Exception {
    private static final long serialVersionUID = 1L;

    public MatrixException(String message) {
        super(message);
    }

    public MatrixException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
