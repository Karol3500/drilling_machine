package pl.edu.pwr.aic.dmp.metaEA.iwo;

import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.alg.IwoCore;
import pl.edu.pwr.aic.dmp.metaEA.AlgorithmTuner;
import pl.edu.pwr.aic.dmp.utils.IwoParameters;
import pl.edu.pwr.aic.dmp.utils.Parameters;

public class IwoAlgorithmTuner extends AlgorithmTuner {

	public IwoAlgorithmTuner(Core algorithm, Parameters algParams, String mapFilePath) {
		super(algorithm, algParams, mapFilePath);
	}

	public static void main(String[] args){
		String map = "src/main/resources/maps_for_research/mapa101.tsp";
		
		AlgorithmTuner tuner = new AlgorithmTuner(new IwoCore(), new IwoParameters(), map);
		tuner.performExperiment(1);
		
		tuner.PrintBestFoundSolution();
	}
}
