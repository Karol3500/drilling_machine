package pl.edu.pwr.aic.dmp.metaEA;
import java.util.ArrayList;
import java.util.List;

import org.coinor.opents.BestEverAspirationCriteria;
import org.coinor.opents.MoveManager;
import org.coinor.opents.ObjectiveFunction;
import org.coinor.opents.SimpleTabuList;
import org.coinor.opents.SingleThreadedTabuSearch;
import org.coinor.opents.TabuList;
import org.coinor.opents.TabuSearch;

import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.mapUtils.CityReader;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.IwoMoveManager;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.utils.Machine;
import pl.edu.pwr.aic.dmp.utils.MachineParameters;
import pl.edu.pwr.aic.dmp.utils.Parameters;

public class AlgorithmTuner {

	private static final int TABU_SEARCH_TENURE = 10;
	public static int maxSecondsPerTest = 120;
	private Core algorithm;
	private Parameters algParams;
	private List<TuningExperimentResult> experimentResults;
	private ObjectiveFunction objFunc;
	private MoveManager moveManager;
	private TabuList tabuList;
	private TabuSearch tabuSearchEngine;
	private String mapFilePath;

	public AlgorithmTuner(Core algorithm, Parameters algParams, String mapFilePath){
		this.algorithm = algorithm;
		this.algParams = algParams;
		experimentResults = new ArrayList<TuningExperimentResult>();
		objFunc = new DmpObjectiveFunction();
		moveManager = new IwoMoveManager();
		tabuList = new SimpleTabuList(TABU_SEARCH_TENURE);
		this.mapFilePath = mapFilePath;
	}

	public void performExperiment(int iterations){
		setupAlgorithm();
		setupTabuSearchEngine(iterations);
		tabuSearchEngine.startSolving();
	}

	public void printAllSolutions() {
		for(TuningExperimentResult res : experimentResults){
			System.out.println(res);
		}
	}

	public void PrintBestFoundSolution() {
		DmpParametersSolution best = (DmpParametersSolution) tabuSearchEngine.getBestSolution();
		System.out.println("BestSolution:\n" + best);
	}

	private void setupTabuSearchEngine(int iterations) {
		tabuSearchEngine = new SingleThreadedTabuSearch(
				new DmpParametersSolution(algorithm, experimentResults),
				moveManager,
				objFunc,
				tabuList,
				new BestEverAspirationCriteria(), false);

		tabuSearchEngine.setIterationsToGo(iterations);
	}

	private void setupAlgorithm() {
		algorithm = configureAlgorithm(algorithm,
				algParams.setSaneDefaults(),
				getDefaultMachine().getDrillChangeInterval());
	}

	private Machine getDefaultMachine() {
		return new Machine((MachineParameters)new MachineParameters().setSaneDefaults());
	}

	private Core configureAlgorithm(Core algorithm, Parameters algParams, int drillChangeInterval) {
		algorithm.setAlgorithmParameters(algParams);
		algorithm.setDrillChangeInterval(drillChangeInterval);
		loadAndSetMap(algorithm, mapFilePath);
		return algorithm;
	}

	private void loadAndSetMap(Core algorithm, String filePath) {
		CityReader cr = new CityReader(); 
		cr.loadFile(filePath);
		algorithm.setCities(cr.getMapClone());
	}

	public List<TuningExperimentResult> getExperimentResults() {
		return experimentResults;
	}

	public void setExperimentResults(List<TuningExperimentResult> experimentResults) {
		this.experimentResults = experimentResults;
	}
}
