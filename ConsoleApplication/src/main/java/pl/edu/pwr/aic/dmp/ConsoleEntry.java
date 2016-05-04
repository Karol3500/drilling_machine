package pl.edu.pwr.aic.dmp;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.helpers.FileParsingStrategy;
import pl.edu.pwr.aic.dmp.utils.ExperimentResult;
import pl.edu.pwr.aic.dmp.utils.ExperimentSetup;
import pl.edu.pwr.aic.dmp.utils.export.CsvUnitSolutionExporter;

public abstract class ConsoleEntry {

	protected ExperimentSetup setup;

	public ConsoleEntry() {
		super();
	}

	public boolean init(Core algorithm, FileParsingStrategy parsingStrategy, String args[]) {
		if(args.length == 0){
			printHelp();
			return false;
		}
		setup = new ExperimentSetup();
		List<Parameters> rawParams = getParametersListsFromFile(args[1], parsingStrategy);
		setup.setupExperimentEnvironment(args[0],algorithm,rawParams,
				Integer.valueOf(args[3]), Integer.valueOf(args[2]));
		return true;
	}

	public List<Parameters> getParametersListsFromFile(String filePath, FileParsingStrategy parsingStrategy){
		List<String> allLines = new ArrayList<>();
		try(FileReader fr = new FileReader(filePath); Scanner s = new Scanner(fr)){
			s.useDelimiter("\\n");
			while(s.hasNext()){
				allLines.add(s.next());
			}
			return parsingStrategy.parseLines(allLines);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public void printHelp() {
		System.out.println("Expected parameters:\n" + 
				"path to map file, eg: resources/maps_working/a280.tsp\n" +
				"path to algorithm parameters file, eg: resources/ga_params\n" + 						
				"drill change interval, eg: 20\n" +
				"numberOfUnitExperimentRepetitions, eg: 4");
	}
	
	public void exportResultsToFile(){
		CsvUnitSolutionExporter exporter = new CsvUnitSolutionExporter();
		try {
			List<ExperimentResult> experimentResults = setup.getResults();
			for(int i=0; i < experimentResults.size(); i++){
				ExperimentResult res = experimentResults.get(i);
				exporter.createNewFile(setup.getAlgorithm().getAlgorithmName() + "_" + res.getMapName());
				writeParameters(exporter, res.getParams());
				exporter.writeUnitResults(res.getResults());
				exporter.writeEmptyRow();
				exporter.writeRow(Arrays.asList("Averaged result:"));
				exporter.writeUnitResult(res.getAveragedUnitResult());
			}
		} catch (Exception e) {
		}
	}

	private void writeParameters(CsvUnitSolutionExporter exporter, Parameters params) {
		try {
			exporter.writeRow(Arrays.asList("Algorithm parameters:"));
			List<?> paramNames = params.getParameterNamesAsList();
			List<?> paramValues = params.getParameterValuesAsList();
			for(int i=0; i < paramNames.size(); i++){
				exporter.writeRow(Arrays.asList(paramNames.get(i), paramValues.get(i)));
			}
			exporter.writeEmptyRow();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}