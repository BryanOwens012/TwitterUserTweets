// Written by Bryan Owens, 8/15/2015

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFileObj {

	/*
	 * Sample usage: WriteToFileObj x = new WriteToFileObj("output.txt");
	 * x.write("Hello world!"); x.close();
	 */

	private String outputFile;
	private File file;
	private FileWriter fw;
	private BufferedWriter bw;
	private String lastOutput; // optional but possibly useful

	public WriteToFileObj(String outputFile) {
		this.outputFile = outputFile;
		try {
			file = new File(outputFile);
			if (!file.exists()) {
				file.createNewFile();
			}
			fw = new FileWriter(file.getAbsoluteFile(), true); // append
			// fw = new FileWriter(file.getAbsoluteFile()); // overwrite instead
			// of append
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(String outputContent) {
		try {
			bw.write(outputContent);
			lastOutput = outputContent;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getOutputFile() {
		return outputFile;
	}

	public String getLastOutput() {
		return lastOutput;
	}
}