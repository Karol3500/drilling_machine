package pl.edu.pwr.aic.dmp.utils;

import java.util.ArrayList;
import java.util.List;

public class UnitResult {
	private double executionTimeInSeconds;
	private double bestRouteLength;
	List<Integer> bestRoute;
	
	public UnitResult(){
		bestRoute = new ArrayList<Integer>();
	}

	public double getExecutionTimeInSeconds() {
		return executionTimeInSeconds;
	}

	public void setExecutionTimeInSeconds(double executionTimeInSeconds) {
		this.executionTimeInSeconds = executionTimeInSeconds;
	}

	public double getBestRouteLength() {
		return bestRouteLength;
	}

	public void setBestRouteLength(double d) {
		this.bestRouteLength = d;
	}

	public List<Integer> getBestRoute() {
		return bestRoute;
	}

	public void setBestRoute(List<Integer> permutation) {
		this.bestRoute = permutation;
	}
}
