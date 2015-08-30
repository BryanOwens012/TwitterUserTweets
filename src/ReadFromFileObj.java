// Written by Bryan Owens, 8/15/2015

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReadFromFileObj {

	/*
	 * Sample usage: ReadFromFileObj x = new ReadFromFileObj("input.txt"); while
	 * (!x.isEmpty()) { System.out.println(x.readLine()); } x.close();
	 */

	private String inputFile;
	private BufferedReader br;
	private String delimiter;

	private ArrayList<String> arr;
	private String sCurrentLine;

	public ReadFromFileObj(String inputFile, String delimiter) {
		// Reads line by line. For each line, this returns an array of the
		// parsed data
		this.inputFile = inputFile;
		try {
			br = new BufferedReader(new FileReader(inputFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ReadFromFileObj(String inputFile) {
		// Reads line by line. For each line, this returns an array of the
		// parsed data
		this.inputFile = inputFile;
		this.delimiter = " ";
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					inputFile), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String[] readDelimitedLine() {
		try {
			sCurrentLine = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] sCurrentWordsArr = sCurrentLine.split(delimiter);
		return sCurrentWordsArr;
	}

	public String readLine() {
		try {
			sCurrentLine = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sCurrentLine;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public boolean isEmpty() {
		try {
			return !br.ready();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void close() {
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
