package pl.edu.pwr.aic.dmp.utils;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.alg.utils.Parameters;

public class ExperimentResult {

	List<UnitResult> results;
	Parameters params;
	Class<? extends Core> algorithmClass;
	String mapName;
	
	public ExperimentResult(Parameters params, String mapName, Class<? extends Core> clazz){
		results = new ArrayList<UnitResult>();
		this.params = params;
		this.algorithmClass = clazz;
		this.mapName = mapName;
	}

	public List<UnitResult> getResults() {
		return results;
	}
	
	public void setResults(List<UnitResult> results){
		this.results = results;
	}
	
	public Parameters getParams() {
		return params;
	}

	public void setParams(Parameters params) {
		this.params = params;
	}

	public Class<? extends Core> getAlgorithmClass() {
		return algorithmClass;
	}

	public void setAlgorithmClass(Class<Core> algorithmClass) {
		this.algorithmClass = algorithmClass;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	public UnitResult getAveragedUnitResult(){
		List<Integer> bestPermutation = null;
		double totalExecutionTimeSum = 0d;
		double totalRouteLengthSum = 0d;
		double bestRouteLength = Double.MAX_VALUE;
		for(UnitResult u : results){
			totalExecutionTimeSum += u.getExecutionTimeInSeconds();
			totalRouteLengthSum += u.getBestRouteLength();
			if(u.getBestRouteLength() < bestRouteLength){
				bestRouteLength = u.getBestRouteLength();
				bestPermutation = u.getBestRoute();
			}
		}
		UnitResult res = new UnitResult();
		res.setBestRouteLength(totalRouteLengthSum/results.size());
		res.setExecutionTimeInSeconds(totalExecutionTimeSum/results.size());
		res.setBestRoute(bestPermutation);
		return res;
	}
}
