
public class ColMixer {

	String[][] logTableStr = {
			{ "00", "00", "19", "01", "32", "02", "1a", "c6", "4b", "c7", "1b", "68", "33", "ee", "df", "03" },
			{ "64", "04", "e0", "0e", "34", "8d", "81", "ef", "4c", "71", "08", "c8", "f8", "69", "1c", "c1" },
			{ "7d", "c2", "1d", "b5", "f9", "b9", "27", "6a", "4d", "e4", "a6", "72", "9a", "c9", "09", "78" },
			{ "65", "2f", "8a", "05", "21", "0f", "e1", "24", "12", "f0", "82", "45", "35", "93", "da", "8e" },
			{ "96", "8f", "db", "bd", "36", "d0", "ce", "94", "13", "5c", "d2", "f1", "40", "46", "83", "38" },
			{ "66", "dd", "fd", "30", "bf", "06", "8b", "62", "b3", "25", "e2", "98", "22", "88", "91", "10" },
			{ "7e", "6e", "48", "c3", "a3", "b6", "1e", "42", "3a", "6b", "28", "54", "fa", "85", "3d", "ba" },
			{ "2b", "79", "0a", "15", "9b", "9f", "5e", "ca", "4e", "d4", "ac", "e5", "f3", "73", "a7", "57" },
			{ "af", "58", "a8", "50", "f4", "ea", "d6", "74", "4f", "ae", "e9", "d5", "e7", "e6", "ad", "e8" },
			{ "2c", "d7", "75", "7a", "eb", "16", "0b", "f5", "59", "cb", "5f", "b0", "9c", "a9", "51", "a0" },
			{ "7f", "0c", "f6", "6f", "17", "c4", "49", "ec", "d8", "43", "1f", "2d", "a4", "76", "7b", "b7" },
			{ "cc", "bb", "3e", "5a", "fb", "60", "b1", "86", "3b", "52", "a1", "6c", "aa", "55", "29", "9d" },
			{ "97", "b2", "87", "90", "61", "be", "dc", "fc", "bc", "95", "cf", "cd", "37", "3f", "5b", "d1" },
			{ "53", "39", "84", "3c", "41", "a2", "6d", "47", "14", "2a", "9e", "5d", "56", "f2", "d3", "ab" },
			{ "44", "11", "92", "d9", "23", "20", "2e", "89", "b4", "7c", "b8", "26", "77", "99", "e3", "a5" },
			{ "67", "4a", "ed", "de", "c5", "31", "fe", "18", "0d", "63", "8c", "80", "c0", "f7", "70", "07" } };

	String[][] expTableStr = {
			{ "01", "03", "05", "0f", "11", "33", "55", "ff", "1a", "2e", "72", "96", "a1", "f8", "13", "35" },
			{ "5f", "e1", "38", "48", "d8", "73", "95", "a4", "f7", "02", "06", "0a", "1e", "22", "66", "aa" },
			{ "e5", "34", "5c", "e4", "37", "59", "eb", "26", "6a", "be", "d9", "70", "90", "ab", "e6", "31" },
			{ "53", "f5", "04", "0c", "14", "3c", "44", "cc", "4f", "d1", "68", "b8", "d3", "6e", "b2", "cd" },
			{ "4c", "d4", "67", "a9", "e0", "3b", "4d", "d7", "62", "a6", "f1", "08", "18", "28", "78", "88" },
			{ "83", "9e", "b9", "d0", "6b", "bd", "dc", "7f", "81", "98", "b3", "ce", "49", "db", "76", "9a" },
			{ "b5", "c4", "57", "f9", "10", "30", "50", "f0", "0b", "1d", "27", "69", "bb", "d6", "61", "a3" },
			{ "fe", "19", "2b", "7d", "87", "92", "ad", "ec", "2f", "71", "93", "ae", "e9", "20", "60", "a0" },
			{ "fb", "16", "3a", "4e", "d2", "6d", "b7", "c2", "5d", "e7", "32", "56", "fa", "15", "3f", "41" },
			{ "c3", "5e", "e2", "3d", "47", "c9", "40", "c0", "5b", "ed", "2c", "74", "9c", "bf", "da", "75" },
			{ "9f", "ba", "d5", "64", "ac", "ef", "2a", "7e", "82", "9d", "bc", "df", "7a", "8e", "89", "80" },
			{ "9b", "b6", "c1", "58", "e8", "23", "65", "af", "ea", "25", "6f", "b1", "c8", "43", "c5", "54" },
			{ "fc", "1f", "21", "63", "a5", "f4", "07", "09", "1b", "2d", "77", "99", "b0", "cb", "46", "ca" },
			{ "45", "cf", "4a", "de", "79", "8b", "86", "91", "a8", "e3", "3e", "42", "c6", "51", "f3", "0e" },
			{ "12", "36", "5a", "ee", "29", "7b", "8d", "8c", "8f", "8a", "85", "94", "a7", "f2", "0d", "17" },
			{ "39", "4b", "dd", "7c", "84", "97", "a2", "fd", "1c", "24", "6c", "b4", "c7", "52", "f6", "01" } };

