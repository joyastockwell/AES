import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ParseInfo {
	
	String fileName;
	String keyStr;
	String ptextStr;
	int iters;
	int rounds;
	
	public ParseInfo(String fileName){
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(
					fileName));
			String line = reader.readLine();
			//set number iterations
			if (line != null) {
				this.iters = Integer.parseInt(line);
				// read next line
				line = reader.readLine();
			} else
				System.out.println("I/O error: not enough lines in file");
			//set number rounds
			if (line != null) {
				this.rounds = Integer.parseInt(line);
				// read next line
				line = reader.readLine();
			} else
				System.out.println("I/O error: not enough lines in file");
			//set the key string
			if (line != null) {
				this.keyStr = line;
				// read next line
				line = reader.readLine();
			} else
				System.out.println("I/O error: not enough lines in file");
			//set the ptext string
			if (line != null) {
				this.ptextStr = line;
			} else
				System.out.println("I/O error: not enough lines in file");
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
