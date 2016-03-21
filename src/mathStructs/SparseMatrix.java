package mathStructs;

import java.util.ArrayList;
import java.util.List;

//TODO:Testing

/**
 * 
 * Class representing Sparse Matrices with arbitrary size, and complex elements. 
 * Supports basic operations such as addition, subtraction and multiplication.
 *
 * Requires SparseCoord.java class.
 *
 */
public class SparseMatrix {

	private List<Complex> sparseValues = new ArrayList<Complex>();
	private List<SparseCoord> Coordinates = new ArrayList<SparseCoord>();

	/**
	 * Default SparseMatrix Constructor
	 */
	public SparseMatrix(){
	}

	/**
	 * Constructs SparseMatrix from Matrix;
	 * WARNING: ROW AND COLUMN SIZE DATA IS NOT RETAINED
	 * @param M - Matrix Template
	 */
	public SparseMatrix(Matrix M){

		int I = M.getColSize();
		int J = M.getRowSize();	
		int totalElementNumber = I * J;
		int iCount = 0;
		int jCount = 0;

		//Main Loop, escape when all of M's elements are surveyed
		for(int i = 0; i<totalElementNumber;){

			while(jCount < J){
				while(iCount < I){

					Complex z = M.getElement(iCount,jCount);
					double zR = z.getReal();
					double zI = z.getImag();

					if(zR != 0.0 || zI != 0.0){
						sparseValues.add(z);
						Coordinates.add(new SparseCoord(iCount,jCount));
					}
					iCount = iCount + 1;
				}
				jCount = jCount + 1;
			}
		}

	}

	//Getters/Setters

	/**
	 * Method to set SparseMatrix Complex Element at given coordinate
	 * @param i - i coordinate
	 * @param j - j coordinate
	 * @param value
	 */
	public void setElement(int i, int j, Complex value){

		SparseCoord x = new SparseCoord(i,j);
		int length = Coordinates.size();

		//checking element exists
		for (int lCount = 0; lCount < length;){
			SparseCoord z = Coordinates.get(lCount);
			int xI = x.getI();
			int xJ = x.getJ();
			if (xI == z.getI() && xJ == z.getJ()){
				//setting complex
				sparseValues.set(lCount, value);
			}

			lCount = lCount + 1;		
		}

		//appending to lists if coordinate does not exist
		sparseValues.add(value);
		Coordinates.add(x);
	}

	/**
	 * Method to get SparseMatrix Complex Element at given coordinate
	 * @param i - i coordinate
	 * @param j - j coordinate
	 * @return Complex Element
	 */
	public Complex getElement(int i, int j){

		SparseCoord x = new SparseCoord(i,j);
		int length = Coordinates.size();

		//checking element exists

		for (int lCount = 0; lCount < length;){
			SparseCoord z = Coordinates.get(lCount);
			int xI = x.getI();
			int xJ = x.getJ();
			if (xI == z.getI() && xJ == z.getJ()){
				//returning complex
				return sparseValues.get(lCount);			
			}

			lCount = lCount + 1;		
		}

		//complaining if coordinate does not exist
		return null;

	}

	/**
	 * Method to add two SparseMatrices,
	 * S1 will be changed, S2 will not
	 * @param S1
	 * @param S2
	 */
	public void add(SparseMatrix S1, SparseMatrix S2){

		int s2Counter;
		int s1Counter;
		int s1length = S1.Coordinates.size();
		int s2length = S2.Coordinates.size();
		boolean exists = false;

		for (s2Counter = 0; s2Counter < s2length;){

			SparseCoord x = S2.Coordinates.get(s2Counter);

			//checking if element exists in S1
			for (s1Counter = 0; s1Counter < s1length;){

				SparseCoord z = Coordinates.get(s1Counter);
				int xI = x.getI();
				int xJ = x.getJ();


				if (xI == z.getI() && xJ == z.getJ()){

					Complex CS1 = S1.sparseValues.get(s1Counter);
					Complex CS2 = S2.sparseValues.get(s2Counter);
					S1.setElement(x.getI(),x.getJ(),Complex.addComplex(CS1,CS2));
					exists = true;
				}
				s1Counter = s1Counter + 1;
			}

			//appending to lists if coordinate does not exist
			if(exists == false){
				S1.sparseValues.add(S2.sparseValues.get(s2Counter));
				S1.Coordinates.add(S2.Coordinates.get(s2Counter));
			}
			s2Counter = s2Counter + 1;
			exists = false;
		}
	}

