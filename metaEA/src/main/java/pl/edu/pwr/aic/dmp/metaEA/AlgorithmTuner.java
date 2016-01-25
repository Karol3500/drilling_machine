package pl.edu.pwr.aic.dmp.metaEA;
import java.util.ArrayList;
import java.util.List;

import org.coinor.opents.BestEverAspirationCriteria;
import org.coinor.opents.MoveManager;
import org.coinor.opents.ObjectiveFunction;
import org.coinor.opents.SimpleTabuList;
import org.coinor.opents.SingleThreadedTabuSearch;
import org.coinor.opents.Solution;
import org.coinor.opents.TabuList;
import org.coinor.opents.TabuSearch;

import pl.edu.pwr.aic.dmp.alg.IwoCore;
import pl.edu.pwr.aic.dmp.mapUtils.CityReader;
import pl.edu.pwr.aic.dmp.metaEA.Parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.IwoMoveManager;
import pl.edu.pwr.aic.dmp.utils.ExperimentResult;
import pl.edu.pwr.aic.dmp.utils.IwoParameters;
import pl.edu.pwr.aic.dmp.utils.Machine;
import pl.edu.pwr.aic.dmp.utils.MachineParameters;

public class AlgorithmTuner {
	
	public static int maxSecondsPerTest = 120;
	
	public static void main(String[] args){
		IwoCore iwo = prepareIwoAlgorithm();
		List<TuningExperimentResult> results = new ArrayList<TuningExperimentResult> ();
		Solution solution =  new DmpParametersSolution(iwo, results);
		ObjectiveFunction objFunc = new DmpObjectiveFunction();
		Solution initialSolution  = (Solution)solution.clone();
		MoveManager moveManager = new IwoMoveManager();
		TabuList tabuList = new SimpleTabuList(10);
		TabuSearch tabuSearch = new SingleThreadedTabuSearch(
		initialSolution,
		moveManager,
		objFunc,
		tabuList,
		new BestEverAspirationCriteria(), false);
		
		tabuSearch.setIterationsToGo(10);
		tabuSearch.startSolving();
		
		DmpParametersSolution best = (DmpParametersSolution) tabuSearch.getBestSolution();
		System.out.println("BestSolution:\n" + best);
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
		for(TuningExperimentResult res : results){
			System.out.println(res);
		}
		
	}

	private static IwoCore prepareIwoAlgorithm() {
		IwoCore iwo = new IwoCore();
		iwo.setAlgorithmParameters(new IwoParameters().setSaneDefaults());
		iwo.setDrillChangeInterval(new Machine((MachineParameters)new MachineParameters().setSaneDefaults()).getDrillChangeInterval());
		loadAndSetMap(iwo, "src/main/resources/maps_for_research/mapa101.tsp");
		return iwo;
	}

	private static void loadAndSetMap(IwoCore iwo, String filePath) {
		CityReader cr = new CityReader(); 
		cr.loadFile(filePath);
		iwo.setCities(cr.getMapClone());
	}
}
