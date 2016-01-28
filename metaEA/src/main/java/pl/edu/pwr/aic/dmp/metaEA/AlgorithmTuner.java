package pl.edu.pwr.aic.dmp.metaEA;
import org.coinor.opents.BestEverAspirationCriteria;
import org.coinor.opents.MoveManager;
import org.coinor.opents.ObjectiveFunction;
import org.coinor.opents.SimpleTabuList;
import org.coinor.opents.SingleThreadedTabuSearch;
import org.coinor.opents.TabuList;
import org.coinor.opents.TabuSearch;

import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.mapUtils.CityReader;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.utils.Machine;
import pl.edu.pwr.aic.dmp.utils.MachineParameters;
import pl.edu.pwr.aic.dmp.utils.Parameters;

public class AlgorithmTuner {

	private static final int TABU_SEARCH_TENURE = 10;
	public static int maxSecondsPerTest = 120;
	private Core algorithm;
	private Parameters algParams;
	private ObjectiveFunction objFunc;
	private MoveManager moveManager;
	private TabuList tabuList;
	private TabuSearch tabuSearchEngine;
	private String mapFilePath;

	public AlgorithmTuner(Core algorithm, Parameters algParams, String mapFilePath, MoveManager moveManager){
		this.algorithm = algorithm;
		this.algParams = algParams;
		objFunc = new DmpObjectiveFunction();
		this.moveManager = moveManager;
		tabuList = new SimpleTabuList(TABU_SEARCH_TENURE);
		this.mapFilePath = mapFilePath;
	}

	public void performExperiment(int iterations){
		setupAlgorithm();
		setupTabuSearchEngine(iterations);
		tabuSearchEngine.startSolving();
	}

	public void printAllSolutions() {
		for(TuningExperimentResult s : SolutionsSingleton.getResultsList()){
			System.out.println(s);
		}
	}

	public void printBestFoundSolution() {
		DmpParametersSolution best = (DmpParametersSolution) tabuSearchEngine.getBestSolution();
		Parameters params = best.getParameters();
		double objFunVal = 0;
		double routeLength = 0;
		for(TuningExperimentResult res : SolutionsSingleton.getResultsList()){
			if(res.getParams().equals(params)){
				objFunVal = res.objectiveFunctionResult;
				routeLength = res.getRouteLength();
			}
		}
		System.out.println("\nBestSolution:\n" + best);
		System.out.println("\nObjective function value: " + objFunVal);
		System.out.println("\nRoute length: " + routeLength);
	}

	private void setupTabuSearchEngine(int iterations) {
		tabuSearchEngine = new SingleThreadedTabuSearch(
				new DmpParametersSolution(algorithm, algorithm.getAlgorithmParameters()),
				moveManager,
				objFunc,
				tabuList,
				new BestEverAspirationCriteria(), false);

		tabuSearchEngine.setIterationsToGo(iterations);
	}

	private void setupAlgorithm() {
		algorithm = configureAlgorithm(algorithm,
				algParams,
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
}
