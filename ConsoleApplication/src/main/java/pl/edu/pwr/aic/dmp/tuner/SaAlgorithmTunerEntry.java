package pl.edu.pwr.aic.dmp.tuner;

import java.io.IOException;

import jxl.write.WriteException;
import pl.edu.pwr.aic.dmp.alg.SACore;
import pl.edu.pwr.aic.dmp.alg.utils.SaParameters;
import pl.edu.pwr.aic.dmp.metaEA.sa.move.SaMoveManager;

public class SaAlgorithmTunerEntry extends AbstractTuningApplicationEntry{
	private static final String DEFAULT_MAP_FILE_NAME = "src/main/resources/maps_working/a280.tsp";
	private static final String DEFAULT_RESULT_FILE_NAME = "SaA280";
	
	public static void main(String[] args) throws WriteException, IOException{
		runExperiment(args, DEFAULT_MAP_FILE_NAME, DEFAULT_RESULT_FILE_NAME, new SACore(),
				new SaParameters().setSaneDefaults(),new SaMoveManager());
	}
}
