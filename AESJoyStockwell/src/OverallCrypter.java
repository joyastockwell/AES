import javax.xml.bind.DatatypeConverter;

public class OverallCrypter {
	
	int[][] textMatrix = new int[4][4];
	int[][] rkeyMatrix;
	SBox sBox;
	int iters;
	int rounds;
	boolean debug;
	
	public OverallCrypter(String ptext, int[][] rkeyMatrix, SBox sBox, int iters, int rounds, boolean debug){
		this.debug = debug;
		this.rkeyMatrix = rkeyMatrix;
		this.sBox = sBox;
		this.iters = iters;
		this.rounds = rounds;
		this.ptextToMatrix(ptext);
		}
	
	// turn the key into matrix form
		private void ptextToMatrix(String ptext) {
			// take 2 hex chars (i.e. 2 nibbles = 1 byte) and turn them into an int
			// then store that int into the textMatrix
			int nextByte = 0;
			for(int col = 0; col < 4; col++)
				for (int row = 0; row < 4; row++)
				{
					String nextValStr = ptext.substring(nextByte, nextByte + 2);
					// this returns an array of length 1; we just want the elem
					int nextValInt = DatatypeConverter.parseHexBinary(nextValStr)[0];
					// get rid of leading 1s
					if (nextValInt < 0) nextValInt = nextValInt ^ 0xffffff00;
					this.textMatrix[row][col] = nextValInt;
					// increment nextByte by 2
					nextByte += 2;
				}
			if(this.debug) {
				System.out.println("the plaintext turned into a 4*4 matrix:");
				for (int row = 0; row < 4; row++){
					for(int col = 0; col < 4; col++)
					{
						System.out.print(Integer.toHexString(this.textMatrix[row][col]));
						System.out.print(" ");
					}
					System.out.println();
				}
			}
		}
		
		// run all rounds
		public void runAllRounds() {
			// initialization vector 0, so don't put anything in here
			int[][] ctextOld = new int[4][4];
			// a matrix for the plaintext, which we'll xor with the output
			int[][] ptext = new int[4][4];
			for (int row = 0; row < 4; row++){
				for (int col = 0; col < 4; col++){
					ptext[row][col] = this.textMatrix[row][col];
				}
			}
			for (int i = 0; i < this.iters; i++) {
				// xor ptext with ciphertext from old round
				for (int row = 0; row < 4; row++){
					for (int col = 0; col < 4; col++){
						this.textMatrix[row][col] = ptext[row][col] ^ ctextOld[row][col];
					}
				}
				if (this.debug) {
					System.out.println("xor of the previous ciphertext with the plaintext: ");
					for (int row = 0; row < 4; row++){
						for (int col = 0; col < 4; col++){
							System.out.print(Integer.toHexString(this.textMatrix[row][col]) + " ");
						}
						System.out.println();
					}
				}
				// run encryption algorithm once
				this.crypt();
				// copy current output for xoring in next round
				for (int row = 0; row < 4; row++){
					for (int col = 0; col < 4; col++){
						ctextOld[row][col] = this.textMatrix[row][col];
					}
				}
			}
		}
		
		//runs one rounds	
		public void crypt() {
			ARKSR arksr = new ARKSR(this.textMatrix, this.rkeyMatrix, false);
			ColMixer colmixer = new ColMixer(this.textMatrix, false);
			// initial ark round
			arksr.ark(0);
			if(this.debug){
				System.out.println("the matrix after the ark step:");
				this.printTextMatrix();
			}
			// regular rounds + last round, which has no column mixing
			for (int i = 1; i <= rounds; i++) {
				if(this.debug) System.out.println("round number: " + i);
				this.bs();
				if(this.debug){
					System.out.println("the matrix after the bs step:");
					this.printTextMatrix();
				}
				arksr.sr(i);
				if(this.debug) {
					System.out.println("the matrix after the sr step:");
					this.printTextMatrix();
				}
				// no mix columns on last round
				if (i < rounds) colmixer.cm();
				if(this.debug) {
					System.out.println("the matrix after the cm step:");
					this.printTextMatrix();
				}
				arksr.ark(i);
				if(this.debug) {
					System.out.println("the matrix after the ark step:");
					this.printTextMatrix();
				}
			}
		}
		
		// sub the bytes using the sBox
		private void bs() {
			for (int row = 0; row < 4; row++){
				for (int col = 0; col < 4; col++){
					this.textMatrix[row][col] = this.sBox.subst(this.textMatrix[row][col]);
				}
			}
		}
		
		private void printTextMatrix() {
			for (int row = 0; row < 4; row++){
				for (int col = 0; col < 4; col++){
					System.out.print(Integer.toHexString(this.textMatrix[row][col]) + " ");
					}
				System.out.println();
				}
		}
		
		public void printTextMatrixSingleLine() {
			for (int col = 0; col < 4; col++){
				for (int row = 0; row < 4; row++){
					// make sure to print a leading 0 on numbers less than 16
					if (this.textMatrix[row][col] < 16)
					System.out.print("0");
					System.out.print(Integer.toHexString(this.textMatrix[row][col]));
					}
				}
		}

}
