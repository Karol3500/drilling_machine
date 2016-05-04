package pl.edu.pwr.aic.dmp;

import pl.edu.pwr.aic.dmp.alg.SACore;
import pl.edu.pwr.aic.dmp.helpers.file.parameters.SaParametersProcessingStrategy;

public class SaConsoleEntry extends ConsoleEntry{

	public static void main(String[] args){
		ConsoleEntry entry = new SaConsoleEntry();
		if(!entry.init(new SACore(), new SaParametersProcessingStrategy(), args)){
			return;
		}

		entry.setup.performExperiment();
		entry.exportResultsToFile();
	}
}
