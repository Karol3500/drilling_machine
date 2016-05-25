package pl.edu.pwr.aic.dmp;

import pl.edu.pwr.aic.dmp.alg.RandomCore;
import pl.edu.pwr.aic.dmp.helpers.file.parameters.RandomParametersProcessingStrategy;

public class RandomConsoleEntry extends ConsoleEntry{

	public static void main(String[] args){
//		args= new String[]{"src/main/resources/maps_working/a280.tsp","src/test/resources/randomParameters", "20", "1"};
		ConsoleEntry entry = new RandomConsoleEntry();
		if(!entry.init(new RandomCore(), new RandomParametersProcessingStrategy(), args)){
			return;
		}
		entry.setup.performExperiment();
		entry.exportResultsToFile();
	}
}
