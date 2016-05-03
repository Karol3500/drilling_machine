package pl.edu.pwr.aic.dmp;

import pl.edu.pwr.aic.dmp.alg.SACore;
import pl.edu.pwr.aic.dmp.helpers.file.parameters.SaParametersProcessingStrategy;

public class SaConsoleEntry extends AbstractConsoleEntry{

	public static void main(String[] args){
		AbstractConsoleEntry entry = new SaConsoleEntry();
		entry.init(new SACore(), new SaParametersProcessingStrategy(), args);

		entry.setup.performExperiment();
		System.out.println(entry.setup.getResults().get(0).getResults().get(0).getPermutation());
	}
}
