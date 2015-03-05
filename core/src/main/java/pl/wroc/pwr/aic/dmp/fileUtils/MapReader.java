package pl.wroc.pwr.aic.dmp.fileUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MapReader {
	
	public Scanner readFileAsScanner(String path){
		FileReader fileReader = openFile(path);
		return fileReader != null ? new Scanner(fileReader) : null;
	}

	private FileReader openFile(String path) {
		try {
			return new FileReader(path);
		} catch (FileNotFoundException e) {
			return null;
		}
	}
}
