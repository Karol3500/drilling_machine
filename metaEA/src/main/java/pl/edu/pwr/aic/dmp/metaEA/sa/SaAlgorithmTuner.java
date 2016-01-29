package pl.edu.pwr.aic.dmp.metaEA.sa;

import org.coinor.opents.MoveManager;

import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.alg.SACore;
import pl.edu.pwr.aic.dmp.metaEA.AlgorithmTuner;
import pl.edu.pwr.aic.dmp.metaEA.sa.move.random.SaRandomMoveManager;
import pl.edu.pwr.aic.dmp.metaEA.sa.move.small.SaSlightMoveManager;
import pl.edu.pwr.aic.dmp.utils.Parameters;
import pl.edu.pwr.aic.dmp.utils.SaParameters;

public class SaAlgorithmTuner extends AlgorithmTuner {

	public SaAlgorithmTuner(Core algorithm, Parameters algParams, String mapFilePath, MoveManager moveManager) {
		super(algorithm, algParams, mapFilePath, moveManager);
	}

	public static void main(String[] args){
		String map = "src/main/resources/maps_for_research/mapa101.tsp";
		
//		AlgorithmTuner tuner = new AlgorithmTuner(new SACore(), 
//				new SaParameters().setSaneDefaults(), map, new SaRandomMoveManager());
		
		AlgorithmTuner tuner = new AlgorithmTuner(new SACore(), 
		new SaParameters().setSaneDefaults(), map, new SaSlightMoveManager());
		tuner.performExperiment(10);
		//tuner.printAllSolutions();
		//tuner.printBestFoundSolution();
		tuner.printBestSolutionInTermsOfRouteLength();
	}
}