	int[][] logTableInt = new int[16][16];

	int[][] expTableInt = new int[16][16];

	int[][] coefMatrix = { { 02, 03, 01, 01 }, { 01, 02, 03, 01 }, { 01, 01, 02, 03 }, { 03, 01, 01, 02 } };

	int[][] textMatrix;

	boolean debug;

	public ColMixer(int[][] textMatrix, boolean debug) {
		this.textMatrix = textMatrix;
		this.debug = debug;
		// convert the tables to ints
		for (int row = 0; row < 16; row++)
			for (int col = 0; col < 16; col++) {
				this.logTableInt[row][col] = Integer.parseInt(this.logTableStr[row][col], 16);
				this.expTableInt[row][col] = Integer.parseInt(this.expTableStr[row][col], 16);
			}
	}

	// multiply coefMatrix by textMatrix in GF(2^8)
	public void cm() {
		// create a temp matrix to keep new text vals (answers) in so we can use
		// the old text values for multiplication instead of overwriting them
		int[][] temp = new int[4][4];
		// row and col describe position of val in textMatrix after matrix
		// multiplication
		for (int row = 0; row < 4; row++)
			for (int col = 0; col < 4; col++) {
				int val = 0;
				// multiply the rows in the coef matrix by the cols of the text
				// matrix
				// so hold the coef matrix's row and the text matrix's col ind
				// constant
				// and change the index of the cols in the coef matrix and the
				// rows of the text matrix
				for (int colCoefRowText = 0; colCoefRowText < 4; colCoefRowText++) {
					int coef = this.coefMatrix[row][colCoefRowText];
					int text = this.textMatrix[colCoefRowText][col];
					if (this.debug && row > 0) {
						System.out.println("rowCoef: " + row);
						System.out.println("colText: " + col);
						System.out.println("colCoefRowText: " + colCoefRowText);
						System.out.println("coef: " + coef);
						System.out.println("text: " + Integer.toHexString(text));
					}
					int prod = this.multOne(coef, text);
					// if (this.debug) System.out.println("product: " + prod);
					val = val ^ prod;
				}
				temp[row][col] = val;
			}
		// set the real text matrix to the temporary one full mixed column vals
		for (int row = 0; row < 4; row++)
			for (int col = 0; col < 4; col++)
				this.textMatrix[row][col] = temp[row][col];
	}

	// takes column and row
	// multiplies one val from the coef matrix by one val from textMatrix
	// returns product
	public int multOne(int coef, int text) {
		int retVal = text;
		// if retVal is 0, it will stay zero no matter how we multiply it.
		if (retVal == 0)
			return retVal;
		// if the coef we multiply by is one, just leave alone
		if (coef == 1)
			return retVal;
		// if we are multiplying by 2, left shift once
		if (coef == 2) {
			retVal = retVal << 1;
			// if we've overflowed a byte (gotten a number = 256 or bigger)
			// we must xor with b100011011 = 283 decimal
			if (retVal >= 256) {
				retVal = 283 ^ retVal;
			}
			return retVal;
		}
		// if we are multiplying by 3 (only other option), use lookup tables
		// get the log of the byte base 3, add the log of 3 base 3 (i.e. 1),
		// and exponentiate: 3^sum
		// first nibble of the byte choose row of logTableInt entry that will
		// replace it
		// second nibble chooses column
		int firstNib = retVal >> 4;
		int secondNib = retVal % 16;
		int exp = 1 + logTableInt[firstNib][secondNib];
		// look up in exp table same way
		firstNib = exp >> 4;
		secondNib = exp % 16;
		retVal = expTableInt[firstNib][secondNib];
		return retVal;
	}

}
