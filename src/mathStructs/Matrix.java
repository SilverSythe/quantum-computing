package mathStructs;

//TODO: IMPLEMENT COMPLEX VERSION OF MATRIX
//TODO: IMPLEMENT INVERSE OPERATION

/**
 * Class representing an arbitrary nxm matrix with real-valued elements. Supports basic operations such as addition,
 * multiplication, transpose and inverse operations.
 */
public class Matrix {
    //Row and column size of matrix
    private int rowSize, colSize;
    private Complex[] values;


    /**
     * Default constructor.
     */
    public Matrix(){
        rowSize = 1;
        colSize = 1;

        values = new Complex[1];
        values[0] = new Complex(0.0, 0.0);
    }

    /**
     * Constructor. Creates a matrix of a specific size.
     * @param rows the amount of rows of the matrix
     * @param cols the amount of columns of the matrix
     */
    public Matrix(int rows, int cols){
        rowSize = rows;
        colSize = cols;
        values = new Complex[rows * cols];

        for(int i=0;i<rowSize;i++){
            for(int j=0;j<colSize;j++){
                setElement(i, j, new Complex(0.0, 0.0));
            }
        }
    }

    /**
     * Copy constructor.
     * @param M the matrix to copy
     */
    public Matrix(Matrix M){
        this.rowSize = M.rowSize;
        this.colSize = M.colSize;
        values = new Complex[M.rowSize * M.colSize];
        for(int i=0;i<rowSize;i++){
            for(int j=0;j<colSize;j++){
                this.setElement(i, j, M.getElement(i, j));
            }
        }
    }

    /**
     * Sets a specific value of the matrix.
     * @param i the row
     * @param j the column
     * @param val the value to assign
     */
    public void setElement(int i, int j, Complex val){
        values[i*colSize + j] = val;
    }

    /**
     * Gets a specific value of the matrix
     * @param i the row
     * @param j the column
     */
    public Complex getElement(int i, int j){
        return values[i*colSize + j];
    }

    /**
     * Set this matrix to the identity matrix, but only if it is a square matrix.
     */
    public void setIdentity() throws MatrixException{
        if(rowSize != colSize){
            throw new MatrixException("Not a square matrix, cannot set to identity.");
        }
        for(int i=0;i<rowSize;i++){
            for(int j=0;j<colSize;j++){
                if(i==j){
                    setElement(i, j, new Complex(1.0, 0.0));
                } else {
                    setElement(i, j, new Complex(0.0, 0.0));
                }
            }
        }
    }

    /**
     * Get the amount of rows the matrix has.
     * @return amount of rows
     */
    public int getRowSize() {
        return rowSize;
    }

    /**
     * Returns the amount of columns the matrix has.
     * @return amount of columns
     */
    public int getColSize() {
        return colSize;
    }

    /**
     * Computes and returns the determinant of the matrix.
     * @return the determinant
     */
    public double det() throws MatrixException{
        if(rowSize != colSize){
            throw new MatrixException("Not a square matrix, cannot calculate determinant.");
        }


    }

    /**
     * Takes the transpose of the matrix by swapping elements across the main diagonal.
     */
    public void transpose(){
        Matrix orig = new Matrix(this);
        Matrix T = new Matrix(colSize, rowSize);

        for(int i=0;i<rowSize;i++){
            for(int j=0;j<colSize;j++){
                T.setElement(i, j, orig.getElement(j, i));
            }
        }
        for(int n=0;n<values.length;n++){
            values[n] = T.getElement(n/rowSize, n%colSize);
        }
    }

    public void invert(){

    }

    /**
     * Returns a String representation of the matrix.
     * @return the String representation
     */
    public String toString(){
        String str = "";

        for(int i=0;i<rowSize;i++){
            for(int j=0;j<colSize;j++){
                str += (this.getElement(i, j).toString() + " ");
            }
            str += "\n";
        }

        return str;
    }

    /**
     * Multiply a matrix by a scalar.
     * @param c the scalar to multiply with
     * @param M the matrix
     * @return the result
     */
    public static Matrix mult(double c, Matrix M){
        Matrix out = new Matrix(M);
        for(int i=0;i<out.rowSize;i++){
            for(int j=0;j<out.colSize;j++){
                out.setElement(i, j, Complex.multComplex(out.getElement(i,j), c));
            }
        }
        return out;
    }

    /**
     * Multiply two matrices A*B.
     * @param A the first (left) matrix
     * @param B the second (right) matrix
     * @return the result
     */
    public static Matrix mult(Matrix A, Matrix B) throws MatrixException{
        if(A.colSize != B.rowSize){
            throw new MatrixException("Dimension mismatch, cannot multiply.");
        }

        Matrix M = new Matrix(A.rowSize, B.colSize);

        for(int i=0;i<M.rowSize;i++){
            for(int j=0;j<M.colSize;j++){
                Complex value = new Complex(0.0, 0.0);

                for(int k=0;k<A.colSize;k++){
                    Complex.addComplex(value, Complex.multComplex(A.getElement(i,k), B.getElement(k,j)));
                }

                M.setElement(i, j, value);
            }
        }

        return M;
    }

    public static Matrix add(Matrix A, Matrix B) throws MatrixException{
        if((A.colSize != B.colSize) || (A.rowSize != B.rowSize)){
            throw new MatrixException("Dimension mismatch, cannot add.");
        }

        Matrix M = new Matrix();

        for(int i=0;i<M.rowSize;i++){
            for(int j=0;j<M.colSize;j++){
                M.setElement(i, j, Complex.addComplex(A.getElement(i, j), B.getElement(i, j)));
            }
        }

        return M;
    }
}
