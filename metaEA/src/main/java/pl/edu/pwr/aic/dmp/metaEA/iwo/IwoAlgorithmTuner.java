package pl.edu.pwr.aic.dmp.metaEA.iwo;

import org.coinor.opents.MoveManager;

import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.alg.IwoCore;
import pl.edu.pwr.aic.dmp.metaEA.AlgorithmTuner;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.IwoMoveManager;
import pl.edu.pwr.aic.dmp.utils.IwoParameters;
import pl.edu.pwr.aic.dmp.utils.Parameters;

public class IwoAlgorithmTuner extends AlgorithmTuner {

	public IwoAlgorithmTuner(Core algorithm, Parameters algParams, String mapFilePath, MoveManager moveManager) {
		super(algorithm, algParams, mapFilePath, moveManager);
	}

	public static void main(String[] args){
		String map = "src/main/resources/maps_for_research/mapa101.tsp";
		
		AlgorithmTuner tuner = new AlgorithmTuner(new IwoCore(), new IwoParameters().setSaneDefaults(), map, new IwoMoveManager());
		tuner.performExperiment(1);
		tuner.printAllSolutions();
		//tuner.printBestFoundSolution();
	}
}
