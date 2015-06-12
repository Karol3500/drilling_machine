package pl.edu.pwr.aic.dmp.alg.utils;

import java.util.ArrayList;
import java.util.List;

public class UnitResult {
	private double executionTimeInSeconds;
	private long bestRouteLength;
	List<Integer> bestPermutation;
	
	public UnitResult(){
		bestPermutation = new ArrayList<Integer>();
	}

	public double getExecutionTimeInSeconds() {
		return executionTimeInSeconds;
	}

	public void setExecutionTimeInSeconds(double executionTimeInSeconds) {
		this.executionTimeInSeconds = executionTimeInSeconds;
	}

	public long getBestSolutionLength() {
		return bestRouteLength;
	}

	public void setBestSolutionLength(long bestSolutionLength) {
		this.bestRouteLength = bestSolutionLength;
	}

	public List<Integer> getPermutation() {
		return bestPermutation;
	}

	public void setPermutation(List<Integer> permutation) {
		this.bestPermutation = permutation;
	}
}
