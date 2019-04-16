
public class ARKSR {

	int textMatrix[][];
	int rkeyMatrix[][];
	boolean debug;
	
	
	public ARKSR(int[][] textMatrix, int[][] rkeyMatrix, boolean debug){
		this.textMatrix = textMatrix;
		this.rkeyMatrix = rkeyMatrix;
		this.debug = debug;
	}
	
	// wrap-around-shift rows over by their index
	// (so just skip 0th row, shift the first by 1, the second by 2, and the third by 3)
	public void sr(int i){
		int row = 1;
		int temp = this.textMatrix[row][0];
		this.textMatrix[row][0] = this.textMatrix[row][0 + 1];
		this.textMatrix[row][0 + 1] = this.textMatrix[row][0 + 2];
		this.textMatrix[row][0 + 2] = this.textMatrix[row][0 + 3];
		this.textMatrix[row][0 + 3] = temp;
		row++;
		temp = this.textMatrix[row][0 + 1];
		int temp0 = this.textMatrix[row][0];
		this.textMatrix[row][0] = this.textMatrix[row][0 + 2];
		this.textMatrix[row][0 + 1] = this.textMatrix[row][0 + 3];
		this.textMatrix[row][0 + 2] = temp0;
		this.textMatrix[row][0 + 3] = temp;
		row++;
		temp0 = this.textMatrix[row][0];
		temp = this.textMatrix[row][0 + 1];
		int temp2 =  this.textMatrix[row][0 + 2];
		this.textMatrix[row][0] = this.textMatrix[row][0 + 3];
		this.textMatrix[row][0 + 1] = temp0;
		this.textMatrix[row][0 + 2] = temp;
		this.textMatrix[row][0 + 3] = temp2;
	}
	/*public void sr(int i){
		int row = 1;
		int temp = this.textMatrix[row][4 * i];
		this.textMatrix[row][4 * i] = this.textMatrix[row][4 * i + 1];
		this.textMatrix[row][4 * i + 1] = this.textMatrix[row][4 * i + 2];
		this.textMatrix[row][4 * i + 2] = this.textMatrix[row][4 * i + 3];
		this.textMatrix[row][4 * i + 3] = temp;
		row++;
		temp = this.textMatrix[row][4 * i + 1];
		int temp0 = this.textMatrix[row][4 * i];
		this.textMatrix[row][4 * i] = this.textMatrix[row][4 * i + 2];
		this.textMatrix[row][4 * i + 1] = this.textMatrix[row][4 * i + 3];
		this.textMatrix[row][4 * i + 2] = temp0;
		this.textMatrix[row][4 * i + 3] = temp;
		row++;
		temp0 = this.textMatrix[row][4 * i];
		temp = this.textMatrix[row][4 * i + 1];
		int temp2 =  this.textMatrix[row][4 * i + 2];
		this.textMatrix[row][4 * i] = this.textMatrix[row][4 * i + 3];
		this.textMatrix[row][4 * i + 1] = temp0;
		this.textMatrix[row][4 * i + 2] = temp;
		this.textMatrix[row][4 * i + 3] = temp2;
	}*/
	
	// adds round key for round i
	// round key is rkeyMatrix[0:4][4 * i:4 * i + 4]
	public void ark(int i){
		int textVal;
		int keyVal;
		if (this.debug) System.out.println("roundkey: ");
		for (int row = 0; row < 4; row++){
			for (int col = 0; col < 4; col++){
				textVal = this.textMatrix[row][col];
				keyVal = this.rkeyMatrix[row][4 * i + col];
				if (this.debug) System.out.print(Integer.toHexString(keyVal) + " ");
				textVal = textVal ^ keyVal;
				this.textMatrix[row][col] = textVal;
			}
			if (this.debug) System.out.println();
		}
	}
	
}
