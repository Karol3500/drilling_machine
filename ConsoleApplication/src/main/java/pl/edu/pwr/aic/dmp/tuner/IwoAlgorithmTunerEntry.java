package pl.edu.pwr.aic.dmp.tuner;

import java.io.IOException;

import jxl.write.WriteException;
import pl.edu.pwr.aic.dmp.alg.IwoCore;
import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.IwoMoveManager;

public class IwoAlgorithmTunerEntry extends AbstractTuningApplicationEntry{
	private static final String DEFAULT_MAP_FILE_NAME = "src/main/resources/maps_working/pr1002.tsp";
	private static final String DEFAULT_RESULT_FILE_NAME = "IwoPR1002";
	
	public static void main(String[] args) throws WriteException, IOException{
		runExperiment(args, DEFAULT_MAP_FILE_NAME, DEFAULT_RESULT_FILE_NAME, new IwoCore(),
				new IwoParameters().setSaneDefaults(),new IwoMoveManager(), 20);
	}
}
