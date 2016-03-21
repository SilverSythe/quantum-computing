package mathStructs;
/**
 * Supplementary Class to SparseMatrix,
 * Allows for i/j coordinate cohesion for Sparse Matrices
 * 
 */
public class SparseCoord {

	private int iCoord;
	private int jCoord;

	/**
	 * Default SparseCoord Constructor
	 */
	public SparseCoord(){
		iCoord = 0;
		jCoord = 0;

	}

	/**
	 * SparseCoord constructor with specified coordinates
	 * @param i - i coordinate
	 * @param j - j coordinate
	 */
	public SparseCoord(int i, int j){
		iCoord = i;
		jCoord = j;		

	}

	/**
	 * Method to get i coordinate from SparseCoord object
	 * @return i coordinate int
	 */
	public int getI(){

		return this.iCoord;		
	}

	/**
	 * Method to get j coordinate from SparseCoord object
	 * @return j coordinate int
	 */
	public int getJ(){

		return this.jCoord;		
	}
}
