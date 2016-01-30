package pl.edu.pwr.aic.dmp.utils.excel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jxl.write.WriteException;

public class ExcelExporterUtil {
	
	protected static final String FILE_EXT = ".xls";

	public void createFile(String fileName) throws IOException, WriteException{
		Path path = Paths.get(fileName + FILE_EXT);
		if(!Files.exists(path)){
			Files.createFile(path);
		}
		else{
			int index = 1;
			Path increasedPath = increasePath(fileName, index);
			while(Files.exists(increasedPath)){
				index++;
				increasedPath = increasePath(fileName, index);
			}
			Files.createFile(increasedPath);
		}
//		WorkbookSettings settings = new WorkbookSettings();
//		settings.setLocale(new Locale("pl", "PL"));
//		WritableWorkbook workbook = Workbook.createWorkbook(file, settings);
//	    WritableSheet excelSheet = workbook.getSheet(0);
//	    createLabel(excelSheet);
//	    createContent(excelSheet);
//		workbook.write();
//		workbook.close();
	}

	private Path increasePath(String fileName, int index) {
		return Paths.get(fileName + "_" + index + FILE_EXT);
	}
}

