package pl.edu.pwr.aic.dmp.metaEA;

import java.io.IOException;

import jxl.write.WriteException;
import pl.edu.pwr.aic.dmp.alg.GACore;
import pl.edu.pwr.aic.dmp.alg.utils.GaParameters;
import pl.edu.pwr.aic.dmp.metaEA.ga.move.GaMoveManager;

public class GaAlgorithmTunerEntry extends TuningApplicationEntry{
	private static final String DEFAULT_MAP_FILE_NAME = "src/main/resources/maps_working/pr1002.tsp";
	private static final String DEFAULT_RESULT_FILE_NAME = "GaPR1002";
	
	public static void main(String[] args) throws WriteException, IOException{
		runExperiment(args, DEFAULT_MAP_FILE_NAME, DEFAULT_RESULT_FILE_NAME, new GACore(),
				new GaParameters().setSaneDefaults(),new GaMoveManager());
	}
}
