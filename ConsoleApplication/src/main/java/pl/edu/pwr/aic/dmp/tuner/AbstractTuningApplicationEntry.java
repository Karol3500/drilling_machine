package pl.edu.pwr.aic.dmp.tuner;

import java.io.IOException;

import org.coinor.opents.MoveManager;

import jxl.write.WriteException;
import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.helpers.CommonApplicationEntryHelper;
import pl.edu.pwr.aic.dmp.metaEA.AlgorithmTuner;

public abstract class AbstractTuningApplicationEntry {

	private static final int TABU_ITERATIONS_NUMBER = 10;
	private static CommonApplicationEntryHelper helper;
	private static AlgorithmTuner tuner;
	
	protected static void runExperiment(String[] args, String defaultMapFileName, String defaultResultFileName, 
			Core alg, Parameters algParams, MoveManager mm, int drillChangeInterval)
			throws WriteException, IOException {
		helper = new CommonApplicationEntryHelper();
		helper.initMapAndSolutionsSignleton(args, defaultMapFileName, defaultResultFileName);
		tuner = initTuner(alg, algParams, mm, drillChangeInterval);
		tuner.performExperiment(TABU_ITERATIONS_NUMBER);
	}
	
	private static AlgorithmTuner initTuner(Core alg, Parameters parameters, MoveManager mm, int drillChangeInterval) {
		return new AlgorithmTuner(alg,parameters,helper.getMap(),mm, drillChangeInterval);
	}
}
