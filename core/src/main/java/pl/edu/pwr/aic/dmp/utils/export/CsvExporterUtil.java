package pl.edu.pwr.aic.dmp.utils.export;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jxl.write.WriteException;

public class CsvExporterUtil {
	
	public static final String CSV_DELIMITER = " | ";
	public static final String FILE_EXT = ".csv";

	public String createFile(String fileName) throws IOException, WriteException{
		Path path = Paths.get(fileName + FILE_EXT);
		if(!Files.exists(path)){
			Files.createFile(path);
			return path.toString();
		}
		else{
			int index = 1;
			Path increasedPath = increasePath(fileName, index);
			while(Files.exists(increasedPath)){
				index++;
				increasedPath = increasePath(fileName, index);
			}
			Files.createFile(increasedPath);
			return increasedPath.toString();
		}
	}

	private Path increasePath(String fileName, int index) {
		return Paths.get(fileName + "_" + index + FILE_EXT);
	}

	public boolean fileExists(String name) {
		return Files.exists(Paths.get(name));
	}

	public File openFile(String name) {
		return new File(name);
	}

	public void writeRow(List<Object> row, String fileName) throws IOException {
		String rowString = getStringRowRepresentation(row);
		Files.write(Paths.get(fileName),Arrays.asList(rowString), StandardOpenOption.APPEND);
	}

	private String getStringRowRepresentation(List<Object> row) {
		StringBuilder b = new StringBuilder();
		if(row == null || row.isEmpty()){
			return "";
		}
		for(Object o : row.subList(0, row.size()-1)){
			b.append(o);
			b.append(CSV_DELIMITER);
		}
		b.append(row.get(row.size()-1));
		return b.toString();
	}

	public void writeRows(List<List<Object>> rows, String fileName) throws IOException {
		List<String> rowStrings = getArrayRowsRepresentation(rows);
		Files.write(Paths.get(fileName), rowStrings, StandardOpenOption.APPEND);
	}

	private List<String> getArrayRowsRepresentation(List<List<Object>> rows) {
		List<String> rowStrings = new ArrayList<String>();
		for(List<Object> row : rows){
			rowStrings.add(getStringRowRepresentation(row));
		}
		return rowStrings;
	}
}

