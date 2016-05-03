package pl.edu.pwr.aic.dmp;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.helpers.FileParsingStrategy;
import pl.edu.pwr.aic.dmp.utils.ExperimentSetup;

public abstract class AbstractConsoleEntry {

	protected ExperimentSetup setup;

	public AbstractConsoleEntry() {
		super();
	}

	public void init(Core algorithm, FileParsingStrategy parsingStrategy, String args[]) {
		if(args.length == 0){
			printHelp();
		}
		setup = new ExperimentSetup();
		List<Parameters> rawParams = getParametersListsFromFile(args[1], parsingStrategy);
		setup.setupExperimentEnvironment(args[0],algorithm,rawParams,
				Integer.valueOf(args[3]), Integer.valueOf(args[2]));
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
				"path to map file, eg: src/main/resources/maps_working/a280.tsp\n" +
				"path to algorithm parameters file, eg: src/main/resources/ga_params_example\n" + 						
				"drill change interval, eg: 20\n" +
				"numberOfUnitExperimentRepetitions, eg: 4");
	}

}