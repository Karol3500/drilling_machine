package pl.edu.pwr.aic.dmp.alg.utils;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.utils.Parameters;

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
		double executionTime = 0d;
		double bestRouteLength = 0d;
		double bestRouteLengthSoFar = Double.MAX_VALUE;
		for(UnitResult u : results){
			executionTime += u.getExecutionTimeInSeconds();
			bestRouteLength += u.getBestRouteLength();
			if(u.getBestRouteLength() < bestRouteLengthSoFar){
				bestRouteLengthSoFar = u.getBestRouteLength();
				bestPermutation = u.getPermutation();
			}
		}
		UnitResult res = new UnitResult();
		res.setBestRouteLength(bestRouteLength/results.size());
		res.setExecutionTimeInSeconds(executionTime/results.size());
		res.setPermutation(bestPermutation);
		return res;
	}
}
