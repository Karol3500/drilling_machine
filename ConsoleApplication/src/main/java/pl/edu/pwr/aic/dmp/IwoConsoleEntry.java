package pl.edu.pwr.aic.dmp;

import pl.edu.pwr.aic.dmp.alg.IwoCore;

public class IwoConsoleEntry extends AbstractConsoleEntry{
		public static void main(String[] args){
			AbstractConsoleEntry entry = new IwoConsoleEntry();
			entry.init(new IwoCore(), new IwoParametersProcessingStrategy(), args);

			entry.setup.performExperiment();
			System.out.println(entry.setup.getResults().get(0).getResults().get(0).getPermutation());
	}
}
