import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class ReadNumLinesObj {

	String inputFile;
	LineNumberReader lnr;
	int numLines;

	public ReadNumLinesObj(String inputFile) throws IOException {
		this.inputFile = inputFile;
		lnr = new LineNumberReader(new FileReader(new File(inputFile)));
		lnr.skip(Long.MAX_VALUE);
		numLines = lnr.getLineNumber() + 1; // Add 1 because line index starts
											// at 0
		// Finally, the LineNumberReader object should be closed to prevent
		// resource leak
		lnr.close();
	}

	public int getNumLines() {
		return numLines;
	}
}
