package pl.edu.pwr.aic.dmp.metaEA.parameters;

import java.util.List;

import org.coinor.opents.SolutionAdapter;

import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.metaEA.TuningExperimentResult;
import pl.edu.pwr.aic.dmp.utils.Parameters;

public class DmpParametersSolution extends SolutionAdapter{

	private static final long serialVersionUID = 1L;

	List<TuningExperimentResult> results;
	Parameters parameters;
	Core algorithm;
	
	public Parameters getParameters() {
		return parameters;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

	public Core getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(Core algorithm) {
		this.algorithm = algorithm;
	}
	
	public DmpParametersSolution(Core alg, Parameters params){
		this.algorithm = alg;
		this.parameters = params;
	}
	
	public DmpParametersSolution(Core alg, List<TuningExperimentResult> results) {
		this.algorithm = alg;
		this.parameters = alg.getAlgorithmParameters();
		this.results = results;
	}
	
	public List<TuningExperimentResult> getExperimentResults(){
		return results;
	}

	@Override
	public Object clone(){
		DmpParametersSolution dmpParametersSolution = new DmpParametersSolution(algorithm, (Parameters)parameters.clone());
		dmpParametersSolution.setObjectiveValue(getClonedObjValOrNull());
		dmpParametersSolution.results = results;
		return dmpParametersSolution;
	}

	protected double[] getClonedObjValOrNull() {
		return this.getObjectiveValue() != null ? this.getObjectiveValue().clone() : null;
	}
	
	@Override
	public String toString(){
		return parameters.toString();
	}
}
