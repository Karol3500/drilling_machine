package pl.edu.pwr.aic.dmp;

import pl.edu.pwr.aic.dmp.alg.GACore;
import pl.edu.pwr.aic.dmp.helpers.file.parameters.GaParametersProcessingStrategy;

public class GaConsoleEntry extends AbstractConsoleEntry {

	public static void main(String[] args) throws InterruptedException{
		AbstractConsoleEntry entry = new GaConsoleEntry();
		entry.init(new GACore(), new GaParametersProcessingStrategy(), args);

		entry.setup.performExperiment();
		System.out.println(entry.setup.getResults().get(0).getResults().get(0).getPermutation());
	}
}
