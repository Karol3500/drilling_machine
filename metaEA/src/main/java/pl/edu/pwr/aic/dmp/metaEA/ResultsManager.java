package pl.edu.pwr.aic.dmp.metaEA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResultsManager {

	public List<TuningExperimentResult> getResultsSortedByRouteLength(){
		List<TuningExperimentResult> resultsCopy = getResCopy();
		Collections.sort(resultsCopy,new RouteLengthComparator());
		return resultsCopy;
	}
	
	public List<TuningExperimentResult> getResultsSortedByExecutionTime(){
		List<TuningExperimentResult> resultsCopy = getResCopy();
		Collections.sort(resultsCopy,new ExecutionTimeComparator());
		return resultsCopy;
	}
	
	public List<TuningExperimentResult> getResultsSortedByObjectiveFunction(){
		List<TuningExperimentResult> resultsCopy = getResCopy();
		Collections.sort(resultsCopy,new ObjectiveFunctionComparator());
		return resultsCopy;
	}
	
	private List<TuningExperimentResult> getResCopy(){
		return new ArrayList<>(SolutionsSingleton.getResultsList());
	}
	
	private class ObjectiveFunctionComparator implements Comparator<TuningExperimentResult>{

		@Override
		public int compare(TuningExperimentResult o1, TuningExperimentResult o2) {
			if(o1.getObjectiveFunctionResult() > o2.getObjectiveFunctionResult()){
				return 1;
			}
			if(o1.getObjectiveFunctionResult() < o2.getObjectiveFunctionResult()){
				return -1;
			}
			return 0;
		}
		
	}
	
	private class ExecutionTimeComparator implements Comparator<TuningExperimentResult>{

		@Override
		public int compare(TuningExperimentResult o1, TuningExperimentResult o2) {
			if(o1.getExecTime() > o2.getExecTime()){
				return 1;
			}
			if(o1.getExecTime() < o2.getExecTime()){
				return -1;
			}
			return 0;
		}
		
	}
	
	private class RouteLengthComparator implements Comparator<TuningExperimentResult>{

		@Override
		public int compare(TuningExperimentResult o1, TuningExperimentResult o2) {
			if(o1.getRouteLength() > o2.getRouteLength()){
				return 1;
			}
			if(o1.getRouteLength() < o2.getRouteLength()){
				return -1;
			}
			return 0;
		}
		
	}
	
}