	/**
	 * Method to subtract one SpareseMatrix from another
	 * S1 will be changed, S2 will not
	 * @param S1
	 * @param S2
	 */
	public void subtract(SparseMatrix S1, SparseMatrix S2){

		int s2Counter;
		int s1Counter;
		int s1length = S1.Coordinates.size();
		int s2length = S2.Coordinates.size();
		boolean exists = false;

		for (s2Counter = 0; s2Counter < s2length;){

			SparseCoord x = S2.Coordinates.get(s2Counter);

			//checking if element exists in S1
			for (s1Counter = 0; s1Counter < s1length;){

				SparseCoord z = Coordinates.get(s1Counter);
				int xI = x.getI();
				int xJ = x.getJ();


				if (xI == z.getI() && xJ == z.getJ()){

					Complex CS1 = S1.sparseValues.get(s1Counter);
					Complex CS2 = S2.sparseValues.get(s2Counter);
					S1.setElement(x.getI(),x.getJ(),Complex.subComplex(CS1,CS2));
					exists = true;
				}
				s1Counter = s1Counter + 1;
			}

			//appending to lists if coordinate does not exist
			if(exists == false){
				Complex C = new Complex();
				Complex CS2 = S2.sparseValues.get(s2Counter);
				CS2 = Complex.subComplex(C, CS2);		
				S1.sparseValues.add(CS2);
				S1.Coordinates.add(S2.Coordinates.get(s2Counter));
			}
			s2Counter = s2Counter + 1;
			exists = false;
		}
	}

	/**
	 * Method to multiply two Sparse Matrices
	 * Returns new SparseMatrix object
	 * Row and Column size for each must be specified
	 * WARNING: MULTIPLICATION WILL BE ATTEMPTED EVEN IF WRONG ROW/COLUMN SPECIFICATIONS GIVEN
	 * Returns null in event of incompatible matrix dimensions for multiplication.
	 * @param S1
	 * @param S1rowsize
	 * @param S1colsize
	 * @param S2
	 * @param S2rowsize
	 * @param S2colsize
	 * @return Resultant Sparse Matrix
	 */
	public SparseMatrix SparseMult(SparseMatrix S1, int S1rowsize, int S1colsize, SparseMatrix S2, int S2rowsize, int S2colsize){

		if(S1rowsize != S2colsize){
			System.out.println("Given Matrix Dimensions incompatible with multiplication.");
			return null;
		}

		SparseMatrix S3 = new SparseMatrix();

		List<Complex> S1RowValues = new ArrayList<Complex>();
		List<Integer> S1RowCoords = new ArrayList<Integer>();
		List<Complex> S2ColValues = new ArrayList<Complex>();
		List<Integer> S2ColCoords = new ArrayList<Integer>();
		Complex PlaceHold = new Complex (0.0,0.0);

		int S1rowcount = 0;
		int S2columncount = 0;
		int totalElements = S1rowsize * S2colsize;
		int iterationNumber = 0;

		while(iterationNumber < totalElements){

			//Find all S1 elements in row S1RowCount
			for(int n = 0; n < S1.Coordinates.size();){
				SparseCoord x = S1.Coordinates.get(n);
				int xi = x.getI();
				if(xi == S1rowcount){
					S1RowValues.add(S1.sparseValues.get(n));
					S1RowCoords.add(S1.Coordinates.get(n).getJ());
				}
				n = n + 1;
			}

			//Find all S2 elements in column S2ColCount
			for(int n = 0; n < S2.Coordinates.size();){
				SparseCoord x = S2.Coordinates.get(n);
				int xi = x.getI();
				if(xi == S2columncount){
					S2ColValues.add(S2.sparseValues.get(n));
					S1RowCoords.add(S2.Coordinates.get(n).getI());

				}
				n = n + 1;
			}

			//Compare Coordinates of found values
			//Determine required multiplications
			for(int n = 0; n < S1RowValues.size();){
				int xs1 = S1RowCoords.get(n);

				//check relevant coordinate exists
				if(S2ColCoords.indexOf(xs1) != -1){
					int xs2 = S2ColCoords.indexOf(xs1);
					Complex xc1 = S1RowValues.get(n);
					Complex xc2 = S2ColValues.get(xs2);
					Complex xc3 = Complex.multComplex(xc1,xc2);

					//Creates running total of what S3 element will be
					PlaceHold = Complex.addComplex(PlaceHold, xc3);

				}
				n = n + 1;
			}

			//Set S3 Value
			S3.sparseValues.add(PlaceHold);
			PlaceHold.setComplex(0.0, 0.0);

			//Calculate and set S3 Coordinate
			int S3i = iterationNumber%(S2rowsize);
			int S3j = (iterationNumber - S3i)/(S2rowsize);
			SparseCoord S3Coord = new SparseCoord(S3i,S3j);
			S3.Coordinates.add(S3Coord);
		}

		return S3;

	}

}





