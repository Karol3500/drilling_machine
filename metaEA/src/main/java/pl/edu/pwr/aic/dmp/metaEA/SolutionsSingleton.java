package pl.edu.pwr.aic.dmp.metaEA;

import java.util.ArrayList;
import java.util.List;

public class SolutionsSingleton {

	public final static SolutionsSingleton INSTANCE = new SolutionsSingleton();

	private static final List<TuningExperimentResult> RESULTS = new ArrayList<TuningExperimentResult>();
	
	private SolutionsSingleton() {
    }
	
	public static List<TuningExperimentResult> getResultsList(){
		return RESULTS;
	}
	
	public static void addResult(TuningExperimentResult result){
		RESULTS.add(result);
	}
}
