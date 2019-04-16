import javax.xml.bind.DatatypeConverter;

public class Main {

	public static void main(String[] args) {
		ParseInfo pf = new ParseInfo(args[0]);
		decrypt(pf);
	}

	private static void decrypt(ParseInfo pf){
		// set up the keys
		String keyStr = pf.keyStr;//"2b28ab097eaef7cf15d2154f16a6883c";
		int[][] rkeyMatrix = new int[4][44];
		SBox sBox = new SBox(false);
		KeyScheduler ks = new KeyScheduler(keyStr, rkeyMatrix, sBox, false);
		ks.setKeyMatrixBig(false);
		// start encryption
		String ptextStr = pf.ptextStr;//"328831e0435a3137f6309807a88da234";
		int iters = pf.iters;//10000;
		int rounds = pf.rounds;//10;
		OverallCrypter oc = new OverallCrypter(ptextStr, rkeyMatrix, sBox, iters, rounds, false);
		oc.runAllRounds();
		// print the text matrix as a single line
		oc.printTextMatrixSingleLine();
	}

}