package pl.edu.pwr.aic.dmp.helpers;

import java.io.IOException;

import jxl.write.WriteException;
import pl.edu.pwr.aic.dmp.metaEA.SolutionsSingleton;
import pl.edu.pwr.aic.dmp.metaEA.export.CsvTuningExperimentResultExporter;

public class CommonApplicationEntryHelper {
	private String map;
	private String fileName;
	
	private static String getFileName(String[] args, String defaultResultFileName) {
		return args.length==2 ? args[1] : defaultResultFileName;
	}

	private static String getMap(String[] args, String defaultResultFileName) {
		return args.length >0 ? args[0] : defaultResultFileName;
	}

	public void initMapAndSolutionsSignleton(String[] args, String degaultMapFileName, String defaultResultFileName) throws WriteException, IOException {
		map = getMap(args, degaultMapFileName);
		fileName = getFileName(args, defaultResultFileName);
		SolutionsSingleton.setResultExporter(new CsvTuningExperimentResultExporter(fileName));
	}

	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMap() {
		return map;
	}
	public void setMap(String map) {
		this.map = map;
	}
}
