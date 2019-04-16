import javax.xml.bind.DatatypeConverter;

// makes a 4 * 44 matrix that contains all the round keys
// where round i's key is the submatrix [0:4][i * 4: i * 4 + 4]
public class KeyScheduler {
	
	char[] keyArr;
	//matrix[row][col][nibble]
	char[][][] keyMatrixSmall = new char[4][4][2];
	int[][] keyMatrixBig;
	boolean debug;
	SBox sBox;

	
	public KeyScheduler(String keyStr, int[][] keyMatrixBig, SBox sBox, boolean debug)
	{
		this.keyArr = keyStr.toCharArray();
		this.keyMatrixBig = keyMatrixBig;
		this.sBox = sBox;
		this.debug = debug;
		this.setKeyMatrixSmall();
	}
	
	// get the 4 * 44 matrix that will be used to get the round keys
	public void setKeyMatrixBig(boolean debug) {
		// fill in the first 4 columns with keyMatrixSmall
		// start with 0 so DatatypeConverter works
		for(int col = 0; col < 4; col++)
			for (int row = 0; row < 4; row++)
			{
				String str0 = "0" + Character.toString(this.keyMatrixSmall[row][col][0]);
				String str1 = "0" + Character.toString(this.keyMatrixSmall[row][col][1]);
				// turn the hex into integers: each char is a nibble
				// should never be negative, so no need to xor with 0xffffff0
				int nib0 = DatatypeConverter.parseHexBinary(str0)[0] << 4;
				int nib1 = DatatypeConverter.parseHexBinary(str1)[0];
				if(this.debug) {
					System.out.println("nib0: " + nib0);
					System.out.println("nib1: " + nib1);
				}
				// never negative, so no need to xor the nibbles
				// with 0xffffff00 to get rid of leading ones; just eah other to get whole byte
				int matrVal = nib0^nib1;
				this.keyMatrixBig[row][col] = matrVal; 
			}
		
		if(this.debug) {
			this.printKeyMatrixBig();
		}
		
		for (int col = 4; col < 44; col++){
			byte[] xor0 = new byte[1];
			byte[] xor1 = new byte[1];
			// if the col isn't a multiple of 4, just copy col - 1 and xor with col - 4
			if (col % 4 != 0) {
				for (int row = 0; row < 4; row++){
					// first nibble of col - 1
					int bytemin1 = this.keyMatrixBig[row][col - 1];
					/*
					if (this.debug){
						System.out.println("bytemin1: " + bytemin1);
						if (bytemin1 < 0)
						{
							this.printKeyMatrixBig();
						}
						System.out.println("row: " + row);
						System.out.println("col: " + col);
					}
					*/
					// first nibble of col - 4
					int bytemin4 = this.keyMatrixBig[row][col - 4];
					//get xors of bytes in columns
					int xor = bytemin1^bytemin4;
					// set the entry in col to their xor
					this.keyMatrixBig[row][col] = xor;
				}
			} // otherwise, if the col number is divisible by 4 
			else {
				// bump up each row from the last column; put old top on bottom
				this.keyMatrixBig[0][col] = this.keyMatrixBig[1][col - 1];
				this.keyMatrixBig[1][col] = this.keyMatrixBig[2][col - 1];
				this.keyMatrixBig[2][col] = this.keyMatrixBig[3][col - 1];
				this.keyMatrixBig[3][col] = this.keyMatrixBig[0][col - 1];
				for (int row = 0; row < 4; row++) {
					int currByte = this.keyMatrixBig[row][col];
					if (this.debug) {
						System.out.println("row: " + row);
						System.out.println("col: " + col);
					}
					currByte = this.sBox.subst(currByte);
					// xor with round const if first row, where round is col/4 since each round needs 4 cols
					int round = col/4;
					if(this.debug) {
						System.out.println("sBoxVal int: " + Integer.toHexString(currByte));
						System.out.println("round: " + round);
					}
					if (row == 0) {
						int rc = this.roundConst(round);
						currByte = currByte ^ rc;
						if (this.debug) 
							System.out.println("round constant: " + rc);
					}
					// xor with col - 4; get col - 4 val, then xor
					int bytemin4 = keyMatrixBig[row][col - 4];
					currByte = currByte ^ bytemin4;
					//put the result of all the xors into the matrix
					keyMatrixBig[row][col] = currByte;
				}
			}
		}
		
		if(this.debug) {
			this.printKeyMatrixBig();
		}
	}
	
	private int roundConst(int round) {
		// constant list courtesy wikipedia
		int[] constsInDecimal = 
			{0, 1, 2, 4, 8, 16, 32, 64, 128, 27, 54};
		return constsInDecimal[round];
	}
	
	// turn the key into matrix form
	private void setKeyMatrixSmall() {
		int nextnib = 0;
		for(int col = 0; col < 4; col++)
			for (int row = 0; row < 4; row++)
			{
				this.keyMatrixSmall[row][col][0] = this.keyArr[nextnib];
				nextnib++;
				this.keyMatrixSmall[row][col][1] = this.keyArr[nextnib];
				nextnib++;
			}
		if(this.debug) {
			System.out.println("the key turned into a 4*4 matrix");
			for (int row = 0; row < 4; row++){
				for(int col = 0; col < 4; col++)
				{
					System.out.print(this.keyMatrixSmall[row][col][0]);
					System.out.print(this.keyMatrixSmall[row][col][1]);
					System.out.print(" ");
				}
				System.out.println();
			}
		}
	}
	
	private void printKeyMatrixBig() {
		System.out.println("the matrix from which we'll take round keys so far");
		for (int r = 0; r < 4; r++){
			for(int c = 0; c < 44; c++)
			{
				String printVal = Integer.toHexString(this.keyMatrixBig[r][c]);
				System.out.print(printVal);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
}
