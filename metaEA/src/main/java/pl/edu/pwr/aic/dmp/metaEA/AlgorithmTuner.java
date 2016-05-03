package pl.edu.pwr.aic.dmp.metaEA;
import java.util.List;

import org.coinor.opents.BestEverAspirationCriteria;
import org.coinor.opents.MoveManager;
import org.coinor.opents.ObjectiveFunction;
import org.coinor.opents.SimpleTabuList;
import org.coinor.opents.SingleThreadedTabuSearch;
import org.coinor.opents.TabuList;
import org.coinor.opents.TabuSearch;

import pl.edu.pwr.aic.dmp.alg.City;
import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.mapUtils.CityReader;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParametersSolution;

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
	private int drillChangeInterval;
	
	public AlgorithmTuner(Core algorithm, Parameters algParams, String mapFilePath,
			MoveManager moveManager, int drillChangeInterval){
		this.algorithm = algorithm;
		this.algParams = algParams;
		objFunc = new DmpObjectiveFunction();
		this.moveManager = moveManager;
		tabuList = new SimpleTabuList(TABU_SEARCH_TENURE);
		this.mapFilePath = mapFilePath;
		this.drillChangeInterval = drillChangeInterval;
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
		double routeLength = 0;
		for(TuningExperimentResult res : SolutionsSingleton.getResultsList()){
			if(res.getParams().equals(params)){
				routeLength = res.getRouteLength();
			}
		}
		System.out.println("\nBestSolution:\n" + best);
		System.out.println("\nObjective function value: " + best.getObjectiveValue()[0]);
		System.out.println("\nRoute length: " + routeLength);
	}

	public void printBestSolutionInTermsOfRouteLength(){
		ResultsManager m = new ResultsManager();
		System.out.println(m.getResultsSortedByExecutionTime().get(0));
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
		algorithm = configureAlgorithm(algorithm, algParams, drillChangeInterval);
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
		List<City> cities = cr.getMapClone();
		algorithm.setStartCity(cities.get(0));
		algorithm.setCities(cities.subList(1, cities.size()));
	}
}
