package pl.edu.pwr.aic.dmp.metaEA.ga;

import org.coinor.opents.MoveManager;

import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.alg.GACore;
import pl.edu.pwr.aic.dmp.alg.utils.GaParameters;
import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.metaEA.AlgorithmTuner;
import pl.edu.pwr.aic.dmp.metaEA.ga.move.GaMoveManager;

public class GaAlgorithmTuner extends AlgorithmTuner {

	public GaAlgorithmTuner(Core algorithm, Parameters algParams, String mapFilePath, MoveManager moveManager) {
		super(algorithm, algParams, mapFilePath, moveManager);
	}

	public static void main(String[] args){
		String map = "src/main/resources/maps_for_research/mapa101.tsp";
		
		AlgorithmTuner tuner = new AlgorithmTuner(new GACore(), 
				new GaParameters().setSaneDefaults(), map, new GaMoveManager());
		tuner.performExperiment(1);
		//tuner.printAllSolutions();
		tuner.printBestFoundSolution();
	}
}
