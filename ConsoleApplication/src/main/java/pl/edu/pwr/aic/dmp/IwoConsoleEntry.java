package pl.edu.pwr.aic.dmp;

import pl.edu.pwr.aic.dmp.alg.IwoCore;
import pl.edu.pwr.aic.dmp.helpers.file.parameters.IwoParametersProcessingStrategy;

public class IwoConsoleEntry extends ConsoleEntry{
	public static void main(String[] args){
		ConsoleEntry entry = new IwoConsoleEntry();
		if(!entry.init(new IwoCore(), new IwoParametersProcessingStrategy(), args)){
			return;
		}

		entry.setup.performExperiment();
		entry.exportResultsToFile();
	}
}
