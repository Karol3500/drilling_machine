package pl.edu.pwr.aic.dmp;

import pl.edu.pwr.aic.dmp.alg.GACore;
import pl.edu.pwr.aic.dmp.helpers.file.parameters.GaParametersProcessingStrategy;

public class GaConsoleEntry extends ConsoleEntry {

	public static void main(String[] args) throws InterruptedException{
//		args= new String[]{"src/main/resources/maps_working/pr107.tsp","src/test/resources/gaParameters", "20", "1"};
		ConsoleEntry entry = new GaConsoleEntry();
		if(!entry.init(new GACore(), new GaParametersProcessingStrategy(), args)){
			return;
		}
		entry.setup.performExperiment();
		entry.exportResultsToFile();
	}
}
