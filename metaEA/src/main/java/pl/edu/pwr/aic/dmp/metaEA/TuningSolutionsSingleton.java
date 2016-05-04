package pl.edu.pwr.aic.dmp.metaEA;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.aic.dmp.metaEA.export.CsvTuningExperimentResultExporter;

public class TuningSolutionsSingleton {

	private static final List<TuningExperimentResult> RESULTS = new ArrayList<TuningExperimentResult>();
	public static CsvTuningExperimentResultExporter exporter;
	
	private TuningSolutionsSingleton() {
    }
	
	public static void setResultExporter(CsvTuningExperimentResultExporter resExporter){
		exporter = resExporter;
	}
	
	public static List<TuningExperimentResult> getResultsList(){
		return RESULTS;
	}
	
	public static void addResult(TuningExperimentResult result){
		RESULTS.add(result);
		try {
			exporter.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
